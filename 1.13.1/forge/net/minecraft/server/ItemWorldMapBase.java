package net.minecraft.server;

import javax.annotation.Nullable;

public class ItemWorldMapBase extends Item {
    public ItemWorldMapBase(Item.Info item$info) {
        super(item$info);
    }

    public boolean W_() {
        return true;
    }

    @Nullable
    public Packet<?> a(ItemStack var1, World var2, EntityHuman var3) {
        return null;
    }
}
