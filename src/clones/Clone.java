package clones;

import index.Pos;
import java.util.Objects;
import java.util.Set;

/**
 * The representation of a clone.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class Clone {

    private final Integer distance;
    private final Set<Pos> positions;

    public Clone(Integer distance, Set<Pos> positions) {
        this.distance = distance;
        this.positions = positions;
    }

    public Integer getDistance() {
        return distance;
    }

    public Set<Pos> getPositions() {
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

    private boolean equal(Set<Pos> pp1, Set<Pos> pp2) {
        return pp1.containsAll(pp2) && pp2.containsAll(pp1);
    }
}
