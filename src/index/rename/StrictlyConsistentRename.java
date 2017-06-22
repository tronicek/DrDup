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
            case CLASS:
            case ENUM:
            case INTERFACE:
            case TYPE_PARAMETER:
                return "TYPE#";
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
        return "TYPE#";
    }

    @Override
    public String renameTypeArg(String name) {
        return "TYPE#";
    }
}
