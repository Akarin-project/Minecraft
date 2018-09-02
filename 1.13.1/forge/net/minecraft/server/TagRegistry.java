package net.minecraft.server;

public class TagRegistry implements IResourcePackListener {
    private final TagsServer<Block> a = new TagsServer<Block>(IRegistry.BLOCK, "tags/blocks", "block");
    private final TagsServer<Item> b = new TagsServer<Item>(IRegistry.ITEM, "tags/items", "item");
    private final TagsServer<FluidType> c = new TagsServer<FluidType>(IRegistry.FLUID, "tags/fluids", "fluid");

    public TagRegistry() {
    }

    public TagsServer<Block> a() {
        return this.a;
    }

    public TagsServer<Item> b() {
        return this.b;
    }

    public TagsServer<FluidType> c() {
        return this.c;
    }

    public void d() {
        this.a.b();
        this.b.b();
        this.c.b();
    }

    public void a(IResourceManager iresourcemanager) {
        this.d();
        this.a.a(iresourcemanager);
        this.b.a(iresourcemanager);
        this.c.a(iresourcemanager);
        TagsBlock.a(this.a);
        TagsItem.a(this.b);
        TagsFluid.a(this.c);
    }

    public void a(PacketDataSerializer packetdataserializer) {
        this.a.a(packetdataserializer);
        this.b.a(packetdataserializer);
        this.c.a(packetdataserializer);
    }

    public static TagRegistry b(PacketDataSerializer packetdataserializer) {
        TagRegistry tagregistry = new TagRegistry();
        tagregistry.a().b(packetdataserializer);
        tagregistry.b().b(packetdataserializer);
        tagregistry.c().b(packetdataserializer);
        return tagregistry;
    }
}
