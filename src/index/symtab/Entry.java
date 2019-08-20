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

    public Entry(String name, SymbolKind kind, int count) {
        this.name = name;
        this.kind = kind;
        this.count = count;
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

    @Override
    public String toString() {
        return String.format("entry: %s, %s, %d", name, kind, count);
    }
}
