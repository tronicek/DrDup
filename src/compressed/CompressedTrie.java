package compressed;

import clones.Clone;
import clones.CloneSet;
import index.Index;
import index.Method;
import index.MethodTokens;
import index.Pos;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * The implementation of the compressed TRIE.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class CompressedTrie implements Index, Serializable {

    private static final long serialVersionUID = 1L;
    protected final CompressedTrieNode root = new CompressedTrieNode();
    private final boolean printTokens;
    private final boolean printSimilarity;
    private final boolean useSegmentDistance;
    private final int segmentRangeCoefficient;

    public CompressedTrie(Properties conf) {
        printTokens = Boolean.parseBoolean(conf.getProperty("printTokens"));
        printSimilarity = Boolean.parseBoolean(conf.getProperty("printSimilarity"));
        String metric = conf.getProperty("metric", "Levenshtein");
        useSegmentDistance = metric.equals("segment");
        segmentRangeCoefficient = Integer.parseInt(conf.getProperty("segmentRangeCoefficient", "20"));
    }

    public CompressedTrieNode getRoot() {
        return root;
    }

    @Override
    public CloneSet detectClonesType2(int minSize) {
        CloneSet clones = new CloneSet();
        List<CompressedTrieNode> nodes = new ArrayList<>();
        List<Pos[]> positions = new ArrayList<>();
        CompressedTrieEdge edge = root.findEdge("METHOD");
        if (edge == null) {
            return clones;
        }
        nodes.add(edge.getDestination());
        positions.add(edge.getPositions());
        while (!nodes.isEmpty()) {
            CompressedTrieNode node = nodes.remove(0);
            Pos[] pp = positions.remove(0);
            if (node.isLeaf()) {
                if (pp.length > 1 && minimumSize(pp) >= minSize) {
                    Clone clone = new Clone(0, pp);
                    clones.addClone(clone);
                }
                continue;
            }
            for (CompressedTrieEdge e : node.getEdges()) {
                nodes.add(e.getDestination());
                positions.add(e.getPositions());
            }
        }
        return clones;
    }

    private long minimumSize(Pos[] pp) {
        long minSize = Long.MAX_VALUE;
        for (Pos p : pp) {
            long size = p.getLines();
            if (size < minSize) {
                minSize = size;
            }
        }
        return minSize;
    }

    @Override
    public CloneSet detectClonesType3(int minSize, int distance) {
        List<Method> methods = createMethodList(minSize);
        Map<Integer, Set<MethodTokens>> tokensMap = createTokensSizeMap(methods);
        int coef = useSegmentDistance ? segmentRangeCoefficient : 1;
        for (Integer size : tokensMap.keySet()) {
            List<MethodTokens> mts = new ArrayList<>();
            for (int i = size - coef * distance; i <= size + coef * distance; i++) {
                Set<MethodTokens> p = tokensMap.get(i);
                if (p != null) {
                    mts.addAll(p);
                }
            }
            computeDistance(distance, tokensMap.get(size), mts);
        }
        if (printTokens) {
            List<MethodTokens> mts = new ArrayList<>();
            for (Set<MethodTokens> set : tokensMap.values()) {
                mts.addAll(set);
            }
            printMethodTokens(mts);
        }
        if (printSimilarity) {
            List<MethodTokens> mts = new ArrayList<>();
            for (Set<MethodTokens> set : tokensMap.values()) {
                mts.addAll(set);
            }
            printSimilarityMap(mts);
        }
        return findClones(distance, methods);
    }

    private List<Method> createMethodList(int minSize) {
        List<Method> mm = new ArrayList<>();
        List<CompressedTrieNode> nodes = new ArrayList<>();
        List<Pos[]> positions = new ArrayList<>();
        CompressedTrieEdge edge = root.findEdge("METHOD");
        if (edge == null) {
            return mm;
        }
        nodes.add(edge.getDestination());
        positions.add(edge.getPositions());
        List<List<String>> tokens = new ArrayList<>();
        List<String> seq = new ArrayList<>();
        seq.addAll(edge.getLabel());
        tokens.add(seq);
        while (!nodes.isEmpty()) {
            CompressedTrieNode node = nodes.remove(0);
            Pos[] pp = positions.remove(0);
            List<String> tt = tokens.remove(0);
            if (node.isLeaf()) {
                MethodTokens mt = new MethodTokens(tt);
                for (Pos p : pp) {
                    if (p.getLines() >= minSize) {
                        Method m = new Method(p.getMid(), p.getFile(), p.getStart(), p.getEnd(), p.getStartLine(), p.getEndLine(), mt);
                        mm.add(m);
                    }
                }
                continue;
            }
            for (CompressedTrieEdge e : node.getEdges()) {
                nodes.add(e.getDestination());
                positions.add(e.getPositions());
                List<String> tt2 = new ArrayList<>();
                tt2.addAll(tt);
                tt2.addAll(e.getLabel());
                tokens.add(tt2);
            }
        }
        return mm;
    }

    private Map<Integer, Set<MethodTokens>> createTokensSizeMap(List<Method> methods) {
        Map<Integer, Set<MethodTokens>> map = new TreeMap<>();
        for (Method m : methods) {
            MethodTokens mt = m.getTokens();
            int size = mt.getTokensSize();
            Set<MethodTokens> mts = map.get(size);
            if (mts == null) {
                mts = new HashSet<>();
                map.put(size, mts);
            }
            mts.add(mt);
        }
        return map;
    }

    private void computeDistance(int maxDistance, Collection<MethodTokens> mts1, Collection<MethodTokens> mts2) {
        for (MethodTokens mt1 : mts1) {
            for (MethodTokens mt2 : mts2) {
                if (mt1 == mt2) {
                    continue;
                }
                if (mt1.getDistanceTo(mt2) == null) {
                    mt1.computeDistanceTo(mt2, maxDistance, useSegmentDistance);
                }
            }
        }
    }

    private CloneSet findClones(int distance, List<Method> methods) {
        CloneSet clones = new CloneSet();
        Set<MethodPair> pairs = new TreeSet<>();
        Map<Integer, MethodTokens> tmap = createTokensMap(methods);
        Map<Integer, Pos> pmap = createPosMap();
        for (Method m : methods) {
            MethodTokens mt = m.getTokens();
            for (Integer i : mt.getSimilarTokens()) {
                MethodTokens mt2 = tmap.get(i);
                for (Integer mid : mt2.getMids()) {
                    MethodPair pair = new MethodPair(m.getMid(), mid);
                    if (!pairs.contains(pair)) {
                        Pos[] pp = new Pos[2];
                        pp[0] = pmap.get(m.getMid());
                        pp[1] = pmap.get(mid);
                        int d = mt.getDistanceTo(mt2);
                        if (d <= distance) {
                            Clone clone = new Clone(d, pp);
                            clones.addClone(clone);
                            pairs.add(pair);
                        }
                    }
                }
            }
        }
        return clones;
    }

    private Map<Integer, Pos> createPosMap() {
        Map<Integer, Pos> map = new HashMap<>();
        CompressedTrieEdge edge = root.findEdge("METHOD");
        if (edge == null) {
            return map;
        }
        for (Pos pos : edge.getPositions()) {
            assert !map.containsKey(pos.getMid());
            map.put(pos.getMid(), pos);
        }
        return map;
    }

    private Map<Integer, MethodTokens> createTokensMap(List<Method> methods) {
        Map<Integer, MethodTokens> map = new HashMap<>();
        for (Method m : methods) {
            MethodTokens mt = m.getTokens();
            if (!map.containsKey(mt.getTid())) {
                map.put(mt.getTid(), mt);
            }
        }
        return map;
    }

    @Override
    public void print() {
        root.print();
    }

    private void printMethodTokens(List<MethodTokens> methodTokens) {
        System.out.println("--- Method tokens ---");
        for (MethodTokens mt : methodTokens) {
            System.out.printf("%d -> %s%n", mt.getTid(), mt.getTokens());
        }
        System.out.println("--- End of method tokens ---");
    }

    public void printMethods(List<String> tokens) {
        CompressedTrieNode p = root;
        Pos[] pos = null;
        for (String token : tokens) {
            CompressedTrieEdge edge = p.findEdge(token);
            p = edge.getDestination();
            pos = edge.getPositions();
        }
        List<CompressedTrieNode> nodes = new ArrayList<>();
        nodes.add(p);
        List<Pos[]> positions = new ArrayList<>();
        positions.add(pos);
        while (!nodes.isEmpty()) {
            CompressedTrieNode node = nodes.remove(0);
            Pos[] ps = positions.remove(0);
            if (node.isLeaf()) {
                System.out.printf("pos: %s%n", Arrays.asList(ps));
            }
            for (CompressedTrieEdge edge : node.getEdges()) {
                nodes.add(edge.getDestination());
                positions.add(edge.getPositions());
            }
        }
    }

    private void printSimilarityMap(List<MethodTokens> methodTokens) {
        System.out.println("--- Similarity map ---");
        for (MethodTokens mt : methodTokens) {
            mt.print();
        }
        System.out.println("--- End of similarity map ---");
    }

    @Override
    public void writeMethods(String file) throws IOException {
        Path path = Paths.get(file);
        try (BufferedWriter out = Files.newBufferedWriter(path)) {
            CompressedTrieEdge edge = root.findEdge("METHOD");
            for (Pos pos : edge.getPositions()) {
                String f = pos.getFile().replace("\\", "/");
                String s = String.format("%s|%d|%d|%d|%d", f, pos.getStart(), pos.getEnd(), pos.getStartLine(), pos.getEndLine());
                out.append(s);
                out.newLine();
            }
        }
    }

    private static class MethodPair implements Comparable<MethodPair> {

        int mid1, mid2;

        public MethodPair(int mid1, int mid2) {
            this.mid1 = min(mid1, mid2);
            this.mid2 = max(mid1, mid2);
        }

        private int min(int a, int b) {
            return a < b ? a : b;
        }

        private int max(int a, int b) {
            return a > b ? a : b;
        }

        @Override
        public int compareTo(MethodPair p) {
            if (mid1 < p.mid1) {
                return -1;
            }
            if (mid1 > p.mid1) {
                return 1;
            }
            if (mid2 < p.mid2) {
                return -1;
            }
            if (mid2 > p.mid2) {
                return 1;
            }
            return 0;
        }
    }
}
