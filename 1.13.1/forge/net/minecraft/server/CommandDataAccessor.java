package net.minecraft.server;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

public interface CommandDataAccessor {
    void a(NBTTagCompound var1) throws CommandSyntaxException;

    NBTTagCompound a() throws CommandSyntaxException;

    IChatBaseComponent b();

    IChatBaseComponent a(NBTBase var1);

    IChatBaseComponent a(ArgumentNBTKey.c var1, double var2, int var4);
}
