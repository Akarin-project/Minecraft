package net.minecraft.server;

import javax.annotation.Nullable;

public interface DefinedStructureProcessor {
    @Nullable
    DefinedStructure.BlockInfo a(IBlockAccess var1, BlockPosition var2, DefinedStructure.BlockInfo var3);
}
