package net.minecraft.server;

import com.google.common.collect.Multimap;
import java.util.Set;

public class ItemTool extends ItemToolMaterial {
    private final Set<Block> a;
    protected float b;
    protected float c;
    protected float d;

    protected ItemTool(float f, float f1, ToolMaterial toolmaterial, Set<Block> set, Item.Info item$info) {
        super(toolmaterial, item$info);
        this.a = set;
        this.b = toolmaterial.b();
        this.c = f + toolmaterial.c();
        this.d = f1;
    }

    public float getDestroySpeed(ItemStack var1, IBlockData iblockdata) {
        return this.a.contains(iblockdata.getBlock()) ? this.b : 1.0F;
    }

    public boolean a(ItemStack itemstack, EntityLiving var2, EntityLiving entityliving) {
        itemstack.damage(2, entityliving);
        return true;
    }

    public boolean a(ItemStack itemstack, World world, IBlockData iblockdata, BlockPosition blockposition, EntityLiving entityliving) {
        if (!world.isClientSide && iblockdata.e(world, blockposition) != 0.0F) {
            itemstack.damage(1, entityliving);
        }

        return true;
    }

    public Multimap<String, AttributeModifier> a(EnumItemSlot enumitemslot) {
        Multimap multimap = super.a(enumitemslot);
        if (enumitemslot == EnumItemSlot.MAINHAND) {
            multimap.put(GenericAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(g, "Tool modifier", (double)this.c, 0));
            multimap.put(GenericAttributes.g.getName(), new AttributeModifier(h, "Tool modifier", (double)this.d, 0));
        }

        return multimap;
    }
}
