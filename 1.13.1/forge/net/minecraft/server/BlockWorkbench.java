package net.minecraft.server;

import javax.annotation.Nullable;

public class BlockWorkbench extends Block {
    protected BlockWorkbench(Block.Info block$info) {
        super(block$info);
    }

    public boolean interact(IBlockData var1, World world, BlockPosition blockposition, EntityHuman entityhuman, EnumHand var5, EnumDirection var6, float var7, float var8, float var9) {
        if (world.isClientSide) {
            return true;
        } else {
            entityhuman.openTileEntity(new BlockWorkbench.TileEntityContainerWorkbench(world, blockposition));
            entityhuman.a(StatisticList.INTERACT_WITH_CRAFTING_TABLE);
            return true;
        }
    }

    public static class TileEntityContainerWorkbench implements ITileEntityContainer {
        private final World a;
        private final BlockPosition b;

        public TileEntityContainerWorkbench(World world, BlockPosition blockposition) {
            this.a = world;
            this.b = blockposition;
        }

        public IChatBaseComponent getDisplayName() {
            return new ChatMessage(Blocks.CRAFTING_TABLE.m() + ".name", new Object[0]);
        }

        public boolean hasCustomName() {
            return false;
        }

        @Nullable
        public IChatBaseComponent getCustomName() {
            return null;
        }

        public Container createContainer(PlayerInventory playerinventory, EntityHuman var2) {
            return new ContainerWorkbench(playerinventory, this.a, this.b);
        }

        public String getContainerName() {
            return "minecraft:crafting_table";
        }
    }
}
