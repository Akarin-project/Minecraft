package net.minecraft.server;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class SpawnerCreature {
    private static final Logger a = LogManager.getLogger();
    private static final int b = (int)Math.pow(17.0D, 2.0D);
    private final Set<ChunkCoordIntPair> c = Sets.newHashSet();

    public SpawnerCreature() {
    }

    public int a(WorldServer worldserver, boolean flag, boolean flag1, boolean flag2) {
        if (!flag && !flag1) {
            return 0;
        } else {
            this.c.clear();
            int i = 0;

            for(EntityHuman entityhuman : worldserver.players) {
                if (!entityhuman.isSpectator()) {
                    int j = MathHelper.floor(entityhuman.locX / 16.0D);
                    int k = MathHelper.floor(entityhuman.locZ / 16.0D);
                    boolean flag3 = true;

                    for(int l = -8; l <= 8; ++l) {
                        for(int i1 = -8; i1 <= 8; ++i1) {
                            boolean flag4 = l == -8 || l == 8 || i1 == -8 || i1 == 8;
                            ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(l + j, i1 + k);
                            if (!this.c.contains(chunkcoordintpair)) {
                                ++i;
                                if (!flag4 && worldserver.getWorldBorder().isInBounds(chunkcoordintpair)) {
                                    PlayerChunk playerchunk = worldserver.getPlayerChunkMap().getChunk(chunkcoordintpair.x, chunkcoordintpair.z);
                                    if (playerchunk != null && playerchunk.e()) {
                                        this.c.add(chunkcoordintpair);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            int i4 = 0;
            BlockPosition blockposition1 = worldserver.getSpawn();

            for(EnumCreatureType enumcreaturetype : EnumCreatureType.values()) {
                if ((!enumcreaturetype.c() || flag1) && (enumcreaturetype.c() || flag) && (!enumcreaturetype.d() || flag2)) {
                    int j4 = enumcreaturetype.b() * i / b;
                    int k4 = worldserver.a(enumcreaturetype.a(), j4);
                    if (k4 <= j4) {
                        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

                        label142:
                        for(ChunkCoordIntPair chunkcoordintpair1 : this.c) {
                            BlockPosition blockposition = getRandomPosition(worldserver, chunkcoordintpair1.x, chunkcoordintpair1.z);
                            int j1 = blockposition.getX();
                            int k1 = blockposition.getY();
                            int l1 = blockposition.getZ();
                            IBlockData iblockdata = worldserver.getType(blockposition);
                            if (!iblockdata.isOccluding()) {
                                int i2 = 0;

                                for(int j2 = 0; j2 < 3; ++j2) {
                                    int k2 = j1;
                                    int l2 = k1;
                                    int i3 = l1;
                                    boolean flag5 = true;
                                    BiomeBase.BiomeMeta biomebase$biomemeta = null;
                                    GroupDataEntity groupdataentity = null;
                                    int j3 = MathHelper.f(Math.random() * 4.0D);
                                    int k3 = 0;

                                    for(int l3 = 0; l3 < j3; ++l3) {
                                        k2 += worldserver.random.nextInt(6) - worldserver.random.nextInt(6);
                                        l2 += worldserver.random.nextInt(1) - worldserver.random.nextInt(1);
                                        i3 += worldserver.random.nextInt(6) - worldserver.random.nextInt(6);
                                        blockposition$mutableblockposition.c(k2, l2, i3);
                                        float f = (float)k2 + 0.5F;
                                        float f1 = (float)i3 + 0.5F;
                                        if (!worldserver.isPlayerNearby((double)f, (double)l2, (double)f1, 24.0D) && !(blockposition1.distanceSquared((double)f, (double)l2, (double)f1) < 576.0D)) {
                                            if (biomebase$biomemeta == null) {
                                                biomebase$biomemeta = worldserver.a(enumcreaturetype, blockposition$mutableblockposition);
                                                if (biomebase$biomemeta == null) {
                                                    break;
                                                }

                                                j3 = biomebase$biomemeta.c + worldserver.random.nextInt(1 + biomebase$biomemeta.d - biomebase$biomemeta.c);
                                            }

                                            if (worldserver.a(enumcreaturetype, biomebase$biomemeta, blockposition$mutableblockposition)) {
                                                EntityPositionTypes.Surface entitypositiontypes$surface = EntityPositionTypes.a(biomebase$biomemeta.b);
                                                if (entitypositiontypes$surface != null && a(entitypositiontypes$surface, worldserver, blockposition$mutableblockposition, biomebase$biomemeta.b)) {
                                                    EntityInsentient entityinsentient;
                                                    try {
                                                        entityinsentient = (EntityInsentient)biomebase$biomemeta.b.a(worldserver);
                                                    } catch (Exception exception) {
                                                        a.warn("Failed to create mob", exception);
                                                        return i4;
                                                    }

                                                    entityinsentient.setPositionRotation((double)f, (double)l2, (double)f1, worldserver.random.nextFloat() * 360.0F, 0.0F);
                                                    if (entityinsentient.a(worldserver, false) && entityinsentient.a(worldserver)) {
                                                        groupdataentity = entityinsentient.prepare(worldserver.getDamageScaler(new BlockPosition(entityinsentient)), groupdataentity, (NBTTagCompound)null);
                                                        if (entityinsentient.a(worldserver)) {
                                                            ++i2;
                                                            ++k3;
                                                            worldserver.addEntity(entityinsentient);
                                                        } else {
                                                            entityinsentient.die();
                                                        }

                                                        if (i2 >= entityinsentient.dg()) {
                                                            continue label142;
                                                        }

                                                        if (entityinsentient.c(k3)) {
                                                            break;
                                                        }
                                                    }

                                                    i4 += i2;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return i4;
        }
    }

    private static BlockPosition getRandomPosition(World world, int i, int j) {
        Chunk chunk = world.getChunkAt(i, j);
        int k = i * 16 + world.random.nextInt(16);
        int l = j * 16 + world.random.nextInt(16);
        int i1 = chunk.a(HeightMap.Type.LIGHT_BLOCKING, k, l) + 1;
        int j1 = world.random.nextInt(i1 + 1);
        return new BlockPosition(k, j1, l);
    }

    public static boolean a(IBlockData iblockdata, Fluid fluid) {
        if (iblockdata.k()) {
            return false;
        } else if (iblockdata.isPowerSource()) {
            return false;
        } else if (!fluid.e()) {
            return false;
        } else {
            return !iblockdata.a(TagsBlock.RAILS);
        }
    }

    public static boolean a(EntityPositionTypes.Surface entitypositiontypes$surface, IWorldReader iworldreader, BlockPosition blockposition, @Nullable EntityTypes<? extends EntityInsentient> entitytypes) {
        if (entitytypes != null && iworldreader.getWorldBorder().a(blockposition)) {
            IBlockData iblockdata = iworldreader.getType(blockposition);
            Fluid fluid = iworldreader.b(blockposition);
            switch(entitypositiontypes$surface) {
            case IN_WATER:
                return fluid.a(TagsFluid.WATER) && iworldreader.b(blockposition.down()).a(TagsFluid.WATER) && !iworldreader.getType(blockposition.up()).isOccluding();
            case ON_GROUND:
            default:
                IBlockData iblockdata1 = iworldreader.getType(blockposition.down());
                if (iblockdata1.q() || entitytypes != null && EntityPositionTypes.a(entitytypes, iblockdata1)) {
                    Block block = iblockdata1.getBlock();
                    boolean flag = block != Blocks.BEDROCK && block != Blocks.BARRIER;
                    return flag && a(iblockdata, fluid) && a(iworldreader.getType(blockposition.up()), iworldreader.b(blockposition.up()));
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public static void a(GeneratorAccess generatoraccess, BiomeBase biomebase, int i, int j, Random random) {
        List list = biomebase.getMobs(EnumCreatureType.CREATURE);
        if (!list.isEmpty()) {
            int k = i << 4;
            int l = j << 4;

            while(random.nextFloat() < biomebase.e()) {
                BiomeBase.BiomeMeta biomebase$biomemeta = (BiomeBase.BiomeMeta)WeightedRandom.a(random, list);
                int i1 = biomebase$biomemeta.c + random.nextInt(1 + biomebase$biomemeta.d - biomebase$biomemeta.c);
                GroupDataEntity groupdataentity = null;
                int j1 = k + random.nextInt(16);
                int k1 = l + random.nextInt(16);
                int l1 = j1;
                int i2 = k1;

                for(int j2 = 0; j2 < i1; ++j2) {
                    boolean flag = false;

                    for(int k2 = 0; !flag && k2 < 4; ++k2) {
                        BlockPosition blockposition = a(generatoraccess, biomebase$biomemeta.b, j1, k1);
                        if (a(EntityPositionTypes.Surface.ON_GROUND, generatoraccess, blockposition, biomebase$biomemeta.b)) {
                            EntityInsentient entityinsentient;
                            try {
                                entityinsentient = (EntityInsentient)biomebase$biomemeta.b.a(generatoraccess.getMinecraftWorld());
                            } catch (Exception exception) {
                                a.warn("Failed to create mob", exception);
                                continue;
                            }

                            double d0 = MathHelper.a((double)j1, (double)k + (double)entityinsentient.width, (double)k + 16.0D - (double)entityinsentient.width);
                            double d1 = MathHelper.a((double)k1, (double)l + (double)entityinsentient.width, (double)l + 16.0D - (double)entityinsentient.width);
                            entityinsentient.setPositionRotation(d0, (double)blockposition.getY(), d1, random.nextFloat() * 360.0F, 0.0F);
                            if (entityinsentient.a(generatoraccess, false) && entityinsentient.a(generatoraccess)) {
                                groupdataentity = entityinsentient.prepare(generatoraccess.getDamageScaler(new BlockPosition(entityinsentient)), groupdataentity, (NBTTagCompound)null);
                                generatoraccess.addEntity(entityinsentient);
                                flag = true;
                            }
                        }

                        j1 += random.nextInt(5) - random.nextInt(5);

                        for(k1 += random.nextInt(5) - random.nextInt(5); j1 < k || j1 >= k + 16 || k1 < l || k1 >= l + 16; k1 = i2 + random.nextInt(5) - random.nextInt(5)) {
                            j1 = l1 + random.nextInt(5) - random.nextInt(5);
                        }
                    }
                }
            }

        }
    }

    private static BlockPosition a(GeneratorAccess generatoraccess, @Nullable EntityTypes<? extends EntityInsentient> entitytypes, int i, int j) {
        BlockPosition blockposition = new BlockPosition(i, generatoraccess.a(EntityPositionTypes.b(entitytypes), i, j), j);
        BlockPosition blockposition1 = blockposition.down();
        return generatoraccess.getType(blockposition1).a(generatoraccess, blockposition1, PathMode.LAND) ? blockposition1 : blockposition;
    }
}
