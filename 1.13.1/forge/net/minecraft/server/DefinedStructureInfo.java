package net.minecraft.server;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class DefinedStructureInfo {
    private EnumBlockMirror a = EnumBlockMirror.NONE;
    private EnumBlockRotation b = EnumBlockRotation.NONE;
    private BlockPosition c = new BlockPosition(0, 0, 0);
    private boolean d;
    @Nullable
    private Block e;
    @Nullable
    private ChunkCoordIntPair f;
    @Nullable
    private StructureBoundingBox g;
    private boolean h = true;
    private boolean i = true;
    private float j = 1.0F;
    @Nullable
    private Random k;
    @Nullable
    private Long l;
    @Nullable
    private Integer m;
    private int n;

    public DefinedStructureInfo() {
    }

    public DefinedStructureInfo a() {
        DefinedStructureInfo definedstructureinfo1 = new DefinedStructureInfo();
        definedstructureinfo1.a = this.a;
        definedstructureinfo1.b = this.b;
        definedstructureinfo1.c = this.c;
        definedstructureinfo1.d = this.d;
        definedstructureinfo1.e = this.e;
        definedstructureinfo1.f = this.f;
        definedstructureinfo1.g = this.g;
        definedstructureinfo1.h = this.h;
        definedstructureinfo1.i = this.i;
        definedstructureinfo1.j = this.j;
        definedstructureinfo1.k = this.k;
        definedstructureinfo1.l = this.l;
        definedstructureinfo1.m = this.m;
        definedstructureinfo1.n = this.n;
        return definedstructureinfo1;
    }

    public DefinedStructureInfo a(EnumBlockMirror enumblockmirror) {
        this.a = enumblockmirror;
        return this;
    }

    public DefinedStructureInfo a(EnumBlockRotation enumblockrotation) {
        this.b = enumblockrotation;
        return this;
    }

    public DefinedStructureInfo a(BlockPosition blockposition) {
        this.c = blockposition;
        return this;
    }

    public DefinedStructureInfo a(boolean flag) {
        this.d = flag;
        return this;
    }

    public DefinedStructureInfo a(Block block) {
        this.e = block;
        return this;
    }

    public DefinedStructureInfo a(ChunkCoordIntPair chunkcoordintpair) {
        this.f = chunkcoordintpair;
        return this;
    }

    public DefinedStructureInfo a(StructureBoundingBox structureboundingbox) {
        this.g = structureboundingbox;
        return this;
    }

    public DefinedStructureInfo a(@Nullable Long olong) {
        this.l = olong;
        return this;
    }

    public DefinedStructureInfo a(@Nullable Random random) {
        this.k = random;
        return this;
    }

    public DefinedStructureInfo a(float fx) {
        this.j = fx;
        return this;
    }

    public EnumBlockMirror b() {
        return this.a;
    }

    public DefinedStructureInfo c(boolean flag) {
        this.h = flag;
        return this;
    }

    public EnumBlockRotation c() {
        return this.b;
    }

    public BlockPosition d() {
        return this.c;
    }

    public Random b(@Nullable BlockPosition blockposition) {
        if (this.k != null) {
            return this.k;
        } else if (this.l != null) {
            return this.l == 0L ? new Random(SystemUtils.b()) : new Random(this.l);
        } else {
            return blockposition == null ? new Random(SystemUtils.b()) : SeededRandom.a(blockposition.getX(), blockposition.getZ(), 0L, 987234911L);
        }
    }

    public float g() {
        return this.j;
    }

    public boolean h() {
        return this.d;
    }

    @Nullable
    public Block i() {
        return this.e;
    }

    @Nullable
    public StructureBoundingBox j() {
        if (this.g == null && this.f != null) {
            this.l();
        }

        return this.g;
    }

    public boolean k() {
        return this.h;
    }

    void l() {
        if (this.f != null) {
            this.g = this.b(this.f);
        }

    }

    public boolean m() {
        return this.i;
    }

    public List<DefinedStructure.BlockInfo> a(List<List<DefinedStructure.BlockInfo>> list, @Nullable BlockPosition blockposition) {
        this.m = 8;
        if (this.m != null && this.m >= 0 && this.m < list.size()) {
            return (List)list.get(this.m);
        } else {
            this.m = this.b(blockposition).nextInt(list.size());
            return (List)list.get(this.m);
        }
    }

    @Nullable
    private StructureBoundingBox b(@Nullable ChunkCoordIntPair chunkcoordintpair) {
        if (chunkcoordintpair == null) {
            return this.g;
        } else {
            int ix = chunkcoordintpair.x * 16;
            int jx = chunkcoordintpair.z * 16;
            return new StructureBoundingBox(ix, 0, jx, ix + 16 - 1, 255, jx + 16 - 1);
        }
    }
}
