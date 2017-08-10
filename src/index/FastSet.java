package index;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * The implementation of a set.
 *
 * @author Zdenek Tronicek, tronicek@tarleton.edu
 */
public class FastSet<T> implements Set<T> {

    private Object[] items;
    private int size;

    public FastSet(int initialSize) {
        items = new Object[initialSize];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object obj) {
        for (int i = 0; i < size; i++) {
            if (obj.equals(items[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new FastSetIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] p = new Object[size];
        System.arraycopy(items, 0, p, 0, size);
        return p;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        Object[] p = new Object[size];
        System.arraycopy(items, 0, p, 0, size);
        return (T[]) p;
    }

    @Override
    public boolean add(T item) {
        // no check if item is already present
        if (size == items.length) {
            Object[] p = new Object[items.length * 2];
            System.arraycopy(items, 0, p, 0, items.length);
            items = p;
        }
        items[size] = item;
        size++;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> col) {
        for (Object o : col) {
            T t = (T) o;
            if (!contains(t)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public boolean remove(Object obj) {
        throw new UnsupportedOperationException("Operation not supported.");
    }

    @Override
    public boolean addAll(Collection<? extends T> col) {
        throw new UnsupportedOperationException("Operation not supported.");
    }

    @Override
    public boolean retainAll(Collection<?> col) {
        throw new UnsupportedOperationException("Operation not supported.");
    }

    @Override
    public boolean removeAll(Collection<?> col) {
        throw new UnsupportedOperationException("Operation not supported.");
    }

    private class FastSetIterator implements Iterator<T> {

        private int index;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            Object p = items[index];
            index++;
            return (T) p;
        }
    }
}
