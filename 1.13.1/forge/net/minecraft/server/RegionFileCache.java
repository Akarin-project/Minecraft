package net.minecraft.server;

import com.google.common.collect.Maps;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.annotation.Nullable;

public class RegionFileCache {
    private static final Map<File, RegionFile> a = Maps.newHashMap();

    public static synchronized RegionFile a(File file1, int i, int j) {
        File file2 = new File(file1, "region");
        File file3 = new File(file2, "r." + (i >> 5) + "." + (j >> 5) + ".mca");
        RegionFile regionfile = (RegionFile)a.get(file3);
        if (regionfile != null) {
            return regionfile;
        } else {
            if (!file2.exists()) {
                file2.mkdirs();
            }

            if (a.size() >= 256) {
                a();
            }

            RegionFile regionfile1 = new RegionFile(file3);
            a.put(file3, regionfile1);
            return regionfile1;
        }
    }

    public static synchronized void a() {
        for(RegionFile regionfile : a.values()) {
            try {
                if (regionfile != null) {
                    regionfile.c();
                }
            } catch (IOException ioexception) {
                ioexception.printStackTrace();
            }
        }

        a.clear();
    }

    @Nullable
    public static DataInputStream read(File file1, int i, int j) {
        RegionFile regionfile = a(file1, i, j);
        return regionfile.a(i & 31, j & 31);
    }

    @Nullable
    public static DataOutputStream write(File file1, int i, int j) {
        RegionFile regionfile = a(file1, i, j);
        return regionfile.c(i & 31, j & 31);
    }
}
