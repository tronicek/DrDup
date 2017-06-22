package index.rename;

import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeKind;

/**
 * The rename strategy.
 * 
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public abstract class RenameStrategy {

    public static RenameStrategy instance(String name) {
        switch (name) {
            case "blind":
                return new BlindRename();
            case "consistent":
                return new ConsistentRename();
            case "strictly-consistent":
                return new StrictlyConsistentRename();
        }
        System.out.println("missing rename strategy, using default...");
        return new BlindRename();
    }

    public abstract void enterBlock();

    public abstract void exitBlock();

    public abstract void enterMethod();

    public abstract void exitMethod();

    public abstract void declareVar(ElementKind kind, String name);

    public abstract String rename(ElementKind kind, String name, boolean isStatic);

    public abstract String renamePrimitiveType(TypeKind kind);
    
    public abstract String renameTypeArg(String name);
}
