package index.rename;

import javax.lang.model.element.ElementKind;

/**
 * The blind rename strategy.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class BlindRename extends RenameStrategy {

    @Override
    public void enterMethod() {
    }

    @Override
    public void exitMethod() {
    }

    @Override
    public void enterBlock() {
    }

    @Override
    public void exitBlock() {
    }

    @Override
    public String declare(ElementKind kind, String name) {
        return rename(kind, name);
    }

    @Override
    public String declareGlobal(ElementKind kind, String name) {
        return rename(kind, name);
    }

    @Override
    public String rename(ElementKind kind, String name) {
        if ("this".equals(name)) {
            return "THIS";
        }
        if ("super".equals(name)) {
            return "SUPER";
        }
        switch (kind) {
            case ANNOTATION_TYPE:
            case CLASS:
            case ENUM:
            case INTERFACE:
            case TYPE_PARAMETER:
            case ENUM_CONSTANT:
            case EXCEPTION_PARAMETER:
            case FIELD:
            case LOCAL_VARIABLE:
            case PARAMETER:
            case RESOURCE_VARIABLE:
                return "ID#";
            case CONSTRUCTOR:
            case METHOD:
                return "METHOD#";
            default:
                return "OTHER#";
        }
    }

}
