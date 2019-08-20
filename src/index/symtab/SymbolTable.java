package index.symtab;

import index.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class that helps rename parameters, fields and methods.
 *
 * @author Zdenek Tronicek
 */
public class SymbolTable {

    private static final Logger logger = Logger.getInstance();
    private final List<Entry> entries = new ArrayList<>();
    private final Map<SymbolKind, Integer> countMap;
    private final boolean global;
    private final SymbolTable next;

    public SymbolTable(boolean global, SymbolTable next) {
        this.global = global;
        this.countMap = global ? new HashMap<>() : next.countMap;
        this.next = next;
    }
    
    public String declare(String name, SymbolKind kind) {
        logger.printf("declare: %s, %s%n", name, kind);
        Integer c = countMap.get(kind);
        if (c == null) {
            c = 1;
        } else {
            c++;
        }
        countMap.put(kind, c);
        Entry entry = new Entry(name, kind, c);
        entries.add(entry);
        print();
        return entry.norm();
    }

    public String declareGlobal(String name, SymbolKind kind) {
        if (!global) {
            return next.declareGlobal(name, kind);
        }
        logger.printf("declare global: %s, %s%n", name, kind);
        Integer c = countMap.get(kind);
        if (c == null) {
            c = 1;
        } else {
            c++;
        }
        countMap.put(kind, c);
        Entry entry = new Entry(name, kind, c);
        entries.add(entry);
        print();
        return entry.norm();
    }

    public String lookup(String name, SymbolKind kind) {
        for (Entry e : entries) {
            if (name.equals(e.getName()) && kind == e.getKind()) {
                return e.norm();
            }
        }
        if (!global) {
            return next.lookup(name, kind);
        }
        return null;
    }

    public void print() {
        logger.println("symbol table:");
        for (Entry e : entries) {
            logger.println(e);
        }
        logger.println("----------------");
    }

    public SymbolTable getNext() {
        return next;
    }
}
