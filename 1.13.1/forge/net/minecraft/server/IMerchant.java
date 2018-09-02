package net.minecraft.server;

import javax.annotation.Nullable;

public interface IMerchant {
    void setTradingPlayer(@Nullable EntityHuman var1);

    @Nullable
    EntityHuman getTrader();

    @Nullable
    MerchantRecipeList getOffers(EntityHuman var1);

    void a(MerchantRecipe var1);

    void a(ItemStack var1);

    IChatBaseComponent getScoreboardDisplayName();

    World getWorld();

    BlockPosition getPosition();
}
