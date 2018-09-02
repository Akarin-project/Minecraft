package net.minecraft.server;

import com.google.common.base.CharMatcher;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourcePackFolder extends ResourcePackAbstract {
    private static final Logger b = LogManager.getLogger();
    private static final boolean c = SystemUtils.e() == SystemUtils.OS.WINDOWS;
    private static final CharMatcher d = CharMatcher.is('\\');

    public ResourcePackFolder(File file1) {
        super(file1);
    }

    public static boolean a(File file1, String s) throws IOException {
        String s1 = file1.getCanonicalPath();
        if (c) {
            s1 = d.replaceFrom(s1, '/');
        }

        return s1.endsWith(s);
    }

    protected InputStream a(String s) throws IOException {
        File file1 = this.e(s);
        if (file1 == null) {
            throw new ResourceNotFoundException(this.a, s);
        } else {
            return new FileInputStream(file1);
        }
    }

    protected boolean c(String s) {
        return this.e(s) != null;
    }

    @Nullable
    private File e(String s) {
        try {
            File file1 = new File(this.a, s);
            if (file1.isFile() && a(file1, s)) {
                return file1;
            }
        } catch (IOException var3) {
            ;
        }

        return null;
    }

    public Set<String> a(EnumResourcePackType enumresourcepacktype) {
        HashSet hashset = Sets.newHashSet();
        File file1 = new File(this.a, enumresourcepacktype.a());
        File[] afile = file1.listFiles(DirectoryFileFilter.DIRECTORY);
        if (afile != null) {
            for(File file2 : afile) {
                String s = a(file1, file2);
                if (s.equals(s.toLowerCase(Locale.ROOT))) {
                    hashset.add(s.substring(0, s.length() - 1));
                } else {
                    this.d(s);
                }
            }
        }

        return hashset;
    }

    public void close() throws IOException {
    }

    public Collection<MinecraftKey> a(EnumResourcePackType enumresourcepacktype, String s, int i, Predicate<String> predicate) {
        File file1 = new File(this.a, enumresourcepacktype.a());
        ArrayList arraylist = Lists.newArrayList();

        for(String s1 : this.a(enumresourcepacktype)) {
            this.a(new File(new File(file1, s1), s), i, s1, arraylist, s + "/", predicate);
        }

        return arraylist;
    }

    private void a(File file1, int i, String s, List<MinecraftKey> list, String s1, Predicate<String> predicate) {
        File[] afile = file1.listFiles();
        if (afile != null) {
            for(File file2 : afile) {
                if (file2.isDirectory()) {
                    if (i > 0) {
                        this.a(file2, i - 1, s, list, s1 + file2.getName() + "/", predicate);
                    }
                } else if (!file2.getName().endsWith(".mcmeta") && predicate.test(file2.getName())) {
                    try {
                        list.add(new MinecraftKey(s, s1 + file2.getName()));
                    } catch (ResourceKeyInvalidException resourcekeyinvalidexception) {
                        b.error(resourcekeyinvalidexception.getMessage());
                    }
                }
            }
        }

    }
}
