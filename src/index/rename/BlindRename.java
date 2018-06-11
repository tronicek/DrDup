package index.rename;

import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeKind;

/**
 * The blind rename strategy.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class BlindRename extends RenameStrategy {

    @Override
    public RenameStrategy newInstance() {
        return new BlindRename();
    }

    @Override
    public void enterBlock() {
    }

    @Override
    public void exitBlock() {
    }

    @Override
    public void enterMethod() {
    }

    @Override
    public void exitMethod() {
    }

    @Override
    public void declareVar(ElementKind kind, String name) {
    }

    @Override
    public String rename(ElementKind kind, String name, boolean isStatic) {
        switch (kind) {
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
        }
        return null;
    }

    @Override
    public String renamePrimitiveType(TypeKind kind) {
        return "ID#";
    }

    @Override
    public String renameTypeArg(String name) {
        return "ID#";
    }
}
