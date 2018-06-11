package index.rename;

import index.symtab.SymbolKind;
import index.symtab.SymbolTable;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.TypeKind;

/**
 * The consistent rename strategy.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class ConsistentRename extends RenameStrategy {

    private final SymbolTable symbolTable = new SymbolTable();

    @Override
    public RenameStrategy newInstance() {
        return new ConsistentRename();
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
        symbolTable.declareVar(name, SymbolKind.ID);
    }

    @Override
    public String rename(ElementKind kind, String name, boolean isStatic) {
        switch (kind) {
            case CLASS:
            case ENUM:
            case INTERFACE:
            case TYPE_PARAMETER:
                return symbolTable.lookup(name, SymbolKind.TYPE);
            case ENUM_CONSTANT:
            case EXCEPTION_PARAMETER:
            case FIELD:
            case LOCAL_VARIABLE:
            case PARAMETER:
            case RESOURCE_VARIABLE:
                return symbolTable.lookup(name, SymbolKind.ID);
            case CONSTRUCTOR:
            case METHOD:
                return symbolTable.lookup(name, SymbolKind.METHOD);
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
