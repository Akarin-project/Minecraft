package net.minecraft.server;

import java.util.ArrayList;
import javax.annotation.Nullable;

public class MerchantRecipeList extends ArrayList<MerchantRecipe> {
    public MerchantRecipeList() {
    }

    public MerchantRecipeList(NBTTagCompound nbttagcompound) {
        this.a(nbttagcompound);
    }

    @Nullable
    public MerchantRecipe a(ItemStack itemstack, ItemStack itemstack1, int i) {
        if (i > 0 && i < this.size()) {
            MerchantRecipe merchantrecipe1 = (MerchantRecipe)this.get(i);
            return !this.a(itemstack, merchantrecipe1.getBuyItem1()) || (!itemstack1.isEmpty() || merchantrecipe1.hasSecondItem()) && (!merchantrecipe1.hasSecondItem() || !this.a(itemstack1, merchantrecipe1.getBuyItem2())) || itemstack.getCount() < merchantrecipe1.getBuyItem1().getCount() || merchantrecipe1.hasSecondItem() && itemstack1.getCount() < merchantrecipe1.getBuyItem2().getCount() ? null : merchantrecipe1;
        } else {
            for(int j = 0; j < this.size(); ++j) {
                MerchantRecipe merchantrecipe = (MerchantRecipe)this.get(j);
                if (this.a(itemstack, merchantrecipe.getBuyItem1()) && itemstack.getCount() >= merchantrecipe.getBuyItem1().getCount() && (!merchantrecipe.hasSecondItem() && itemstack1.isEmpty() || merchantrecipe.hasSecondItem() && this.a(itemstack1, merchantrecipe.getBuyItem2()) && itemstack1.getCount() >= merchantrecipe.getBuyItem2().getCount())) {
                    return merchantrecipe;
                }
            }

            return null;
        }
    }

    private boolean a(ItemStack itemstack, ItemStack itemstack1) {
        ItemStack itemstack2 = itemstack.cloneItemStack();
        if (itemstack2.getItem().usesDurability()) {
            itemstack2.setDamage(itemstack2.getDamage());
        }

        return ItemStack.c(itemstack2, itemstack1) && (!itemstack1.hasTag() || itemstack2.hasTag() && GameProfileSerializer.a(itemstack1.getTag(), itemstack2.getTag(), false));
    }

    public void a(PacketDataSerializer packetdataserializer) {
        packetdataserializer.writeByte((byte)(this.size() & 255));

        for(int i = 0; i < this.size(); ++i) {
            MerchantRecipe merchantrecipe = (MerchantRecipe)this.get(i);
            packetdataserializer.a(merchantrecipe.getBuyItem1());
            packetdataserializer.a(merchantrecipe.getBuyItem3());
            ItemStack itemstack = merchantrecipe.getBuyItem2();
            packetdataserializer.writeBoolean(!itemstack.isEmpty());
            if (!itemstack.isEmpty()) {
                packetdataserializer.a(itemstack);
            }

            packetdataserializer.writeBoolean(merchantrecipe.h());
            packetdataserializer.writeInt(merchantrecipe.e());
            packetdataserializer.writeInt(merchantrecipe.f());
        }

    }

    public void a(NBTTagCompound nbttagcompound) {
        NBTTagList nbttaglist = nbttagcompound.getList("Recipes", 10);

        for(int i = 0; i < nbttaglist.size(); ++i) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompound(i);
            this.add(new MerchantRecipe(nbttagcompound1));
        }

    }

    public NBTTagCompound a() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        NBTTagList nbttaglist = new NBTTagList();

        for(int i = 0; i < this.size(); ++i) {
            MerchantRecipe merchantrecipe = (MerchantRecipe)this.get(i);
            nbttaglist.add((NBTBase)merchantrecipe.k());
        }

        nbttagcompound.set("Recipes", nbttaglist);
        return nbttagcompound;
    }
}
