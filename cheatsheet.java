import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Cheatsheet {

    @FunctionalInterface
    public interface MyIterator<T> {
        boolean hasNext();
        T next();
    }

    @FunctionalInterface
    public interface Function<T, R> {
        R apply(T value);
    }

    @FunctionalInterface
    public interface Supplier<T> {
        T get();
    }

    @FunctionalInterface
    public interface Customer<T> {
        void accept(T value);
    }

    public interface Comparable<T> {
        int compareTo(T other);
    }

    public static class ListIterator<T> implements MyIterator<T> {
        private List<T> list;
        private int index = 0;

        public ListIterator(List<T> list) {
            this.list = list;
        }

        @Override
        public boolean hasNext() {
            return index < list.size();
        }

        @Override
        public T next() {
            return list.get(index++);
        }
    }

    public static class Person implements Comparable<Person> {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public int compareTo(Person other) {
            // return this.age - other.age
            return Integer.compare(this.age, other.age);
        }

        @Override
        public String toString() {
            return name + " (" + age + ")";
        }
    }


    public static void main(String[] args) {

        // --- Iterator ---
        List<Integer> numbers = new ArrayList<>();
        numbers.add(10);
        numbers.add(20);
        numbers.add(30);

        MyIterator<Integer> it = new ListIterator<>(numbers);

        while (it.hasNext()) {
            Integer x = it.next();
            System.out.println("Iterator value = " + x);
        }

        // --- Supplier and Customer ---
        Supplier<String> helloSupplier = () -> "Hello from supplier";

        Customer<String> stringCustomer =
                msg -> System.out.println("Customer received: " + msg);

        String value = helloSupplier.get();
        stringCustomer.accept(value);

        // --- Function ---
        Function<Integer, String> intToString =
                x -> "Number is: " + x;

        String result = intToString.apply(10);
        System.out.println(result);

        Function<Integer, Integer> doubler =
                x -> x * 2;

        int doubled = doubler.apply(5);
        System.out.println("doubled = " + doubled);


        // --- Comparable ---
        List<Person> people = new ArrayList<>();
        people.add(new Person("Alice", 30));
        people.add(new Person("Bob", 20));
        people.add(new Person("Charlie", 25));

        Collections.sort(people); // uses Person.compareTo

        for (Person p : people) {
            System.out.println(p);
        }

        // --- Map ---
        Map<String, Integer> ages = new HashMap<>();

        ages.put("Alice", 30);
        ages.put("Bob", 20);
        ages.put("Charlie", 25);

        System.out.println("Bob age = " + ages.get("Bob"));

        ages.forEach((name, age) ->
                System.out.println("Key = " + name + ", Value = " + age));

        if (ages instanceof Map) {
            System.out.println("ages is a Map with size = " + ages.size());
        }
    }
}
