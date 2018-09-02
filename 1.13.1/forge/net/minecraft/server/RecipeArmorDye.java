package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.ArrayList;

public class RecipeArmorDye extends IRecipeComplex {
    public RecipeArmorDye(MinecraftKey minecraftkey) {
        super(minecraftkey);
    }

    public boolean a(IInventory iinventory, World var2) {
        if (!(iinventory instanceof InventoryCrafting)) {
            return false;
        } else {
            ItemStack itemstack = ItemStack.a;
            ArrayList arraylist = Lists.newArrayList();

            for(int i = 0; i < iinventory.getSize(); ++i) {
                ItemStack itemstack1 = iinventory.getItem(i);
                if (!itemstack1.isEmpty()) {
                    if (itemstack1.getItem() instanceof ItemArmorColorable) {
                        if (!itemstack.isEmpty()) {
                            return false;
                        }

                        itemstack = itemstack1;
                    } else {
                        if (!(itemstack1.getItem() instanceof ItemDye)) {
                            return false;
                        }

                        arraylist.add(itemstack1);
                    }
                }
            }

            return !itemstack.isEmpty() && !arraylist.isEmpty();
        }
    }

    public ItemStack craftItem(IInventory iinventory) {
        ItemStack itemstack = ItemStack.a;
        int[] aint = new int[3];
        int i = 0;
        int j = 0;
        ItemArmorColorable itemarmorcolorable = null;

        for(int k = 0; k < iinventory.getSize(); ++k) {
            ItemStack itemstack1 = iinventory.getItem(k);
            if (!itemstack1.isEmpty()) {
                Item item = itemstack1.getItem();
                if (item instanceof ItemArmorColorable) {
                    itemarmorcolorable = (ItemArmorColorable)item;
                    if (!itemstack.isEmpty()) {
                        return ItemStack.a;
                    }

                    itemstack = itemstack1.cloneItemStack();
                    itemstack.setCount(1);
                    if (itemarmorcolorable.e(itemstack1)) {
                        int l = itemarmorcolorable.f(itemstack);
                        float f = (float)(l >> 16 & 255) / 255.0F;
                        float f1 = (float)(l >> 8 & 255) / 255.0F;
                        float f2 = (float)(l & 255) / 255.0F;
                        i = (int)((float)i + Math.max(f, Math.max(f1, f2)) * 255.0F);
                        aint[0] = (int)((float)aint[0] + f * 255.0F);
                        aint[1] = (int)((float)aint[1] + f1 * 255.0F);
                        aint[2] = (int)((float)aint[2] + f2 * 255.0F);
                        ++j;
                    }
                } else {
                    if (!(item instanceof ItemDye)) {
                        return ItemStack.a;
                    }

                    float[] afloat = ((ItemDye)item).d().d();
                    int l1 = (int)(afloat[0] * 255.0F);
                    int i2 = (int)(afloat[1] * 255.0F);
                    int k2 = (int)(afloat[2] * 255.0F);
                    i += Math.max(l1, Math.max(i2, k2));
                    aint[0] += l1;
                    aint[1] += i2;
                    aint[2] += k2;
                    ++j;
                }
            }
        }

        if (itemarmorcolorable == null) {
            return ItemStack.a;
        } else {
            int i1 = aint[0] / j;
            int j1 = aint[1] / j;
            int k1 = aint[2] / j;
            float f3 = (float)i / (float)j;
            float f4 = (float)Math.max(i1, Math.max(j1, k1));
            i1 = (int)((float)i1 * f3 / f4);
            j1 = (int)((float)j1 * f3 / f4);
            k1 = (int)((float)k1 * f3 / f4);
            int j2 = (i1 << 8) + j1;
            j2 = (j2 << 8) + k1;
            itemarmorcolorable.a(itemstack, j2);
            return itemstack;
        }
    }

    public RecipeSerializer<?> a() {
        return RecipeSerializers.c;
    }
}
