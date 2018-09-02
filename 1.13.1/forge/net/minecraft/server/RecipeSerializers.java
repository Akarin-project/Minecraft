package net.minecraft.server;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.function.Function;

public class RecipeSerializers {
    private static final Map<String, RecipeSerializer<?>> q = Maps.newHashMap();
    public static final RecipeSerializer<ShapedRecipes> a = a(new ShapedRecipes.a());
    public static final RecipeSerializer<ShapelessRecipes> b = a(new ShapelessRecipes.a());
    public static final RecipeSerializers.a<RecipeArmorDye> c = (RecipeSerializers.a)a(new RecipeSerializers.a("crafting_special_armordye", RecipeArmorDye::new));
    public static final RecipeSerializers.a<RecipeBookClone> d = (RecipeSerializers.a)a(new RecipeSerializers.a("crafting_special_bookcloning", RecipeBookClone::new));
    public static final RecipeSerializers.a<RecipeMapClone> e = (RecipeSerializers.a)a(new RecipeSerializers.a("crafting_special_mapcloning", RecipeMapClone::new));
    public static final RecipeSerializers.a<RecipeMapExtend> f = (RecipeSerializers.a)a(new RecipeSerializers.a("crafting_special_mapextending", RecipeMapExtend::new));
    public static final RecipeSerializers.a<RecipeFireworks> g = (RecipeSerializers.a)a(new RecipeSerializers.a("crafting_special_firework_rocket", RecipeFireworks::new));
    public static final RecipeSerializers.a<RecipeFireworksStar> h = (RecipeSerializers.a)a(new RecipeSerializers.a("crafting_special_firework_star", RecipeFireworksStar::new));
    public static final RecipeSerializers.a<RecipeFireworksFade> i = (RecipeSerializers.a)a(new RecipeSerializers.a("crafting_special_firework_star_fade", RecipeFireworksFade::new));
    public static final RecipeSerializers.a<RecipeRepair> j = (RecipeSerializers.a)a(new RecipeSerializers.a("crafting_special_repairitem", RecipeRepair::new));
    public static final RecipeSerializers.a<RecipeTippedArrow> k = (RecipeSerializers.a)a(new RecipeSerializers.a("crafting_special_tippedarrow", RecipeTippedArrow::new));
    public static final RecipeSerializers.a<RecipeBannerDuplicate> l = (RecipeSerializers.a)a(new RecipeSerializers.a("crafting_special_bannerduplicate", RecipeBannerDuplicate::new));
    public static final RecipeSerializers.a<RecipeBannerAdd> m = (RecipeSerializers.a)a(new RecipeSerializers.a("crafting_special_banneraddpattern", RecipeBannerAdd::new));
    public static final RecipeSerializers.a<RecipiesShield> n = (RecipeSerializers.a)a(new RecipeSerializers.a("crafting_special_shielddecoration", RecipiesShield::new));
    public static final RecipeSerializers.a<RecipeShulkerBox> o = (RecipeSerializers.a)a(new RecipeSerializers.a("crafting_special_shulkerboxcoloring", RecipeShulkerBox::new));
    public static final RecipeSerializer<FurnaceRecipe> p = a(new FurnaceRecipe.a());

    public static <S extends RecipeSerializer<T>, T extends IRecipe> S a(S recipeserializer) {
        if (q.containsKey(recipeserializer.a())) {
            throw new IllegalArgumentException("Duplicate recipe serializer " + recipeserializer.a());
        } else {
            q.put(recipeserializer.a(), recipeserializer);
            return (S)recipeserializer;
        }
    }

    public static IRecipe a(MinecraftKey minecraftkey, JsonObject jsonobject) {
        String s = ChatDeserializer.h(jsonobject, "type");
        RecipeSerializer recipeserializer = (RecipeSerializer)q.get(s);
        if (recipeserializer == null) {
            throw new JsonSyntaxException("Invalid or unsupported recipe type '" + s + "'");
        } else {
            return recipeserializer.a(minecraftkey, jsonobject);
        }
    }

    public static IRecipe a(PacketDataSerializer packetdataserializer) {
        MinecraftKey minecraftkey = packetdataserializer.l();
        String s = packetdataserializer.e(32767);
        RecipeSerializer recipeserializer = (RecipeSerializer)q.get(s);
        if (recipeserializer == null) {
            throw new IllegalArgumentException("Unknown recipe serializer " + s);
        } else {
            return recipeserializer.a(minecraftkey, packetdataserializer);
        }
    }

    public static <T extends IRecipe> void a(T irecipe, PacketDataSerializer packetdataserializer) {
        packetdataserializer.a(irecipe.getKey());
        packetdataserializer.a(irecipe.a().a());
        RecipeSerializer recipeserializer = irecipe.a();
        recipeserializer.a(packetdataserializer, irecipe);
    }

    public static final class a<T extends IRecipe> implements RecipeSerializer<T> {
        private final String a;
        private final Function<MinecraftKey, T> b;

        public a(String s, Function<MinecraftKey, T> function) {
            this.a = s;
            this.b = function;
        }

        public T a(MinecraftKey minecraftkey, JsonObject var2) {
            return (T)(this.b.apply(minecraftkey));
        }

        public T a(MinecraftKey minecraftkey, PacketDataSerializer var2) {
            return (T)(this.b.apply(minecraftkey));
        }

        public void a(PacketDataSerializer var1, T var2) {
        }

        public String a() {
            return this.a;
        }
    }
}
