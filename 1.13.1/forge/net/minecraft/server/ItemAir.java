package net.minecraft.server;

public class ItemAir extends Item {
    private final Block a;

    public ItemAir(Block block, Item.Info item$info) {
        super(item$info);
        this.a = block;
    }

    public String getName() {
        return this.a.m();
    }
}
