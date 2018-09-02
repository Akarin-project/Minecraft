package net.minecraft.server;

import java.io.File;
import java.io.FileFilter;
import java.util.Map;
import java.util.function.Supplier;

public class ResourcePackSourceFolder implements ResourcePackSource {
    private static final FileFilter a = (file1) -> {
        boolean flag = file1.isFile() && file1.getName().endsWith(".zip");
        boolean flag1 = file1.isDirectory() && (new File(file1, "pack.mcmeta")).isFile();
        return flag || flag1;
    };
    private final File file;

    public ResourcePackSourceFolder(File file1) {
        this.file = file1;
    }

    public <T extends ResourcePackLoader> void a(Map<String, T> map, ResourcePackLoader.b<T> resourcepackloader$b) {
        if (!this.file.isDirectory()) {
            this.file.mkdirs();
        }

        File[] afile = this.file.listFiles(a);
        if (afile != null) {
            for(File file1 : afile) {
                String s = "file/" + file1.getName();
                ResourcePackLoader resourcepackloader = ResourcePackLoader.a(s, false, this.a(file1), resourcepackloader$b, ResourcePackLoader.Position.TOP);
                if (resourcepackloader != null) {
                    map.put(s, resourcepackloader);
                }
            }

        }
    }

    private Supplier<IResourcePack> a(File file1) {
        return file1.isDirectory() ? () -> {
            return new ResourcePackFolder(file1);
        } : () -> {
            return new ResourcePackFile(file1);
        };
    }
}
