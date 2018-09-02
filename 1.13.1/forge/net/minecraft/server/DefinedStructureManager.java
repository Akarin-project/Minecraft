package net.minecraft.server;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixTypes;
import com.mojang.datafixers.DataFixer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.util.Map;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefinedStructureManager implements IResourcePackListener {
    private static final Logger a = LogManager.getLogger();
    private final Map<MinecraftKey, DefinedStructure> b = Maps.newHashMap();
    private final DataFixer c;
    private final MinecraftServer d;
    private final java.nio.file.Path e;

    public DefinedStructureManager(MinecraftServer minecraftserver, File file1, DataFixer datafixer) {
        this.d = minecraftserver;
        this.c = datafixer;
        this.e = file1.toPath().resolve("generated").normalize();
        minecraftserver.getResourceManager().a(this);
    }

    public DefinedStructure a(MinecraftKey minecraftkey) {
        DefinedStructure definedstructure = this.b(minecraftkey);
        if (definedstructure == null) {
            definedstructure = new DefinedStructure();
            this.b.put(minecraftkey, definedstructure);
        }

        return definedstructure;
    }

    @Nullable
    public DefinedStructure b(MinecraftKey minecraftkey) {
        return (DefinedStructure)this.b.computeIfAbsent(minecraftkey, (minecraftkey1) -> {
            DefinedStructure definedstructure = this.f(minecraftkey1);
            return definedstructure != null ? definedstructure : this.e(minecraftkey1);
        });
    }

    public void a(IResourceManager var1) {
        this.b.clear();
    }

    @Nullable
    private DefinedStructure e(MinecraftKey minecraftkey) {
        MinecraftKey minecraftkey1 = new MinecraftKey(minecraftkey.b(), "structures/" + minecraftkey.getKey() + ".nbt");

        try {
            IResource iresource = this.d.getResourceManager().a(minecraftkey1);
            Throwable throwable = null;

            DefinedStructure definedstructure;
            try {
                definedstructure = this.a(iresource.b());
            } catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            } finally {
                if (iresource != null) {
                    if (throwable != null) {
                        try {
                            iresource.close();
                        } catch (Throwable throwable1) {
                            throwable.addSuppressed(throwable1);
                        }
                    } else {
                        iresource.close();
                    }
                }

            }

            return definedstructure;
        } catch (FileNotFoundException var18) {
            return null;
        } catch (Throwable throwable3) {
            a.error("Couldn't load structure {}: {}", minecraftkey, throwable3.toString());
            return null;
        }
    }

    @Nullable
    private DefinedStructure f(MinecraftKey minecraftkey) {
        if (!this.e.toFile().isDirectory()) {
            return null;
        } else {
            java.nio.file.Path path = this.b(minecraftkey, ".nbt");

            try {
                FileInputStream fileinputstream = new FileInputStream(path.toFile());
                Throwable throwable = null;

                DefinedStructure definedstructure;
                try {
                    definedstructure = this.a(fileinputstream);
                } catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                } finally {
                    if (fileinputstream != null) {
                        if (throwable != null) {
                            try {
                                fileinputstream.close();
                            } catch (Throwable throwable1) {
                                throwable.addSuppressed(throwable1);
                            }
                        } else {
                            fileinputstream.close();
                        }
                    }

                }

                return definedstructure;
            } catch (FileNotFoundException var18) {
                return null;
            } catch (IOException ioexception) {
                a.error("Couldn't load structure from {}", path, ioexception);
                return null;
            }
        }
    }

    private DefinedStructure a(InputStream inputstream) throws IOException {
        NBTTagCompound nbttagcompound = NBTCompressedStreamTools.a(inputstream);
        if (!nbttagcompound.hasKeyOfType("DataVersion", 99)) {
            nbttagcompound.setInt("DataVersion", 500);
        }

        DefinedStructure definedstructure = new DefinedStructure();
        definedstructure.b(GameProfileSerializer.a(this.c, DataFixTypes.STRUCTURE, nbttagcompound, nbttagcompound.getInt("DataVersion")));
        return definedstructure;
    }

    public boolean c(MinecraftKey minecraftkey) {
        DefinedStructure definedstructure = (DefinedStructure)this.b.get(minecraftkey);
        if (definedstructure == null) {
            return false;
        } else {
            java.nio.file.Path path = this.b(minecraftkey, ".nbt");
            java.nio.file.Path path1 = path.getParent();
            if (path1 == null) {
                return false;
            } else {
                try {
                    Files.createDirectories(Files.exists(path1, new LinkOption[0]) ? path1.toRealPath() : path1);
                } catch (IOException var19) {
                    a.error("Failed to create parent directory: {}", path1);
                    return false;
                }

                NBTTagCompound nbttagcompound = definedstructure.a(new NBTTagCompound());

                try {
                    FileOutputStream fileoutputstream = new FileOutputStream(path.toFile());
                    Throwable throwable = null;

                    try {
                        NBTCompressedStreamTools.a(nbttagcompound, fileoutputstream);
                    } catch (Throwable throwable2) {
                        throwable = throwable2;
                        throw throwable2;
                    } finally {
                        if (fileoutputstream != null) {
                            if (throwable != null) {
                                try {
                                    fileoutputstream.close();
                                } catch (Throwable throwable1) {
                                    throwable.addSuppressed(throwable1);
                                }
                            } else {
                                fileoutputstream.close();
                            }
                        }

                    }

                    return true;
                } catch (Throwable var21) {
                    return false;
                }
            }
        }
    }

    private java.nio.file.Path a(MinecraftKey minecraftkey, String s) {
        try {
            java.nio.file.Path path = this.e.resolve(minecraftkey.b());
            java.nio.file.Path path1 = path.resolve("structures");
            return SystemUtils.a(path1, minecraftkey.getKey(), s);
        } catch (InvalidPathException invalidpathexception) {
            throw new ResourceKeyInvalidException("Invalid resource path: " + minecraftkey, invalidpathexception);
        }
    }

    private java.nio.file.Path b(MinecraftKey minecraftkey, String s) {
        if (minecraftkey.getKey().contains("//")) {
            throw new ResourceKeyInvalidException("Invalid resource path: " + minecraftkey);
        } else {
            java.nio.file.Path path = this.a(minecraftkey, s);
            if (path.startsWith(this.e) && SystemUtils.a(path) && SystemUtils.b(path)) {
                return path;
            } else {
                throw new ResourceKeyInvalidException("Invalid resource path: " + path);
            }
        }
    }

    public void d(MinecraftKey minecraftkey) {
        this.b.remove(minecraftkey);
    }
}
