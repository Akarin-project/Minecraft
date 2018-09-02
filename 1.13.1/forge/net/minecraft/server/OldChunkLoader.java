package net.minecraft.server;

public class OldChunkLoader {
    public static OldChunkLoader.OldChunk a(NBTTagCompound nbttagcompound) {
        int i = nbttagcompound.getInt("xPos");
        int j = nbttagcompound.getInt("zPos");
        OldChunkLoader.OldChunk oldchunkloader$oldchunk = new OldChunkLoader.OldChunk(i, j);
        oldchunkloader$oldchunk.g = nbttagcompound.getByteArray("Blocks");
        oldchunkloader$oldchunk.f = new OldNibbleArray(nbttagcompound.getByteArray("Data"), 7);
        oldchunkloader$oldchunk.e = new OldNibbleArray(nbttagcompound.getByteArray("SkyLight"), 7);
        oldchunkloader$oldchunk.d = new OldNibbleArray(nbttagcompound.getByteArray("BlockLight"), 7);
        oldchunkloader$oldchunk.c = nbttagcompound.getByteArray("HeightMap");
        oldchunkloader$oldchunk.b = nbttagcompound.getBoolean("TerrainPopulated");
        oldchunkloader$oldchunk.h = nbttagcompound.getList("Entities", 10);
        oldchunkloader$oldchunk.i = nbttagcompound.getList("TileEntities", 10);
        oldchunkloader$oldchunk.j = nbttagcompound.getList("TileTicks", 10);

        try {
            oldchunkloader$oldchunk.a = nbttagcompound.getLong("LastUpdate");
        } catch (ClassCastException var5) {
            oldchunkloader$oldchunk.a = (long)nbttagcompound.getInt("LastUpdate");
        }

        return oldchunkloader$oldchunk;
    }

    public static void a(OldChunkLoader.OldChunk oldchunkloader$oldchunk, NBTTagCompound nbttagcompound, WorldChunkManager worldchunkmanager) {
        nbttagcompound.setInt("xPos", oldchunkloader$oldchunk.k);
        nbttagcompound.setInt("zPos", oldchunkloader$oldchunk.l);
        nbttagcompound.setLong("LastUpdate", oldchunkloader$oldchunk.a);
        int[] aint = new int[oldchunkloader$oldchunk.c.length];

        for(int i = 0; i < oldchunkloader$oldchunk.c.length; ++i) {
            aint[i] = oldchunkloader$oldchunk.c[i];
        }

        nbttagcompound.setIntArray("HeightMap", aint);
        nbttagcompound.setBoolean("TerrainPopulated", oldchunkloader$oldchunk.b);
        NBTTagList nbttaglist = new NBTTagList();

        for(int j = 0; j < 8; ++j) {
            boolean flag = true;

            for(int k = 0; k < 16 && flag; ++k) {
                for(int l = 0; l < 16 && flag; ++l) {
                    for(int i1 = 0; i1 < 16; ++i1) {
                        int j1 = k << 11 | i1 << 7 | l + (j << 4);
                        byte b0 = oldchunkloader$oldchunk.g[j1];
                        if (b0 != 0) {
                            flag = false;
                            break;
                        }
                    }
                }
            }

            if (!flag) {
                byte[] abyte1 = new byte[4096];
                NibbleArray nibblearray = new NibbleArray();
                NibbleArray nibblearray1 = new NibbleArray();
                NibbleArray nibblearray2 = new NibbleArray();

                for(int l2 = 0; l2 < 16; ++l2) {
                    for(int k1 = 0; k1 < 16; ++k1) {
                        for(int l1 = 0; l1 < 16; ++l1) {
                            int i2 = l2 << 11 | l1 << 7 | k1 + (j << 4);
                            byte b1 = oldchunkloader$oldchunk.g[i2];
                            abyte1[k1 << 8 | l1 << 4 | l2] = (byte)(b1 & 255);
                            nibblearray.a(l2, k1, l1, oldchunkloader$oldchunk.f.a(l2, k1 + (j << 4), l1));
                            nibblearray1.a(l2, k1, l1, oldchunkloader$oldchunk.e.a(l2, k1 + (j << 4), l1));
                            nibblearray2.a(l2, k1, l1, oldchunkloader$oldchunk.d.a(l2, k1 + (j << 4), l1));
                        }
                    }
                }

                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Y", (byte)(j & 255));
                nbttagcompound1.setByteArray("Blocks", abyte1);
                nbttagcompound1.setByteArray("Data", nibblearray.asBytes());
                nbttagcompound1.setByteArray("SkyLight", nibblearray1.asBytes());
                nbttagcompound1.setByteArray("BlockLight", nibblearray2.asBytes());
                nbttaglist.add((NBTBase)nbttagcompound1);
            }
        }

        nbttagcompound.set("Sections", nbttaglist);
        byte[] abyte = new byte[256];
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

        for(int j2 = 0; j2 < 16; ++j2) {
            for(int k2 = 0; k2 < 16; ++k2) {
                blockposition$mutableblockposition.c(oldchunkloader$oldchunk.k << 4 | j2, 0, oldchunkloader$oldchunk.l << 4 | k2);
                abyte[k2 << 4 | j2] = (byte)(IRegistry.BIOME.a(worldchunkmanager.getBiome(blockposition$mutableblockposition, Biomes.b)) & 255);
            }
        }

        nbttagcompound.setByteArray("Biomes", abyte);
        nbttagcompound.set("Entities", oldchunkloader$oldchunk.h);
        nbttagcompound.set("TileEntities", oldchunkloader$oldchunk.i);
        if (oldchunkloader$oldchunk.j != null) {
            nbttagcompound.set("TileTicks", oldchunkloader$oldchunk.j);
        }

        nbttagcompound.setBoolean("convertedFromAlphaFormat", true);
    }

    public static class OldChunk {
        public long a;
        public boolean b;
        public byte[] c;
        public OldNibbleArray d;
        public OldNibbleArray e;
        public OldNibbleArray f;
        public byte[] g;
        public NBTTagList h;
        public NBTTagList i;
        public NBTTagList j;
        public final int k;
        public final int l;

        public OldChunk(int ix, int jx) {
            this.k = ix;
            this.l = jx;
        }
    }
}
