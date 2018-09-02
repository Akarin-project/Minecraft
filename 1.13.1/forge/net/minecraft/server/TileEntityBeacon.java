package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class TileEntityBeacon extends TileEntityContainer implements IWorldInventory, ITickable {
    public static final MobEffectList[][] a = new MobEffectList[][]{{MobEffects.FASTER_MOVEMENT, MobEffects.FASTER_DIG}, {MobEffects.RESISTANCE, MobEffects.JUMP}, {MobEffects.INCREASE_DAMAGE}, {MobEffects.REGENERATION}};
    private static final Set<MobEffectList> e = (Set)Arrays.stream(a).flatMap(Arrays::stream).collect(Collectors.toSet());
    private final List<TileEntityBeacon.BeaconColorTracker> f = Lists.newArrayList();
    private boolean i;
    private boolean j;
    public int levels = -1;
    @Nullable
    public MobEffectList primaryEffect;
    @Nullable
    public MobEffectList secondaryEffect;
    private ItemStack inventorySlot = ItemStack.a;
    private IChatBaseComponent o;

    public TileEntityBeacon() {
        super(TileEntityTypes.BEACON);
    }

    public void Y_() {
        if (this.world.getTime() % 80L == 0L) {
            this.p();
            if (this.i) {
                this.a(SoundEffects.BLOCK_BEACON_AMBIENT);
            }
        }

        if (!this.world.isClientSide && this.i != this.j) {
            this.j = this.i;
            this.a(this.i ? SoundEffects.BLOCK_BEACON_ACTIVATE : SoundEffects.BLOCK_BEACON_DEACTIVATE);
        }

    }

    public void p() {
        if (this.world != null) {
            this.E();
            this.D();
        }

    }

    public void a(SoundEffect soundeffect) {
        this.world.a((EntityHuman)null, this.position, soundeffect, SoundCategory.BLOCKS, 1.0F, 1.0F);
    }

    private void D() {
        if (this.i && this.levels > 0 && !this.world.isClientSide && this.primaryEffect != null) {
            double d0 = (double)(this.levels * 10 + 10);
            byte b0 = 0;
            if (this.levels >= 4 && this.primaryEffect == this.secondaryEffect) {
                b0 = 1;
            }

            int ix = (9 + this.levels * 2) * 20;
            int jx = this.position.getX();
            int k = this.position.getY();
            int l = this.position.getZ();
            AxisAlignedBB axisalignedbb = (new AxisAlignedBB((double)jx, (double)k, (double)l, (double)(jx + 1), (double)(k + 1), (double)(l + 1))).g(d0).b(0.0D, (double)this.world.getHeight(), 0.0D);
            List list = this.world.a(EntityHuman.class, axisalignedbb);

            for(EntityHuman entityhuman : list) {
                entityhuman.addEffect(new MobEffect(this.primaryEffect, ix, b0, true, true));
            }

            if (this.levels >= 4 && this.primaryEffect != this.secondaryEffect && this.secondaryEffect != null) {
                for(EntityHuman entityhuman1 : list) {
                    entityhuman1.addEffect(new MobEffect(this.secondaryEffect, ix, 0, true, true));
                }
            }
        }

    }

    private void E() {
        int ix = this.position.getX();
        int jx = this.position.getY();
        int k = this.position.getZ();
        int l = this.levels;
        this.levels = 0;
        this.f.clear();
        this.i = true;
        TileEntityBeacon.BeaconColorTracker tileentitybeacon$beaconcolortracker = new TileEntityBeacon.BeaconColorTracker(EnumColor.WHITE.d());
        this.f.add(tileentitybeacon$beaconcolortracker);
        boolean flag = true;
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

        for(int i1 = jx + 1; i1 < 256; ++i1) {
            IBlockData iblockdata = this.world.getType(blockposition$mutableblockposition.c(ix, i1, k));
            Block block = iblockdata.getBlock();
            float[] afloat;
            if (block instanceof BlockStainedGlass) {
                afloat = ((BlockStainedGlass)block).d().d();
            } else {
                if (!(block instanceof BlockStainedGlassPane)) {
                    if (iblockdata.b(this.world, blockposition$mutableblockposition) >= 15 && block != Blocks.BEDROCK) {
                        this.i = false;
                        this.f.clear();
                        break;
                    }

                    tileentitybeacon$beaconcolortracker.a();
                    continue;
                }

                afloat = ((BlockStainedGlassPane)block).d().d();
            }

            if (!flag) {
                afloat = new float[]{(tileentitybeacon$beaconcolortracker.b()[0] + afloat[0]) / 2.0F, (tileentitybeacon$beaconcolortracker.b()[1] + afloat[1]) / 2.0F, (tileentitybeacon$beaconcolortracker.b()[2] + afloat[2]) / 2.0F};
            }

            if (Arrays.equals(afloat, tileentitybeacon$beaconcolortracker.b())) {
                tileentitybeacon$beaconcolortracker.a();
            } else {
                tileentitybeacon$beaconcolortracker = new TileEntityBeacon.BeaconColorTracker(afloat);
                this.f.add(tileentitybeacon$beaconcolortracker);
            }

            flag = false;
        }

        if (this.i) {
            for(int k1 = 1; k1 <= 4; this.levels = k1++) {
                int l1 = jx - k1;
                if (l1 < 0) {
                    break;
                }

                boolean flag1 = true;

                for(int i2 = ix - k1; i2 <= ix + k1 && flag1; ++i2) {
                    for(int j1 = k - k1; j1 <= k + k1; ++j1) {
                        Block block1 = this.world.getType(new BlockPosition(i2, l1, j1)).getBlock();
                        if (block1 != Blocks.EMERALD_BLOCK && block1 != Blocks.GOLD_BLOCK && block1 != Blocks.DIAMOND_BLOCK && block1 != Blocks.IRON_BLOCK) {
                            flag1 = false;
                            break;
                        }
                    }
                }

                if (!flag1) {
                    break;
                }
            }

            if (this.levels == 0) {
                this.i = false;
            }
        }

        if (!this.world.isClientSide && l < this.levels) {
            for(EntityPlayer entityplayer : this.world.a(EntityPlayer.class, (new AxisAlignedBB((double)ix, (double)jx, (double)k, (double)ix, (double)(jx - 4), (double)k)).grow(10.0D, 5.0D, 10.0D))) {
                CriterionTriggers.l.a(entityplayer, this);
            }
        }

    }

    public int s() {
        return this.levels;
    }

    @Nullable
    public PacketPlayOutTileEntityData getUpdatePacket() {
        return new PacketPlayOutTileEntityData(this.position, 3, this.aa_());
    }

    public NBTTagCompound aa_() {
        return this.save(new NBTTagCompound());
    }

    @Nullable
    private static MobEffectList e(int ix) {
        MobEffectList mobeffectlist = MobEffectList.fromId(ix);
        return e.contains(mobeffectlist) ? mobeffectlist : null;
    }

    public void load(NBTTagCompound nbttagcompound) {
        super.load(nbttagcompound);
        this.primaryEffect = e(nbttagcompound.getInt("Primary"));
        this.secondaryEffect = e(nbttagcompound.getInt("Secondary"));
        this.levels = nbttagcompound.getInt("Levels");
    }

    public NBTTagCompound save(NBTTagCompound nbttagcompound) {
        super.save(nbttagcompound);
        nbttagcompound.setInt("Primary", MobEffectList.getId(this.primaryEffect));
        nbttagcompound.setInt("Secondary", MobEffectList.getId(this.secondaryEffect));
        nbttagcompound.setInt("Levels", this.levels);
        return nbttagcompound;
    }

    public int getSize() {
        return 1;
    }

    public boolean P_() {
        return this.inventorySlot.isEmpty();
    }

    public ItemStack getItem(int ix) {
        return ix == 0 ? this.inventorySlot : ItemStack.a;
    }

    public ItemStack splitStack(int ix, int jx) {
        if (ix == 0 && !this.inventorySlot.isEmpty()) {
            if (jx >= this.inventorySlot.getCount()) {
                ItemStack itemstack = this.inventorySlot;
                this.inventorySlot = ItemStack.a;
                return itemstack;
            } else {
                return this.inventorySlot.cloneAndSubtract(jx);
            }
        } else {
            return ItemStack.a;
        }
    }

    public ItemStack splitWithoutUpdate(int ix) {
        if (ix == 0) {
            ItemStack itemstack = this.inventorySlot;
            this.inventorySlot = ItemStack.a;
            return itemstack;
        } else {
            return ItemStack.a;
        }
    }

    public void setItem(int ix, ItemStack itemstack) {
        if (ix == 0) {
            this.inventorySlot = itemstack;
        }

    }

    public IChatBaseComponent getDisplayName() {
        return (IChatBaseComponent)(this.o != null ? this.o : new ChatMessage("container.beacon", new Object[0]));
    }

    public boolean hasCustomName() {
        return this.o != null;
    }

    @Nullable
    public IChatBaseComponent getCustomName() {
        return this.o;
    }

    public void setCustomName(@Nullable IChatBaseComponent ichatbasecomponent) {
        this.o = ichatbasecomponent;
    }

    public int getMaxStackSize() {
        return 1;
    }

    public boolean a(EntityHuman entityhuman) {
        if (this.world.getTileEntity(this.position) != this) {
            return false;
        } else {
            return !(entityhuman.d((double)this.position.getX() + 0.5D, (double)this.position.getY() + 0.5D, (double)this.position.getZ() + 0.5D) > 64.0D);
        }
    }

    public void startOpen(EntityHuman var1) {
    }

    public void closeContainer(EntityHuman var1) {
    }

    public boolean b(int var1, ItemStack itemstack) {
        return itemstack.getItem() == Items.EMERALD || itemstack.getItem() == Items.DIAMOND || itemstack.getItem() == Items.GOLD_INGOT || itemstack.getItem() == Items.IRON_INGOT;
    }

    public String getContainerName() {
        return "minecraft:beacon";
    }

    public Container createContainer(PlayerInventory playerinventory, EntityHuman var2) {
        return new ContainerBeacon(playerinventory, this);
    }

    public int getProperty(int ix) {
        switch(ix) {
        case 0:
            return this.levels;
        case 1:
            return MobEffectList.getId(this.primaryEffect);
        case 2:
            return MobEffectList.getId(this.secondaryEffect);
        default:
            return 0;
        }
    }

    public void setProperty(int ix, int jx) {
        switch(ix) {
        case 0:
            this.levels = jx;
            break;
        case 1:
            this.primaryEffect = e(jx);
            break;
        case 2:
            this.secondaryEffect = e(jx);
        }

        if (!this.world.isClientSide && ix == 1 && this.i) {
            this.a(SoundEffects.BLOCK_BEACON_POWER_SELECT);
        }

    }

    public int h() {
        return 3;
    }

    public void clear() {
        this.inventorySlot = ItemStack.a;
    }

    public boolean c(int ix, int jx) {
        if (ix == 1) {
            this.p();
            return true;
        } else {
            return super.c(ix, jx);
        }
    }

    public int[] getSlotsForFace(EnumDirection var1) {
        return new int[0];
    }

    public boolean canPlaceItemThroughFace(int var1, ItemStack var2, @Nullable EnumDirection var3) {
        return false;
    }

    public boolean canTakeItemThroughFace(int var1, ItemStack var2, EnumDirection var3) {
        return false;
    }

    public static class BeaconColorTracker {
        private final float[] a;
        private int b;

        public BeaconColorTracker(float[] afloat) {
            this.a = afloat;
            this.b = 1;
        }

        protected void a() {
            ++this.b;
        }

        public float[] b() {
            return this.a;
        }
    }
}
