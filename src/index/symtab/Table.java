package index.symtab;

import index.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The symbol table of a method.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class Table {

    private static final Logger logger = Logger.getInstance();
    private final List<Entry> entries = new ArrayList<>();
    private final Map<SymbolKind, Integer> countMap = new HashMap<>();
    private int currentLevel;

    public void enterBlock() {
        currentLevel++;
    }

    public void exitBlock() {
        List<Entry> p = new ArrayList<>();
        for (Entry e : entries) {
            if (e.getLevel() == currentLevel) {
                p.add(e);
            }
        }
        entries.removeAll(p);
        currentLevel--;
    }

    public void declareVar(String name, SymbolKind kind) {
        logger.printf("declare: %s, %s%n", name, kind);
        store(name, kind, currentLevel);
    }

    public String lookup(String name, SymbolKind kind) {
        for (int i = entries.size() - 1; i >= 0; i--) {
            Entry e = entries.get(i);
            if (name.equals(e.getName()) && kind == e.getKind()) {
                return e.norm();
            }
        }
        int level, i = name.indexOf('.');
        if (i > 0) {
            String var = name.substring(0, i);
            level = findLevel(var);
        } else {
            level = currentLevel;
        }
        Entry e = store(name, kind, level);
        return e.norm();
    }

    private int findLevel(String name) {
        for (int i = entries.size() - 1; i >= 0; i--) {
            Entry e = entries.get(i);
            if (name.equals(e.getName())) {
                return e.getLevel();
            }
        }
        return 0;
    }

    private Entry store(String name, SymbolKind kind, int level) {
        Integer c = countMap.get(kind);
        if (c == null) {
            c = 1;
        } else {
            c++;
        }
        countMap.put(kind, c);
        Entry entry = new Entry(name, kind, c, level);
        entries.add(entry);
        print();
        return entry;
    }

    public void print() {
        logger.println("symbol table:");
        for (Entry e : entries) {
            logger.println(e);
        }
        logger.println("----------------");
    }
}
