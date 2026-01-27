import java.util.*;

public class SimpleSet<T> implements Collection<T> {

    private final List<T> backing = new ArrayList<>();

    public SimpleSet(List<T> elements) {
        for (T e : elements) {
            if (!backing.contains(e)) {
                backing.add(e);
            }
        }
    }
    
    @Override
    public int size() {
        return backing.size();
    }

    @Override
    public boolean isEmpty() {
        return backing.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return backing.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return backing.iterator();
    }

    @Override
    public Object[] toArray() {
        return backing.toArray();
    }

    @Override
    public <E> E[] toArray(E[] a) {
        return backing.toArray(a);
    }

    @Override
    public boolean add(T t) {
        if (backing.contains(t)) {
            return false;         
        }
        return backing.add(t);
    }

    @Override
    public boolean remove(Object o) {
        return backing.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return backing.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        boolean modified = false;
        for (T e : c) {
            if (add(e)) {          
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return backing.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return backing.retainAll(c);
    }

    @Override
    public void clear() {
        backing.clear();
    }
}
