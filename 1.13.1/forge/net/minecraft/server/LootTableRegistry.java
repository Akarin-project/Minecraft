package net.minecraft.server;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootTableRegistry implements IResourcePackListener {
    private static final Logger c = LogManager.getLogger();
    private static final Gson d = (new GsonBuilder()).registerTypeAdapter(LootValueBounds.class, new LootValueBounds.a()).registerTypeAdapter(LootSelector.class, new LootSelector.a()).registerTypeAdapter(LootTable.class, new LootTable.a()).registerTypeHierarchyAdapter(LootSelectorEntry.class, new LootSelectorEntry.a()).registerTypeHierarchyAdapter(LootItemFunction.class, new LootItemFunctions.a()).registerTypeHierarchyAdapter(LootItemCondition.class, new LootItemConditions.a()).registerTypeHierarchyAdapter(LootTableInfo.EntityTarget.class, new LootTableInfo$b$a()).create();
    private final Map<MinecraftKey, LootTable> e = Maps.newHashMap();
    public static final int a = "loot_tables/".length();
    public static final int b = ".json".length();

    public LootTableRegistry() {
    }

    public LootTable getLootTable(MinecraftKey minecraftkey) {
        return (LootTable)this.e.getOrDefault(minecraftkey, LootTable.a);
    }

    public void a(IResourceManager iresourcemanager) {
        this.e.clear();

        for(MinecraftKey minecraftkey : iresourcemanager.a("loot_tables", (s1) -> {
            return s1.endsWith(".json");
        })) {
            String s = minecraftkey.getKey();
            MinecraftKey minecraftkey1 = new MinecraftKey(minecraftkey.b(), s.substring(a, s.length() - b));

            try {
                IResource iresource = iresourcemanager.a(minecraftkey);
                Throwable throwable = null;

                try {
                    LootTable loottable = (LootTable)ChatDeserializer.a(d, IOUtils.toString(iresource.b(), StandardCharsets.UTF_8), LootTable.class);
                    if (loottable != null) {
                        this.e.put(minecraftkey1, loottable);
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
            } catch (Throwable throwable3) {
                c.error("Couldn't read loot table {} from {}", minecraftkey1, minecraftkey, throwable3);
            }
        }

    }
}
