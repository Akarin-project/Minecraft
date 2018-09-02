package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class WeightedRandom {
    public static int a(List<? extends WeightedRandom.WeightedRandomChoice> list) {
        int i = 0;
        int j = 0;

        for(int k = list.size(); j < k; ++j) {
            WeightedRandom.WeightedRandomChoice weightedrandom$weightedrandomchoice = (WeightedRandom.WeightedRandomChoice)list.get(j);
            i += weightedrandom$weightedrandomchoice.a;
        }

        return i;
    }

    public static <T extends WeightedRandom.WeightedRandomChoice> T a(Random random, List<T> list, int i) {
        if (i <= 0) {
            throw new IllegalArgumentException();
        } else {
            int j = random.nextInt(i);
            return (T)a(list, j);
        }
    }

    public static <T extends WeightedRandom.WeightedRandomChoice> T a(List<T> list, int i) {
        int j = 0;

        for(int k = list.size(); j < k; ++j) {
            WeightedRandom.WeightedRandomChoice weightedrandom$weightedrandomchoice = (WeightedRandom.WeightedRandomChoice)list.get(j);
            i -= weightedrandom$weightedrandomchoice.a;
            if (i < 0) {
                return (T)weightedrandom$weightedrandomchoice;
            }
        }

        return (T)null;
    }

    public static <T extends WeightedRandom.WeightedRandomChoice> T a(Random random, List<T> list) {
        return (T)a(random, list, a(list));
    }

    public static class WeightedRandomChoice {
        protected int a;

        public WeightedRandomChoice(int i) {
            this.a = i;
        }
    }
}
