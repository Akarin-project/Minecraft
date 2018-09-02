package net.minecraft.server;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkTaskScheduler extends Scheduler<ChunkCoordIntPair, ChunkStatus, ProtoChunk> {
    private static final Logger b = LogManager.getLogger();
    private final World c;
    private final ChunkGenerator<?> d;
    private final IChunkLoader e;
    private final IAsyncTaskHandler f;
    private final Long2ObjectMap<Scheduler<ChunkCoordIntPair, ChunkStatus, ProtoChunk>.a> progressCache = new ExpiringMap<Scheduler<ChunkCoordIntPair, ChunkStatus, ProtoChunk>.a>(8192, 5000) {
        protected boolean a(Scheduler<ChunkCoordIntPair, ChunkStatus, ProtoChunk>.a scheduler$a) {
            ProtoChunk protochunk = (ProtoChunk)scheduler$a.a();
            return !protochunk.ab_() && !protochunk.h();
        }
    };

    public ChunkTaskScheduler(int i, World world, ChunkGenerator<?> chunkgenerator, IChunkLoader ichunkloader, IAsyncTaskHandler iasynctaskhandler) {
        super("WorldGen", i, ChunkStatus.FINALIZED, () -> {
            return new EnumMap(ChunkStatus.class);
        }, () -> {
            return new EnumMap(ChunkStatus.class);
        });
        this.c = world;
        this.d = chunkgenerator;
        this.e = ichunkloader;
        this.f = iasynctaskhandler;
    }

    @Nullable
    protected Scheduler<ChunkCoordIntPair, ChunkStatus, ProtoChunk>.a a(ChunkCoordIntPair chunkcoordintpair, boolean flag) {
        synchronized(this.e) {
            return flag ? (Scheduler.a)this.progressCache.computeIfAbsent(chunkcoordintpair.a(), (var2) -> {
                ProtoChunk protochunk;
                try {
                    protochunk = this.e.b(this.c, chunkcoordintpair.x, chunkcoordintpair.z, (var0) -> {
                    });
                } catch (ReportedException reportedexception) {
                    throw reportedexception;
                } catch (Exception exception) {
                    b.error("Couldn't load protochunk", exception);
                    protochunk = null;
                }

                if (protochunk != null) {
                    protochunk.setLastSaved(this.c.getTime());
                    return new Scheduler.a(chunkcoordintpair, protochunk, protochunk.i());
                } else {
                    return new Scheduler.a(chunkcoordintpair, new ProtoChunk(chunkcoordintpair, ChunkConverter.a), ChunkStatus.EMPTY);
                }
            }) : (Scheduler.a)this.progressCache.get(chunkcoordintpair.a());
        }
    }

    protected ProtoChunk a(ChunkCoordIntPair chunkcoordintpair, ChunkStatus chunkstatus, Map<ChunkCoordIntPair, ProtoChunk> map) {
        return chunkstatus.a(this.c, this.d, map, chunkcoordintpair.x, chunkcoordintpair.z);
    }

    protected Scheduler<ChunkCoordIntPair, ChunkStatus, ProtoChunk>.a a(ChunkCoordIntPair var1, Scheduler<ChunkCoordIntPair, ChunkStatus, ProtoChunk>.a scheduler$a) {
        ((ProtoChunk)scheduler$a.a()).a(1);
        return scheduler$a;
    }

    protected void b(ChunkCoordIntPair var1, Scheduler<ChunkCoordIntPair, ChunkStatus, ProtoChunk>.a scheduler$a) {
        ((ProtoChunk)scheduler$a.a()).a(-1);
    }

    public void a(BooleanSupplier booleansupplier) {
        synchronized(this.e) {
            ObjectIterator objectiterator = this.progressCache.values().iterator();

            while(objectiterator.hasNext()) {
                Scheduler.a scheduler$a = (Scheduler.a)objectiterator.next();
                ProtoChunk protochunk = (ProtoChunk)scheduler$a.a();
                if (protochunk.h() && protochunk.i().d() == ChunkStatus.Type.PROTOCHUNK) {
                    try {
                        protochunk.setLastSaved(this.c.getTime());
                        this.e.saveChunk(this.c, protochunk);
                        protochunk.a(false);
                    } catch (IOException ioexception) {
                        b.error("Couldn't save chunk", ioexception);
                    } catch (ExceptionWorldConflict exceptionworldconflict) {
                        b.error("Couldn't save chunk; already in use by another instance of Minecraft?", exceptionworldconflict);
                    }
                }

                if (!booleansupplier.getAsBoolean()) {
                    return;
                }
            }

        }
    }
}
