package net.minecraft.server;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class TileEntityHopper extends TileEntityLootable implements IHopper, ITickable {
    private NonNullList<ItemStack> items = NonNullList.<ItemStack>a(5, ItemStack.a);
    private int f = -1;
    private long j;

    public TileEntityHopper() {
        super(TileEntityTypes.HOPPER);
    }

    public void load(NBTTagCompound nbttagcompound) {
        super.load(nbttagcompound);
        this.items = NonNullList.<ItemStack>a(this.getSize(), ItemStack.a);
        if (!this.d(nbttagcompound)) {
            ContainerUtil.b(nbttagcompound, this.items);
        }

        if (nbttagcompound.hasKeyOfType("CustomName", 8)) {
            this.setCustomName(IChatBaseComponent.ChatSerializer.a(nbttagcompound.getString("CustomName")));
        }

        this.f = nbttagcompound.getInt("TransferCooldown");
    }

    public NBTTagCompound save(NBTTagCompound nbttagcompound) {
        super.save(nbttagcompound);
        if (!this.e(nbttagcompound)) {
            ContainerUtil.a(nbttagcompound, this.items);
        }

        nbttagcompound.setInt("TransferCooldown", this.f);
        IChatBaseComponent ichatbasecomponent = this.getCustomName();
        if (ichatbasecomponent != null) {
            nbttagcompound.setString("CustomName", IChatBaseComponent.ChatSerializer.a(ichatbasecomponent));
        }

        return nbttagcompound;
    }

    public int getSize() {
        return this.items.size();
    }

    public ItemStack splitStack(int i, int jx) {
        this.d((EntityHuman)null);
        return ContainerUtil.a(this.q(), i, jx);
    }

    public void setItem(int i, ItemStack itemstack) {
        this.d((EntityHuman)null);
        this.q().set(i, itemstack);
        if (itemstack.getCount() > this.getMaxStackSize()) {
            itemstack.setCount(this.getMaxStackSize());
        }

    }

    public IChatBaseComponent getDisplayName() {
        return (IChatBaseComponent)(this.i != null ? this.i : new ChatMessage("container.hopper", new Object[0]));
    }

    public int getMaxStackSize() {
        return 64;
    }

    public void Y_() {
        if (this.world != null && !this.world.isClientSide) {
            --this.f;
            this.j = this.world.getTime();
            if (!this.E()) {
                this.setCooldown(0);
                this.a(() -> {
                    return a(this);
                });
            }

        }
    }

    private boolean a(Supplier<Boolean> supplier) {
        if (this.world != null && !this.world.isClientSide) {
            if (!this.E() && this.getBlock().get(BlockHopper.ENABLED)) {
                boolean flag = false;
                if (!this.p()) {
                    flag = this.s();
                }

                if (!this.r()) {
                    flag |= supplier.get();
                }

                if (flag) {
                    this.setCooldown(8);
                    this.update();
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    private boolean p() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public boolean P_() {
        return this.p();
    }

    private boolean r() {
        for(ItemStack itemstack : this.items) {
            if (itemstack.isEmpty() || itemstack.getCount() != itemstack.getMaxStackSize()) {
                return false;
            }
        }

        return true;
    }

    private boolean s() {
        IInventory iinventory = this.D();
        if (iinventory == null) {
            return false;
        } else {
            EnumDirection enumdirection = ((EnumDirection)this.getBlock().get(BlockHopper.FACING)).opposite();
            if (this.a(iinventory, enumdirection)) {
                return false;
            } else {
                for(int i = 0; i < this.getSize(); ++i) {
                    if (!this.getItem(i).isEmpty()) {
                        ItemStack itemstack = this.getItem(i).cloneItemStack();
                        ItemStack itemstack1 = addItem(this, iinventory, this.splitStack(i, 1), enumdirection);
                        if (itemstack1.isEmpty()) {
                            iinventory.update();
                            return true;
                        }

                        this.setItem(i, itemstack);
                    }
                }

                return false;
            }
        }
    }

    private boolean a(IInventory iinventory, EnumDirection enumdirection) {
        if (iinventory instanceof IWorldInventory) {
            IWorldInventory iworldinventory = (IWorldInventory)iinventory;
            int[] aint = iworldinventory.getSlotsForFace(enumdirection);

            for(int k : aint) {
                ItemStack itemstack1 = iworldinventory.getItem(k);
                if (itemstack1.isEmpty() || itemstack1.getCount() != itemstack1.getMaxStackSize()) {
                    return false;
                }
            }
        } else {
            int i = iinventory.getSize();

            for(int jx = 0; jx < i; ++jx) {
                ItemStack itemstack = iinventory.getItem(jx);
                if (itemstack.isEmpty() || itemstack.getCount() != itemstack.getMaxStackSize()) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean b(IInventory iinventory, EnumDirection enumdirection) {
        if (iinventory instanceof IWorldInventory) {
            IWorldInventory iworldinventory = (IWorldInventory)iinventory;
            int[] aint = iworldinventory.getSlotsForFace(enumdirection);

            for(int i : aint) {
                if (!iworldinventory.getItem(i).isEmpty()) {
                    return false;
                }
            }
        } else {
            int jx = iinventory.getSize();

            for(int k = 0; k < jx; ++k) {
                if (!iinventory.getItem(k).isEmpty()) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean a(IHopper ihopper) {
        IInventory iinventory = b(ihopper);
        if (iinventory != null) {
            EnumDirection enumdirection = EnumDirection.DOWN;
            if (b(iinventory, enumdirection)) {
                return false;
            }

            if (iinventory instanceof IWorldInventory) {
                IWorldInventory iworldinventory = (IWorldInventory)iinventory;
                int[] aint = iworldinventory.getSlotsForFace(enumdirection);

                for(int i : aint) {
                    if (a(ihopper, iinventory, i, enumdirection)) {
                        return true;
                    }
                }
            } else {
                int jx = iinventory.getSize();

                for(int k = 0; k < jx; ++k) {
                    if (a(ihopper, iinventory, k, enumdirection)) {
                        return true;
                    }
                }
            }
        } else {
            for(EntityItem entityitem : c(ihopper)) {
                if (a(ihopper, entityitem)) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean a(IHopper ihopper, IInventory iinventory, int i, EnumDirection enumdirection) {
        ItemStack itemstack = iinventory.getItem(i);
        if (!itemstack.isEmpty() && b(iinventory, itemstack, i, enumdirection)) {
            ItemStack itemstack1 = itemstack.cloneItemStack();
            ItemStack itemstack2 = addItem(iinventory, ihopper, iinventory.splitStack(i, 1), (EnumDirection)null);
            if (itemstack2.isEmpty()) {
                iinventory.update();
                return true;
            }

            iinventory.setItem(i, itemstack1);
        }

        return false;
    }

    public static boolean a(IInventory iinventory, EntityItem entityitem) {
        boolean flag = false;
        ItemStack itemstack = entityitem.getItemStack().cloneItemStack();
        ItemStack itemstack1 = addItem((IInventory)null, iinventory, itemstack, (EnumDirection)null);
        if (itemstack1.isEmpty()) {
            flag = true;
            entityitem.die();
        } else {
            entityitem.setItemStack(itemstack1);
        }

        return flag;
    }

    public static ItemStack addItem(@Nullable IInventory iinventory, IInventory iinventory1, ItemStack itemstack, @Nullable EnumDirection enumdirection) {
        if (iinventory1 instanceof IWorldInventory && enumdirection != null) {
            IWorldInventory iworldinventory = (IWorldInventory)iinventory1;
            int[] aint = iworldinventory.getSlotsForFace(enumdirection);

            for(int k = 0; k < aint.length && !itemstack.isEmpty(); ++k) {
                itemstack = a(iinventory, iinventory1, itemstack, aint[k], enumdirection);
            }
        } else {
            int i = iinventory1.getSize();

            for(int jx = 0; jx < i && !itemstack.isEmpty(); ++jx) {
                itemstack = a(iinventory, iinventory1, itemstack, jx, enumdirection);
            }
        }

        return itemstack;
    }

    private static boolean a(IInventory iinventory, ItemStack itemstack, int i, @Nullable EnumDirection enumdirection) {
        if (!iinventory.b(i, itemstack)) {
            return false;
        } else {
            return !(iinventory instanceof IWorldInventory) || ((IWorldInventory)iinventory).canPlaceItemThroughFace(i, itemstack, enumdirection);
        }
    }

    private static boolean b(IInventory iinventory, ItemStack itemstack, int i, EnumDirection enumdirection) {
        return !(iinventory instanceof IWorldInventory) || ((IWorldInventory)iinventory).canTakeItemThroughFace(i, itemstack, enumdirection);
    }

    private static ItemStack a(@Nullable IInventory iinventory, IInventory iinventory1, ItemStack itemstack, int i, @Nullable EnumDirection enumdirection) {
        ItemStack itemstack1 = iinventory1.getItem(i);
        if (a(iinventory1, itemstack, i, enumdirection)) {
            boolean flag = false;
            boolean flag1 = iinventory1.P_();
            if (itemstack1.isEmpty()) {
                iinventory1.setItem(i, itemstack);
                itemstack = ItemStack.a;
                flag = true;
            } else if (a(itemstack1, itemstack)) {
                int jx = itemstack.getMaxStackSize() - itemstack1.getCount();
                int k = Math.min(itemstack.getCount(), jx);
                itemstack.subtract(k);
                itemstack1.add(k);
                flag = k > 0;
            }

            if (flag) {
                if (flag1 && iinventory1 instanceof TileEntityHopper) {
                    TileEntityHopper tileentityhopper1 = (TileEntityHopper)iinventory1;
                    if (!tileentityhopper1.J()) {
                        byte b0 = 0;
                        if (iinventory instanceof TileEntityHopper) {
                            TileEntityHopper tileentityhopper = (TileEntityHopper)iinventory;
                            if (tileentityhopper1.j >= tileentityhopper.j) {
                                b0 = 1;
                            }
                        }

                        tileentityhopper1.setCooldown(8 - b0);
                    }
                }

                iinventory1.update();
            }
        }

        return itemstack;
    }

    @Nullable
    private IInventory D() {
        EnumDirection enumdirection = (EnumDirection)this.getBlock().get(BlockHopper.FACING);
        return a(this.getWorld(), this.position.shift(enumdirection));
    }

    @Nullable
    public static IInventory b(IHopper ihopper) {
        return a(ihopper.getWorld(), ihopper.G(), ihopper.H() + 1.0D, ihopper.I());
    }

    public static List<EntityItem> c(IHopper ihopper) {
        return (List)ihopper.i().d().stream().flatMap((axisalignedbb) -> {
            return ihopper.getWorld().a(EntityItem.class, axisalignedbb.d(ihopper.G() - 0.5D, ihopper.H() - 0.5D, ihopper.I() - 0.5D), IEntitySelector.a).stream();
        }).collect(Collectors.toList());
    }

    @Nullable
    public static IInventory a(World world, BlockPosition blockposition) {
        return a(world, (double)blockposition.getX() + 0.5D, (double)blockposition.getY() + 0.5D, (double)blockposition.getZ() + 0.5D);
    }

    @Nullable
    public static IInventory a(World world, double d0, double d1, double d2) {
        Object object = null;
        BlockPosition blockposition = new BlockPosition(d0, d1, d2);
        IBlockData iblockdata = world.getType(blockposition);
        Block block = iblockdata.getBlock();
        if (block.isTileEntity()) {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof IInventory) {
                object = (IInventory)tileentity;
                if (object instanceof TileEntityChest && block instanceof BlockChest) {
                    object = ((BlockChest)block).getInventory(iblockdata, world, blockposition, true);
                }
            }
        }

        if (object == null) {
            List list = world.getEntities((Entity)null, new AxisAlignedBB(d0 - 0.5D, d1 - 0.5D, d2 - 0.5D, d0 + 0.5D, d1 + 0.5D, d2 + 0.5D), IEntitySelector.d);
            if (!list.isEmpty()) {
                object = (IInventory)list.get(world.random.nextInt(list.size()));
            }
        }

        return (IInventory)object;
    }

    private static boolean a(ItemStack itemstack, ItemStack itemstack1) {
        if (itemstack.getItem() != itemstack1.getItem()) {
            return false;
        } else if (itemstack.getDamage() != itemstack1.getDamage()) {
            return false;
        } else if (itemstack.getCount() > itemstack.getMaxStackSize()) {
            return false;
        } else {
            return ItemStack.equals(itemstack, itemstack1);
        }
    }

    public double G() {
        return (double)this.position.getX() + 0.5D;
    }

    public double H() {
        return (double)this.position.getY() + 0.5D;
    }

    public double I() {
        return (double)this.position.getZ() + 0.5D;
    }

    private void setCooldown(int i) {
        this.f = i;
    }

    private boolean E() {
        return this.f > 0;
    }

    private boolean J() {
        return this.f > 8;
    }

    public String getContainerName() {
        return "minecraft:hopper";
    }

    public Container createContainer(PlayerInventory playerinventory, EntityHuman entityhuman) {
        this.d(entityhuman);
        return new ContainerHopper(playerinventory, this, entityhuman);
    }

    protected NonNullList<ItemStack> q() {
        return this.items;
    }

    protected void a(NonNullList<ItemStack> nonnulllist) {
        this.items = nonnulllist;
    }

    public void a(Entity entity) {
        if (entity instanceof EntityItem) {
            BlockPosition blockposition = this.getPosition();
            if (VoxelShapes.c(VoxelShapes.a(entity.getBoundingBox().d((double)(-blockposition.getX()), (double)(-blockposition.getY()), (double)(-blockposition.getZ()))), this.i(), OperatorBoolean.AND)) {
                this.a(() -> {
                    return a(this, (EntityItem)entity);
                });
            }
        }

    }
}
