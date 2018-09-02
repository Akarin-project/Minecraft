package net.minecraft.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DebugReportAdvancement implements DebugReportProvider {
    private static final Logger b = LogManager.getLogger();
    private static final Gson c = (new GsonBuilder()).setPrettyPrinting().create();
    private final DebugReportGenerator d;
    private final List<Consumer<Consumer<Advancement>>> e = ImmutableList.of(new DebugReportAdvancementTheEnd(), new DebugReportAdvancementHusbandry(), new DebugReportAdvancementAdventure(), new DebugReportAdvancementNether(), new DebugReportAdvancementStory());

    public DebugReportAdvancement(DebugReportGenerator debugreportgenerator) {
        this.d = debugreportgenerator;
    }

    public void a(HashCache hashcache) throws IOException {
        java.nio.file.Path path = this.d.b();
        HashSet hashset = Sets.newHashSet();
        Consumer consumer = (advancement) -> {
            if (!hashset.add(advancement.getName())) {
                throw new IllegalStateException("Duplicate advancement " + advancement.getName());
            } else {
                this.a(hashcache, advancement.a().b(), path.resolve("data/" + advancement.getName().b() + "/advancements/" + advancement.getName().getKey() + ".json"));
            }
        };

        for(Consumer consumer1 : this.e) {
            consumer1.accept(consumer);
        }

    }

    private void a(HashCache hashcache, JsonObject jsonobject, java.nio.file.Path path) {
        try {
            String s = c.toJson(jsonobject);
            String s1 = a.hashUnencodedChars(s).toString();
            if (!Objects.equals(hashcache.a(path), s1) || !Files.exists(path, new LinkOption[0])) {
                Files.createDirectories(path.getParent());
                BufferedWriter bufferedwriter = Files.newBufferedWriter(path);
                Throwable throwable = null;

                try {
                    bufferedwriter.write(s);
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
            }

            hashcache.a(path, s1);
        } catch (IOException ioexception) {
            b.error("Couldn't save advancement {}", path, ioexception);
        }

    }

    public String a() {
        return "Advancements";
    }
}
