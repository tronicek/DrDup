package compressed;

import index.Pos;

/**
 * The stack node.
 * 
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class EStackNode {

    private final CompressedTrieEdge edge;
    private final int end;
    private final Pos pos;

    public EStackNode(CompressedTrieEdge edge, int end, Pos pos) {
        this.edge = edge;
        this.end = end;
        this.pos = pos;
    }

    public CompressedTrieEdge getEdge() {
        return edge;
    }

    public int getEnd() {
        return end;
    }

    public Pos getPos() {
        return pos;
    }
    
    @Override
    public String toString() {
        return String.format("%s:%d", edge, end);
        //return String.format("-> %d", edge.getDestination().getNum());
    }
}
