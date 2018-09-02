package net.minecraft.server;

public class LightEngineBlock extends LightEngine {
    public LightEngineBlock() {
    }

    public EnumSkyBlock a() {
        return EnumSkyBlock.BLOCK;
    }

    public void a(RegionLimitedWorldAccess regionlimitedworldaccess, IChunkAccess ichunkaccess) {
        for(BlockPosition blockposition : ichunkaccess.j()) {
            this.a(regionlimitedworldaccess, blockposition, this.b(regionlimitedworldaccess, blockposition));
            this.a(ichunkaccess.getPos(), blockposition, this.a(regionlimitedworldaccess, blockposition));
        }

        this.a(regionlimitedworldaccess, ichunkaccess.getPos());
    }
}
