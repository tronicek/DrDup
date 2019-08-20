package index;

import com.sun.tools.javac.tree.TreeScanner;
import index.rename.RenameStrategy;
import java.util.Properties;

/**
 * The AST scanner.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public abstract class IndexScanner extends TreeScanner {

    protected static final Logger logger = Logger.getInstance();
    protected final String srcDir;
    protected final RenameStrategy renameStrategy;
    protected final boolean ignoreTypeArgs;
    protected final boolean ignoreModifiers;
    protected final boolean ignoreExceptions;
    protected final boolean useWrappersForPrimitiveTypes;

    public IndexScanner(Properties conf) {
        srcDir = conf.getProperty("sourceDir");
        renameStrategy = RenameStrategy.instance(conf.getProperty("rename", "blind"));
        ignoreTypeArgs = Boolean.parseBoolean(conf.getProperty("ignoreTypeArgs"));
        ignoreModifiers = Boolean.parseBoolean(conf.getProperty("ignoreModifiers"));
        ignoreExceptions = Boolean.parseBoolean(conf.getProperty("ignoreExceptions"));
        useWrappersForPrimitiveTypes = Boolean.parseBoolean(conf.getProperty("useWrappersForPrimitiveTypes"));
    }

    public abstract Index getTrie();
    
    public abstract int getTrieNodeCount();
    
    public abstract int getTrieEdgeCount();
}
