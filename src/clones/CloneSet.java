package clones;

import index.Pos;
import java.util.ArrayList;
import java.util.List;

/**
 * The representation of a clone set.
 * 
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class CloneSet {

    private final List<Clone> clones = new ArrayList<>();

    public List<Clone> getClones() {
        return clones;
    }

    public void addClone(Clone clone) {
        clones.add(clone);
    }
    
    public void print() {
        for (Clone clone : clones) {
            System.out.printf("distance: %d%n", clone.getDistance());
            for (Pos p : clone.getPositions()) {
                System.out.printf("  file: %s, start: %d, end: %d, startline: %d, endline: %d%n",
                        p.getFile(), p.getStart(), p.getEnd(), p.getStartLine(), p.getEndLine());
            }
        }
    }
}
