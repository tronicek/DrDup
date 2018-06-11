package compressed;

import index.Logger;
import index.Pos;
import java.io.Serializable;
import java.util.List;

/**
 * The node of the compressed TRIE.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class CompressedTrieNode implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getInstance();
    private static int count;
    private final int num;
    private CompressedTrieEdge[] edges = new CompressedTrieEdge[1];
    private int edgesCount;

    public CompressedTrieNode() {
        num = count++;
    }

    public static int getCount() {
        return count;
    }

    public boolean isLeaf() {
        return edgesCount == 0;
    }

    public CompressedTrieEdge[] getEdges() {
        if (edgesCount == edges.length) {
            return edges;
        }
        CompressedTrieEdge[] ee = new CompressedTrieEdge[edgesCount];
        System.arraycopy(edges, 0, ee, 0, edgesCount);
        return ee;
    }

    public CompressedTrieEdge findEdge(String label) {
        for (int i = 0; i < edgesCount; i++) {
            List<String> elab = edges[i].getLabel();
            String s = elab.get(0);
            if (s.equals(label)) {
                return edges[i];
            }
        }
        return null;
    }

//    public CompressedTrieEdge addEdge(List<String> linearization, int start, int end, Pos pos) {
//        CompressedTrieNode dst = new CompressedTrieNode();
//        logger.printf("adding edge %d -> %d %s%n", num, dst.num, linearization.subList(start, end));
//        CompressedTrieEdge edge = new CompressedTrieEdge(linearization, start, end, dst);
//        addEdge(edge);
//        edge.addPosition(pos);
//        return edge;
//    }
    public CompressedTrieEdge addEdge(List<String> linearization, int ind, Pos pos) {
        String label = linearization.get(ind);
//        logger.printf("addChild: %s, %s%n", label, pos);
//        CompressedTrieEdge e = findEdge(label);
//        if (e == null) {
        CompressedTrieNode dst = new CompressedTrieNode();
        logger.printf("adding edge %d -> %d (%s)%n", num, dst.num, label);
        CompressedTrieEdge e = new CompressedTrieEdge(linearization, ind, ind + 1, dst);
        addEdge(e);
//        }
        e.addPosition(pos);
        return e;
    }

    public void addEdge(CompressedTrieEdge e) {
        if (edgesCount == edges.length) {
            CompressedTrieEdge[] ee = new CompressedTrieEdge[edges.length * 2];
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
            CompressedTrieNode dst = edges[i].getDestination();
            dst.print();
        }
    }
    
    public void checkDegree() {
        assert edgesCount == 0 || edgesCount > 1;
        for (int i = 0; i < edgesCount; i++) {
            CompressedTrieNode dst = edges[i].getDestination();
            dst.checkDegree();
        }
    }

    public void checkDegreeSimplified() {
        for (int i = 0; i < edgesCount; i++) {
            CompressedTrieNode dst = edges[i].getDestination();
            dst.checkDegree();
        }
    }

    @Override
    public String toString() {
        return String.format("node %d", num);
    }
}
