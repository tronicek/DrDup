package compressed;

import index.Pos;
import java.io.Serializable;
import java.util.List;

/**
 * The edge of the compressed TRIE.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class CompressedTrieEdge implements Serializable {

    private static final long serialVersionUID = 1L;
    private static int count;
    private final List<String> linearization;
    private int start;
    private int end;
    private CompressedTrieNode destination;
    private Pos[] positions = new Pos[1];
    private int positionsCount;

    public CompressedTrieEdge(List<String> linearization, int start, int end, CompressedTrieNode destination) {
        //System.out.printf("new edge: %d, %d, %s%n", start, end, linearization.subList(start, end));
        this.linearization = linearization;
        this.start = start;
        this.end = end;
        this.destination = destination;
        count++;
        assert start < end;
    }

    public static int getCount() {
        return count;
    }

    public List<String> getLinearization() {
        return linearization;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        //System.out.printf("changing end from %d to %d%n", this.end, end);
        this.end = end;
    }
    
    public void incEnd() {
        end++;
    }

    public List<String> getLabel() {
        return linearization.subList(start, end);
    }

    public CompressedTrieNode getDestination() {
        return destination;
    }

    public void setDestination(CompressedTrieNode destination) {
        this.destination = destination;
    }

    public Pos[] getPositions() {
        if (positionsCount == positions.length) {
            return positions;
        }
        Pos[] pp = new Pos[positionsCount];
        System.arraycopy(positions, 0, pp, 0, positionsCount);
        return pp;
    }

//    public Pos getLastAddedPosition() {
//        return positions[positionsCount - 1];
//    }

    public void addPosition(Pos position) {
        if (positionsCount > 0 && positions[positionsCount - 1].equals(position)) {
            return;
        }
        if (positionsCount == positions.length) {
            Pos[] pp = new Pos[positions.length * 2];
            System.arraycopy(positions, 0, pp, 0, positions.length);
            positions = pp;
        }
        positions[positionsCount] = position;
        positionsCount++;
    }

    public void print() {
        System.out.printf("  edge: %s, start: %d, end: %d, destination: %d%n", getLabel(), start, end, destination.getNum());
        for (int i = 0; i < positionsCount; i++) {
            System.out.printf("    position: %s%n", positions[i]);
        }
    }

    @Override
    public String toString() {
        return String.format("%s %d,%d -> %s", getLabel(), start, end, destination.getNum());
    }
}
