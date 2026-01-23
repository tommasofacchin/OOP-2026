import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class FiboSequence implements Iterable<Integer> {
    private final int length;             
    private final List<Integer> cache;     

    public FiboSequence(int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length must be >= 0");
        }
        this.length = length;
        this.cache = new ArrayList<>();
        if (length > 0) cache.add(0);      
        if (length > 1) cache.add(1);     
    }

    @Override
    public Iterator<Integer> iterator() {
        return new FiboIterator();
    }

    private class FiboIterator implements Iterator<Integer> {
        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < length;
        }
        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            if (index < cache.size()) {
                return cache.get(index++);
            }

            int size = cache.size();              
            int nextFib = cache.get(size - 1) + cache.get(size - 2);
            cache.add(nextFib);                  
            index++;
            return nextFib;
        }
    }
}
