package net.minecraft.server;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.Objects;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugReportMojangson implements DebugReportProvider {
    private static final Logger b = LogManager.getLogger();
    private final DebugReportGenerator c;

    public DebugReportMojangson(DebugReportGenerator debugreportgenerator) {
        this.c = debugreportgenerator;
    }

    public void a(HashCache hashcache) throws IOException {
        java.nio.file.Path path = this.c.b();

        for(java.nio.file.Path path1 : this.c.a()) {
            Files.walk(path1).filter((path2) -> {
                return path2.toString().endsWith(".snbt");
            }).forEach((path4) -> {
                this.a(hashcache, path4, this.a(path1, path4), path);
            });
        }

    }

    public String a() {
        return "SNBT -> NBT";
    }

    private String a(java.nio.file.Path path, java.nio.file.Path path1) {
        String s = path.relativize(path1).toString().replaceAll("\\\\", "/");
        return s.substring(0, s.length() - ".snbt".length());
    }

    private void a(HashCache hashcache, java.nio.file.Path path, String s, java.nio.file.Path path1) {
        try {
            java.nio.file.Path path2 = path1.resolve(s + ".nbt");
            BufferedReader bufferedreader = Files.newBufferedReader(path);
            Throwable throwable = null;

            try {
                String s1 = IOUtils.toString(bufferedreader);
                String s2 = a.hashUnencodedChars(s1).toString();
                if (!Objects.equals(hashcache.a(path2), s2) || !Files.exists(path2, new LinkOption[0])) {
                    Files.createDirectories(path2.getParent());
                    OutputStream outputstream = Files.newOutputStream(path2);
                    Throwable throwable1 = null;

                    try {
                        NBTCompressedStreamTools.a(MojangsonParser.parse(s1), outputstream);
                    } catch (Throwable throwable4) {
                        throwable1 = throwable4;
                        throw throwable4;
                    } finally {
                        if (outputstream != null) {
                            if (throwable1 != null) {
                                try {
                                    outputstream.close();
                                } catch (Throwable throwable3) {
                                    throwable1.addSuppressed(throwable3);
                                }
                            } else {
                                outputstream.close();
                            }
                        }

                    }
                }

                hashcache.a(path2, s2);
            } catch (Throwable throwable5) {
                throwable = throwable5;
                throw throwable5;
            } finally {
                if (bufferedreader != null) {
                    if (throwable != null) {
                        try {
                            bufferedreader.close();
                        } catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    } else {
                        bufferedreader.close();
                    }
                }

            }
        } catch (CommandSyntaxException commandsyntaxexception) {
            b.error("Couldn't convert {} from SNBT to NBT at {} as it's invalid SNBT", s, path, commandsyntaxexception);
        } catch (IOException ioexception) {
            b.error("Couldn't convert {} from SNBT to NBT at {}", s, path, ioexception);
        }

    }
}
