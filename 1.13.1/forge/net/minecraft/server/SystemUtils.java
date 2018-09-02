package net.minecraft.server;

import com.google.common.collect.Iterators;
import it.unimi.dsi.fastutil.Hash.Strategy;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.function.Consumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SystemUtils {
    public static LongSupplier a = System::nanoTime;
    private static final Logger b = LogManager.getLogger();
    private static final Pattern c = Pattern.compile(".*\\.|(?:CON|PRN|AUX|NUL|COM1|COM2|COM3|COM4|COM5|COM6|COM7|COM8|COM9|LPT1|LPT2|LPT3|LPT4|LPT5|LPT6|LPT7|LPT8|LPT9)(?:\\..*)?", 2);

    public static <K, V> Collector<Entry<? extends K, ? extends V>, ?, Map<K, V>> a() {
        return Collectors.toMap(Entry::getKey, Entry::getValue);
    }

    public static <T extends Comparable<T>> String a(IBlockState<T> iblockstate, Object object) {
        return iblockstate.a((Comparable)object);
    }

    public static String a(String s, @Nullable MinecraftKey minecraftkey) {
        return minecraftkey == null ? s + ".unregistered_sadface" : s + '.' + minecraftkey.b() + '.' + minecraftkey.getKey().replace('/', '.');
    }

    public static long b() {
        return c() / 1000000L;
    }

    public static long c() {
        return a.getAsLong();
    }

    public static long d() {
        return Instant.now().toEpochMilli();
    }

    public static SystemUtils.OS e() {
        String s = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        if (s.contains("win")) {
            return SystemUtils.OS.WINDOWS;
        } else if (s.contains("mac")) {
            return SystemUtils.OS.OSX;
        } else if (s.contains("solaris")) {
            return SystemUtils.OS.SOLARIS;
        } else if (s.contains("sunos")) {
            return SystemUtils.OS.SOLARIS;
        } else if (s.contains("linux")) {
            return SystemUtils.OS.LINUX;
        } else {
            return s.contains("unix") ? SystemUtils.OS.LINUX : SystemUtils.OS.UNKNOWN;
        }
    }

    public static Stream<String> f() {
        RuntimeMXBean runtimemxbean = ManagementFactory.getRuntimeMXBean();
        return runtimemxbean.getInputArguments().stream().filter((s) -> {
            return s.startsWith("-X");
        });
    }

    public static boolean a(java.nio.file.Path path) {
        java.nio.file.Path path1 = path.normalize();
        return path1.equals(path);
    }

    public static boolean b(java.nio.file.Path path) {
        for(java.nio.file.Path path1 : path) {
            if (c.matcher(path1.toString()).matches()) {
                return false;
            }
        }

        return true;
    }

    public static java.nio.file.Path a(java.nio.file.Path path, String s, String s1) {
        String s2 = s + s1;
        java.nio.file.Path path1 = Paths.get(s2);
        if (path1.endsWith(s1)) {
            throw new InvalidPathException(s2, "empty resource name");
        } else {
            return path.resolve(path1);
        }
    }

    @Nullable
    public static <V> V a(FutureTask<V> futuretask, Logger logger) {
        try {
            futuretask.run();
            return (V)futuretask.get();
        } catch (ExecutionException executionexception) {
            logger.fatal("Error executing task", executionexception);
        } catch (InterruptedException interruptedexception) {
            logger.fatal("Error executing task", interruptedexception);
        }

        return (V)null;
    }

    public static <T> T a(List<T> list) {
        return (T)list.get(list.size() - 1);
    }

    public static <T> T a(Iterable<T> iterable, @Nullable T object) {
        Iterator iterator = iterable.iterator();
        Object object1 = iterator.next();
        if (object != null) {
            Object object2 = object1;

            while(object2 != object) {
                if (iterator.hasNext()) {
                    object2 = iterator.next();
                }
            }

            if (iterator.hasNext()) {
                return (T)iterator.next();
            }
        }

        return (T)object1;
    }

    public static <T> T b(Iterable<T> iterable, @Nullable T object) {
        Iterator iterator = iterable.iterator();

        Object object1;
        Object object2;
        for(object1 = null; iterator.hasNext(); object1 = object2) {
            object2 = iterator.next();
            if (object2 == object) {
                if (object1 == null) {
                    object1 = iterator.hasNext() ? Iterators.getLast(iterator) : object;
                }
                break;
            }
        }

        return (T)object1;
    }

    public static <T> T a(Supplier<T> supplier) {
        return (T)supplier.get();
    }

    public static <T> T a(T object, Consumer<T> consumer) {
        consumer.accept(object);
        return (T)object;
    }

    public static <K> Strategy<K> g() {
        return SystemUtils.IdentityHashingStrategy.INSTANCE;
    }

    static enum IdentityHashingStrategy implements Strategy<Object> {
        INSTANCE;

        private IdentityHashingStrategy() {
        }

        public int hashCode(Object object) {
            return System.identityHashCode(object);
        }

        public boolean equals(Object object, Object object1) {
            return object == object1;
        }
    }

    public static enum OS {
        LINUX,
        SOLARIS,
        WINDOWS {
        },
        OSX {
        },
        UNKNOWN;

        private OS() {
        }
    }
}
