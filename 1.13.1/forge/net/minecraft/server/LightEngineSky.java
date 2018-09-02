package net.minecraft.server;

public class LightEngineSky extends LightEngine {
    public static final EnumDirection[] a = new EnumDirection[]{EnumDirection.WEST, EnumDirection.NORTH, EnumDirection.EAST, EnumDirection.SOUTH};

    public LightEngineSky() {
    }

    public EnumSkyBlock a() {
        return EnumSkyBlock.SKY;
    }

    public void a(RegionLimitedWorldAccess regionlimitedworldaccess, IChunkAccess ichunkaccess) {
        int i = ichunkaccess.getPos().d();
        int j = ichunkaccess.getPos().e();

        try (
            BlockPosition.b blockposition$b = BlockPosition.b.r();
            BlockPosition.b blockposition$b1 = BlockPosition.b.r();
        ) {
            for(int k = 0; k < 16; ++k) {
                for(int l = 0; l < 16; ++l) {
                    int i1 = ichunkaccess.a(HeightMap.Type.LIGHT_BLOCKING, k, l) + 1;
                    int j1 = k + i;
                    int k1 = l + j;

                    for(int l1 = i1; l1 < ichunkaccess.getSections().length * 16 - 1; ++l1) {
                        blockposition$b.f(j1, l1, k1);
                        this.a(regionlimitedworldaccess, blockposition$b, 15);
                    }

                    this.a(ichunkaccess.getPos(), j1, i1, k1, 15);

                    for(EnumDirection enumdirection : a) {
                        int i2 = regionlimitedworldaccess.a(HeightMap.Type.LIGHT_BLOCKING, j1 + enumdirection.getAdjacentX(), k1 + enumdirection.getAdjacentZ());
                        if (i2 - i1 >= 2) {
                            for(int j2 = i1; j2 <= i2; ++j2) {
                                blockposition$b1.f(j1 + enumdirection.getAdjacentX(), j2, k1 + enumdirection.getAdjacentZ());
                                int k2 = regionlimitedworldaccess.getType(blockposition$b1).b(regionlimitedworldaccess, blockposition$b1);
                                if (k2 != regionlimitedworldaccess.K()) {
                                    this.a(regionlimitedworldaccess, blockposition$b1, 15 - k2 - 1);
                                    this.a(ichunkaccess.getPos(), blockposition$b1, 15 - k2 - 1);
                                }
                            }
                        }
                    }
                }
            }

            this.a(regionlimitedworldaccess, ichunkaccess.getPos());
        }

    }
}
