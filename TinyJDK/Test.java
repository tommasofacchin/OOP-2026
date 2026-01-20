//package tinyjdk;



public class Test {
    public static void main(String[] args) {

        testArrayList();

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
}