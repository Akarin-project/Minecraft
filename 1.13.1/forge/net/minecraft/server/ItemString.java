package net.minecraft.server;

public class ItemString extends ItemBlock {
    public ItemString(Item.Info item$info) {
        super(Blocks.TRIPWIRE, item$info);
    }

    public String getName() {
        return this.m();
    }
}
