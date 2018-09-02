package net.minecraft.server;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Random;

public class PortalTravelAgent {
    private static final BlockPortal a = (BlockPortal)Blocks.NETHER_PORTAL;
    private final WorldServer world;
    private final Random c;
    private final Long2ObjectMap<PortalTravelAgent.ChunkCoordinatesPortal> d = new Long2ObjectOpenHashMap(4096);

    public PortalTravelAgent(WorldServer worldserver) {
        this.world = worldserver;
        this.c = new Random(worldserver.getSeed());
    }

    public void a(Entity entity, float f) {
        if (this.world.worldProvider.getDimensionManager() != DimensionManager.THE_END) {
            if (!this.b(entity, f)) {
                this.a(entity);
                this.b(entity, f);
            }
        } else {
            int i = MathHelper.floor(entity.locX);
            int j = MathHelper.floor(entity.locY) - 1;
            int k = MathHelper.floor(entity.locZ);
            boolean flag = true;
            boolean flag1 = false;

            for(int l = -2; l <= 2; ++l) {
                for(int i1 = -2; i1 <= 2; ++i1) {
                    for(int j1 = -1; j1 < 3; ++j1) {
                        int k1 = i + i1 * 1 + l * 0;
                        int l1 = j + j1;
                        int i2 = k + i1 * 0 - l * 1;
                        boolean flag2 = j1 < 0;
                        this.world.setTypeUpdate(new BlockPosition(k1, l1, i2), flag2 ? Blocks.OBSIDIAN.getBlockData() : Blocks.AIR.getBlockData());
                    }
                }
            }

            entity.setPositionRotation((double)i, (double)j, (double)k, entity.yaw, 0.0F);
            entity.motX = 0.0D;
            entity.motY = 0.0D;
            entity.motZ = 0.0D;
        }
    }

