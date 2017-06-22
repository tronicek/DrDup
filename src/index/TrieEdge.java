package index;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The edge of the TRIE.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class TrieEdge implements Comparable<TrieEdge>, Serializable {

    private static final long serialVersionUID = 1L;
    private static int count;
    private final String label;
    private final TrieNode destination;
    private final Set<Pos> positions = new HashSet<>();

    public TrieEdge(String label, TrieNode destination) {
        this.label = label;
        this.destination = destination;
        count++;
    }

    public static int getCount() {
        return count;
    }

    public String getLabel() {
        return label;
    }

    public TrieNode getDestination() {
        return destination;
    }

    public Set<Pos> getPositions() {
        return positions;
    }

    public void addPosition(Pos position) {
        positions.add(position);
    }

    @Override
    public int compareTo(TrieEdge that) {
        return label.compareTo(that.label);
    }

    public void print() {
        System.out.printf("  edge: %s, destination: %d%n", label, destination.getNum());
        for (Pos pos : positions) {
            System.out.printf("    position: %s%n", pos);
        }
    }
}
