package clones;

import index.Pos;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The representation of a clone.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class Clone {

    private final Integer distance;
    private final Pos[] positions;

    public Clone(Integer distance, Pos[] positions) {
        this.distance = distance;
        this.positions = positions;
    }

    public Integer getDistance() {
        return distance;
    }

    public Pos[] getPositions() {
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

    private boolean equal(Pos[] pp1, Pos[] pp2) {
        Set<Pos> s1 = new HashSet<>();
        s1.addAll(Arrays.asList(pp1));
        Set<Pos> s2 = new HashSet<>();
        s2.addAll(Arrays.asList(pp2));
        return s1.containsAll(s2) && s2.containsAll(s1);
    }
}
