package index.rename;

import index.symtab.SymbolKind;
import index.symtab.SymbolTable;
import javax.lang.model.element.ElementKind;

/**
 * The consistent rename strategy.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class ConsistentRename extends RenameStrategy {

    private SymbolTable symbolTable;

    @Override
    public void enterMethod() {
        symbolTable = new SymbolTable(true, symbolTable);
    }

    @Override
    public void exitMethod() {
        symbolTable = symbolTable.getNext();
    }

    @Override
    public void enterBlock() {
        symbolTable = new SymbolTable(false, symbolTable);
    }

    @Override
    public void exitBlock() {
        symbolTable = symbolTable.getNext();
    }

    @Override
    public String declare(ElementKind kind, String name) {
        SymbolKind sym = SymbolKind.fromElementKind(kind);
        return symbolTable.declare(name, sym);
    }

    @Override
    public String declareGlobal(ElementKind kind, String name) {
        SymbolKind sym = SymbolKind.fromElementKind(kind);
        return symbolTable.declareGlobal(name, sym);
    }

    @Override
    public String rename(ElementKind kind, String name) {
        if ("this".equals(name) || "super".equals(name)) {
            return name;
        }
        SymbolKind sym = SymbolKind.fromElementKind(kind);
        return symbolTable.lookup(name, sym);
    }
}
