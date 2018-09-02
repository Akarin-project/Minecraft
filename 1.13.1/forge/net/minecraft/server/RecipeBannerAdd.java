package net.minecraft.server;

import javax.annotation.Nullable;

public class RecipeBannerAdd extends IRecipeComplex {
    public RecipeBannerAdd(MinecraftKey minecraftkey) {
        super(minecraftkey);
    }

    public boolean a(IInventory iinventory, World var2) {
        if (!(iinventory instanceof InventoryCrafting)) {
            return false;
        } else {
            boolean flag = false;

            for(int i = 0; i < iinventory.getSize(); ++i) {
                ItemStack itemstack = iinventory.getItem(i);
                if (itemstack.getItem() instanceof ItemBanner) {
                    if (flag) {
                        return false;
                    }

                    if (TileEntityBanner.a(itemstack) >= 6) {
                        return false;
                    }

                    flag = true;
                }
            }

            return flag && this.c(iinventory) != null;
        }
    }

    public ItemStack craftItem(IInventory iinventory) {
        ItemStack itemstack = ItemStack.a;

        for(int i = 0; i < iinventory.getSize(); ++i) {
            ItemStack itemstack1 = iinventory.getItem(i);
            if (!itemstack1.isEmpty() && itemstack1.getItem() instanceof ItemBanner) {
                itemstack = itemstack1.cloneItemStack();
                itemstack.setCount(1);
                break;
            }
        }

        EnumBannerPatternType enumbannerpatterntype = this.c(iinventory);
        if (enumbannerpatterntype != null) {
            EnumColor enumcolor = EnumColor.WHITE;

            for(int j = 0; j < iinventory.getSize(); ++j) {
                Item item = iinventory.getItem(j).getItem();
                if (item instanceof ItemDye) {
                    enumcolor = ((ItemDye)item).d();
                    break;
                }
            }

            NBTTagCompound nbttagcompound1 = itemstack.a("BlockEntityTag");
            NBTTagList nbttaglist;
            if (nbttagcompound1.hasKeyOfType("Patterns", 9)) {
                nbttaglist = nbttagcompound1.getList("Patterns", 10);
            } else {
                nbttaglist = new NBTTagList();
                nbttagcompound1.set("Patterns", nbttaglist);
            }

            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setString("Pattern", enumbannerpatterntype.b());
            nbttagcompound.setInt("Color", enumcolor.getColorIndex());
            nbttaglist.add((NBTBase)nbttagcompound);
        }

        return itemstack;
    }

    @Nullable
    private EnumBannerPatternType c(IInventory iinventory) {
        for(EnumBannerPatternType enumbannerpatterntype : EnumBannerPatternType.values()) {
            if (enumbannerpatterntype.d()) {
                boolean flag = true;
                if (enumbannerpatterntype.e()) {
                    boolean flag1 = false;
                    boolean flag2 = false;

                    for(int i = 0; i < iinventory.getSize() && flag; ++i) {
                        ItemStack itemstack = iinventory.getItem(i);
                        if (!itemstack.isEmpty() && !(itemstack.getItem() instanceof ItemBanner)) {
                            if (itemstack.getItem() instanceof ItemDye) {
                                if (flag2) {
                                    flag = false;
                                    break;
                                }

                                flag2 = true;
                            } else {
                                if (flag1 || !itemstack.doMaterialsMatch(enumbannerpatterntype.f())) {
                                    flag = false;
                                    break;
                                }

                                flag1 = true;
                            }
                        }
                    }

                    if (!flag1 || !flag2) {
                        flag = false;
                    }
                } else if (iinventory.getSize() == enumbannerpatterntype.c().length * enumbannerpatterntype.c()[0].length()) {
                    EnumColor enumcolor1 = null;

                    for(int j = 0; j < iinventory.getSize() && flag; ++j) {
                        int k = j / 3;
                        int l = j % 3;
                        ItemStack itemstack1 = iinventory.getItem(j);
                        Item item = itemstack1.getItem();
                        if (!itemstack1.isEmpty() && !(item instanceof ItemBanner)) {
                            if (!(item instanceof ItemDye)) {
                                flag = false;
                                break;
                            }

                            EnumColor enumcolor = ((ItemDye)item).d();
                            if (enumcolor1 != null && enumcolor1 != enumcolor) {
                                flag = false;
                                break;
                            }

                            if (enumbannerpatterntype.c()[k].charAt(l) == ' ') {
                                flag = false;
                                break;
                            }

                            enumcolor1 = enumcolor;
                        } else if (enumbannerpatterntype.c()[k].charAt(l) != ' ') {
                            flag = false;
                            break;
                        }
                    }
                } else {
                    flag = false;
                }

                if (flag) {
                    return enumbannerpatterntype;
                }
            }
        }

        return null;
    }

    public RecipeSerializer<?> a() {
        return RecipeSerializers.m;
    }
}
