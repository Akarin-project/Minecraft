package net.minecraft.server;

import com.mojang.datafixers.DataFixTypes;
import com.mojang.datafixers.DataFixer;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldNBTStorage implements IDataManager, IPlayerFileData {
    private static final Logger b = LogManager.getLogger();
    private final File baseDir;
    private final File playerDir;
    private final long sessionId = SystemUtils.b();
    private final String f;
    private final DefinedStructureManager g;
    protected final DataFixer a;

    public WorldNBTStorage(File file1, String s, @Nullable MinecraftServer minecraftserver, DataFixer datafixer) {
        this.a = datafixer;
        this.baseDir = new File(file1, s);
        this.baseDir.mkdirs();
        this.playerDir = new File(this.baseDir, "playerdata");
        this.f = s;
        if (minecraftserver != null) {
            this.playerDir.mkdirs();
            this.g = new DefinedStructureManager(minecraftserver, this.baseDir, datafixer);
        } else {
            this.g = null;
        }

        this.j();
    }

    private void j() {
        try {
            File file1 = new File(this.baseDir, "session.lock");
            DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file1));

            try {
                dataoutputstream.writeLong(this.sessionId);
            } finally {
                dataoutputstream.close();
            }

        } catch (IOException ioexception) {
            ioexception.printStackTrace();
            throw new RuntimeException("Failed to check session lock, aborting");
        }
    }

    public File getDirectory() {
        return this.baseDir;
    }

    public void checkSession() throws ExceptionWorldConflict {
        try {
            File file1 = new File(this.baseDir, "session.lock");
            DataInputStream datainputstream = new DataInputStream(new FileInputStream(file1));

            try {
                if (datainputstream.readLong() != this.sessionId) {
                    throw new ExceptionWorldConflict("The save is being accessed from another location, aborting");
                }
            } finally {
                datainputstream.close();
            }

        } catch (IOException var7) {
            throw new ExceptionWorldConflict("Failed to check session lock, aborting");
        }
    }

    public IChunkLoader createChunkLoader(WorldProvider var1) {
        throw new RuntimeException("Old Chunk Storage is no longer supported.");
    }

    @Nullable
    public WorldData getWorldData() {
        File file1 = new File(this.baseDir, "level.dat");
        if (file1.exists()) {
            WorldData worlddata = WorldLoader.a(file1, this.a);
            if (worlddata != null) {
                return worlddata;
            }
        }

        file1 = new File(this.baseDir, "level.dat_old");
        return file1.exists() ? WorldLoader.a(file1, this.a) : null;
    }

    public void saveWorldData(WorldData worlddata, @Nullable NBTTagCompound nbttagcompound) {
        NBTTagCompound nbttagcompound1 = worlddata.a(nbttagcompound);
        NBTTagCompound nbttagcompound2 = new NBTTagCompound();
        nbttagcompound2.set("Data", nbttagcompound1);

        try {
            File file1 = new File(this.baseDir, "level.dat_new");
            File file2 = new File(this.baseDir, "level.dat_old");
            File file3 = new File(this.baseDir, "level.dat");
            NBTCompressedStreamTools.a(nbttagcompound2, new FileOutputStream(file1));
            if (file2.exists()) {
                file2.delete();
            }

            file3.renameTo(file2);
            if (file3.exists()) {
                file3.delete();
            }

            file1.renameTo(file3);
            if (file1.exists()) {
                file1.delete();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public void saveWorldData(WorldData worlddata) {
        this.saveWorldData(worlddata, (NBTTagCompound)null);
    }

    public void save(EntityHuman entityhuman) {
        try {
            NBTTagCompound nbttagcompound = entityhuman.save(new NBTTagCompound());
            File file1 = new File(this.playerDir, entityhuman.bu() + ".dat.tmp");
            File file2 = new File(this.playerDir, entityhuman.bu() + ".dat");
            NBTCompressedStreamTools.a(nbttagcompound, new FileOutputStream(file1));
            if (file2.exists()) {
                file2.delete();
            }

            file1.renameTo(file2);
        } catch (Exception var5) {
            b.warn("Failed to save player data for {}", entityhuman.getDisplayName().getString());
        }

    }

    @Nullable
    public NBTTagCompound load(EntityHuman entityhuman) {
        NBTTagCompound nbttagcompound = null;

        try {
            File file1 = new File(this.playerDir, entityhuman.bu() + ".dat");
            if (file1.exists() && file1.isFile()) {
                nbttagcompound = NBTCompressedStreamTools.a(new FileInputStream(file1));
            }
        } catch (Exception var4) {
            b.warn("Failed to load player data for {}", entityhuman.getDisplayName().getString());
        }

        if (nbttagcompound != null) {
            int i = nbttagcompound.hasKeyOfType("DataVersion", 3) ? nbttagcompound.getInt("DataVersion") : -1;
            entityhuman.f(GameProfileSerializer.a(this.a, DataFixTypes.PLAYER, nbttagcompound, i));
        }

        return nbttagcompound;
    }

    public IPlayerFileData getPlayerFileData() {
        return this;
    }

    public String[] getSeenPlayers() {
        String[] astring = this.playerDir.list();
        if (astring == null) {
            astring = new String[0];
        }

        for(int i = 0; i < astring.length; ++i) {
            if (astring[i].endsWith(".dat")) {
                astring[i] = astring[i].substring(0, astring[i].length() - 4);
            }
        }

        return astring;
    }

    public void a() {
    }

    public File getDataFile(DimensionManager dimensionmanager, String s) {
        File file1 = new File(dimensionmanager.a(this.baseDir), "data");
        file1.mkdirs();
        return new File(file1, s + ".dat");
    }

    public DefinedStructureManager h() {
        return this.g;
    }

    public DataFixer i() {
        return this.a;
    }
}
