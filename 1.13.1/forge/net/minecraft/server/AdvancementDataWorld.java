package net.minecraft.server;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AdvancementDataWorld implements IResourcePackListener {
    private static final Logger c = LogManager.getLogger();
    public static final Gson DESERIALIZER = (new GsonBuilder()).registerTypeHierarchyAdapter(Advancement.SerializedAdvancement.class, (jsonelement, var1, jsondeserializationcontext) -> {
        JsonObject jsonobject = ChatDeserializer.m(jsonelement, "advancement");
        return Advancement.SerializedAdvancement.a(jsonobject, jsondeserializationcontext);
    }).registerTypeAdapter(AdvancementRewards.class, new AdvancementRewards.b()).registerTypeHierarchyAdapter(IChatBaseComponent.class, new IChatBaseComponent.ChatSerializer()).registerTypeHierarchyAdapter(ChatModifier.class, new ChatModifier.ChatModifierSerializer()).registerTypeAdapterFactory(new ChatTypeAdapterFactory()).create();
    public static final Advancements REGISTRY = new Advancements();
    public static final int a = "advancements/".length();
    public static final int b = ".json".length();
    private boolean f;

    public AdvancementDataWorld() {
    }

    private Map<MinecraftKey, Advancement.SerializedAdvancement> b(IResourceManager iresourcemanager) {
        HashMap hashmap = Maps.newHashMap();

        for(MinecraftKey minecraftkey : iresourcemanager.a("advancements", (s1) -> {
            return s1.endsWith(".json");
        })) {
            String s = minecraftkey.getKey();
            MinecraftKey minecraftkey1 = new MinecraftKey(minecraftkey.b(), s.substring(a, s.length() - b));

            try {
                IResource iresource = iresourcemanager.a(minecraftkey);
                Throwable throwable = null;

                try {
                    Advancement.SerializedAdvancement advancement$serializedadvancement = (Advancement.SerializedAdvancement)ChatDeserializer.a(DESERIALIZER, IOUtils.toString(iresource.b(), StandardCharsets.UTF_8), Advancement.SerializedAdvancement.class);
                    if (advancement$serializedadvancement == null) {
                        c.error("Couldn't load custom advancement {} from {} as it's empty or null", minecraftkey1, minecraftkey);
                    } else {
                        hashmap.put(minecraftkey1, advancement$serializedadvancement);
                    }
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
            } catch (IllegalArgumentException | JsonParseException jsonparseexception) {
                c.error("Parsing error loading custom advancement {}: {}", minecraftkey1, jsonparseexception.getMessage());
                this.f = true;
            } catch (IOException ioexception) {
                c.error("Couldn't read custom advancement {} from {}", minecraftkey1, minecraftkey, ioexception);
                this.f = true;
            }
        }

        return hashmap;
    }

    @Nullable
    public Advancement a(MinecraftKey minecraftkey) {
        return REGISTRY.a(minecraftkey);
    }

    public Collection<Advancement> b() {
        return REGISTRY.c();
    }

    public void a(IResourceManager iresourcemanager) {
        this.f = false;
        REGISTRY.a();
        Map map = this.b(iresourcemanager);
        REGISTRY.a(map);

        for(Advancement advancement : REGISTRY.b()) {
            if (advancement.c() != null) {
                AdvancementTree.a(advancement);
            }
        }

    }
}
