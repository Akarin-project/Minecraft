package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import javax.annotation.Nullable;

public class EntityPainting extends EntityHanging {
    public Paintings art;

    public EntityPainting(World world) {
        super(EntityTypes.PAINTING, world);
    }

    public EntityPainting(World world, BlockPosition blockposition, EnumDirection enumdirection) {
        super(EntityTypes.PAINTING, world, blockposition);
        ArrayList arraylist = Lists.newArrayList();
        int i = 0;

        for(Paintings paintings : IRegistry.MOTIVE) {
            this.art = paintings;
            this.setDirection(enumdirection);
            if (this.survives()) {
                arraylist.add(paintings);
                int j = paintings.b() * paintings.c();
                if (j > i) {
                    i = j;
                }
            }
        }

        if (!arraylist.isEmpty()) {
            Iterator iterator = arraylist.iterator();

            while(iterator.hasNext()) {
                Paintings paintings1 = (Paintings)iterator.next();
                if (paintings1.b() * paintings1.c() < i) {
                    iterator.remove();
                }
            }

            this.art = (Paintings)arraylist.get(this.random.nextInt(arraylist.size()));
        }

        this.setDirection(enumdirection);
    }

    public void b(NBTTagCompound nbttagcompound) {
        nbttagcompound.setString("Motive", IRegistry.MOTIVE.getKey(this.art).toString());
        super.b(nbttagcompound);
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.art = IRegistry.MOTIVE.getOrDefault(MinecraftKey.a(nbttagcompound.getString("Motive")));
        super.a(nbttagcompound);
    }

    public int getWidth() {
        return this.art.b();
    }

    public int getHeight() {
        return this.art.c();
    }

    public void a(@Nullable Entity entity) {
        if (this.world.getGameRules().getBoolean("doEntityDrops")) {
            this.a(SoundEffects.ENTITY_PAINTING_BREAK, 1.0F, 1.0F);
            if (entity instanceof EntityHuman) {
                EntityHuman entityhuman = (EntityHuman)entity;
                if (entityhuman.abilities.canInstantlyBuild) {
                    return;
                }
            }

            this.a(Items.PAINTING);
        }
    }

    public void m() {
        this.a(SoundEffects.ENTITY_PAINTING_PLACE, 1.0F, 1.0F);
    }

    public void setPositionRotation(double d0, double d1, double d2, float var7, float var8) {
        this.setPosition(d0, d1, d2);
    }
}
