package index;

import clones.CloneSet;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.tools.javac.api.JavacTaskImpl;
import com.sun.tools.javac.api.JavacTool;
import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.main.JavaCompiler;
import com.sun.tools.javac.main.Option;
import com.sun.tools.javac.tree.JCTree.JCCompilationUnit;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Options;
import compressed.FullCompressedIndexScanner;
import compressed.SimplifiedCompressedIndexScanner;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import nicad.NiCadConvertor;
import plain.FullIndexScanner;
import plain.SimplifiedIndexScanner;

/**
 * The connection with javac.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class Engine {

    private final Properties conf;
    private final String index;
    private final boolean compressed;
    private final boolean normalizeAST;
    private final boolean printStats;
    private final boolean printTrie;
    private final String type;
    private final int minSize;
    private final int maxSize;
    private final int distance;
    private final String sourceEncoding;
    private final String methodFile;
    private List<JCCompilationUnit> units;
    private CloneSet clones;

    public Engine(Properties conf) {
        this.conf = conf;
        index = conf.getProperty("index", "full");
        compressed = Boolean.parseBoolean(conf.getProperty("compressed", "false"));
        normalizeAST = Boolean.parseBoolean(conf.getProperty("normalizeAST"));
        printStats = Boolean.parseBoolean(conf.getProperty("printStats"));
        printTrie = Boolean.parseBoolean(conf.getProperty("printTrie"));
        type = conf.getProperty("type", "2");
        minSize = Integer.parseInt(conf.getProperty("minSize", "0"));
        maxSize = Integer.parseInt(conf.getProperty("maxSize", "1000000"));
        distance = Integer.parseInt(conf.getProperty("distance", "2"));
        sourceEncoding = conf.getProperty("sourceEncoding", "UTF-8");
        methodFile = conf.getProperty("methodFile");
    }

    public CloneSet getClones() {
        return clones;
    }

    private void prepareCompiler(Context ctx) {
        Options.instance(ctx).put(Option.ENCODING, sourceEncoding);
        DiagnosticListener<JavaFileObject> diagnosticListener = System.out::println;
        ctx.put(DiagnosticListener.class, diagnosticListener);
        JavaCompiler compiler = JavaCompiler.instance(ctx);
        compiler.attrParseOnly = true;
        compiler.genEndPos = true;
    }

    private List<String> listFiles(Path dir, final String ext, final boolean recursively) throws IOException {
        final Path topDir = dir;
        final List<String> p = new ArrayList<>();
        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                if (topDir.equals(dir)) {
                    return FileVisitResult.CONTINUE;
                } else {
                    return recursively ? FileVisitResult.CONTINUE : FileVisitResult.SKIP_SUBTREE;
                }
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String s = file.toString();
                if (s.endsWith(ext)) {
                    p.add(s);
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return p;
    }

    private String buildClasspath(String cp) throws IOException {
        List<String> jars = new ArrayList<>();
        for (String s : cp.split("[;:]")) {
            Path p = Paths.get(s);
            if (Files.isDirectory(p)) {
                List<String> ff = listFiles(p, ".jar", false);
                jars.addAll(ff);
            } else {
                jars.add(s);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String s : jars) {
            if (sb.length() > 0) {
                sb.append(File.pathSeparatorChar);
            }
            sb.append(s);
        }
        return sb.toString();
    }

    private Context prepare() throws Exception {
        Context ctx = new Context();
        prepareCompiler(ctx);
        // gather source files
        JavacFileManager fileManager = (JavacFileManager) ctx.get(JavaFileManager.class);
        List<JavaFileObject> fileObjects = new ArrayList<>();
        String srcDirs = conf.getProperty("sourceDir");
        for (String srcDir : srcDirs.split(":")) {
            Path dir = Paths.get(srcDir);
            List<String> files = listFiles(dir, ".java", true);
            for (String f : files) {
                fileObjects.add(fileManager.getFileForInput(f));
            }
        }
        JavacTool tool = JavacTool.create();
        String cp = buildClasspath(conf.getProperty("classpath", "."));
        List<String> options = Arrays.asList("-cp", cp);
        JavacTaskImpl javacTaskImpl = (JavacTaskImpl) tool.getTask(null, fileManager, null, options, null, fileObjects);
        javacTaskImpl.updateContext(ctx);
        // get compilation units
        units = new ArrayList<>();
        for (CompilationUnitTree t : javacTaskImpl.parse()) {
            units.add((JCCompilationUnit) t);
        }
        javacTaskImpl.analyze();
        return ctx;
    }

    public void perform() throws Exception {
        prepare();
        Statistics stats = new Statistics();
        IndexScanner scan;
        switch (index) {
            case "simplified":
                scan = compressed ? new SimplifiedCompressedIndexScanner(conf) : new SimplifiedIndexScanner(conf);
                break;
            case "full":
            default:
                scan = compressed ? new FullCompressedIndexScanner(conf) : new FullIndexScanner(conf);
        }
        NormalizationScanner normScan = normalizeAST ? new NormalizationScanner() : null;
        CountingScanner cscan = printStats ? new CountingScanner() : null;
        for (JCCompilationUnit cu : units) {
            if (normalizeAST) {
                cu.accept(normScan);
            }
            cu.accept(scan);
            if (printStats) {
                cu.accept(cscan);
                stats.store(cscan.getLines(), cscan.getNodes(), scan.getTrieNodeCount(), scan.getTrieEdgeCount(), Pos.getCount());
            }
        }
        if (printStats) {
            stats.print();
        }
        Index trie = scan.getTrie();
        if (printTrie) {
            trie.print();
        }
        if (methodFile != null) {
            trie.writeMethods(methodFile);
        }
        switch (type) {
            case "2":
                clones = trie.detectClonesType2(minSize, maxSize);
                break;
            case "3":
                clones = trie.detectClonesType3(minSize, maxSize, distance);
                break;
            case "2+3":
                clones = trie.detectClonesType23(minSize, maxSize, distance);
                break;
            default:
                throw new RuntimeException("invalid type");
        }
        String nicadOutput = conf.getProperty("NiCadOutputFile");
        if (nicadOutput == null) {
            clones.print();
        } else {
            NiCadConvertor conv = new NiCadConvertor(conf);
            conv.convert(clones, nicadOutput);
        }
    }
}
