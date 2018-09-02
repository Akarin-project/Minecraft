package net.minecraft.server;

import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class EntitySheep extends EntityAnimal {
    private static final DataWatcherObject<Byte> bC = DataWatcher.a(EntitySheep.class, DataWatcherRegistry.a);
    private final InventoryCrafting container = new InventoryCrafting(new Container() {
        public boolean canUse(EntityHuman var1) {
            return false;
        }
    }, 2, 1);
    private static final Map<EnumColor, IMaterial> bE = (Map)SystemUtils.a(Maps.newEnumMap(EnumColor.class), (enummap) -> {
        enummap.put(EnumColor.WHITE, Blocks.WHITE_WOOL);
        enummap.put(EnumColor.ORANGE, Blocks.ORANGE_WOOL);
        enummap.put(EnumColor.MAGENTA, Blocks.MAGENTA_WOOL);
        enummap.put(EnumColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
        enummap.put(EnumColor.YELLOW, Blocks.YELLOW_WOOL);
        enummap.put(EnumColor.LIME, Blocks.LIME_WOOL);
        enummap.put(EnumColor.PINK, Blocks.PINK_WOOL);
        enummap.put(EnumColor.GRAY, Blocks.GRAY_WOOL);
        enummap.put(EnumColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
        enummap.put(EnumColor.CYAN, Blocks.CYAN_WOOL);
        enummap.put(EnumColor.PURPLE, Blocks.PURPLE_WOOL);
        enummap.put(EnumColor.BLUE, Blocks.BLUE_WOOL);
        enummap.put(EnumColor.BROWN, Blocks.BROWN_WOOL);
        enummap.put(EnumColor.GREEN, Blocks.GREEN_WOOL);
        enummap.put(EnumColor.RED, Blocks.RED_WOOL);
        enummap.put(EnumColor.BLACK, Blocks.BLACK_WOOL);
    });
    private static final Map<EnumColor, float[]> bG = Maps.newEnumMap((Map)Arrays.stream(EnumColor.values()).collect(Collectors.toMap((enumcolor) -> {
        return enumcolor;
    }, EntitySheep::c)));
    private int bH;
    private PathfinderGoalEatTile bI;

    private static float[] c(EnumColor enumcolor) {
        if (enumcolor == EnumColor.WHITE) {
            return new float[]{0.9019608F, 0.9019608F, 0.9019608F};
        } else {
            float[] afloat = enumcolor.d();
            float f = 0.75F;
            return new float[]{afloat[0] * 0.75F, afloat[1] * 0.75F, afloat[2] * 0.75F};
        }
    }

    public EntitySheep(World world) {
        super(EntityTypes.SHEEP, world);
        this.setSize(0.9F, 1.3F);
    }

    protected void n() {
        this.bI = new PathfinderGoalEatTile(this);
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalPanic(this, 1.25D));
        this.goalSelector.a(2, new PathfinderGoalBreed(this, 1.0D));
        this.goalSelector.a(3, new PathfinderGoalTempt(this, 1.1D, RecipeItemStack.a(Items.WHEAT), false));
        this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 1.1D));
        this.goalSelector.a(5, this.bI);
        this.goalSelector.a(6, new PathfinderGoalRandomStrollLand(this, 1.0D));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
    }

    protected void mobTick() {
        this.bH = this.bI.g();
        super.mobTick();
    }

    public void k() {
        if (this.world.isClientSide) {
            this.bH = Math.max(0, this.bH - 1);
        }

        super.k();
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(8.0D);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue((double)0.23F);
    }

    protected void x_() {
        super.x_();
        this.datawatcher.register(bC, (byte)0);
    }

    @Nullable
    protected MinecraftKey getDefaultLootTable() {
        if (this.isSheared()) {
            return LootTables.W;
        } else {
            switch(this.getColor()) {
            case WHITE:
            default:
                return LootTables.X;
            case ORANGE:
                return LootTables.Y;
            case MAGENTA:
                return LootTables.Z;
            case LIGHT_BLUE:
                return LootTables.aa;
            case YELLOW:
                return LootTables.ab;
            case LIME:
                return LootTables.ac;
            case PINK:
                return LootTables.ad;
            case GRAY:
                return LootTables.ae;
            case LIGHT_GRAY:
                return LootTables.af;
            case CYAN:
                return LootTables.ag;
            case PURPLE:
                return LootTables.ah;
            case BLUE:
                return LootTables.ai;
            case BROWN:
                return LootTables.aj;
            case GREEN:
                return LootTables.ak;
            case RED:
                return LootTables.al;
            case BLACK:
                return LootTables.am;
            }
        }
    }

    public boolean a(EntityHuman entityhuman, EnumHand enumhand) {
        ItemStack itemstack = entityhuman.b(enumhand);
        if (itemstack.getItem() == Items.SHEARS && !this.isSheared() && !this.isBaby()) {
            if (!this.world.isClientSide) {
                this.setSheared(true);
                int i = 1 + this.random.nextInt(3);

                for(int j = 0; j < i; ++j) {
                    EntityItem entityitem = this.a((IMaterial)bE.get(this.getColor()), 1);
                    if (entityitem != null) {
                        entityitem.motY += (double)(this.random.nextFloat() * 0.05F);
                        entityitem.motX += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
                        entityitem.motZ += (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F);
                    }
                }
            }

            itemstack.damage(1, entityhuman);
            this.a(SoundEffects.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
        }

        return super.a(entityhuman, enumhand);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        nbttagcompound.setBoolean("Sheared", this.isSheared());
        nbttagcompound.setByte("Color", (byte)this.getColor().getColorIndex());
    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        this.setSheared(nbttagcompound.getBoolean("Sheared"));
        this.setColor(EnumColor.fromColorIndex(nbttagcompound.getByte("Color")));
    }

    protected SoundEffect D() {
        return SoundEffects.ENTITY_SHEEP_AMBIENT;
    }

    protected SoundEffect d(DamageSource var1) {
        return SoundEffects.ENTITY_SHEEP_HURT;
    }

    protected SoundEffect cs() {
        return SoundEffects.ENTITY_SHEEP_DEATH;
    }

    protected void a(BlockPosition var1, IBlockData var2) {
        this.a(SoundEffects.ENTITY_SHEEP_STEP, 0.15F, 1.0F);
    }

    public EnumColor getColor() {
        return EnumColor.fromColorIndex(this.datawatcher.get(bC) & 15);
    }

    public void setColor(EnumColor enumcolor) {
        byte b0 = this.datawatcher.get(bC);
        this.datawatcher.set(bC, (byte)(b0 & 240 | enumcolor.getColorIndex() & 15));
    }

    public boolean isSheared() {
        return (this.datawatcher.get(bC) & 16) != 0;
    }

    public void setSheared(boolean flag) {
        byte b0 = this.datawatcher.get(bC);
        if (flag) {
            this.datawatcher.set(bC, (byte)(b0 | 16));
        } else {
            this.datawatcher.set(bC, (byte)(b0 & -17));
        }

    }

    public static EnumColor a(Random random) {
        int i = random.nextInt(100);
        if (i < 5) {
            return EnumColor.BLACK;
        } else if (i < 10) {
            return EnumColor.GRAY;
        } else if (i < 15) {
            return EnumColor.LIGHT_GRAY;
        } else if (i < 18) {
            return EnumColor.BROWN;
        } else {
            return random.nextInt(500) == 0 ? EnumColor.PINK : EnumColor.WHITE;
        }
    }

    public EntitySheep b(EntityAgeable entityageable) {
        EntitySheep entitysheep1 = (EntitySheep)entityageable;
        EntitySheep entitysheep2 = new EntitySheep(this.world);
        entitysheep2.setColor(this.a(this, entitysheep1));
        return entitysheep2;
    }

    public void x() {
        this.setSheared(false);
        if (this.isBaby()) {
            this.setAge(60);
        }

    }

    @Nullable
    public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, @Nullable GroupDataEntity groupdataentity, @Nullable NBTTagCompound nbttagcompound) {
        groupdataentity = super.prepare(difficultydamagescaler, groupdataentity, nbttagcompound);
        this.setColor(a(this.world.random));
        return groupdataentity;
    }

    private EnumColor a(EntityAnimal entityanimal, EntityAnimal entityanimal1) {
        EnumColor enumcolor = ((EntitySheep)entityanimal).getColor();
        EnumColor enumcolor1 = ((EntitySheep)entityanimal1).getColor();
        this.container.setItem(0, new ItemStack(ItemDye.a(enumcolor)));
        this.container.setItem(1, new ItemStack(ItemDye.a(enumcolor1)));
        ItemStack itemstack = entityanimal.world.E().craft(this.container, ((EntitySheep)entityanimal).world);
        Item item = itemstack.getItem();
        EnumColor enumcolor2;
        if (item instanceof ItemDye) {
            enumcolor2 = ((ItemDye)item).d();
        } else {
            enumcolor2 = this.world.random.nextBoolean() ? enumcolor : enumcolor1;
        }

        return enumcolor2;
    }

    public float getHeadHeight() {
        return 0.95F * this.length;
    }

    // $FF: synthetic method
    public EntityAgeable createChild(EntityAgeable entityageable) {
        return this.b(entityageable);
    }
}
