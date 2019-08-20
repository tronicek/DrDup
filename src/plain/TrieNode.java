package plain;

import index.Logger;
import index.Pos;
import java.io.Serializable;

/**
 * The node of the TRIE.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class TrieNode implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getInstance();
    private static int count;
    private final int num;
    private TrieEdge[] edges = new TrieEdge[1];
    private int edgesCount;

    public TrieNode() {
        num = count++;
    }

    public static int getCount() {
        return count;
    }

    public boolean isLeaf() {
        return edgesCount == 0;
    }

    public TrieEdge[] getEdges() {
        if (edgesCount == edges.length) {
            return edges;
        }
        TrieEdge[] ee = new TrieEdge[edgesCount];
        System.arraycopy(edges, 0, ee, 0, edgesCount);
        return ee;
    }

    public TrieEdge findEdge(String label) {
        for (int i = 0; i < edgesCount; i++) {
            String s = edges[i].getLabel();
            if (s.equals(label)) {
                return edges[i];
            }
        }
        return null;
    }

    public TrieNode addChild(String label, Pos pos) {
        assert label != null;
        logger.printf("addChild: %s, %s%n", label, pos);
        TrieEdge e = findEdge(label);
        if (e == null) {
            TrieNode dst = new TrieNode();
            logger.printf("adding edge %d -> %d (%s)%n", num, dst.num, label);
            e = new TrieEdge(label, dst);
            addEdge(e);
        }
        e.addPosition(pos);
        return e.getDestination();
    }

    private void addEdge(TrieEdge e) {
        if (edgesCount == edges.length) {
            TrieEdge[] ee = new TrieEdge[edges.length * 2];
            System.arraycopy(edges, 0, ee, 0, edges.length);
            edges = ee;
        }
        edges[edgesCount] = e;
        edgesCount++;
    }

    public int getNum() {
        return num;
    }

    public void print() {
        System.out.printf("node %d%n", num);
        for (int i = 0; i < edgesCount; i++) {
            edges[i].print();
        }
        for (int i = 0; i < edgesCount; i++) {
            TrieNode dst = edges[i].getDestination();
            dst.print();
        }
    }

    @Override
    public String toString() {
        return String.format("node %d", num);
    }
}
