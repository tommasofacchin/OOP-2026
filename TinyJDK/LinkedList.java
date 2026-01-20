
public class LinkedList<T> implements List<T> {

    /*
    struct node {
         int data;
         struct node* next;
    }
     */

    private class Node {
        T data;
        Node next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head = null;

    public LinkedList() {
    }

    @Override
    public void add(T e) {
        if (head == null) {
            head = new Node(e);
        } else {
            Node n = head;
            while (n.next != null) {
                n = n.next;
            }
            n.next = new Node(e);
        }
    }


    @Override
    public int size() {
        int cnt = 0;
        for (Node n = head; n != null; ++cnt, n = n.next);
        return cnt;
    }


    private Node seek(int i) {
        assert (i >= 0);
        Node n = head;
        for (; i-- > 0; n = n.next) {
            if (n.next == null)
                throw new RuntimeException();
        }
        return n;
    }

    @Override
    public T get(int i) {
        return seek(i).data;
    }

    @Override
    public void set(int i, T e) {
        seek(i).data = e;
    }

    @Override
    public boolean contains(T o) {
        Node n = head;
        while (n != null) {
            if (n.data.equals(o)) return true;
            n = n.next;
        }
        return false;
    }

    @Override
    public void remove(T o) {
        if (head == null) return;

        if (head.data.equals(o)) {
            head = head.next;
            return;
        }

        Node prev = head;
        Node cur = head.next;

        while (cur != null) {
            if (cur.data.equals(o)) {
                prev.next = cur.next;  
                return;
            }
            prev = cur;
            cur = cur.next;
        }
    }


    @Override
    public void clear() {
        head = null;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T r = current.data;
                current = current.next;
                return r;
            }
        };
    }
}
