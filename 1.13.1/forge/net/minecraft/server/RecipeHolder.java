package net.minecraft.server;

import com.google.common.collect.Lists;
import javax.annotation.Nullable;

public interface RecipeHolder {
    void a(@Nullable IRecipe var1);

    @Nullable
    IRecipe i();

    default void d(EntityHuman entityhuman) {
        IRecipe irecipe = this.i();
        if (irecipe != null && !irecipe.c()) {
            entityhuman.a(Lists.newArrayList(new IRecipe[]{irecipe}));
            this.a((IRecipe)null);
        }

    }

    default boolean a(World world, EntityPlayer entityplayer, @Nullable IRecipe irecipe) {
        if (irecipe == null || !irecipe.c() && world.getGameRules().getBoolean("doLimitedCrafting") && !entityplayer.B().b(irecipe)) {
            return false;
        } else {
            this.a(irecipe);
            return true;
        }
    }
}
