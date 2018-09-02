package net.minecraft.server;

public class ItemItemFrame extends ItemHanging {
    public ItemItemFrame(Item.Info item$info) {
        super(EntityItemFrame.class, item$info);
    }

    protected boolean a(EntityHuman entityhuman, EnumDirection enumdirection, ItemStack itemstack, BlockPosition blockposition) {
        return !World.k(blockposition) && entityhuman.a(blockposition, enumdirection, itemstack);
    }
}
