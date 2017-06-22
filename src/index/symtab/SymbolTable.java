package index.symtab;

import com.sun.tools.javac.util.Name;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * The class that helps rename parameters, fields and methods.
 *
 * @author Zdenek Tronicek
 */
public class SymbolTable {
    
    private final Deque<Table> stack = new ArrayDeque<>();
    private Table currentTable = new Table();

    public void enterBlock() {
        currentTable.enterBlock();
    }

    public void exitBlock() {
        currentTable.exitBlock();
    }

    public void enterMethod() {
        stack.push(currentTable);
        currentTable = new Table();
    }

    public void exitMethod() {
        currentTable = stack.pop();
    }

    public void declareVar(String name, SymbolKind kind) {
        currentTable.declareVar(name, kind);
    }

    public String lookup(Name name, SymbolKind kind) {
        return currentTable.lookup(name.toString(), kind);
    }

    public String lookup(String name, SymbolKind kind) {
        return currentTable.lookup(name, kind);
    }

    public void print() {
        currentTable.print();
    }
}
