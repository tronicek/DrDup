package index;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * The representation of method tokens.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class MethodTokens {

    private static int count;
    private final int tid;
    private final List<String> tokens;
    private Integer[] mids = new Integer[0];
    private final Map<Integer, Integer> distMap = new TreeMap<>();

    public MethodTokens(List<String> tokens) {
        this.tokens = tokens;
        count++;
        tid = count;
    }

    public int getTid() {
        return tid;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public int getTokensSize() {
        return tokens.size();
    }

    public Integer[] getMids() {
        return mids;
    }

    public void addMid(Integer mid) {
        Integer[] ii = new Integer[mids.length + 1];
        System.arraycopy(mids, 0, ii, 0, mids.length);
        ii[mids.length] = mid;
        mids = ii;
    }

    public Set<Integer> getSimilarTokens() {
        return distMap.keySet();
    }

    public Integer getDistanceTo(MethodTokens mt) {
        return distMap.get(mt.getTid());
    }

    public void computeDistanceTo(MethodTokens mt, int maxDistance, boolean useSegmentDistance) {
        int d;
        if (useSegmentDistance) {
            d = approxSegmentDistance(mt, maxDistance);
        } else {
            d = levenshteinDistance(mt, maxDistance);
        }
        distMap.put(mt.tid, d);
        mt.distMap.put(tid, d);
    }

    private int levenshteinDistance(MethodTokens mt, int maxDistance) {
        int n = tokens.size();
        int[] d = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            d[i] = i;
        }
        int[] nd = new int[n + 1];
        List<String> tokens2 = mt.tokens;
        for (int i = 0; i < tokens2.size(); i++) {
            String t2 = tokens2.get(i);
            nd[0] = i;
            int min = nd[0];
            for (int j = 0; j < tokens.size(); j++) {
                String t1 = tokens.get(j);
                if (t1.equals(t2)) {
                    nd[j + 1] = d[j];
                } else {
                    nd[j + 1] = min(d[j + 1] + 1, nd[j] + 1, d[j] + 1);
                }
                if (nd[j + 1] < min) {
                    min = nd[j + 1];
                }
            }
            if (min > maxDistance) {
                d[n] = maxDistance + 1;
                break;
            }
            int[] p = d;
            d = nd;
            nd = p;
        }
        return d[n];
    }

    private int approxSegmentDistance(MethodTokens mt, int maxDistance) {
        int d = -1, i = 0;
        while (i < mt.tokens.size()) {
            i = find(mt.tokens, i);
            d++;
            if (d > maxDistance) {
                break;
            }
        }
        return d;
    }

    private int find(List<String> tt, int index) {
        int max = index + 1;
        for (int i = 0; i < tokens.size(); i++) {
            int j = index;
            while (j < tt.size()) {
                String t1 = tokens.get(i + j - index);
                String t2 = tt.get(j);
                if (!t1.equals(t2)) {
                    break;
                }
                j++;
            }
            if (j > max) {
                max = j;
            }
        }
        return max;
    }

    private int min(int a, int b, int c) {
        int m = a < b ? a : b;
        if (c < m) {
            m = c;
        }
        return m;
    }

    public void print() {
        System.out.printf("%d, %s%n", tid, tokens);
        for (Integer i : distMap.keySet()) {
            Integer d = distMap.get(i);
            System.out.printf("  %d -> %d%n", i, d);
        }
    }

    @Override
    public int hashCode() {
        return tid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MethodTokens) {
            MethodTokens mt = (MethodTokens) obj;
            return tid == mt.tid;
        }
        return false;
    }
}
