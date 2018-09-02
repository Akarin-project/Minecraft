package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Streams;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Scheduler<K, T extends SchedulerTask<K, T>, R> {
    private static final Logger b = LogManager.getLogger();
    protected final ExecutorService a;
    private final ExecutorService c;
    private final AtomicInteger d = new AtomicInteger(1);
    private final List<CompletableFuture<R>> e = Lists.newArrayList();
    private CompletableFuture<R> f = CompletableFuture.completedFuture((Object)null);
    private CompletableFuture<R> g = CompletableFuture.completedFuture((Object)null);
    private final Supplier<Map<T, CompletableFuture<R>>> h;
    private final Supplier<Map<T, CompletableFuture<Void>>> i;
    private final T j;

    public Scheduler(String s, int ix, T schedulertask, Supplier<Map<T, CompletableFuture<R>>> supplier, Supplier<Map<T, CompletableFuture<Void>>> supplier1) {
        this.j = schedulertask;
        this.h = supplier;
        this.i = supplier1;
        if (ix == 0) {
            this.a = MoreExecutors.newDirectExecutorService();
        } else {
            this.a = Executors.newSingleThreadExecutor(new NamedIncrementingThreadFactory(s + "-Scheduler"));
        }

        if (ix <= 1) {
            this.c = MoreExecutors.newDirectExecutorService();
        } else {
            this.c = new ForkJoinPool(ix - 1, (forkjoinpool) -> {
                return new ForkJoinWorkerThread(forkjoinpool) {
                    {
                        this.setName(s + "-Worker-" + Scheduler.this.d.getAndIncrement());
                    }
                };
            }, (thread, throwable) -> {
                b.error(String.format("Caught exception in thread %s", thread), throwable);
            }, true);
        }

    }

    public CompletableFuture<R> a(K object) {
        CompletableFuture completablefuture = this.f;
        Supplier supplier = () -> {
            return this.b(object).a(completablefuture, this.j);
        };
        CompletableFuture completablefuture1 = CompletableFuture.supplyAsync(supplier, this.a);
        CompletableFuture completablefuture2 = completablefuture1.thenComposeAsync((completablefuture3) -> {
            return completablefuture3;
        }, this.c);
        this.e.add(completablefuture2);
        return completablefuture2;
    }

    public CompletableFuture<R> a() {
        CompletableFuture completablefuture = (CompletableFuture)this.e.remove(this.e.size() - 1);
        CompletableFuture completablefuture1 = CompletableFuture.allOf((CompletableFuture[])this.e.toArray(new CompletableFuture[0])).thenCompose((var1) -> {
            return completablefuture;
        });
        this.g = completablefuture1;
        this.e.clear();
        this.f = completablefuture1;
        return completablefuture1;
    }

    protected Scheduler<K, T, R>.a b(K object) {
        return this.a(object, true);
    }

    @Nullable
    protected abstract Scheduler<K, T, R>.a a(K var1, boolean var2);

    public void b() throws InterruptedException {
        this.a.shutdown();
        this.a.awaitTermination(1L, TimeUnit.DAYS);
        this.c.shutdown();
        this.c.awaitTermination(1L, TimeUnit.DAYS);
    }

    protected abstract R a(K var1, T var2, Map<K, R> var3);

    @Nullable
    public R b(K object, boolean flag) {
        Scheduler.a scheduler$a = this.a(object, flag);
        return (R)(scheduler$a != null ? scheduler$a.a() : null);
    }

    public CompletableFuture<R> c() {
        CompletableFuture completablefuture = this.g;
        return completablefuture.thenApply((object) -> {
            return object;
        });
    }

    protected abstract void b(K var1, Scheduler<K, T, R>.a var2);

    protected abstract Scheduler<K, T, R>.a a(K var1, Scheduler<K, T, R>.a var2);

    public final class a {
        private final Map<T, CompletableFuture<R>> b;
        private final K c;
        private final R d;

        public a(Object object, Object object1, SchedulerTask schedulertask) {
            this.b = (Map)Scheduler.this.h.get();
            this.c = (K)object;

            for(this.d = (R)object1; schedulertask != null; schedulertask = schedulertask.a()) {
                this.b.put(schedulertask, CompletableFuture.completedFuture(object1));
            }

        }

        public R a() {
            return this.d;
        }

        private CompletableFuture<R> a(CompletableFuture<R> completablefuture, T schedulertask) {
            ConcurrentHashMap concurrenthashmap = new ConcurrentHashMap();
            return (CompletableFuture)this.b.computeIfAbsent(schedulertask, (var4) -> {
                if (schedulertask.a() == null) {
                    return CompletableFuture.completedFuture(this.d);
                } else {
                    schedulertask.a(this.c, (object, schedulertask2) -> {
                        CompletableFuture completablefuture4 = (CompletableFuture)concurrenthashmap.put(object, Scheduler.this.a(object, Scheduler.this.b(object)).a(completablefuture, schedulertask2));
                    });
                    CompletableFuture[] acompletablefuture = (CompletableFuture[])Streams.concat(new Stream[]{Stream.of(completablefuture), concurrenthashmap.values().stream()}).toArray((i) -> {
                        return new CompletableFuture[i];
                    });
                    CompletableFuture completablefuture2 = CompletableFuture.allOf(acompletablefuture).thenApplyAsync((var3) -> {
                        return Scheduler.this.a(this.c, schedulertask, Maps.transformValues(concurrenthashmap, (completablefuture3) -> {
                            try {
                                return completablefuture3.get();
                            } catch (ExecutionException | InterruptedException interruptedexception) {
                                throw new RuntimeException(interruptedexception);
                            }
                        }));
                    }, Scheduler.this.c).thenApplyAsync((object) -> {
                        for(Object object1 : concurrenthashmap.keySet()) {
                            Scheduler.this.b(object1, Scheduler.this.b(object1));
                        }

                        return object;
                    }, Scheduler.this.a);
                    this.b.put(schedulertask, completablefuture2);
                    return completablefuture2;
                }
            });
        }
    }
}
