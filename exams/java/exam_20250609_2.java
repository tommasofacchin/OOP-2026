import javafx.util.Pair;
import java.util.Iterator;
import java.util.function.Function;

public class FunSeq<X extends Number & Comparable<? super X>, Y extends Number> implements Iterable<Pair<X, Y>> {
    private final X a, b;
    private final Function<X, Y> f;
    private final Function<X, X> inc;

    public FunSeq(X a, X b, Function<X, Y> f, Function<X, X> inc) {
        this.a = a;
        this.b = b;
        this.f = f;
        this.inc = inc;
    }

    @Override
    public Iterator<Pair<X, Y>> iterator(){
        return new Iterator<>(){
            private X x = a;

            @Override
            public boolean hasNext(){
                return x.compareTo(b) < 0;
            }

            @Override 
            public Pair<X, Y> next(){
                Pair<X, Y> r = new Pair<>(x, f.apply(x));
                x = inc.apply(x);
                return r;
            }
        };
    }

    public static void test() {

        FunSeq<Double, Double> seq =
                new FunSeq<>(
                        -2.0,
                        2.0,
                        x -> x * x - 2 * x + 1,   
                        x -> x + 0.1              
                );

        for (Pair<Double, Double> p : seq) {
            double x = p.getKey();
            double y = p.getValue();
            System.out.printf("f(%g) = %g%n", x, y);
        }

    }

}
