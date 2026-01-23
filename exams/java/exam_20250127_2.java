import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.Iterator;

public class Maps {
    public static <A, B> Collection<B> map1(Iterable<A> c, Function<A, B> f) {
        Collection<B> result = new ArrayList<>();
        for (A x : c) {
            result.add(f.apply(x));
        }
        return result;
    }

  public static <A, B> Iterator<B> map2(Iterator<A> it, Function<A, B> f) {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public B next() {
                A x = it.next();
                return f.apply(x);
            }
        };
    }
}
