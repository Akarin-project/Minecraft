package net.minecraft.server;

public class ItemClock extends Item {
    public ItemClock(Item.Info item$info) {
        super(item$info);
        this.a(new MinecraftKey("time"), new IDynamicTexture() {
        });
    }
}
