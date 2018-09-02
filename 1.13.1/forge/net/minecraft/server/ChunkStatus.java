package net.minecraft.server;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;

public enum ChunkStatus implements SchedulerTask<ChunkCoordIntPair, ChunkStatus> {
    EMPTY("empty", (ChunkTask)null, -1, false, ChunkStatus.Type.PROTOCHUNK),
    BASE("base", new ChunkTaskBase(), 0, false, ChunkStatus.Type.PROTOCHUNK),
    CARVED("carved", new ChunkTaskCarve(), 0, false, ChunkStatus.Type.PROTOCHUNK),
    LIQUID_CARVED("liquid_carved", new ChunkTaskLiquidCarve(), 1, false, ChunkStatus.Type.PROTOCHUNK),
    DECORATED("decorated", new ChunkTaskDecorate(), 1, true, ChunkStatus.Type.PROTOCHUNK) {
        public void a(ChunkCoordIntPair chunkcoordintpair, BiConsumer<ChunkCoordIntPair, ChunkStatus> biconsumer) {
            int i = chunkcoordintpair.x;
            int j = chunkcoordintpair.z;
            ChunkStatus chunkstatus = this.e();
            boolean flag = true;

            for(int k = i - 8; k <= i + 8; ++k) {
                if (k < i - 1 || k > i + 1) {
                    for(int l = j - 8; l <= j + 8; ++l) {
                        if (l < j - 1 || l > j + 1) {
                            ChunkCoordIntPair chunkcoordintpair1 = new ChunkCoordIntPair(k, l);
                            biconsumer.accept(chunkcoordintpair1, EMPTY);
                        }
                    }
                }
            }

            for(int i1 = i - 1; i1 <= i + 1; ++i1) {
                for(int j1 = j - 1; j1 <= j + 1; ++j1) {
                    ChunkCoordIntPair chunkcoordintpair2 = new ChunkCoordIntPair(i1, j1);
                    biconsumer.accept(chunkcoordintpair2, chunkstatus);
                }
            }

        }

        // $FF: synthetic method
        @Nullable
        public SchedulerTask a() {
            return super.e();
        }
    },
    LIGHTED("lighted", new ChunkTaskLight(), 1, true, ChunkStatus.Type.PROTOCHUNK),
    MOBS_SPAWNED("mobs_spawned", new ChunkTaskSpawnMobs(), 0, true, ChunkStatus.Type.PROTOCHUNK),
    FINALIZED("finalized", new ChunkTaskFinalize(), 0, true, ChunkStatus.Type.PROTOCHUNK),
    FULLCHUNK("fullchunk", new ChunkTaskNull(), 0, true, ChunkStatus.Type.LEVELCHUNK),
    POSTPROCESSED("postprocessed", new ChunkTaskNull(), 0, true, ChunkStatus.Type.LEVELCHUNK);

    private static final Map<String, ChunkStatus> k = (Map)SystemUtils.a(Maps.newHashMap(), (hashmap) -> {
        for(ChunkStatus chunkstatus : values()) {
            hashmap.put(chunkstatus.b(), chunkstatus);
        }

    });
    private final String l;
    @Nullable
    private final ChunkTask m;
    private final int n;
    private final ChunkStatus.Type o;
    private final boolean p;

    private ChunkStatus(String s1, ChunkTask chunktask, int j, @Nullable boolean flag, ChunkStatus.Type chunkstatus$type) {
        this.l = s1;
        this.m = chunktask;
        this.n = j;
        this.o = chunkstatus$type;
        this.p = flag;
    }

    public String b() {
        return this.l;
    }

    public ProtoChunk a(World world, ChunkGenerator<?> chunkgenerator, Map<ChunkCoordIntPair, ProtoChunk> map, int i, int j) {
        return this.m.a(this, world, chunkgenerator, map, i, j);
    }

    public void a(ChunkCoordIntPair chunkcoordintpair, BiConsumer<ChunkCoordIntPair, ChunkStatus> biconsumer) {
        int i = chunkcoordintpair.x;
        int j = chunkcoordintpair.z;
        ChunkStatus chunkstatus1 = this.e();

        for(int kx = i - this.n; kx <= i + this.n; ++kx) {
            for(int lx = j - this.n; lx <= j + this.n; ++lx) {
                biconsumer.accept(new ChunkCoordIntPair(kx, lx), chunkstatus1);
            }
        }

    }

    public int c() {
        return this.n;
    }

    public ChunkStatus.Type d() {
        return this.o;
    }

    @Nullable
    public static ChunkStatus a(String s) {
        return (ChunkStatus)k.get(s);
    }

    @Nullable
    public ChunkStatus e() {
        return this.ordinal() == 0 ? null : values()[this.ordinal() - 1];
    }

    public boolean f() {
        return this.p;
    }

    public boolean a(ChunkStatus chunkstatus1) {
        return this.ordinal() >= chunkstatus1.ordinal();
    }

    // $FF: synthetic method
    @Nullable
    public SchedulerTask a() {
        return this.e();
    }

    public static enum Type {
        PROTOCHUNK,
        LEVELCHUNK;

        private Type() {
        }
    }
}
