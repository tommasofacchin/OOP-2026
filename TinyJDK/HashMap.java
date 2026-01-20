public class HashMap<K, V> implements Map<K, V> {

    private List<Map.Pair<K, V>>[] table;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public HashMap() {
        table = (List<Map.Pair<K, V>>[]) new List[100];
    }

    private int index(Object key) {
        int h = key == null ? 0 : key.hashCode();
        return Math.abs(h) % table.length;
    }

    @Override
    public void put(K key, V value) {
        int i = index(key);
        if (table[i] == null) {
            table[i] = new ArrayList<>();
        }
        List<Map.Pair<K, V>> bucket = table[i];

        for (int j = 0; j < bucket.size(); j++) {
            Map.Pair<K, V> p = bucket.get(j);
            if ((p.key == null && key == null) || (p.key != null && p.key.equals(key))) {
                bucket.set(j, new Map.Pair<>(key, value)); 
                return;
            }
        }

        bucket.add(new Map.Pair<>(key, value));
        size++;
    }

    @Override
    public V get(K key) {
        int i = index(key);
        List<Map.Pair<K, V>> bucket = table[i];
        if (bucket == null) {
            throw new RuntimeException("key not found");
        }
        for (int j = 0; j < bucket.size(); j++) {
            Map.Pair<K, V> p = bucket.get(j);
            if ((p.key == null && key == null) || (p.key != null && p.key.equals(key))) {
                return p.value;
            }
        }
        throw new RuntimeException("key not found");
    }

    @Override
    public boolean containsKey(K key) {
        int i = index(key);
        List<Map.Pair<K, V>> bucket = table[i];
        if (bucket == null) return false;
        for (int j = 0; j < bucket.size(); j++) {
            Map.Pair<K, V> p = bucket.get(j);
            if ((p.key == null && key == null) || (p.key != null && p.key.equals(key))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Map.Pair<K, V>> iterator() {
        return new Iterator<Map.Pair<K, V>>() {
            int bucketIndex = 0;
            int innerIndex = 0;

            @Override
            public boolean hasNext() {
                while (bucketIndex < table.length) {
                    List<Map.Pair<K, V>> bucket = table[bucketIndex];
                    if (bucket != null && innerIndex < bucket.size()) {
                        return true;
                    }
                    bucketIndex++;
                    innerIndex = 0;
                }
                return false;
            }

            @Override
            public Map.Pair<K, V> next() {
                if (!hasNext()) {
                    throw new RuntimeException("no more elements");
                }
                return table[bucketIndex].get(innerIndex++);
            }
        };
    }
}
