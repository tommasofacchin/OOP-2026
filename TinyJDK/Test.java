//package tinyjdk;



public class Test {
    public static void main(String[] args) {
        testArrayList();
        testLinkedList();
        testHashMap();
    }
    

    public static void testArrayList(){

        System.out.println("\n--- ArrayList ---");

        ArrayList<String> list = new ArrayList<>();

        list.add("a");
        list.add("b");
        list.add("c");
        System.out.println("size == 3 ? " + (list.size() == 3));

        System.out.println("contains a ? " + list.contains("a"));
        System.out.println("contains z ? " + list.contains("z"));

        System.out.println("get(0) == a ? " + "a".equals(list.get(0)));
        System.out.println("get(2) == c ? " + "c".equals(list.get(2)));

        list.set(1, "B");
        System.out.println("after set(1,B), get(1) == B ? " + "B".equals(list.get(1)));

        list.remove("a");
        System.out.println("after remove(a), size == 2 ? " + (list.size() == 2));
        System.out.println("contains a ? " + list.contains("a"));

        list.clear();
        System.out.println("after clear, size == 0 ? " + (list.size() == 0));
    }


    public static void testLinkedList() {

        System.out.println("\n--- LinkedList ---");

        LinkedList<String> list = new LinkedList<>();

        list.add("a");
        list.add("b");
        list.add("c");
        System.out.println("size == 3 ? " + (list.size() == 3));

        System.out.println("contains a ? " + list.contains("a"));
        System.out.println("contains z ? " + list.contains("z"));

        System.out.println("get(0) == a ? " + "a".equals(list.get(0)));
        System.out.println("get(2) == c ? " + "c".equals(list.get(2)));

        list.set(1, "B");
        System.out.println("after set(1,B), get(1) == B ? " + "B".equals(list.get(1)));

        list.remove("a");
        System.out.println("after remove(a), size == 2 ? " + (list.size() == 2));
        System.out.println("contains a ? " + list.contains("a"));

        list.clear();
        System.out.println("after clear, size == 0 ? " + (list.size() == 0));
    }

    public static void testHashMap() {

        System.out.println("\n--- HashMap ---");

        Map<String, Integer> m = new HashMap<>();

        m.put("a", 1);
        m.put("b", 2);
        m.put("c", 3);
        System.out.println("size == 3 ? " + (m.size() == 3));

        System.out.println("get(a) == 1 ? " + (m.get("a") == 1));
        System.out.println("get(c) == 3 ? " + (m.get("c") == 3));

        System.out.println("containsKey(a) ? " + m.containsKey("a"));
        System.out.println("containsKey(z) ? " + m.containsKey("z"));

        m.put("a", 10);
        System.out.println("after put(a,10), get(a) == 10 ? " + (m.get("a") == 10));
        System.out.println("size still == 3 ? " + (m.size() == 3));

        System.out.println("isEmpty() == false ? " + (!m.isEmpty()));

        int sum = 0;
        Iterator<Map.Pair<String, Integer>> it = m.iterator();
        while (it.hasNext()) {
            Map.Pair<String, Integer> p = it.next();
            sum += p.value;
        }

        System.out.println("iteration works, sum == (10+2+3) ? " + (sum == 15));
    }

    /*
 output expected:
 
 --- ArrayList ---
size == 3 ? true
contains a ? true
contains z ? false
get(0) == a ? true
get(2) == c ? true
after set(1,B), get(1) == B ? true
after remove(a), size == 2 ? false
contains a ? false
after clear, size == 0 ? true

--- LinkedList ---
size == 3 ? true
contains a ? true
contains z ? false
get(0) == a ? true
get(2) == c ? true
after set(1,B), get(1) == B ? true
after remove(a), size == 2 ? true
contains a ? false
after clear, size == 0 ? true

--- HashMap ---
size == 3 ? true
get(a) == 1 ? true
get(c) == 3 ? true
containsKey(a) ? true
containsKey(z) ? false
after put(a,10), get(a) == 10 ? true
size still == 3 ? true
isEmpty() == false ? true
iteration works, sum == (10+2+3) ? true    
    
    */

}