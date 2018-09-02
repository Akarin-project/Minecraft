package net.minecraft.server;

public interface IRecipe {
    boolean a(IInventory var1, World var2);

    ItemStack craftItem(IInventory var1);

    ItemStack d();

    default NonNullList<ItemStack> b(IInventory iinventory) {
        NonNullList nonnulllist = NonNullList.a(iinventory.getSize(), ItemStack.a);

        for(int i = 0; i < nonnulllist.size(); ++i) {
            Item item = iinventory.getItem(i).getItem();
            if (item.p()) {
                nonnulllist.set(i, new ItemStack(item.o()));
            }
        }

        return nonnulllist;
    }

    default NonNullList<RecipeItemStack> e() {
        return NonNullList.<RecipeItemStack>a();
    }

    default boolean c() {
        return false;
    }

    MinecraftKey getKey();

    RecipeSerializer<?> a();
}
