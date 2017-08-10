package clones;

import index.FastSet;
import index.Pos;
import java.util.Objects;

/**
 * The representation of a clone.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class Clone {

    private final Integer distance;
    private final FastSet<Pos> positions;

    public Clone(Integer distance, FastSet<Pos> positions) {
        this.distance = distance;
        this.positions = positions;
    }
    
    public Integer getDistance() {
        return distance;
    }

    public FastSet<Pos> getPositions() {
        return positions;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(distance);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Clone) {
            Clone that = (Clone) obj;
            return distance.equals(that.distance)
                    && equal(positions, that.positions);
        }
        return false;
    }

    private boolean equal(FastSet<Pos> pp1, FastSet<Pos> pp2) {
        return pp1.containsAll(pp2) && pp2.containsAll(pp1);
    }
}
