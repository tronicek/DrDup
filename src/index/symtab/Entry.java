package index.symtab;

/**
 * The symbol table entry.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class Entry {

    private final String name;
    private final SymbolKind kind;
    private final int count;
    private final int level;

    public Entry(String name, SymbolKind kind, int count, int level) {
        this.name = name;
        this.kind = kind;
        this.count = count;
        this.level = level;
    }

    public String norm() {
        return String.format("%s:%d", kind, count);
    }

    public String getName() {
        return name;
    }

    public SymbolKind getKind() {
        return kind;
    }

    public int getCount() {
        return count;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return String.format("entry: %s, %s, %d, %d", name, kind, count, level);
    }
}
