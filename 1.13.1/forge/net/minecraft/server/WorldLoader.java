package net.minecraft.server;

import com.mojang.datafixers.DataFixTypes;
import com.mojang.datafixers.DataFixer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldLoader implements Convertable {
    private static final Logger e = LogManager.getLogger();
    protected final java.nio.file.Path a;
    protected final java.nio.file.Path b;
    protected final DataFixer c;

    public WorldLoader(java.nio.file.Path path, java.nio.file.Path path1, DataFixer datafixer) {
        this.c = datafixer;

        try {
            Files.createDirectories(Files.exists(path, new LinkOption[0]) ? path.toRealPath() : path);
        } catch (IOException ioexception) {
            throw new RuntimeException(ioexception);
        }

        this.a = path;
        this.b = path1;
    }

    @Nullable
    public WorldData c(String s) {
        File file1 = new File(this.a.toFile(), s);
        if (!file1.exists()) {
            return null;
        } else {
            File file2 = new File(file1, "level.dat");
            if (file2.exists()) {
                WorldData worlddata = a(file2, this.c);
                if (worlddata != null) {
                    return worlddata;
                }
            }

            file2 = new File(file1, "level.dat_old");
            return file2.exists() ? a(file2, this.c) : null;
        }
    }

    @Nullable
    public static WorldData a(File file1, DataFixer datafixer) {
        try {
            NBTTagCompound nbttagcompound = NBTCompressedStreamTools.a(new FileInputStream(file1));
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Data");
            NBTTagCompound nbttagcompound2 = nbttagcompound1.hasKeyOfType("Player", 10) ? nbttagcompound1.getCompound("Player") : null;
            nbttagcompound1.remove("Player");
            int i = nbttagcompound1.hasKeyOfType("DataVersion", 99) ? nbttagcompound1.getInt("DataVersion") : -1;
            return new WorldData(GameProfileSerializer.a(datafixer, DataFixTypes.LEVEL, nbttagcompound1, i), datafixer, i, nbttagcompound2);
        } catch (Exception exception) {
            e.error("Exception reading {}", file1, exception);
            return null;
        }
    }

    public IDataManager a(String s, @Nullable MinecraftServer minecraftserver) {
        return new WorldNBTStorage(this.a.toFile(), s, minecraftserver, this.c);
    }

    public boolean isConvertable(String var1) {
        return false;
    }

    public boolean convert(String var1, IProgressUpdate var2) {
        return false;
    }

    public File b(String s, String s1) {
        return this.a.resolve(s).resolve(s1).toFile();
    }
}
