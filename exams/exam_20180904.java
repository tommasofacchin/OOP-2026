import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Random;

public class Es1 {

    // 1.b
    public static class RandomSingleton {
        private RandomSingleton() {
        }

        @Nullable
        private static Random instance;

        @NotNull
        private static Random instance() {
            if (instance == null)
                instance = new Random();
            return instance;
        }

        public void setSeed(int seed) {
            instance = new Random(seed);
        }

        public static int nextInt() {
            return instance().nextInt();
        }

        public static boolean nextBoolean() {
            return instance().nextBoolean();
        }

        public static double nextDouble() {
            return instance().nextDouble();
        }

    }

    // 1.c
    public static class RandomIterator implements Iterator<Integer> {
        private int len;

        public RandomIterator(int len) {
            this.len = len;
        }

        @Override
        public boolean hasNext() {
            return len > 0;
        }

        @Override
        public Integer next() {
            --len;
            return RandomSingleton.nextInt();
        }
    }

}
