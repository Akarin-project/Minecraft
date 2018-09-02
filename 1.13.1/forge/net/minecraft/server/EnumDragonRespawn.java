package net.minecraft.server;

import java.util.List;
import java.util.Random;

public enum EnumDragonRespawn {
    START {
        public void a(WorldServer var1, EnderDragonBattle enderdragonbattle, List<EntityEnderCrystal> list, int var4, BlockPosition var5) {
            BlockPosition blockposition = new BlockPosition(0, 128, 0);

            for(EntityEnderCrystal entityendercrystal : list) {
                entityendercrystal.setBeamTarget(blockposition);
            }

            enderdragonbattle.a(PREPARING_TO_SUMMON_PILLARS);
        }
    },
    PREPARING_TO_SUMMON_PILLARS {
        public void a(WorldServer worldserver, EnderDragonBattle enderdragonbattle, List<EntityEnderCrystal> var3, int i, BlockPosition var5) {
            if (i < 100) {
                if (i == 0 || i == 50 || i == 51 || i == 52 || i >= 95) {
                    worldserver.triggerEffect(3001, new BlockPosition(0, 128, 0), 0);
                }
            } else {
                enderdragonbattle.a(SUMMONING_PILLARS);
            }

        }
    },
    SUMMONING_PILLARS {
        public void a(WorldServer worldserver, EnderDragonBattle enderdragonbattle, List<EntityEnderCrystal> list, int i, BlockPosition var5) {
            boolean flag = true;
            boolean flag1 = i % 40 == 0;
            boolean flag2 = i % 40 == 39;
            if (flag1 || flag2) {
                WorldGenEnder.Spike[] aworldgenender$spike = WorldGenDecoratorSpike.a(worldserver);
                int j = i / 40;
                if (j < aworldgenender$spike.length) {
                    WorldGenEnder.Spike worldgenender$spike = aworldgenender$spike[j];
                    if (flag1) {
                        for(EntityEnderCrystal entityendercrystal : list) {
                            entityendercrystal.setBeamTarget(new BlockPosition(worldgenender$spike.a(), worldgenender$spike.d() + 1, worldgenender$spike.b()));
                        }
                    } else {
                        boolean flag3 = true;

                        for(BlockPosition.MutableBlockPosition blockposition$mutableblockposition : BlockPosition.b(new BlockPosition(worldgenender$spike.a() - 10, worldgenender$spike.d() - 10, worldgenender$spike.b() - 10), new BlockPosition(worldgenender$spike.a() + 10, worldgenender$spike.d() + 10, worldgenender$spike.b() + 10))) {
                            worldserver.setAir(blockposition$mutableblockposition);
                        }

                        worldserver.explode((Entity)null, (double)((float)worldgenender$spike.a() + 0.5F), (double)worldgenender$spike.d(), (double)((float)worldgenender$spike.b() + 0.5F), 5.0F, true);
                        WorldGenEnder worldgenender = new WorldGenEnder();
                        worldgenender.a(worldgenender$spike);
                        worldgenender.a(true);
                        worldgenender.a(new BlockPosition(0, 128, 0));
                        worldgenender.a(worldserver, worldserver.getChunkProviderServer().getChunkGenerator(), new Random(), new BlockPosition(worldgenender$spike.a(), 45, worldgenender$spike.b()), WorldGenFeatureConfiguration.e);
                    }
                } else if (flag1) {
                    enderdragonbattle.a(SUMMONING_DRAGON);
                }
            }

        }
    },
    SUMMONING_DRAGON {
        public void a(WorldServer worldserver, EnderDragonBattle enderdragonbattle, List<EntityEnderCrystal> list, int i, BlockPosition var5) {
            if (i >= 100) {
                enderdragonbattle.a(END);
                enderdragonbattle.f();

                for(EntityEnderCrystal entityendercrystal : list) {
                    entityendercrystal.setBeamTarget((BlockPosition)null);
                    worldserver.explode(entityendercrystal, entityendercrystal.locX, entityendercrystal.locY, entityendercrystal.locZ, 6.0F, false);
                    entityendercrystal.die();
                }
            } else if (i >= 80) {
                worldserver.triggerEffect(3001, new BlockPosition(0, 128, 0), 0);
            } else if (i == 0) {
                for(EntityEnderCrystal entityendercrystal1 : list) {
                    entityendercrystal1.setBeamTarget(new BlockPosition(0, 128, 0));
                }
            } else if (i < 5) {
                worldserver.triggerEffect(3001, new BlockPosition(0, 128, 0), 0);
            }

        }
    },
    END {
        public void a(WorldServer var1, EnderDragonBattle var2, List<EntityEnderCrystal> var3, int var4, BlockPosition var5) {
        }
    };

    private EnumDragonRespawn() {
    }

    public abstract void a(WorldServer var1, EnderDragonBattle var2, List<EntityEnderCrystal> var3, int var4, BlockPosition var5);
}
