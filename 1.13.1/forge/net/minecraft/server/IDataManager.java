package net.minecraft.server;

import com.mojang.datafixers.DataFixer;
import java.io.File;
import javax.annotation.Nullable;

public interface IDataManager {
    @Nullable
    WorldData getWorldData();

    void checkSession() throws ExceptionWorldConflict;

    IChunkLoader createChunkLoader(WorldProvider var1);

    void saveWorldData(WorldData var1, NBTTagCompound var2);

    void saveWorldData(WorldData var1);

    IPlayerFileData getPlayerFileData();

    void a();

    File getDirectory();

    @Nullable
    File getDataFile(DimensionManager var1, String var2);

    DefinedStructureManager h();

    DataFixer i();
}