    public boolean b(Entity entity, float f) {
        boolean flag = true;
        double d0 = -1.0D;
        int i = MathHelper.floor(entity.locX);
        int j = MathHelper.floor(entity.locZ);
        boolean flag1 = true;
        Object object = BlockPosition.ZERO;
        long k = ChunkCoordIntPair.a(i, j);
        if (this.d.containsKey(k)) {
            PortalTravelAgent.ChunkCoordinatesPortal portaltravelagent$chunkcoordinatesportal = (PortalTravelAgent.ChunkCoordinatesPortal)this.d.get(k);
            d0 = 0.0D;
            object = portaltravelagent$chunkcoordinatesportal;
            portaltravelagent$chunkcoordinatesportal.b = this.world.getTime();
            flag1 = false;
        } else {
            BlockPosition blockposition2 = new BlockPosition(entity);

            for(int l = -128; l <= 128; ++l) {
                BlockPosition blockposition1;
                for(int i1 = -128; i1 <= 128; ++i1) {
                    for(BlockPosition blockposition = blockposition2.a(l, this.world.ab() - 1 - blockposition2.getY(), i1); blockposition.getY() >= 0; blockposition = blockposition1) {
                        blockposition1 = blockposition.down();
                        if (this.world.getType(blockposition).getBlock() == a) {
                            for(blockposition1 = blockposition.down(); this.world.getType(blockposition1).getBlock() == a; blockposition1 = blockposition1.down()) {
                                blockposition = blockposition1;
                            }

                            double d1 = blockposition.n(blockposition2);
                            if (d0 < 0.0D || d1 < d0) {
                                d0 = d1;
                                object = blockposition;
                            }
                        }
                    }
                }
            }
        }

        if (d0 >= 0.0D) {
            if (flag1) {
                this.d.put(k, new PortalTravelAgent.ChunkCoordinatesPortal((BlockPosition)object, this.world.getTime()));
            }

            double d5 = (double)((BlockPosition)object).getX() + 0.5D;
            double d7 = (double)((BlockPosition)object).getZ() + 0.5D;
            ShapeDetector.ShapeDetectorCollection shapedetector$shapedetectorcollection = a.c(this.world, (BlockPosition)object);
            boolean flag2 = shapedetector$shapedetectorcollection.getFacing().e().c() == EnumDirection.EnumAxisDirection.NEGATIVE;
            double d2 = shapedetector$shapedetectorcollection.getFacing().k() == EnumDirection.EnumAxis.X ? (double)shapedetector$shapedetectorcollection.a().getZ() : (double)shapedetector$shapedetectorcollection.a().getX();
            double d6 = (double)(shapedetector$shapedetectorcollection.a().getY() + 1) - entity.getPortalOffset().y * (double)shapedetector$shapedetectorcollection.e();
            if (flag2) {
                ++d2;
            }

            if (shapedetector$shapedetectorcollection.getFacing().k() == EnumDirection.EnumAxis.X) {
                d7 = d2 + (1.0D - entity.getPortalOffset().x) * (double)shapedetector$shapedetectorcollection.d() * (double)shapedetector$shapedetectorcollection.getFacing().e().c().a();
            } else {
                d5 = d2 + (1.0D - entity.getPortalOffset().x) * (double)shapedetector$shapedetectorcollection.d() * (double)shapedetector$shapedetectorcollection.getFacing().e().c().a();
            }

            float f1 = 0.0F;
            float f2 = 0.0F;
            float f3 = 0.0F;
            float f4 = 0.0F;
            if (shapedetector$shapedetectorcollection.getFacing().opposite() == entity.getPortalDirection()) {
                f1 = 1.0F;
                f2 = 1.0F;
            } else if (shapedetector$shapedetectorcollection.getFacing().opposite() == entity.getPortalDirection().opposite()) {
                f1 = -1.0F;
                f2 = -1.0F;
            } else if (shapedetector$shapedetectorcollection.getFacing().opposite() == entity.getPortalDirection().e()) {
                f3 = 1.0F;
                f4 = -1.0F;
            } else {
                f3 = -1.0F;
                f4 = 1.0F;
            }

            double d3 = entity.motX;
            double d4 = entity.motZ;
            entity.motX = d3 * (double)f1 + d4 * (double)f4;
            entity.motZ = d3 * (double)f3 + d4 * (double)f2;
            entity.yaw = f - (float)(entity.getPortalDirection().opposite().get2DRotationValue() * 90) + (float)(shapedetector$shapedetectorcollection.getFacing().get2DRotationValue() * 90);
            if (entity instanceof EntityPlayer) {
                ((EntityPlayer)entity).playerConnection.a(d5, d6, d7, entity.yaw, entity.pitch);
                ((EntityPlayer)entity).playerConnection.syncPosition();
            } else {
                entity.setPositionRotation(d5, d6, d7, entity.yaw, entity.pitch);
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean a(Entity entity) {
        boolean flag = true;
        double d0 = -1.0D;
        int i = MathHelper.floor(entity.locX);
        int j = MathHelper.floor(entity.locY);
        int k = MathHelper.floor(entity.locZ);
        int l = i;
        int i1 = j;
        int j1 = k;
        int k1 = 0;
        int l1 = this.c.nextInt(4);
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

        for(int i2 = i - 16; i2 <= i + 16; ++i2) {
            double d1 = (double)i2 + 0.5D - entity.locX;

            for(int k2 = k - 16; k2 <= k + 16; ++k2) {
                double d2 = (double)k2 + 0.5D - entity.locZ;

                label276:
                for(int i3 = this.world.ab() - 1; i3 >= 0; --i3) {
                    if (this.world.isEmpty(blockposition$mutableblockposition.c(i2, i3, k2))) {
                        while(i3 > 0 && this.world.isEmpty(blockposition$mutableblockposition.c(i2, i3 - 1, k2))) {
                            --i3;
                        }

                        for(int j3 = l1; j3 < l1 + 4; ++j3) {
                            int k3 = j3 % 2;
                            int l3 = 1 - k3;
                            if (j3 % 4 >= 2) {
                                k3 = -k3;
                                l3 = -l3;
                            }

                            for(int i4 = 0; i4 < 3; ++i4) {
                                for(int j4 = 0; j4 < 4; ++j4) {
                                    for(int k4 = -1; k4 < 4; ++k4) {
                                        int l4 = i2 + (j4 - 1) * k3 + i4 * l3;
                                        int i5 = i3 + k4;
                                        int j5 = k2 + (j4 - 1) * l3 - i4 * k3;
                                        blockposition$mutableblockposition.c(l4, i5, j5);
                                        if (k4 < 0 && !this.world.getType(blockposition$mutableblockposition).getMaterial().isBuildable() || k4 >= 0 && !this.world.isEmpty(blockposition$mutableblockposition)) {
                                            continue label276;
                                        }
                                    }
                                }
                            }

                            double d5 = (double)i3 + 0.5D - entity.locY;
                            double d7 = d1 * d1 + d5 * d5 + d2 * d2;
                            if (d0 < 0.0D || d7 < d0) {
                                d0 = d7;
                                l = i2;
                                i1 = i3;
                                j1 = k2;
                                k1 = j3 % 4;
                            }
                        }
                    }
                }
            }
        }

        if (d0 < 0.0D) {
            for(int k5 = i - 16; k5 <= i + 16; ++k5) {
                double d3 = (double)k5 + 0.5D - entity.locX;

                for(int i6 = k - 16; i6 <= k + 16; ++i6) {
                    double d4 = (double)i6 + 0.5D - entity.locZ;

                    label214:
                    for(int l6 = this.world.ab() - 1; l6 >= 0; --l6) {
                        if (this.world.isEmpty(blockposition$mutableblockposition.c(k5, l6, i6))) {
                            while(l6 > 0 && this.world.isEmpty(blockposition$mutableblockposition.c(k5, l6 - 1, i6))) {
                                --l6;
                            }

                            for(int k7 = l1; k7 < l1 + 2; ++k7) {
                                int k8 = k7 % 2;
                                int j9 = 1 - k8;

                                for(int l9 = 0; l9 < 4; ++l9) {
                                    for(int j10 = -1; j10 < 4; ++j10) {
                                        int l10 = k5 + (l9 - 1) * k8;
                                        int i11 = l6 + j10;
                                        int j11 = i6 + (l9 - 1) * j9;
                                        blockposition$mutableblockposition.c(l10, i11, j11);
                                        if (j10 < 0 && !this.world.getType(blockposition$mutableblockposition).getMaterial().isBuildable() || j10 >= 0 && !this.world.isEmpty(blockposition$mutableblockposition)) {
                                            continue label214;
                                        }
                                    }
                                }

                                double d6 = (double)l6 + 0.5D - entity.locY;
                                double d8 = d3 * d3 + d6 * d6 + d4 * d4;
                                if (d0 < 0.0D || d8 < d0) {
                                    d0 = d8;
                                    l = k5;
                                    i1 = l6;
                                    j1 = i6;
                                    k1 = k7 % 2;
                                }
                            }
                        }
                    }
                }
            }
        }

        int l5 = l;
        int j2 = i1;
        int j6 = j1;
        int k6 = k1 % 2;
        int l2 = 1 - k6;
        if (k1 % 4 >= 2) {
            k6 = -k6;
            l2 = -l2;
        }

        if (d0 < 0.0D) {
            i1 = MathHelper.clamp(i1, 70, this.world.ab() - 10);
            j2 = i1;

            for(int i7 = -1; i7 <= 1; ++i7) {
                for(int l7 = 1; l7 < 3; ++l7) {
                    for(int l8 = -1; l8 < 3; ++l8) {
                        int k9 = l5 + (l7 - 1) * k6 + i7 * l2;
                        int i10 = j2 + l8;
                        int k10 = j6 + (l7 - 1) * l2 - i7 * k6;
                        boolean flag1 = l8 < 0;
                        blockposition$mutableblockposition.c(k9, i10, k10);
                        this.world.setTypeUpdate(blockposition$mutableblockposition, flag1 ? Blocks.OBSIDIAN.getBlockData() : Blocks.AIR.getBlockData());
                    }
                }
            }
        }

        for(int j7 = -1; j7 < 3; ++j7) {
            for(int i8 = -1; i8 < 4; ++i8) {
                if (j7 == -1 || j7 == 2 || i8 == -1 || i8 == 3) {
                    blockposition$mutableblockposition.c(l5 + j7 * k6, j2 + i8, j6 + j7 * l2);
                    this.world.setTypeAndData(blockposition$mutableblockposition, Blocks.OBSIDIAN.getBlockData(), 3);
                }
            }
        }

        IBlockData iblockdata = (IBlockData)a.getBlockData().set(BlockPortal.AXIS, k6 == 0 ? EnumDirection.EnumAxis.Z : EnumDirection.EnumAxis.X);

        for(int j8 = 0; j8 < 2; ++j8) {
            for(int i9 = 0; i9 < 3; ++i9) {
                blockposition$mutableblockposition.c(l5 + j8 * k6, j2 + i9, j6 + j8 * l2);
                this.world.setTypeAndData(blockposition$mutableblockposition, iblockdata, 18);
            }
        }

        return true;
    }

    public void a(long i) {
        if (i % 100L == 0L) {
            long j = i - 300L;
            ObjectIterator objectiterator = this.d.values().iterator();

            while(objectiterator.hasNext()) {
                PortalTravelAgent.ChunkCoordinatesPortal portaltravelagent$chunkcoordinatesportal = (PortalTravelAgent.ChunkCoordinatesPortal)objectiterator.next();
                if (portaltravelagent$chunkcoordinatesportal == null || portaltravelagent$chunkcoordinatesportal.b < j) {
                    objectiterator.remove();
                }
            }
        }

    }

    public class ChunkCoordinatesPortal extends BlockPosition {
        public long b;

        public ChunkCoordinatesPortal(BlockPosition blockposition, long i) {
            super(blockposition.getX(), blockposition.getY(), blockposition.getZ());
            this.b = i;
        }
    }
}
