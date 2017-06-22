package index;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

/**
 * The node of the TRIE.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class TrieNode implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final Logger logger = Logger.getInstance();
    private static int count;
    private final int num;
    private final Set<TrieEdge> edges = new TreeSet<>();

    public TrieNode() {
        num = count++;
    }

    public static int getCount() {
        return count;
    }

    public boolean isLeaf() {
        return edges.isEmpty();
    }

    public Set<TrieEdge> getEdges() {
        return edges;
    }

    public TrieEdge findEdge(String label) {
        for (TrieEdge e : edges) {
            String s = e.getLabel();
            if (s.equals(label)) {
                return e;
            }
        }
        return null;
    }

    public TrieNode addChild(String label, Pos pos) {
        logger.printf("addChild: %s, %s%n", label, pos);
        TrieEdge e = findEdge(label);
        if (e == null) {
            TrieNode dst = new TrieNode();
            logger.printf("adding edge %d -> %d (%s)%n", num, dst.num, label);
            e = new TrieEdge(label, dst);
            edges.add(e);
        }
        e.addPosition(pos);
        return e.getDestination();
    }

    public int getNum() {
        return num;
    }

    public void print() {
        System.out.printf("node %d%n", num);
        for (TrieEdge e : edges) {
            e.print();
        }
        for (TrieEdge e : edges) {
            TrieNode dst = e.getDestination();
            dst.print();
        }
    }

    @Override
    public String toString() {
        return String.format("node %d", num);
    }
}
