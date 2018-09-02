package net.minecraft.server;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CraftingManager implements IResourcePackListener {
    private static final Logger c = LogManager.getLogger();
    public static final int a = "recipes/".length();
    public static final int b = ".json".length();
    public Map<MinecraftKey, IRecipe> recipes = Maps.newHashMap();
    private boolean e;

    public CraftingManager() {
    }

    public void a(IResourceManager iresourcemanager) {
        Gson gson = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
        this.e = false;
        this.recipes.clear();

        for(MinecraftKey minecraftkey : iresourcemanager.a("recipes", (s1) -> {
            return s1.endsWith(".json");
        })) {
            String s = minecraftkey.getKey();
            MinecraftKey minecraftkey1 = new MinecraftKey(minecraftkey.b(), s.substring(a, s.length() - b));

            try {
                IResource iresource = iresourcemanager.a(minecraftkey);
                Throwable throwable = null;

                try {
                    JsonObject jsonobject = (JsonObject)ChatDeserializer.a(gson, IOUtils.toString(iresource.b(), StandardCharsets.UTF_8), JsonObject.class);
                    if (jsonobject == null) {
                        c.error("Couldn't load recipe {} as it's null or empty", minecraftkey1);
                    } else {
                        this.a(RecipeSerializers.a(minecraftkey1, jsonobject));
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
                c.error("Parsing error loading recipe {}", minecraftkey1, jsonparseexception);
                this.e = true;
            } catch (IOException ioexception) {
                c.error("Couldn't read custom advancement {} from {}", minecraftkey1, minecraftkey, ioexception);
                this.e = true;
            }
        }

        c.info("Loaded {} recipes", this.recipes.size());
    }

    public void a(IRecipe irecipe) {
        if (this.recipes.containsKey(irecipe.getKey())) {
            throw new IllegalStateException("Duplicate recipe ignored with ID " + irecipe.getKey());
        } else {
            this.recipes.put(irecipe.getKey(), irecipe);
        }
    }

    public ItemStack craft(IInventory iinventory, World world) {
        for(IRecipe irecipe : this.recipes.values()) {
            if (irecipe.a(iinventory, world)) {
                return irecipe.craftItem(iinventory);
            }
        }

        return ItemStack.a;
    }

    @Nullable
    public IRecipe b(IInventory iinventory, World world) {
        for(IRecipe irecipe : this.recipes.values()) {
            if (irecipe.a(iinventory, world)) {
                return irecipe;
            }
        }

        return null;
    }

    public NonNullList<ItemStack> c(IInventory iinventory, World world) {
        for(IRecipe irecipe : this.recipes.values()) {
            if (irecipe.a(iinventory, world)) {
                return irecipe.b(iinventory);
            }
        }

        NonNullList nonnulllist = NonNullList.a(iinventory.getSize(), ItemStack.a);

        for(int i = 0; i < nonnulllist.size(); ++i) {
            nonnulllist.set(i, iinventory.getItem(i));
        }

        return nonnulllist;
    }

    @Nullable
    public IRecipe a(MinecraftKey minecraftkey) {
        return (IRecipe)this.recipes.get(minecraftkey);
    }

    public Collection<IRecipe> b() {
        return this.recipes.values();
    }

    public Collection<MinecraftKey> c() {
        return this.recipes.keySet();
    }
}
