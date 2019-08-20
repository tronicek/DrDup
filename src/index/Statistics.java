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

    public void store(int lines, int astNodes, int trieNodes, int trieEdges, int positions) {
        Record rec = new Record(lines, astNodes, trieNodes, trieEdges, positions);
        records.add(rec);
    }

    public void print() {
        int last = 0;
        System.out.println("lines, AST nodes, trie nodes, trie edges, positions");
        for (Record rec : records) {
            if (rec.astNodes - last > THRESHOLD) {
                System.out.println(rec);
                last = rec.astNodes;
            }
        }
        Record rec = records.get(records.size() - 1);
        System.out.println(rec);
        System.out.printf("number of files: %d%n", records.size());
    }

    static class Record {

        int lines;
        int astNodes;
        int trieNodes;
        int trieEdges;
        int positions;

        Record(int lines, int astNodes, int trieNodes, int trieEdges, int positions) {
            this.lines = lines;
            this.astNodes = astNodes;
            this.trieNodes = trieNodes;
            this.trieEdges = trieEdges;
            this.positions = positions;
        }

        @Override
        public String toString() {
            return String.format("%d\t%d\t%d\t%d\t%d", lines, astNodes, trieNodes, trieEdges, positions);
        }
    }
}
