package net.minecraft.server;

import javax.annotation.Nullable;

public interface IPlayerFileData {
    void save(EntityHuman var1);

    @Nullable
    NBTTagCompound load(EntityHuman var1);

    String[] getSeenPlayers();
}
