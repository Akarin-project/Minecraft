package net.minecraft.server;

import java.util.Random;

public class WorldGenFossils extends WorldGenerator<WorldGenFeatureEmptyConfiguration> {
    private static final MinecraftKey a = new MinecraftKey("fossil/spine_1");
    private static final MinecraftKey b = new MinecraftKey("fossil/spine_2");
    private static final MinecraftKey c = new MinecraftKey("fossil/spine_3");
    private static final MinecraftKey d = new MinecraftKey("fossil/spine_4");
    private static final MinecraftKey aH = new MinecraftKey("fossil/spine_1_coal");
    private static final MinecraftKey aI = new MinecraftKey("fossil/spine_2_coal");
    private static final MinecraftKey aJ = new MinecraftKey("fossil/spine_3_coal");
    private static final MinecraftKey aK = new MinecraftKey("fossil/spine_4_coal");
    private static final MinecraftKey aL = new MinecraftKey("fossil/skull_1");
    private static final MinecraftKey aM = new MinecraftKey("fossil/skull_2");
    private static final MinecraftKey aN = new MinecraftKey("fossil/skull_3");
    private static final MinecraftKey aO = new MinecraftKey("fossil/skull_4");
    private static final MinecraftKey aP = new MinecraftKey("fossil/skull_1_coal");
    private static final MinecraftKey aQ = new MinecraftKey("fossil/skull_2_coal");
    private static final MinecraftKey aR = new MinecraftKey("fossil/skull_3_coal");
    private static final MinecraftKey aS = new MinecraftKey("fossil/skull_4_coal");
    private static final MinecraftKey[] aT = new MinecraftKey[]{a, b, c, d, aL, aM, aN, aO};
    private static final MinecraftKey[] aU = new MinecraftKey[]{aH, aI, aJ, aK, aP, aQ, aR, aS};

    public WorldGenFossils() {
    }

    public boolean a(GeneratorAccess generatoraccess, ChunkGenerator<? extends GeneratorSettings> var2, Random var3, BlockPosition blockposition, WorldGenFeatureEmptyConfiguration var5) {
        Random random = generatoraccess.m();
        EnumBlockRotation[] aenumblockrotation = EnumBlockRotation.values();
        EnumBlockRotation enumblockrotation = aenumblockrotation[random.nextInt(aenumblockrotation.length)];
        int i = random.nextInt(aT.length);
        DefinedStructureManager definedstructuremanager = generatoraccess.getDataManager().h();
        DefinedStructure definedstructure = definedstructuremanager.a(aT[i]);
        DefinedStructure definedstructure1 = definedstructuremanager.a(aU[i]);
        ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(blockposition);
        StructureBoundingBox structureboundingbox = new StructureBoundingBox(chunkcoordintpair.d(), 0, chunkcoordintpair.e(), chunkcoordintpair.f(), 256, chunkcoordintpair.g());
        DefinedStructureInfo definedstructureinfo = (new DefinedStructureInfo()).a(enumblockrotation).a(structureboundingbox).a(random);
        BlockPosition blockposition1 = definedstructure.a(enumblockrotation);
        int j = random.nextInt(16 - blockposition1.getX());
        int k = random.nextInt(16 - blockposition1.getZ());
        int l = 256;

        for(int i1 = 0; i1 < blockposition1.getX(); ++i1) {
            for(int j1 = 0; j1 < blockposition1.getX(); ++j1) {
                l = Math.min(l, generatoraccess.a(HeightMap.Type.OCEAN_FLOOR_WG, blockposition.getX() + i1 + j, blockposition.getZ() + j1 + k));
            }
        }

        int k1 = Math.max(l - 15 - random.nextInt(10), 10);
        BlockPosition blockposition2 = definedstructure.a(blockposition.a(j, k1, k), EnumBlockMirror.NONE, enumblockrotation);
        definedstructureinfo.a(0.9F);
        definedstructure.a(generatoraccess, blockposition2, definedstructureinfo, 4);
        definedstructureinfo.a(0.1F);
        definedstructure1.a(generatoraccess, blockposition2, definedstructureinfo, 4);
        return true;
    }
}
