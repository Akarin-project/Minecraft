package net.minecraft.server;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContainerAnvil extends Container {
    private static final Logger f = LogManager.getLogger();
    private final IInventory g = new InventoryCraftResult();
    private final IInventory h = new InventorySubcontainer(new ChatComponentText("Repair"), 2) {
        public void update() {
            super.update();
            ContainerAnvil.this.a(this);
        }
    };
    private final World i;
    private final BlockPosition j;
    public int levelCost;
    private int k;
    public String renameText;
    private final EntityHuman m;

    public ContainerAnvil(PlayerInventory playerinventory, final World world, final BlockPosition blockposition, EntityHuman entityhuman) {
        this.j = blockposition;
        this.i = world;
        this.m = entityhuman;
        this.a(new Slot(this.h, 0, 27, 47));
        this.a(new Slot(this.h, 1, 76, 47));
        this.a(new Slot(this.g, 2, 134, 47) {
            public boolean isAllowed(ItemStack var1) {
                return false;
            }

            public boolean isAllowed(EntityHuman entityhuman1) {
                return (entityhuman1.abilities.canInstantlyBuild || entityhuman1.expLevel >= ContainerAnvil.this.levelCost) && ContainerAnvil.this.levelCost > 0 && this.hasItem();
            }

            public ItemStack a(EntityHuman entityhuman1, ItemStack itemstack) {
                if (!entityhuman1.abilities.canInstantlyBuild) {
                    entityhuman1.levelDown(-ContainerAnvil.this.levelCost);
                }

                ContainerAnvil.this.h.setItem(0, ItemStack.a);
                if (ContainerAnvil.this.k > 0) {
                    ItemStack itemstack1 = ContainerAnvil.this.h.getItem(1);
                    if (!itemstack1.isEmpty() && itemstack1.getCount() > ContainerAnvil.this.k) {
                        itemstack1.subtract(ContainerAnvil.this.k);
                        ContainerAnvil.this.h.setItem(1, itemstack1);
                    } else {
                        ContainerAnvil.this.h.setItem(1, ItemStack.a);
                    }
                } else {
                    ContainerAnvil.this.h.setItem(1, ItemStack.a);
                }

                ContainerAnvil.this.levelCost = 0;
                IBlockData iblockdata1 = world.getType(blockposition);
                if (!world.isClientSide) {
                    if (!entityhuman1.abilities.canInstantlyBuild && iblockdata1.a(TagsBlock.ANVIL) && entityhuman1.getRandom().nextFloat() < 0.12F) {
                        IBlockData iblockdata = BlockAnvil.a_(iblockdata1);
                        if (iblockdata == null) {
                            world.setAir(blockposition);
                            world.triggerEffect(1029, blockposition, 0);
                        } else {
                            world.setTypeAndData(blockposition, iblockdata, 2);
                            world.triggerEffect(1030, blockposition, 0);
                        }
                    } else {
                        world.triggerEffect(1030, blockposition, 0);
                    }
                }

                return itemstack;
            }
        });

        for(int ix = 0; ix < 3; ++ix) {
            for(int jx = 0; jx < 9; ++jx) {
                this.a(new Slot(playerinventory, jx + ix * 9 + 9, 8 + jx * 18, 84 + ix * 18));
            }
        }

        for(int kx = 0; kx < 9; ++kx) {
            this.a(new Slot(playerinventory, kx, 8 + kx * 18, 142));
        }

    }

    public void a(IInventory iinventory) {
        super.a(iinventory);
        if (iinventory == this.h) {
            this.d();
        }

    }

    public void d() {
        ItemStack itemstack = this.h.getItem(0);
        this.levelCost = 1;
        int ix = 0;
        int jx = 0;
        byte b0 = 0;
        if (itemstack.isEmpty()) {
            this.g.setItem(0, ItemStack.a);
            this.levelCost = 0;
        } else {
            ItemStack itemstack1 = itemstack.cloneItemStack();
            ItemStack itemstack2 = this.h.getItem(1);
            Map map = EnchantmentManager.a(itemstack1);
            jx = jx + itemstack.getRepairCost() + (itemstack2.isEmpty() ? 0 : itemstack2.getRepairCost());
            this.k = 0;
            if (!itemstack2.isEmpty()) {
                boolean flag = itemstack2.getItem() == Items.ENCHANTED_BOOK && !ItemEnchantedBook.e(itemstack2).isEmpty();
                if (itemstack1.e() && itemstack1.getItem().a(itemstack, itemstack2)) {
                    int k2 = Math.min(itemstack1.getDamage(), itemstack1.h() / 4);
                    if (k2 <= 0) {
                        this.g.setItem(0, ItemStack.a);
                        this.levelCost = 0;
                        return;
                    }

                    int l2;
                    for(l2 = 0; k2 > 0 && l2 < itemstack2.getCount(); ++l2) {
                        int i3 = itemstack1.getDamage() - k2;
                        itemstack1.setDamage(i3);
                        ++ix;
                        k2 = Math.min(itemstack1.getDamage(), itemstack1.h() / 4);
                    }

                    this.k = l2;
                } else {
                    if (!flag && (itemstack1.getItem() != itemstack2.getItem() || !itemstack1.e())) {
                        this.g.setItem(0, ItemStack.a);
                        this.levelCost = 0;
                        return;
                    }

                    if (itemstack1.e() && !flag) {
                        int kx = itemstack.h() - itemstack.getDamage();
                        int l = itemstack2.h() - itemstack2.getDamage();
                        int i1 = l + itemstack1.h() * 12 / 100;
                        int j1 = kx + i1;
                        int k1 = itemstack1.h() - j1;
                        if (k1 < 0) {
                            k1 = 0;
                        }

                        if (k1 < itemstack1.getDamage()) {
                            itemstack1.setDamage(k1);
                            ix += 2;
                        }
                    }

                    Map map1 = EnchantmentManager.a(itemstack2);
                    boolean flag2 = false;
                    boolean flag3 = false;

                    for(Enchantment enchantment1 : map1.keySet()) {
                        if (enchantment1 != null) {
                            int l1 = map.containsKey(enchantment1) ? map.get(enchantment1) : 0;
                            int i2 = map1.get(enchantment1);
                            i2 = l1 == i2 ? i2 + 1 : Math.max(i2, l1);
                            boolean flag1 = enchantment1.canEnchant(itemstack);
                            if (this.m.abilities.canInstantlyBuild || itemstack.getItem() == Items.ENCHANTED_BOOK) {
                                flag1 = true;
                            }

                            for(Enchantment enchantment : map.keySet()) {
                                if (enchantment != enchantment1 && !enchantment1.b(enchantment)) {
                                    flag1 = false;
                                    ++ix;
                                }
                            }

                            if (!flag1) {
                                flag3 = true;
                            } else {
                                flag2 = true;
                                if (i2 > enchantment1.getMaxLevel()) {
                                    i2 = enchantment1.getMaxLevel();
                                }

                                map.put(enchantment1, i2);
                                int j3 = 0;
                                switch(enchantment1.d()) {
                                case COMMON:
                                    j3 = 1;
                                    break;
                                case UNCOMMON:
                                    j3 = 2;
                                    break;
                                case RARE:
                                    j3 = 4;
                                    break;
                                case VERY_RARE:
                                    j3 = 8;
                                }

                                if (flag) {
                                    j3 = Math.max(1, j3 / 2);
                                }

                                ix += j3 * i2;
                                if (itemstack.getCount() > 1) {
                                    ix = 40;
                                }
                            }
                        }
                    }

                    if (flag3 && !flag2) {
                        this.g.setItem(0, ItemStack.a);
                        this.levelCost = 0;
                        return;
                    }
                }
            }

            if (StringUtils.isBlank(this.renameText)) {
                if (itemstack.hasName()) {
                    b0 = 1;
                    ix += b0;
                    itemstack1.r();
                }
            } else if (!this.renameText.equals(itemstack.getName().getString())) {
                b0 = 1;
                ix += b0;
                itemstack1.a(new ChatComponentText(this.renameText));
            }

            this.levelCost = jx + ix;
            if (ix <= 0) {
                itemstack1 = ItemStack.a;
            }

            if (b0 == ix && b0 > 0 && this.levelCost >= 40) {
                this.levelCost = 39;
            }

            if (this.levelCost >= 40 && !this.m.abilities.canInstantlyBuild) {
                itemstack1 = ItemStack.a;
            }

            if (!itemstack1.isEmpty()) {
                int j2 = itemstack1.getRepairCost();
                if (!itemstack2.isEmpty() && j2 < itemstack2.getRepairCost()) {
                    j2 = itemstack2.getRepairCost();
                }

                if (b0 != ix || b0 == 0) {
                    j2 = j2 * 2 + 1;
                }

                itemstack1.setRepairCost(j2);
                EnchantmentManager.a(map, itemstack1);
            }

            this.g.setItem(0, itemstack1);
            this.b();
        }
    }

    public void addSlotListener(ICrafting icrafting) {
        super.addSlotListener(icrafting);
        icrafting.setContainerData(this, 0, this.levelCost);
    }

    public void b(EntityHuman entityhuman) {
        super.b(entityhuman);
        if (!this.i.isClientSide) {
            this.a(entityhuman, this.i, this.h);
        }
    }

    public boolean canUse(EntityHuman entityhuman) {
        if (!this.i.getType(this.j).a(TagsBlock.ANVIL)) {
            return false;
        } else {
            return entityhuman.d((double)this.j.getX() + 0.5D, (double)this.j.getY() + 0.5D, (double)this.j.getZ() + 0.5D) <= 64.0D;
        }
    }

    public ItemStack shiftClick(EntityHuman entityhuman, int ix) {
        ItemStack itemstack = ItemStack.a;
        Slot slot = (Slot)this.slots.get(ix);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.cloneItemStack();
            if (ix == 2) {
                if (!this.a(itemstack1, 3, 39, true)) {
                    return ItemStack.a;
                }

                slot.a(itemstack1, itemstack);
            } else if (ix != 0 && ix != 1) {
                if (ix >= 3 && ix < 39 && !this.a(itemstack1, 0, 2, false)) {
                    return ItemStack.a;
                }
            } else if (!this.a(itemstack1, 3, 39, false)) {
                return ItemStack.a;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.a);
            } else {
                slot.f();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.a;
            }

            slot.a(entityhuman, itemstack1);
        }

        return itemstack;
    }

    public void a(String s) {
        this.renameText = s;
        if (this.getSlot(2).hasItem()) {
            ItemStack itemstack = this.getSlot(2).getItem();
            if (StringUtils.isBlank(s)) {
                itemstack.r();
            } else {
                itemstack.a(new ChatComponentText(this.renameText));
            }
        }

        this.d();
    }
}
