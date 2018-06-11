package plain2;

import index.Pos;
import index.rename.RenameStrategy;
import plain.TrieNode;

/**
 * The stack node.
 * 
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class StackNode2 {

    private final TrieNode node;
    private final Pos pos;
    private final RenameStrategy renameStrategy;

    public StackNode2(TrieNode node, Pos pos, RenameStrategy renameStrategy) {
        this.node = node;
        this.pos = pos;
        this.renameStrategy = renameStrategy;
    }

    public TrieNode getNode() {
        return node;
    }

    public Pos getPos() {
        return pos;
    }

    public RenameStrategy getRenameStrategy() {
        return renameStrategy;
    }

    @Override
    public String toString() {
        return String.format("%s:%s", node, pos);
    }
}
