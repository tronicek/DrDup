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
    public String renamePrimitiveType(boolean distinguishPrimitiveTypes, TypeKind kind) {
        if (distinguishPrimitiveTypes) {
            return symbolTable.lookup(kind.name(), SymbolKind.TYPE);
        }
        String name = toClassName(kind);
        return symbolTable.lookup(name, SymbolKind.TYPE);
    }

    private String toClassName(TypeKind kind) {
        switch (kind) {
            case BOOLEAN:
                return "java.lang.Boolean";
            case BYTE:
                return "java.lang.Byte";
            case CHAR:
                return "java.lang.Character";
            case DOUBLE:
                return "java.lang.Double";
            case FLOAT:
                return "java.lang.Float";
            case INT:
                return "java.lang.Integer";
            case LONG:
                return "java.lang.Long";
            case SHORT:
                return "java.lang.Short";
            case VOID:
                return "java.lang.Void";
            default:
                return kind.name();
        }
    }
    
    @Override
    public String renameTypeArg(String name) {
        return symbolTable.lookup(name, SymbolKind.TYPE);
    }
}
