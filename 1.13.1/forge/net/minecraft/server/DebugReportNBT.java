package net.minecraft.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugReportNBT implements DebugReportProvider {
    private static final Logger b = LogManager.getLogger();
    private final DebugReportGenerator c;

    public DebugReportNBT(DebugReportGenerator debugreportgenerator) {
        this.c = debugreportgenerator;
    }

    public void a(HashCache var1) throws IOException {
        java.nio.file.Path path = this.c.b();

        for(java.nio.file.Path path1 : this.c.a()) {
            Files.walk(path1).filter((path2) -> {
                return path2.toString().endsWith(".nbt");
            }).forEach((path4) -> {
                this.a(path4, this.a(path1, path4), path);
            });
        }

    }

    public String a() {
        return "NBT to SNBT";
    }

    private String a(java.nio.file.Path path, java.nio.file.Path path1) {
        String s = path.relativize(path1).toString().replaceAll("\\\\", "/");
        return s.substring(0, s.length() - ".nbt".length());
    }

    private void a(java.nio.file.Path path, String s, java.nio.file.Path path1) {
        try {
            NBTTagCompound nbttagcompound = NBTCompressedStreamTools.a(Files.newInputStream(path));
            IChatBaseComponent ichatbasecomponent = nbttagcompound.a("    ", 0);
            String s1 = ichatbasecomponent.getString();
            java.nio.file.Path path2 = path1.resolve(s + ".snbt");
            Files.createDirectories(path2.getParent());
            BufferedWriter bufferedwriter = Files.newBufferedWriter(path2);
            Throwable throwable = null;

            try {
                bufferedwriter.write(s1);
            } catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            } finally {
                if (bufferedwriter != null) {
                    if (throwable != null) {
                        try {
                            bufferedwriter.close();
                        } catch (Throwable throwable1) {
                            throwable.addSuppressed(throwable1);
                        }
                    } else {
                        bufferedwriter.close();
                    }
                }

            }

            b.info("Converted {} from NBT to SNBT", s);
        } catch (IOException ioexception) {
            b.error("Couldn't convert {} from NBT to SNBT at {}", s, path, ioexception);
        }

    }
}
