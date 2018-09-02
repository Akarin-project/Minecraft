package net.minecraft.server;

import com.google.common.collect.Maps;
import java.util.Map;

public class ItemDye extends Item {
    private static final Map<EnumColor, ItemDye> a = Maps.newEnumMap(EnumColor.class);
    private final EnumColor b;

    public ItemDye(EnumColor enumcolor, Item.Info item$info) {
        super(item$info);
        this.b = enumcolor;
        a.put(enumcolor, this);
    }

    public boolean a(ItemStack itemstack, EntityHuman var2, EntityLiving entityliving, EnumHand var4) {
        if (entityliving instanceof EntitySheep) {
            EntitySheep entitysheep = (EntitySheep)entityliving;
            if (!entitysheep.isSheared() && entitysheep.getColor() != this.b) {
                entitysheep.setColor(this.b);
                itemstack.subtract(1);
            }

            return true;
        } else {
            return false;
        }
    }

    public EnumColor d() {
        return this.b;
    }

    public static ItemDye a(EnumColor enumcolor) {
        return (ItemDye)a.get(enumcolor);
    }
}
