package net.minecraft.server;

import java.util.List;
import java.util.Random;

public class ContainerEnchantTable extends Container {
    public IInventory enchantSlots = new InventorySubcontainer(new ChatComponentText("Enchant"), 2) {
        public int getMaxStackSize() {
            return 64;
        }

        public void update() {
            super.update();
            ContainerEnchantTable.this.a(this);
        }
    };
    public World world;
    private final BlockPosition position;
    private final Random l = new Random();
    public int f;
    public int[] costs = new int[3];
    public int[] h = new int[]{-1, -1, -1};
    public int[] i = new int[]{-1, -1, -1};

    public ContainerEnchantTable(PlayerInventory playerinventory, World worldx, BlockPosition blockposition) {
        this.world = worldx;
        this.position = blockposition;
        this.f = playerinventory.player.du();
        this.a(new Slot(this.enchantSlots, 0, 15, 47) {
            public boolean isAllowed(ItemStack var1) {
                return true;
            }

            public int getMaxStackSize() {
                return 1;
            }
        });
        this.a(new Slot(this.enchantSlots, 1, 35, 47) {
            public boolean isAllowed(ItemStack itemstack) {
                return itemstack.getItem() == Items.LAPIS_LAZULI;
            }
        });

        for(int ix = 0; ix < 3; ++ix) {
            for(int j = 0; j < 9; ++j) {
                this.a(new Slot(playerinventory, j + ix * 9 + 9, 8 + j * 18, 84 + ix * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.a(new Slot(playerinventory, k, 8 + k * 18, 142));
        }

    }

    protected void c(ICrafting icrafting) {
        icrafting.setContainerData(this, 0, this.costs[0]);
        icrafting.setContainerData(this, 1, this.costs[1]);
        icrafting.setContainerData(this, 2, this.costs[2]);
        icrafting.setContainerData(this, 3, this.f & -16);
        icrafting.setContainerData(this, 4, this.h[0]);
        icrafting.setContainerData(this, 5, this.h[1]);
        icrafting.setContainerData(this, 6, this.h[2]);
        icrafting.setContainerData(this, 7, this.i[0]);
        icrafting.setContainerData(this, 8, this.i[1]);
        icrafting.setContainerData(this, 9, this.i[2]);
    }

    public void addSlotListener(ICrafting icrafting) {
        super.addSlotListener(icrafting);
        this.c(icrafting);
    }

    public void b() {
        super.b();

        for(int ix = 0; ix < this.listeners.size(); ++ix) {
            ICrafting icrafting = (ICrafting)this.listeners.get(ix);
            this.c(icrafting);
        }

    }

    public void a(IInventory iinventory) {
        if (iinventory == this.enchantSlots) {
            ItemStack itemstack = iinventory.getItem(0);
            if (!itemstack.isEmpty() && itemstack.canEnchant()) {
                if (!this.world.isClientSide) {
                    int lx = 0;

                    for(int j = -1; j <= 1; ++j) {
                        for(int k = -1; k <= 1; ++k) {
                            if ((j != 0 || k != 0) && this.world.isEmpty(this.position.a(k, 0, j)) && this.world.isEmpty(this.position.a(k, 1, j))) {
                                if (this.world.getType(this.position.a(k * 2, 0, j * 2)).getBlock() == Blocks.BOOKSHELF) {
                                    ++lx;
                                }

                                if (this.world.getType(this.position.a(k * 2, 1, j * 2)).getBlock() == Blocks.BOOKSHELF) {
                                    ++lx;
                                }

                                if (k != 0 && j != 0) {
                                    if (this.world.getType(this.position.a(k * 2, 0, j)).getBlock() == Blocks.BOOKSHELF) {
                                        ++lx;
                                    }

                                    if (this.world.getType(this.position.a(k * 2, 1, j)).getBlock() == Blocks.BOOKSHELF) {
                                        ++lx;
                                    }

                                    if (this.world.getType(this.position.a(k, 0, j * 2)).getBlock() == Blocks.BOOKSHELF) {
                                        ++lx;
                                    }

                                    if (this.world.getType(this.position.a(k, 1, j * 2)).getBlock() == Blocks.BOOKSHELF) {
                                        ++lx;
                                    }
                                }
                            }
                        }
                    }

                    this.l.setSeed((long)this.f);

                    for(int i1 = 0; i1 < 3; ++i1) {
                        this.costs[i1] = EnchantmentManager.a(this.l, i1, lx, itemstack);
                        this.h[i1] = -1;
                        this.i[i1] = -1;
                        if (this.costs[i1] < i1 + 1) {
                            this.costs[i1] = 0;
                        }
                    }

                    for(int j1 = 0; j1 < 3; ++j1) {
                        if (this.costs[j1] > 0) {
                            List list = this.a(itemstack, j1, this.costs[j1]);
                            if (list != null && !list.isEmpty()) {
                                WeightedRandomEnchant weightedrandomenchant = (WeightedRandomEnchant)list.get(this.l.nextInt(list.size()));
                                this.h[j1] = IRegistry.ENCHANTMENT.a(weightedrandomenchant.enchantment);
                                this.i[j1] = weightedrandomenchant.level;
                            }
                        }
                    }

                    this.b();
                }
            } else {
                for(int ix = 0; ix < 3; ++ix) {
                    this.costs[ix] = 0;
                    this.h[ix] = -1;
                    this.i[ix] = -1;
                }
            }
        }

    }

    public boolean a(EntityHuman entityhuman, int ix) {
        ItemStack itemstack = this.enchantSlots.getItem(0);
        ItemStack itemstack1 = this.enchantSlots.getItem(1);
        int j = ix + 1;
        if ((itemstack1.isEmpty() || itemstack1.getCount() < j) && !entityhuman.abilities.canInstantlyBuild) {
            return false;
        } else if (this.costs[ix] > 0 && !itemstack.isEmpty() && (entityhuman.expLevel >= j && entityhuman.expLevel >= this.costs[ix] || entityhuman.abilities.canInstantlyBuild)) {
            if (!this.world.isClientSide) {
                List list = this.a(itemstack, ix, this.costs[ix]);
                if (!list.isEmpty()) {
                    entityhuman.enchantDone(itemstack, j);
                    boolean flag = itemstack.getItem() == Items.BOOK;
                    if (flag) {
                        itemstack = new ItemStack(Items.ENCHANTED_BOOK);
                        this.enchantSlots.setItem(0, itemstack);
                    }

                    for(int k = 0; k < list.size(); ++k) {
                        WeightedRandomEnchant weightedrandomenchant = (WeightedRandomEnchant)list.get(k);
                        if (flag) {
                            ItemEnchantedBook.a(itemstack, weightedrandomenchant);
                        } else {
                            itemstack.addEnchantment(weightedrandomenchant.enchantment, weightedrandomenchant.level);
                        }
                    }

                    if (!entityhuman.abilities.canInstantlyBuild) {
                        itemstack1.subtract(j);
                        if (itemstack1.isEmpty()) {
                            this.enchantSlots.setItem(1, ItemStack.a);
                        }
                    }

                    entityhuman.a(StatisticList.ENCHANT_ITEM);
                    if (entityhuman instanceof EntityPlayer) {
                        CriterionTriggers.i.a((EntityPlayer)entityhuman, itemstack, j);
                    }

                    this.enchantSlots.update();
                    this.f = entityhuman.du();
                    this.a(this.enchantSlots);
                    this.world.a((EntityHuman)null, this.position, SoundEffects.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, this.world.random.nextFloat() * 0.1F + 0.9F);
                }
            }

            return true;
        } else {
            return false;
        }
    }

    private List<WeightedRandomEnchant> a(ItemStack itemstack, int ix, int j) {
        this.l.setSeed((long)(this.f + ix));
        List list = EnchantmentManager.b(this.l, itemstack, j, false);
        if (itemstack.getItem() == Items.BOOK && list.size() > 1) {
            list.remove(this.l.nextInt(list.size()));
        }

        return list;
    }

    public void b(EntityHuman entityhuman) {
        super.b(entityhuman);
        if (!this.world.isClientSide) {
            this.a(entityhuman, entityhuman.world, this.enchantSlots);
        }
    }

    public boolean canUse(EntityHuman entityhuman) {
        if (this.world.getType(this.position).getBlock() != Blocks.ENCHANTING_TABLE) {
            return false;
        } else {
            return !(entityhuman.d((double)this.position.getX() + 0.5D, (double)this.position.getY() + 0.5D, (double)this.position.getZ() + 0.5D) > 64.0D);
        }
    }

    public ItemStack shiftClick(EntityHuman entityhuman, int ix) {
        ItemStack itemstack = ItemStack.a;
        Slot slot = (Slot)this.slots.get(ix);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.cloneItemStack();
            if (ix == 0) {
                if (!this.a(itemstack1, 2, 38, true)) {
                    return ItemStack.a;
                }
            } else if (ix == 1) {
                if (!this.a(itemstack1, 2, 38, true)) {
                    return ItemStack.a;
                }
            } else if (itemstack1.getItem() == Items.LAPIS_LAZULI) {
                if (!this.a(itemstack1, 1, 2, true)) {
                    return ItemStack.a;
                }
            } else {
                if (((Slot)this.slots.get(0)).hasItem() || !((Slot)this.slots.get(0)).isAllowed(itemstack1)) {
                    return ItemStack.a;
                }

                if (itemstack1.hasTag() && itemstack1.getCount() == 1) {
                    ((Slot)this.slots.get(0)).set(itemstack1.cloneItemStack());
                    itemstack1.setCount(0);
                } else if (!itemstack1.isEmpty()) {
                    ((Slot)this.slots.get(0)).set(new ItemStack(itemstack1.getItem()));
                    itemstack1.subtract(1);
                }
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
}
