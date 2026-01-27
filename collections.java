import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

interface Iterable<T> {
    Iterator<T> iterator();
}

interface Iterator<T> {
    boolean hasNext();
    T next();
    default void remove() { throw new UnsupportedOperationException(); }
}

// Collection<E>
interface Collection<E> extends Iterable<E> {
    int size();
    boolean isEmpty();
    boolean contains(Object o);
    boolean add(E e);
    boolean remove(Object o);
    void clear();

    Object[] toArray();
    <T> T[] toArray(T[] a);

    boolean containsAll(Collection<?> c);
    boolean addAll(Collection<? extends E> c);
    boolean removeAll(Collection<?> c);
    boolean retainAll(Collection<?> c);

    default void forEach(Consumer<? super E> action) { }
    default boolean removeIf(Predicate<? super E> filter) { return false; }
    default Spliterator<E> spliterator() { return null; }
}

// List<E>
interface List<E> extends Collection<E> {
    E get(int index);
    E set(int index, E element);

    void add(int index, E element);
    boolean add(E e);
    boolean addAll(int index, Collection<? extends E> c);

    E remove(int index);
    boolean remove(Object o);

    int indexOf(Object o);
    int lastIndexOf(Object o);

    ListIterator<E> listIterator();
    ListIterator<E> listIterator(int index);
    List<E> subList(int fromIndex, int toIndex);
}

// ListIterator<E>
interface ListIterator<E> extends Iterator<E> {
    boolean hasNext();
    E next();
    boolean hasPrevious();
    E previous();
    int nextIndex();
    int previousIndex();
    void remove();
    void set(E e);
    void add(E e);
}

// ArrayList<E>
class ArrayList<E> implements List<E> {

    private Object[] elementData;
    private int size;

    public ArrayList() { }
    public ArrayList(int initialCapacity) { }
    public ArrayList(Collection<? extends E> c) { }

    public int size() { return 0; }
    public boolean isEmpty() { return false; }
    public boolean contains(Object o) { return false; }

    public E get(int index) { return null; }
    public E set(int index, E element) { return null; }

    public boolean add(E e) { return false; }
    public void add(int index, E element) { }
    public boolean addAll(Collection<? extends E> c) { return false; }
    public boolean addAll(int index, Collection<? extends E> c) { return false; }

    public E remove(int index) { return null; }
    public boolean remove(Object o) { return false; }
    public void clear() { }

    public int indexOf(Object o) { return -1; }
    public int lastIndexOf(Object o) { return -1; }

    public Object[] toArray() { return null; }
    public <T> T[] toArray(T[] a) { return null; }

    public boolean containsAll(Collection<?> c) { return false; }
    public boolean removeAll(Collection<?> c) { return false; }
    public boolean retainAll(Collection<?> c) { return false; }

    public Iterator<E> iterator() { return null; }
    public ListIterator<E> listIterator() { return null; }
    public ListIterator<E> listIterator(int index) { return null; }
    public List<E> subList(int fromIndex, int toIndex) { return null; }

    public Object clone() { return null; }
    public void ensureCapacity(int minCapacity) { }
    public void trimToSize() { }
}

// LinkedList<E>
class LinkedList<E> implements List<E> {

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;
        Node(Node<E> prev, E element, Node<E> next) { }
    }

    private int size = 0;
    private Node<E> first;
    private Node<E> last;

    public LinkedList() { }
    public LinkedList(Collection<? extends E> c) { }

    public int size() { return 0; }
    public boolean isEmpty() { return false; }
    public boolean contains(Object o) { return false; }

    public E get(int index) { return null; }
    public E set(int index, E element) { return null; }

    public boolean add(E e) { return false; }
    public void add(int index, E element) { }
    public boolean addAll(Collection<? extends E> c) { return false; }
    public boolean addAll(int index, Collection<? extends E> c) { return false; }

    public E remove(int index) { return null; }
    public boolean remove(Object o) { return false; }
    public void clear() { }

    public int indexOf(Object o) { return -1; }
    public int lastIndexOf(Object o) { return -1; }

    public Object[] toArray() { return null; }
    public <T> T[] toArray(T[] a) { return null; }

    public boolean containsAll(Collection<?> c) { return false; }
    public boolean removeAll(Collection<?> c) { return false; }
    public boolean retainAll(Collection<?> c) { return false; }

    public Iterator<E> iterator() { return null; }
    public ListIterator<E> listIterator() { return null; }
    public ListIterator<E> listIterator(int index) { return null; }
    public List<E> subList(int fromIndex, int toIndex) { return null; }

    public void addFirst(E e) { }
    public void addLast(E e) { }
    public E getFirst() { return null; }
    public E getLast() { return null; }
    public E removeFirst() { return null; }
    public E removeLast() { return null; }

    public boolean offerFirst(E e) { return false; }
    public boolean offerLast(E e) { return false; }
    public E peekFirst() { return null; }
    public E peekLast() { return null; }
}

// Set<E>
interface Set<E> extends Collection<E> {
    boolean add(E e);
    boolean addAll(Collection<? extends E> c);
    boolean contains(Object o);
    boolean containsAll(Collection<?> c);
    boolean remove(Object o);
    boolean removeAll(Collection<?> c);
    boolean retainAll(Collection<?> c);
}

