package net.minecraft.server;

public interface IDispenseBehavior {
    IDispenseBehavior NONE = (var0, itemstack) -> {
        return itemstack;
    };

    ItemStack dispense(ISourceBlock var1, ItemStack var2);
}
