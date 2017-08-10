package index;

import index.rename.RenameStrategy;
import com.sun.tools.javac.tree.TreeScanner;
import java.util.Properties;

/**
 * The AST scanner.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public abstract class IndexScanner extends TreeScanner {

    protected static final Logger logger = Logger.getInstance();
    protected final Trie trie;
    protected final String srcDir;
    protected final RenameStrategy renameStrategy;
    protected final boolean ignoreTypeArgs;
    protected final boolean ignoreExceptions;

    public IndexScanner(Properties conf) {
        srcDir = conf.getProperty("sourceDir");
        renameStrategy = RenameStrategy.instance(conf.getProperty("rename", "blind"));
        ignoreTypeArgs = Boolean.parseBoolean(conf.getProperty("ignoreTypeArgs"));
        ignoreExceptions = Boolean.parseBoolean(conf.getProperty("ignoreExceptions", "true"));
        trie = new Trie(conf);
        logger.setTrie(trie);
    }

    public Trie getTrie() {
        return trie;
    }
}
