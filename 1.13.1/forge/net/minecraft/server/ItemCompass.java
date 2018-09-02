package net.minecraft.server;

public class ItemCompass extends Item {
    public ItemCompass(Item.Info item$info) {
        super(item$info);
        this.a(new MinecraftKey("angle"), new IDynamicTexture() {
        });
    }
}
