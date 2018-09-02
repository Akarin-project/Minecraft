package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class TileEntityFurnace extends TileEntityContainer implements IWorldInventory, RecipeHolder, AutoRecipeOutput, ITickable {
    private static final int[] a = new int[]{0};
    private static final int[] e = new int[]{2, 1};
    private static final int[] f = new int[]{1};
    private NonNullList<ItemStack> items = NonNullList.<ItemStack>a(3, ItemStack.a);
    private int burnTime;
    private int ticksForCurrentFuel;
    private int cookTime;
    private int cookTimeTotal;
    private IChatBaseComponent l;
    private final Map<MinecraftKey, Integer> m = Maps.newHashMap();

    private static void a(Map<Item, Integer> map, Tag<Item> tag, int i) {
        for(Item item : tag.a()) {
            map.put(item, i);
        }

    }

    private static void a(Map<Item, Integer> map, IMaterial imaterial, int i) {
        map.put(imaterial.getItem(), i);
    }

    public static Map<Item, Integer> p() {
        LinkedHashMap linkedhashmap = Maps.newLinkedHashMap();
        a(linkedhashmap, Items.LAVA_BUCKET, 20000);
        a(linkedhashmap, Blocks.COAL_BLOCK, 16000);
        a(linkedhashmap, Items.BLAZE_ROD, 2400);
        a(linkedhashmap, Items.COAL, 1600);
        a(linkedhashmap, Items.CHARCOAL, 1600);
        a(linkedhashmap, TagsItem.LOGS, 300);
        a(linkedhashmap, TagsItem.PLANKS, 300);
        a(linkedhashmap, TagsItem.WOODEN_STAIRS, 300);
        a(linkedhashmap, TagsItem.WOODEN_SLABS, 150);
        a(linkedhashmap, TagsItem.WOODEN_TRAPDOORS, 300);
        a(linkedhashmap, TagsItem.WOODEN_PRESSURE_PLATES, 300);
        a(linkedhashmap, Blocks.OAK_FENCE, 300);
        a(linkedhashmap, Blocks.BIRCH_FENCE, 300);
        a(linkedhashmap, Blocks.SPRUCE_FENCE, 300);
        a(linkedhashmap, Blocks.JUNGLE_FENCE, 300);
        a(linkedhashmap, Blocks.DARK_OAK_FENCE, 300);
        a(linkedhashmap, Blocks.ACACIA_FENCE, 300);
        a(linkedhashmap, Blocks.OAK_FENCE_GATE, 300);
        a(linkedhashmap, Blocks.BIRCH_FENCE_GATE, 300);
        a(linkedhashmap, Blocks.SPRUCE_FENCE_GATE, 300);
        a(linkedhashmap, Blocks.JUNGLE_FENCE_GATE, 300);
        a(linkedhashmap, Blocks.DARK_OAK_FENCE_GATE, 300);
        a(linkedhashmap, Blocks.ACACIA_FENCE_GATE, 300);
        a(linkedhashmap, Blocks.NOTE_BLOCK, 300);
        a(linkedhashmap, Blocks.BOOKSHELF, 300);
        a(linkedhashmap, Blocks.JUKEBOX, 300);
        a(linkedhashmap, Blocks.CHEST, 300);
        a(linkedhashmap, Blocks.TRAPPED_CHEST, 300);
        a(linkedhashmap, Blocks.CRAFTING_TABLE, 300);
        a(linkedhashmap, Blocks.DAYLIGHT_DETECTOR, 300);
        a(linkedhashmap, TagsItem.BANNERS, 300);
        a(linkedhashmap, Items.BOW, 300);
        a(linkedhashmap, Items.FISHING_ROD, 300);
        a(linkedhashmap, Blocks.LADDER, 300);
        a(linkedhashmap, Items.SIGN, 200);
        a(linkedhashmap, Items.WOODEN_SHOVEL, 200);
        a(linkedhashmap, Items.WOODEN_SWORD, 200);
        a(linkedhashmap, Items.WOODEN_HOE, 200);
        a(linkedhashmap, Items.WOODEN_AXE, 200);
        a(linkedhashmap, Items.WOODEN_PICKAXE, 200);
        a(linkedhashmap, TagsItem.WOODEN_DOORS, 200);
        a(linkedhashmap, TagsItem.BOATS, 200);
        a(linkedhashmap, TagsItem.WOOL, 100);
        a(linkedhashmap, TagsItem.WOODEN_BUTTONS, 100);
        a(linkedhashmap, Items.STICK, 100);
        a(linkedhashmap, TagsItem.SAPLINGS, 100);
        a(linkedhashmap, Items.BOWL, 100);
        a(linkedhashmap, TagsItem.CARPETS, 67);
        a(linkedhashmap, Blocks.DRIED_KELP_BLOCK, 4001);
        return linkedhashmap;
    }

    public TileEntityFurnace() {
        super(TileEntityTypes.FURNACE);
    }

    public int getSize() {
        return this.items.size();
    }

    public boolean P_() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public ItemStack getItem(int i) {
        return this.items.get(i);
    }

    public ItemStack splitStack(int i, int j) {
        return ContainerUtil.a(this.items, i, j);
    }

    public ItemStack splitWithoutUpdate(int i) {
        return ContainerUtil.a(this.items, i);
    }

    public void setItem(int i, ItemStack itemstack) {
        ItemStack itemstack1 = this.items.get(i);
        boolean flag = !itemstack.isEmpty() && itemstack.doMaterialsMatch(itemstack1) && ItemStack.equals(itemstack, itemstack1);
        this.items.set(i, itemstack);
        if (itemstack.getCount() > this.getMaxStackSize()) {
            itemstack.setCount(this.getMaxStackSize());
        }

        if (i == 0 && !flag) {
            this.cookTimeTotal = this.s();
            this.cookTime = 0;
            this.update();
        }

    }

    public IChatBaseComponent getDisplayName() {
        return (IChatBaseComponent)(this.l != null ? this.l : new ChatMessage("container.furnace", new Object[0]));
    }

    public boolean hasCustomName() {
        return this.l != null;
    }

    @Nullable
    public IChatBaseComponent getCustomName() {
        return this.l;
    }

    public void setCustomName(@Nullable IChatBaseComponent ichatbasecomponent) {
        this.l = ichatbasecomponent;
    }

    public void load(NBTTagCompound nbttagcompound) {
        super.load(nbttagcompound);
        this.items = NonNullList.<ItemStack>a(this.getSize(), ItemStack.a);
        ContainerUtil.b(nbttagcompound, this.items);
        this.burnTime = nbttagcompound.getShort("BurnTime");
        this.cookTime = nbttagcompound.getShort("CookTime");
        this.cookTimeTotal = nbttagcompound.getShort("CookTimeTotal");
        this.ticksForCurrentFuel = fuelTime(this.items.get(1));
        short short1 = nbttagcompound.getShort("RecipesUsedSize");

        for(int i = 0; i < short1; ++i) {
            MinecraftKey minecraftkey = new MinecraftKey(nbttagcompound.getString("RecipeLocation" + i));
            int j = nbttagcompound.getInt("RecipeAmount" + i);
            this.m.put(minecraftkey, j);
        }

        if (nbttagcompound.hasKeyOfType("CustomName", 8)) {
            this.l = IChatBaseComponent.ChatSerializer.a(nbttagcompound.getString("CustomName"));
        }

    }

    public NBTTagCompound save(NBTTagCompound nbttagcompound) {
        super.save(nbttagcompound);
        nbttagcompound.setShort("BurnTime", (short)this.burnTime);
        nbttagcompound.setShort("CookTime", (short)this.cookTime);
        nbttagcompound.setShort("CookTimeTotal", (short)this.cookTimeTotal);
        ContainerUtil.a(nbttagcompound, this.items);
        nbttagcompound.setShort("RecipesUsedSize", (short)this.m.size());
        int i = 0;

        for(Entry entry : this.m.entrySet()) {
            nbttagcompound.setString("RecipeLocation" + i, ((MinecraftKey)entry.getKey()).toString());
            nbttagcompound.setInt("RecipeAmount" + i, entry.getValue());
            ++i;
        }

        if (this.l != null) {
            nbttagcompound.setString("CustomName", IChatBaseComponent.ChatSerializer.a(this.l));
        }

        return nbttagcompound;
    }

    public int getMaxStackSize() {
        return 64;
    }

    private boolean isBurning() {
        return this.burnTime > 0;
    }

    public void Y_() {
        boolean flag = this.isBurning();
        boolean flag1 = false;
        if (this.isBurning()) {
            --this.burnTime;
        }

        if (!this.world.isClientSide) {
            ItemStack itemstack = this.items.get(1);
            if (this.isBurning() || !itemstack.isEmpty() && !((ItemStack)this.items.get(0)).isEmpty()) {
                IRecipe irecipe = this.world.E().b(this, this.world);
                if (!this.isBurning() && this.canBurn(irecipe)) {
                    this.burnTime = fuelTime(itemstack);
                    this.ticksForCurrentFuel = this.burnTime;
                    if (this.isBurning()) {
                        flag1 = true;
                        if (!itemstack.isEmpty()) {
                            Item item = itemstack.getItem();
                            itemstack.subtract(1);
                            if (itemstack.isEmpty()) {
                                Item item1 = item.o();
                                this.items.set(1, item1 == null ? ItemStack.a : new ItemStack(item1));
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canBurn(irecipe)) {
                    ++this.cookTime;
                    if (this.cookTime == this.cookTimeTotal) {
                        this.cookTime = 0;
                        this.cookTimeTotal = this.s();
                        this.burn(irecipe);
                        flag1 = true;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if (!this.isBurning() && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
            }

            if (flag != this.isBurning()) {
                flag1 = true;
                this.world.setTypeAndData(this.position, (IBlockData)this.world.getType(this.position).set(BlockFurnace.LIT, Boolean.valueOf(this.isBurning())), 3);
            }
        }

        if (flag1) {
            this.update();
        }

    }

    private int s() {
        FurnaceRecipe furnacerecipe = (FurnaceRecipe)this.world.E().b(this, this.world);
        return furnacerecipe != null ? furnacerecipe.h() : 200;
    }

    private boolean canBurn(@Nullable IRecipe irecipe) {
        if (!((ItemStack)this.items.get(0)).isEmpty() && irecipe != null) {
            ItemStack itemstack = irecipe.d();
            if (itemstack.isEmpty()) {
                return false;
            } else {
                ItemStack itemstack1 = this.items.get(2);
                if (itemstack1.isEmpty()) {
                    return true;
                } else if (!itemstack1.doMaterialsMatch(itemstack)) {
                    return false;
                } else if (itemstack1.getCount() < this.getMaxStackSize() && itemstack1.getCount() < itemstack1.getMaxStackSize()) {
                    return true;
                } else {
                    return itemstack1.getCount() < itemstack.getMaxStackSize();
                }
            }
        } else {
            return false;
        }
    }

    private void burn(@Nullable IRecipe irecipe) {
        if (irecipe != null && this.canBurn(irecipe)) {
            ItemStack itemstack = this.items.get(0);
            ItemStack itemstack1 = irecipe.d();
            ItemStack itemstack2 = this.items.get(2);
            if (itemstack2.isEmpty()) {
                this.items.set(2, itemstack1.cloneItemStack());
            } else if (itemstack2.getItem() == itemstack1.getItem()) {
                itemstack2.add(1);
            }

            if (!this.world.isClientSide) {
                this.a(this.world, (EntityPlayer)null, irecipe);
            }

            if (itemstack.getItem() == Blocks.WET_SPONGE.getItem() && !((ItemStack)this.items.get(1)).isEmpty() && ((ItemStack)this.items.get(1)).getItem() == Items.BUCKET) {
                this.items.set(1, new ItemStack(Items.WATER_BUCKET));
            }

            itemstack.subtract(1);
        }
    }

    private static int fuelTime(ItemStack itemstack) {
        if (itemstack.isEmpty()) {
            return 0;
        } else {
            Item item = itemstack.getItem();
            return p().getOrDefault(item, 0);
        }
    }

    public static boolean isFuel(ItemStack itemstack) {
        return p().containsKey(itemstack.getItem());
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

    public boolean b(int i, ItemStack itemstack) {
        if (i == 2) {
            return false;
        } else if (i != 1) {
            return true;
        } else {
            ItemStack itemstack1 = this.items.get(1);
            return isFuel(itemstack) || SlotFurnaceFuel.d_(itemstack) && itemstack1.getItem() != Items.BUCKET;
        }
    }

    public int[] getSlotsForFace(EnumDirection enumdirection) {
        if (enumdirection == EnumDirection.DOWN) {
            return e;
        } else {
            return enumdirection == EnumDirection.UP ? a : f;
        }
    }

    public boolean canPlaceItemThroughFace(int i, ItemStack itemstack, @Nullable EnumDirection var3) {
        return this.b(i, itemstack);
    }

    public boolean canTakeItemThroughFace(int i, ItemStack itemstack, EnumDirection enumdirection) {
        if (enumdirection == EnumDirection.DOWN && i == 1) {
            Item item = itemstack.getItem();
            if (item != Items.WATER_BUCKET && item != Items.BUCKET) {
                return false;
            }
        }

        return true;
    }

    public String getContainerName() {
        return "minecraft:furnace";
    }

    public Container createContainer(PlayerInventory playerinventory, EntityHuman var2) {
        return new ContainerFurnace(playerinventory, this);
    }

    public int getProperty(int i) {
        switch(i) {
        case 0:
            return this.burnTime;
        case 1:
            return this.ticksForCurrentFuel;
        case 2:
            return this.cookTime;
        case 3:
            return this.cookTimeTotal;
        default:
            return 0;
        }
    }

    public void setProperty(int i, int j) {
        switch(i) {
        case 0:
            this.burnTime = j;
            break;
        case 1:
            this.ticksForCurrentFuel = j;
            break;
        case 2:
            this.cookTime = j;
            break;
        case 3:
            this.cookTimeTotal = j;
        }

    }

    public int h() {
        return 4;
    }

    public void clear() {
        this.items.clear();
    }

    public void a(AutoRecipeStackManager autorecipestackmanager) {
        for(ItemStack itemstack : this.items) {
            autorecipestackmanager.b(itemstack);
        }

    }

    public void a(IRecipe irecipe) {
        if (this.m.containsKey(irecipe.getKey())) {
            this.m.put(irecipe.getKey(), this.m.get(irecipe.getKey()) + 1);
        } else {
            this.m.put(irecipe.getKey(), 1);
        }

    }

    @Nullable
    public IRecipe i() {
        return null;
    }

    public Map<MinecraftKey, Integer> q() {
        return this.m;
    }

    public boolean a(World var1, EntityPlayer var2, @Nullable IRecipe irecipe) {
        if (irecipe != null) {
            this.a(irecipe);
            return true;
        } else {
            return false;
        }
    }

    public void d(EntityHuman entityhuman) {
        if (!this.world.getGameRules().getBoolean("doLimitedCrafting")) {
            ArrayList arraylist = Lists.newArrayList();

            for(MinecraftKey minecraftkey : this.m.keySet()) {
                IRecipe irecipe = entityhuman.world.E().a(minecraftkey);
                if (irecipe != null) {
                    arraylist.add(irecipe);
                }
            }

            entityhuman.a(arraylist);
        }

        this.m.clear();
    }
}
