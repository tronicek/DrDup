package index;

import java.util.ArrayList;
import java.util.List;

/**
 * The class that stores the number of AST nodes, the number of nodes and edges
 * of the TRIE, and the number of positions.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class Statistics {

    private static final int THRESHOLD = 20000;
    private final List<Record> records = new ArrayList<>();

    public void store(int astNodes, int trieNodes, int trieEdges, int positions) {
        Record rec = new Record(astNodes, trieNodes, trieEdges, positions);
        records.add(rec);
    }

    public void print() {
        int last = 0;
        System.out.println("AST nodes, trie nodes, trie edges, positions");
        for (Record rec : records) {
            if (rec.astNodes - last > THRESHOLD) {
                System.out.println(rec);
                last = rec.astNodes;
            }
        }
        Record rec = records.get(records.size() - 1);
        System.out.println(rec);
    }

    static class Record {

        int astNodes;
        int trieNodes;
        int trieEdges;
        int positions;

        Record(int astNodes, int trieNodes, int trieEdges, int positions) {
            this.astNodes = astNodes;
            this.trieNodes = trieNodes;
            this.trieEdges = trieEdges;
            this.positions = positions;
        }

        @Override
        public String toString() {
            return String.format("%d\t%d\t%d\t%d", astNodes, trieNodes, trieEdges, positions);
        }
    }
}
