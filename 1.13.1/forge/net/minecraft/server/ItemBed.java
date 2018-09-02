package net.minecraft.server;

public class ItemBed extends ItemBlock {
    public ItemBed(Block block, Item.Info item$info) {
        super(block, item$info);
    }

    protected boolean a(BlockActionContext blockactioncontext, IBlockData iblockdata) {
        return blockactioncontext.getWorld().setTypeAndData(blockactioncontext.getClickPosition(), iblockdata, 26);
    }
}
