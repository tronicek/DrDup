package plain2;

import index.Pos;
import index.rename.RenameStrategy;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import plain.TrieNode;

/**
 * The implementation of stack.
 * 
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class Stack2 implements Iterable<StackNode2> {

    private final Deque<StackNode2> nodes = new ArrayDeque<>();

    public void push(TrieNode node, Pos pos, RenameStrategy renameStrategy) {
        StackNode2 p = new StackNode2(node, pos, renameStrategy);
        nodes.addLast(p);
    }

    public void push(StackNode2 node) {
        nodes.addLast(node);
    }

    public StackNode2 pop() {
        return nodes.removeLast();
    }

    @Override
    public Iterator<StackNode2> iterator() {
        return nodes.iterator();
    }
    
    @Override
    public String toString() {
        return nodes.toString();
    }
}
