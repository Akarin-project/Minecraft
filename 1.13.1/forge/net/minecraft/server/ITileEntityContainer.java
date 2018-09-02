package net.minecraft.server;

public interface ITileEntityContainer extends INamableTileEntity {
    Container createContainer(PlayerInventory var1, EntityHuman var2);

    String getContainerName();
}
