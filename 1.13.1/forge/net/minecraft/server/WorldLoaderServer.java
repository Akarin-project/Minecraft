package net.minecraft.server;

import com.google.common.collect.Lists;
import com.mojang.datafixers.DataFixer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldLoaderServer extends WorldLoader {
    private static final Logger e = LogManager.getLogger();

    public WorldLoaderServer(java.nio.file.Path path, java.nio.file.Path path1, DataFixer datafixer) {
        super(path, path1, datafixer);
    }

    protected int c() {
        return 19133;
    }

    public IDataManager a(String s, @Nullable MinecraftServer minecraftserver) {
        return new ServerNBTManager(this.a.toFile(), s, minecraftserver, this.c);
    }

    public boolean isConvertable(String s) {
        WorldData worlddata = this.c(s);
        return worlddata != null && worlddata.k() != this.c();
    }

    public boolean convert(String s, IProgressUpdate iprogressupdate) {
        iprogressupdate.a(0);
        ArrayList arraylist = Lists.newArrayList();
        ArrayList arraylist1 = Lists.newArrayList();
        ArrayList arraylist2 = Lists.newArrayList();
        File file1 = new File(this.a.toFile(), s);
        File file2 = DimensionManager.NETHER.a(file1);
        File file3 = DimensionManager.THE_END.a(file1);
        e.info("Scanning folders...");
        this.a(file1, arraylist);
        if (file2.exists()) {
            this.a(file2, arraylist1);
        }

        if (file3.exists()) {
            this.a(file3, arraylist2);
        }

        int i = arraylist.size() + arraylist1.size() + arraylist2.size();
        e.info("Total conversion count is {}", i);
        WorldData worlddata = this.c(s);
        BiomeLayout biomelayout = BiomeLayout.b;
        BiomeLayout biomelayout1 = BiomeLayout.c;
        WorldChunkManager worldchunkmanager;
        if (worlddata != null && worlddata.getType() == WorldType.FLAT) {
            worldchunkmanager = biomelayout.a(((BiomeLayoutFixedConfiguration)biomelayout.b()).a(Biomes.c));
        } else {
            worldchunkmanager = biomelayout1.a(((BiomeLayoutOverworldConfiguration)biomelayout1.b()).a(worlddata).a(ChunkGeneratorType.a.b()));
        }

        this.a(new File(file1, "region"), arraylist, worldchunkmanager, 0, i, iprogressupdate);
        this.a(new File(file2, "region"), arraylist1, biomelayout.a(((BiomeLayoutFixedConfiguration)biomelayout.b()).a(Biomes.j)), arraylist.size(), i, iprogressupdate);
        this.a(new File(file3, "region"), arraylist2, biomelayout.a(((BiomeLayoutFixedConfiguration)biomelayout.b()).a(Biomes.k)), arraylist.size() + arraylist1.size(), i, iprogressupdate);
        worlddata.d(19133);
        if (worlddata.getType() == WorldType.NORMAL_1_1) {
            worlddata.a(WorldType.NORMAL);
        }

        this.i(s);
        IDataManager idatamanager = this.a(s, (MinecraftServer)null);
        idatamanager.saveWorldData(worlddata);
        return true;
    }

    private void i(String s) {
        File file1 = new File(this.a.toFile(), s);
        if (!file1.exists()) {
            e.warn("Unable to create level.dat_mcr backup");
        } else {
            File file2 = new File(file1, "level.dat");
            if (!file2.exists()) {
                e.warn("Unable to create level.dat_mcr backup");
            } else {
                File file3 = new File(file1, "level.dat_mcr");
                if (!file2.renameTo(file3)) {
                    e.warn("Unable to create level.dat_mcr backup");
                }

            }
        }
    }

    private void a(File file1, Iterable<File> iterable, WorldChunkManager worldchunkmanager, int i, int j, IProgressUpdate iprogressupdate) {
        for(File file2 : iterable) {
            this.a(file1, file2, worldchunkmanager, i, j, iprogressupdate);
            ++i;
            int k = (int)Math.round(100.0D * (double)i / (double)j);
            iprogressupdate.a(k);
        }

    }

    private void a(File file1, File file2, WorldChunkManager worldchunkmanager, int i, int j, IProgressUpdate iprogressupdate) {
        try {
            String s = file2.getName();
            RegionFile regionfile = new RegionFile(file2);
            RegionFile regionfile1 = new RegionFile(new File(file1, s.substring(0, s.length() - ".mcr".length()) + ".mca"));

            for(int k = 0; k < 32; ++k) {
                for(int l = 0; l < 32; ++l) {
                    if (regionfile.d(k, l) && !regionfile1.d(k, l)) {
                        DataInputStream datainputstream = regionfile.a(k, l);
                        if (datainputstream == null) {
                            e.warn("Failed to fetch input stream");
                        } else {
                            NBTTagCompound nbttagcompound = NBTCompressedStreamTools.a(datainputstream);
                            datainputstream.close();
                            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Level");
                            OldChunkLoader.OldChunk oldchunkloader$oldchunk = OldChunkLoader.a(nbttagcompound1);
                            NBTTagCompound nbttagcompound2 = new NBTTagCompound();
                            NBTTagCompound nbttagcompound3 = new NBTTagCompound();
                            nbttagcompound2.set("Level", nbttagcompound3);
                            OldChunkLoader.a(oldchunkloader$oldchunk, nbttagcompound3, worldchunkmanager);
                            DataOutputStream dataoutputstream = regionfile1.c(k, l);
                            NBTCompressedStreamTools.a(nbttagcompound2, dataoutputstream);
                            dataoutputstream.close();
                        }
                    }
                }

                int i1 = (int)Math.round(100.0D * (double)(i * 1024) / (double)(j * 1024));
                int j1 = (int)Math.round(100.0D * (double)((k + 1) * 32 + i * 1024) / (double)(j * 1024));
                if (j1 > i1) {
                    iprogressupdate.a(j1);
                }
            }

            regionfile.c();
            regionfile1.c();
        } catch (IOException ioexception) {
            ioexception.printStackTrace();
        }

    }

    private void a(File file1, Collection<File> collection) {
        File file2 = new File(file1, "region");
        File[] afile = file2.listFiles((var0, s) -> {
            return s.endsWith(".mcr");
        });
        if (afile != null) {
            Collections.addAll(collection, afile);
        }

    }
}
