package index.rename;

import javax.lang.model.element.ElementKind;

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
        }
        System.out.println("missing rename strategy, using default...");
        return new BlindRename();
    }

    public abstract void enterMethod();

    public abstract void exitMethod();

    public abstract void enterBlock();

    public abstract void exitBlock();

    public abstract String declare(ElementKind kind, String name);

    public abstract String declareGlobal(ElementKind kind, String name);

    public abstract String rename(ElementKind kind, String name);

}