// HashSet<E>
class HashSet<E> implements Set<E> {

    private transient HashMap<E, Object> map;
    private static final Object PRESENT = new Object();

    public HashSet() { }
    public HashSet(Collection<? extends E> c) { }
    public HashSet(int initialCapacity, float loadFactor) { }

    public int size() { return 0; }
    public boolean isEmpty() { return false; }
    public boolean contains(Object o) { return false; }

    public boolean add(E e) { return false; }
    public boolean remove(Object o) { return false; }
    public void clear() { }

    public Iterator<E> iterator() { return null; }
    public Object[] toArray() { return null; }
    public <T> T[] toArray(T[] a) { return null; }

    public boolean containsAll(Collection<?> c) { return false; }
    public boolean addAll(Collection<? extends E> c) { return false; }
    public boolean retainAll(Collection<?> c) { return false; }
    public boolean removeAll(Collection<?> c) { return false; }
}

// Map<K, V>
interface Map<K, V> {

    int size();
    boolean isEmpty();

    boolean containsKey(Object key);
    boolean containsValue(Object value);

    V get(Object key);
    V put(K key, V value);
    V remove(Object key);

    void putAll(Map<? extends K, ? extends V> m);
    void clear();

    Set<K> keySet();
    Collection<V> values();
    Set<Map.Entry<K, V>> entrySet();

    default V getOrDefault(Object key, V defaultValue) { return null; }
    default void forEach(BiConsumer<? super K, ? super V> action) { }
    default void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) { }

    default V putIfAbsent(K key, V value) { return null; }
    default boolean remove(Object key, Object value) { return false; }
    default boolean replace(K key, V oldValue, V newValue) { return false; }
    default V replace(K key, V value) { return null; }

    default V computeIfAbsent(K key,
            Function<? super K, ? extends V> mappingFunction) { return null; }

    default V computeIfPresent(K key,
            BiFunction<? super K, ? super V, ? extends V> remappingFunction) { return null; }

    default V compute(K key,
            BiFunction<? super K, ? super V, ? extends V> remappingFunction) { return null; }

    default V merge(K key, V value,
            BiFunction<? super V, ? super V, ? extends V> remappingFunction) { return null; }

    interface Entry<K, V> {
        K getKey();
        V getValue();
        V setValue(V value);
    }
}

// HashMap<K, V>
class HashMap<K, V> implements Map<K, V> {

    static class Node<K, V> implements Map.Entry<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey() { return key; }
        public V getValue() { return value; }
        public V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }
    }

    transient Node<K, V>[] table;
    transient int size;
    int threshold;
    final float loadFactor;

    public HashMap() { this.loadFactor = 0.75f; }
    public HashMap(int initialCapacity) { this.loadFactor = 0.75f; }
    public HashMap(int initialCapacity, float loadFactor) { this.loadFactor = loadFactor; }
    public HashMap(Map<? extends K, ? extends V> m) { this.loadFactor = 0.75f; }

    public int size() { return 0; }
    public boolean isEmpty() { return false; }

    public boolean containsKey(Object key) { return false; }
    public boolean containsValue(Object value) { return false; }

    public V get(Object key) { return null; }
    public V put(K key, V value) { return null; }
    public V remove(Object key) { return null; }

    public void putAll(Map<? extends K, ? extends V> m) { }
    public void clear() { }

    public Set<K> keySet() { return null; }
    public Collection<V> values() { return null; }
    public Set<Map.Entry<K, V>> entrySet() { return null; }

    public V getOrDefault(Object key, V defaultValue) { return null; }
    public void forEach(BiConsumer<? super K, ? super V> action) { }
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) { }

    public V putIfAbsent(K key, V value) { return null; }
    public boolean remove(Object key, Object value) { return false; }
    public boolean replace(K key, V oldValue, V newValue) { return false; }
    public V replace(K key, V value) { return null; }

    public V computeIfAbsent(K key,
            Function<? super K, ? extends V> mappingFunction) { return null; }

    public V computeIfPresent(K key,
            BiFunction<? super K, ? super V, ? extends V> remappingFunction) { return null; }

    public V compute(K key,
            BiFunction<? super K, ? super V, ? extends V> remappingFunction) { return null; }

    public V merge(K key, V value,
            BiFunction<? super V, ? super V, ? extends V> remappingFunction) { return null; }

    public Object clone() { return null; }
}

// SortedSet<E>
interface SortedSet<E> extends Set<E> {
    Comparator<? super E> comparator();
    SortedSet<E> subSet(E fromElement, E toElement);
    SortedSet<E> headSet(E toElement);
    SortedSet<E> tailSet(E fromElement);
    E first();
    E last();
}

// SortedMap<K, V>
interface SortedMap<K, V> extends Map<K, V> {
    Comparator<? super K> comparator();
    SortedMap<K, V> subMap(K fromKey, K toKey);
    SortedMap<K, V> headMap(K toKey);
    SortedMap<K, V> tailMap(K fromKey);
    K firstKey();
    K lastKey();
    java.util.Set<K> keySet();
    java.util.Collection<V> values();
    java.util.Set<Map.Entry<K, V>> entrySet();
}