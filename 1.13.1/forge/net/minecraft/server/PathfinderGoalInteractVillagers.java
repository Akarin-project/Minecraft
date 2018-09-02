package net.minecraft.server;

public class PathfinderGoalInteractVillagers extends PathfinderGoalInteract {
    private int e;
    private final EntityVillager f;

    public PathfinderGoalInteractVillagers(EntityVillager entityvillager) {
        super(entityvillager, EntityVillager.class, 3.0F, 0.02F);
        this.f = entityvillager;
    }

    public void c() {
        super.c();
        if (this.f.dF() && this.b instanceof EntityVillager && ((EntityVillager)this.b).dG()) {
            this.e = 10;
        } else {
            this.e = 0;
        }

    }

    public void e() {
        super.e();
        if (this.e > 0) {
            --this.e;
            if (this.e == 0) {
                InventorySubcontainer inventorysubcontainer = this.f.dD();

                for(int i = 0; i < inventorysubcontainer.getSize(); ++i) {
                    ItemStack itemstack = inventorysubcontainer.getItem(i);
                    ItemStack itemstack1 = ItemStack.a;
                    if (!itemstack.isEmpty()) {
                        Item item = itemstack.getItem();
                        if ((item == Items.BREAD || item == Items.POTATO || item == Items.CARROT || item == Items.BEETROOT) && itemstack.getCount() > 3) {
                            int l = itemstack.getCount() / 2;
                            itemstack.subtract(l);
                            itemstack1 = new ItemStack(item, l);
                        } else if (item == Items.WHEAT && itemstack.getCount() > 5) {
                            int j = itemstack.getCount() / 2 / 3 * 3;
                            int k = j / 3;
                            itemstack.subtract(j);
                            itemstack1 = new ItemStack(Items.BREAD, k);
                        }

                        if (itemstack.isEmpty()) {
                            inventorysubcontainer.setItem(i, ItemStack.a);
                        }
                    }

                    if (!itemstack1.isEmpty()) {
                        double d0 = this.f.locY - (double)0.3F + (double)this.f.getHeadHeight();
                        EntityItem entityitem = new EntityItem(this.f.world, this.f.locX, d0, this.f.locZ, itemstack1);
                        float fx = 0.3F;
                        float f1 = this.f.aS;
                        float f2 = this.f.pitch;
                        entityitem.motX = (double)(-MathHelper.sin(f1 * ((float)Math.PI / 180F)) * MathHelper.cos(f2 * ((float)Math.PI / 180F)) * 0.3F);
                        entityitem.motZ = (double)(MathHelper.cos(f1 * ((float)Math.PI / 180F)) * MathHelper.cos(f2 * ((float)Math.PI / 180F)) * 0.3F);
                        entityitem.motY = (double)(-MathHelper.sin(f2 * ((float)Math.PI / 180F)) * 0.3F + 0.1F);
                        entityitem.n();
                        this.f.world.addEntity(entityitem);
                        break;
                    }
                }
            }
        }

    }
}
