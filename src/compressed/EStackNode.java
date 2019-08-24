package compressed;

import index.Pos;

/**
 * The stack node.
 * 
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class EStackNode {

    private final CompressedTrieEdge edge;
    private final int current;
    private final Pos pos;

    public EStackNode(CompressedTrieEdge edge, int current, Pos pos) {
        this.edge = edge;
        this.current = current;
        this.pos = pos;
        assert current <= edge.getEnd();
    }

    public CompressedTrieEdge getEdge() {
        return edge;
    }

    public int getCurrent() {
        return current;
    }

    public Pos getPos() {
        return pos;
    }
    
    @Override
    public String toString() {
        return String.format("%s:%d", edge, current);
    }
}
