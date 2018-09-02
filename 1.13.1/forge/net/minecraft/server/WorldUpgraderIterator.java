package net.minecraft.server;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.ImmutableMap.Builder;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WorldUpgraderIterator {
    private static final Pattern a = Pattern.compile("^r\\.(-?[0-9]+)\\.(-?[0-9]+)\\.mca$");
    private final File b;
    private final Map<DimensionManager, List<ChunkCoordIntPair>> c;

    public WorldUpgraderIterator(File file1) {
        this.b = file1;
        Builder builder = ImmutableMap.builder();

        for(DimensionManager dimensionmanager : DimensionManager.b()) {
            builder.put(dimensionmanager, this.b(dimensionmanager));
        }

        this.c = builder.build();
    }

    private List<ChunkCoordIntPair> b(DimensionManager dimensionmanager) {
        ArrayList arraylist = Lists.newArrayList();
        File file1 = dimensionmanager.a(this.b);
        List list = this.b(file1);

        for(File file2 : list) {
            arraylist.addAll(this.a(file2));
        }

        list.sort(File::compareTo);
        return arraylist;
    }

    private List<ChunkCoordIntPair> a(File file1) {
        ArrayList arraylist = Lists.newArrayList();
        RegionFile regionfile = null;

        ArrayList arraylist1;
        try {
            Matcher matcher = a.matcher(file1.getName());
            if (matcher.matches()) {
                int l = Integer.parseInt(matcher.group(1)) << 5;
                int i = Integer.parseInt(matcher.group(2)) << 5;
                regionfile = new RegionFile(file1);

                for(int j = 0; j < 32; ++j) {
                    for(int k = 0; k < 32; ++k) {
                        if (regionfile.b(j, k)) {
                            arraylist.add(new ChunkCoordIntPair(j + l, k + i));
                        }
                    }
                }

                return arraylist;
            }

            arraylist1 = arraylist;
        } catch (Throwable var18) {
            arraylist1 = Lists.newArrayList();
            return arraylist1;
        } finally {
            if (regionfile != null) {
                try {
                    regionfile.c();
                } catch (IOException var17) {
                    ;
                }
            }

        }

        return arraylist1;
    }

    private List<File> b(File file1) {
        File file2 = new File(file1, "region");
        File[] afile = file2.listFiles((var0, s) -> {
            return s.endsWith(".mca");
        });
        return afile != null ? Lists.newArrayList(afile) : Lists.newArrayList();
    }

    public List<ChunkCoordIntPair> a(DimensionManager dimensionmanager) {
        return (List)this.c.get(dimensionmanager);
    }
}
