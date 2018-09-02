package net.minecraft.server;

import java.util.List;
import javax.annotation.Nullable;

public class EntityGuardianElder extends EntityGuardian {
    public EntityGuardianElder(World world) {
        super(EntityTypes.ELDER_GUARDIAN, world);
        this.setSize(this.width * 2.35F, this.length * 2.35F);
        this.di();
        if (this.goalRandomStroll != null) {
            this.goalRandomStroll.setTimeBetweenMovement(400);
        }

    }

    public void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue((double)0.3F);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(8.0D);
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(80.0D);
    }

    @Nullable
    protected MinecraftKey getDefaultLootTable() {
        return LootTables.E;
    }

    public int l() {
        return 60;
    }

    protected SoundEffect D() {
        return this.aq() ? SoundEffects.ENTITY_ELDER_GUARDIAN_AMBIENT : SoundEffects.ENTITY_ELDER_GUARDIAN_AMBIENT_LAND;
    }

    protected SoundEffect d(DamageSource var1) {
        return this.aq() ? SoundEffects.ENTITY_ELDER_GUARDIAN_HURT : SoundEffects.ENTITY_ELDER_GUARDIAN_HURT_LAND;
    }

    protected SoundEffect cs() {
        return this.aq() ? SoundEffects.ENTITY_ELDER_GUARDIAN_DEATH : SoundEffects.ENTITY_ELDER_GUARDIAN_DEATH_LAND;
    }

    protected SoundEffect dA() {
        return SoundEffects.ENTITY_ELDER_GUARDIAN_FLOP;
    }

    protected void mobTick() {
        super.mobTick();
        boolean flag = true;
        if ((this.ticksLived + this.getId()) % 1200 == 0) {
            MobEffectList mobeffectlist = MobEffects.SLOWER_DIG;
            List list = this.world.b(EntityPlayer.class, (entityplayer1) -> {
                return this.h(entityplayer1) < 2500.0D && entityplayer1.playerInteractManager.c();
            });
            boolean flag1 = true;
            boolean flag2 = true;
            boolean flag3 = true;

            for(EntityPlayer entityplayer : list) {
                if (!entityplayer.hasEffect(mobeffectlist) || entityplayer.getEffect(mobeffectlist).getAmplifier() < 2 || entityplayer.getEffect(mobeffectlist).getDuration() < 1200) {
                    entityplayer.playerConnection.sendPacket(new PacketPlayOutGameStateChange(10, 0.0F));
                    entityplayer.addEffect(new MobEffect(mobeffectlist, 6000, 2));
                }
            }
        }

        if (!this.dw()) {
            this.a(new BlockPosition(this), 16);
        }

    }
}
