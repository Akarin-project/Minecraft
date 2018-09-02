package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourcePackVanilla implements IResourcePack {
    public static java.nio.file.Path a;
    private static final Logger d = LogManager.getLogger();
    public static Class<?> b;
    public final Set<String> c;

    public ResourcePackVanilla(String... astring) {
        this.c = ImmutableSet.copyOf(astring);
    }

    public InputStream b(String s) throws IOException {
        if (!s.contains("/") && !s.contains("\\")) {
            if (a != null) {
                java.nio.file.Path path = a.resolve(s);
                if (Files.exists(path, new LinkOption[0])) {
                    return Files.newInputStream(path);
                }
            }

            return this.a(s);
        } else {
            throw new IllegalArgumentException("Root resources can only be filenames, not paths (no / allowed!)");
        }
    }

    public InputStream a(EnumResourcePackType enumresourcepacktype, MinecraftKey minecraftkey) throws IOException {
        InputStream inputstream = this.c(enumresourcepacktype, minecraftkey);
        if (inputstream != null) {
            return inputstream;
        } else {
            throw new FileNotFoundException(minecraftkey.getKey());
        }
    }

    public Collection<MinecraftKey> a(EnumResourcePackType enumresourcepacktype, String s, int i, Predicate<String> predicate) {
        HashSet hashset = Sets.newHashSet();
        if (a != null) {
            try {
                hashset.addAll(this.a(i, "minecraft", a.resolve(enumresourcepacktype.a()).resolve("minecraft"), s, predicate));
            } catch (IOException var26) {
                ;
            }

            if (enumresourcepacktype == EnumResourcePackType.CLIENT_RESOURCES) {
                Enumeration enumeration = null;

                try {
                    enumeration = b.getClassLoader().getResources(enumresourcepacktype.a() + "/minecraft");
                } catch (IOException var25) {
                    ;
                }

                while(enumeration != null && enumeration.hasMoreElements()) {
                    try {
                        URI uri = ((URL)enumeration.nextElement()).toURI();
                        if ("file".equals(uri.getScheme())) {
                            hashset.addAll(this.a(i, "minecraft", Paths.get(uri), s, predicate));
                        }
                    } catch (IOException | URISyntaxException var24) {
                        ;
                    }
                }
            }
        }

        try {
            URL url1 = ResourcePackVanilla.class.getResource("/" + enumresourcepacktype.a() + "/.mcassetsroot");
            if (url1 == null) {
                d.error("Couldn't find .mcassetsroot, cannot load vanilla resources");
                return hashset;
            }

            URI uri1 = url1.toURI();
            if ("file".equals(uri1.getScheme())) {
                URL url = new URL(url1.toString().substring(0, url1.toString().length() - ".mcassetsroot".length()) + "minecraft");
                if (url == null) {
                    return hashset;
                }

                java.nio.file.Path path = Paths.get(url.toURI());
                hashset.addAll(this.a(i, "minecraft", path, s, predicate));
            } else if ("jar".equals(uri1.getScheme())) {
                FileSystem filesystem = FileSystems.newFileSystem(uri1, Collections.emptyMap());
                Throwable throwable2 = null;

                try {
                    java.nio.file.Path path1 = filesystem.getPath("/" + enumresourcepacktype.a() + "/minecraft");
                    hashset.addAll(this.a(i, "minecraft", path1, s, predicate));
                } catch (Throwable throwable1) {
                    throwable2 = throwable1;
                    throw throwable1;
                } finally {
                    if (filesystem != null) {
                        if (throwable2 != null) {
                            try {
                                filesystem.close();
                            } catch (Throwable throwable) {
                                throwable2.addSuppressed(throwable);
                            }
                        } else {
                            filesystem.close();
                        }
                    }

                }
            } else {
                d.error("Unsupported scheme {} trying to list vanilla resources (NYI?)", uri1);
            }
        } catch (NoSuchFileException | FileNotFoundException var28) {
            ;
        } catch (IOException | URISyntaxException urisyntaxexception) {
            d.error("Couldn't get a list of all vanilla resources", urisyntaxexception);
        }

        return hashset;
    }

    private Collection<MinecraftKey> a(int i, String s, java.nio.file.Path path, String s1, Predicate<String> predicate) throws IOException {
        ArrayList arraylist = Lists.newArrayList();
        Iterator iterator = Files.walk(path.resolve(s1), i, new FileVisitOption[0]).iterator();

        while(iterator.hasNext()) {
            java.nio.file.Path path1 = (java.nio.file.Path)iterator.next();
            if (!path1.endsWith(".mcmeta") && Files.isRegularFile(path1, new LinkOption[0]) && predicate.test(path1.getFileName().toString())) {
                arraylist.add(new MinecraftKey(s, path.relativize(path1).toString().replaceAll("\\\\", "/")));
            }
        }

        return arraylist;
    }

    @Nullable
    protected InputStream c(EnumResourcePackType enumresourcepacktype, MinecraftKey minecraftkey) {
        String s = "/" + enumresourcepacktype.a() + "/" + minecraftkey.b() + "/" + minecraftkey.getKey();
        if (a != null) {
            java.nio.file.Path path = a.resolve(enumresourcepacktype.a() + "/" + minecraftkey.b() + "/" + minecraftkey.getKey());
            if (Files.exists(path, new LinkOption[0])) {
                try {
                    return Files.newInputStream(path);
                } catch (IOException var7) {
                    ;
                }
            }
        }

        try {
            URL url = ResourcePackVanilla.class.getResource(s);
            return url != null && ResourcePackFolder.a(new File(url.getFile()), s) ? ResourcePackVanilla.class.getResourceAsStream(s) : null;
        } catch (IOException var6) {
            return ResourcePackVanilla.class.getResourceAsStream(s);
        }
    }

    @Nullable
    protected InputStream a(String s) {
        return ResourcePackVanilla.class.getResourceAsStream("/" + s);
    }

    public boolean b(EnumResourcePackType enumresourcepacktype, MinecraftKey minecraftkey) {
        InputStream inputstream = this.c(enumresourcepacktype, minecraftkey);
        boolean flag = inputstream != null;
        IOUtils.closeQuietly(inputstream);
        return flag;
    }

    public Set<String> a(EnumResourcePackType var1) {
        return this.c;
    }

    @Nullable
    public <T> T a(ResourcePackMetaParser<T> resourcepackmetaparser) throws IOException {
        try {
            InputStream inputstream = this.b("pack.mcmeta");
            Throwable throwable = null;

            Object object;
            try {
                object = ResourcePackAbstract.a(resourcepackmetaparser, inputstream);
            } catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            } finally {
                if (inputstream != null) {
                    if (throwable != null) {
                        try {
                            inputstream.close();
                        } catch (Throwable throwable1) {
                            throwable.addSuppressed(throwable1);
                        }
                    } else {
                        inputstream.close();
                    }
                }

            }

            return (T)object;
        } catch (FileNotFoundException | RuntimeException var16) {
            return (T)null;
        }
    }

    public String a() {
        return "Default";
    }

    public void close() {
    }
}
