package net.minecraft.server;

import com.google.gson.JsonObject;

public interface RecipeSerializer<T extends IRecipe> {
    T a(MinecraftKey var1, JsonObject var2);

    T a(MinecraftKey var1, PacketDataSerializer var2);

    void a(PacketDataSerializer var1, T var2);

    String a();
}
