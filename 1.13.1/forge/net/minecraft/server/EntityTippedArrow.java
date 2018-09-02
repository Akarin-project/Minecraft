package net.minecraft.server;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;

public class EntityTippedArrow extends EntityArrow {
    private static final DataWatcherObject<Integer> g = DataWatcher.a(EntityTippedArrow.class, DataWatcherRegistry.b);
    private PotionRegistry potionRegistry = Potions.EMPTY;
    public final Set<MobEffect> effects = Sets.newHashSet();
    private boolean hasColor;

    public EntityTippedArrow(World world) {
        super(EntityTypes.ARROW, world);
    }

    public EntityTippedArrow(World world, double d0, double d1, double d2) {
        super(EntityTypes.ARROW, d0, d1, d2, world);
    }

    public EntityTippedArrow(World world, EntityLiving entityliving) {
        super(EntityTypes.ARROW, entityliving, world);
    }

    public void b(ItemStack itemstack) {
        if (itemstack.getItem() == Items.TIPPED_ARROW) {
            this.potionRegistry = PotionUtil.d(itemstack);
            List list = PotionUtil.b(itemstack);
            if (!list.isEmpty()) {
                for(MobEffect mobeffect : list) {
                    this.effects.add(new MobEffect(mobeffect));
                }
            }

            int i = c(itemstack);
            if (i == -1) {
                this.s();
            } else {
                this.setColor(i);
            }
        } else if (itemstack.getItem() == Items.ARROW) {
            this.potionRegistry = Potions.EMPTY;
            this.effects.clear();
            this.datawatcher.set(g, -1);
        }

    }

    public static int c(ItemStack itemstack) {
        NBTTagCompound nbttagcompound = itemstack.getTag();
        return nbttagcompound != null && nbttagcompound.hasKeyOfType("CustomPotionColor", 99) ? nbttagcompound.getInt("CustomPotionColor") : -1;
    }

    private void s() {
        this.hasColor = false;
        this.datawatcher.set(g, PotionUtil.a(PotionUtil.a(this.potionRegistry, this.effects)));
    }

    public void a(MobEffect mobeffect) {
        this.effects.add(mobeffect);
        this.getDataWatcher().set(g, PotionUtil.a(PotionUtil.a(this.potionRegistry, this.effects)));
    }

    protected void x_() {
        super.x_();
        this.datawatcher.register(g, -1);
    }

    public void tick() {
        super.tick();
        if (this.world.isClientSide) {
            if (this.inGround) {
                if (this.c % 5 == 0) {
                    this.b(1);
                }
            } else {
                this.b(2);
            }
        } else if (this.inGround && this.c != 0 && !this.effects.isEmpty() && this.c >= 600) {
            this.world.broadcastEntityEffect(this, (byte)0);
            this.potionRegistry = Potions.EMPTY;
            this.effects.clear();
            this.datawatcher.set(g, -1);
        }

    }

    private void b(int i) {
        int j = this.getColor();
        if (j != -1 && i > 0) {
            double d0 = (double)(j >> 16 & 255) / 255.0D;
            double d1 = (double)(j >> 8 & 255) / 255.0D;
            double d2 = (double)(j >> 0 & 255) / 255.0D;

            for(int k = 0; k < i; ++k) {
                this.world.addParticle(Particles.s, this.locX + (this.random.nextDouble() - 0.5D) * (double)this.width, this.locY + this.random.nextDouble() * (double)this.length, this.locZ + (this.random.nextDouble() - 0.5D) * (double)this.width, d0, d1, d2);
            }

        }
    }

    public int getColor() {
        return this.datawatcher.get(g);
    }

    public void setColor(int i) {
        this.hasColor = true;
        this.datawatcher.set(g, i);
    }

    public void b(NBTTagCompound nbttagcompound) {
        super.b(nbttagcompound);
        if (this.potionRegistry != Potions.EMPTY && this.potionRegistry != null) {
            nbttagcompound.setString("Potion", IRegistry.POTION.getKey(this.potionRegistry).toString());
        }

        if (this.hasColor) {
            nbttagcompound.setInt("Color", this.getColor());
        }

        if (!this.effects.isEmpty()) {
            NBTTagList nbttaglist = new NBTTagList();

            for(MobEffect mobeffect : this.effects) {
                nbttaglist.add((NBTBase)mobeffect.a(new NBTTagCompound()));
            }

            nbttagcompound.set("CustomPotionEffects", nbttaglist);
        }

    }

    public void a(NBTTagCompound nbttagcompound) {
        super.a(nbttagcompound);
        if (nbttagcompound.hasKeyOfType("Potion", 8)) {
            this.potionRegistry = PotionUtil.c(nbttagcompound);
        }

        for(MobEffect mobeffect : PotionUtil.b(nbttagcompound)) {
            this.a(mobeffect);
        }

        if (nbttagcompound.hasKeyOfType("Color", 99)) {
            this.setColor(nbttagcompound.getInt("Color"));
        } else {
            this.s();
        }

    }

    protected void a(EntityLiving entityliving) {
        super.a(entityliving);

        for(MobEffect mobeffect : this.potionRegistry.a()) {
            entityliving.addEffect(new MobEffect(mobeffect.getMobEffect(), Math.max(mobeffect.getDuration() / 8, 1), mobeffect.getAmplifier(), mobeffect.isAmbient(), mobeffect.isShowParticles()));
        }

        if (!this.effects.isEmpty()) {
            for(MobEffect mobeffect1 : this.effects) {
                entityliving.addEffect(mobeffect1);
            }
        }

    }

    protected ItemStack getItemStack() {
        if (this.effects.isEmpty() && this.potionRegistry == Potions.EMPTY) {
            return new ItemStack(Items.ARROW);
        } else {
            ItemStack itemstack = new ItemStack(Items.TIPPED_ARROW);
            PotionUtil.a(itemstack, this.potionRegistry);
            PotionUtil.a(itemstack, this.effects);
            if (this.hasColor) {
                itemstack.getOrCreateTag().setInt("CustomPotionColor", this.getColor());
            }

            return itemstack;
        }
    }
}
