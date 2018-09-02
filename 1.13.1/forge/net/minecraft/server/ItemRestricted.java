package net.minecraft.server;

import javax.annotation.Nullable;

public class ItemRestricted extends ItemBlock {
    public ItemRestricted(Block block, Item.Info item$info) {
        super(block, item$info);
    }

    @Nullable
    protected IBlockData b(BlockActionContext blockactioncontext) {
        EntityHuman entityhuman = blockactioncontext.getEntity();
        return entityhuman != null && !entityhuman.isCreativeAndOp() ? null : super.b(blockactioncontext);
    }
}
