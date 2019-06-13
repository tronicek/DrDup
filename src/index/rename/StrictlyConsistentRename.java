package index.rename;

import index.symtab.SymbolKind;
import index.symtab.SymbolTable;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeKind;

/**
 * The strictly consistent rename strategy.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class StrictlyConsistentRename extends RenameStrategy {

    private final SymbolTable symbolTable = new SymbolTable();

    @Override
    public RenameStrategy newInstance() {
        return new StrictlyConsistentRename();
    }

    @Override
    public void enterBlock() {
        symbolTable.enterBlock();
    }

    @Override
    public void exitBlock() {
        symbolTable.exitBlock();
    }

    @Override
    public void enterMethod() {
        symbolTable.enterMethod();
    }

    @Override
    public void exitMethod() {
        symbolTable.exitMethod();
    }

    @Override
    public void declareVar(ElementKind kind, String name) {
        SymbolKind k = SymbolKind.instance(kind.toString());
        symbolTable.declareVar(name, k);
    }

    @Override
    public String rename(ElementKind kind, String name, boolean isStatic) {
        switch (kind) {
            case ANNOTATION_TYPE:
            case CLASS:
            case ENUM:
            case INTERFACE:
            case TYPE_PARAMETER:
                return symbolTable.lookup(name, SymbolKind.TYPE);
            case CONSTRUCTOR:
            case ENUM_CONSTANT:
            case EXCEPTION_PARAMETER:
            case LOCAL_VARIABLE:
            case METHOD:
            case PARAMETER:
            case RESOURCE_VARIABLE:
                return symbolTable.lookup(name, SymbolKind.instance(kind.toString()));
            case FIELD: {
                SymbolKind symKind = isStatic ? SymbolKind.STATIC_FIELD : SymbolKind.FIELD;
                return symbolTable.lookup(name, symKind);
            }
        }
        return null;
    }

    @Override
    public String renamePrimitiveType(TypeKind kind) {
        String name;
        switch (kind) {
            case BOOLEAN:
                name = "java.lang.Boolean";
                break;
            case BYTE:
                name = "java.lang.Byte";
                break;
            case CHAR:
                name = "java.lang.Character";
                break;
            case DOUBLE:
                name = "java.lang.Double";
                break;
            case FLOAT:
                name = "java.lang.Float";
                break;
            case INT:
                name = "java.lang.Integer";
                break;
            case LONG:
                name = "java.lang.Long";
                break;
            case SHORT:
                name = "java.lang.Short";
                break;
            case VOID:
                name = "java.lang.Void";
                break;
            default:
                name = kind.name();
        }
        return symbolTable.lookup(name, SymbolKind.TYPE);
    }

    @Override
    public String renameTypeArg(String name) {
        return symbolTable.lookup(name, SymbolKind.TYPE);
    }
}
