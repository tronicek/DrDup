package nicad;

import clones.Clone;
import clones.CloneSet;
import index.Pos;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

/**
 * The convertor to the NiCad output format.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class NiCadConvertor {

    private final boolean printSource;
    private final String sourceEncoding;
    private final String sourceDir;

    public NiCadConvertor(Properties prop) {
        printSource = Boolean.parseBoolean(prop.getProperty("printSource"));
        sourceEncoding = prop.getProperty("sourceEncoding", "UTF-8");
        sourceDir = prop.getProperty("sourceDir");
    }

    public void convert(CloneSet clones, String filename) throws Exception {
        List<NiCadClone> cc = new ArrayList<>();
        for (Clone clone : clones.getClones()) {
            List<NiCadSource> sources = new ArrayList<>();
            int nlines = Integer.MAX_VALUE;
            for (Pos pos : clone.getPositions()) {
                String file = pos.getFile().replace('\\', '/');
                nlines = Integer.min(nlines, pos.getLines());
                NiCadSource src = new NiCadSource(file, pos.getStartLine(), pos.getEndLine());
                sources.add(src);
            }
            Integer sim = clone.getDistance() == 0 ? 100 : null;
            NiCadClone nc = new NiCadClone(nlines, sim, sources);
            cc.add(nc);
        }
        if (printSource) {
            addSource(cc);
        }
        NiCadClones ncc = new NiCadClones(cc);
        JAXBContext ctx = JAXBContext.newInstance("nicad");
        Marshaller marshaller = ctx.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        try (FileOutputStream out = new FileOutputStream(filename)) {
            marshaller.marshal(ncc, out);
        }
    }

    private void addSource(List<NiCadClone> clones) throws IOException {
        for (NiCadClone clone : clones) {
            for (NiCadSource src : clone.getSources()) {
                String file = src.getFile();
                Integer startline = src.getStartline();
                Integer endline = src.getEndline();
                String code = readFile(file, startline, endline);
                src.setSourceCode(code);
            }
        }
    }

    private String readFile(String file, int startline, int endline) throws IOException {
        Path path = Paths.get(sourceDir, file);
        Charset cs = Charset.forName(sourceEncoding);
        List<String> lines = Files.readAllLines(path, cs);
        List<String> selected = lines.subList(startline - 1, endline);
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (String line : selected) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }
}
