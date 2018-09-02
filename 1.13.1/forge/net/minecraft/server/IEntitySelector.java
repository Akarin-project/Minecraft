package net.minecraft.server;

import com.google.common.base.Predicates;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public final class IEntitySelector {
    public static final Predicate<Entity> a = Entity::isAlive;
    public static final Predicate<EntityLiving> b = EntityLiving::isAlive;
    public static final Predicate<Entity> c = (entity) -> {
        return entity.isAlive() && !entity.isVehicle() && !entity.isPassenger();
    };
    public static final Predicate<Entity> d = (entity) -> {
        return entity instanceof IInventory && entity.isAlive();
    };
    public static final Predicate<Entity> e = (entity) -> {
        return !(entity instanceof EntityHuman) || !((EntityHuman)entity).isSpectator() && !((EntityHuman)entity).u();
    };
    public static final Predicate<Entity> f = (entity) -> {
        return !(entity instanceof EntityHuman) || !((EntityHuman)entity).isSpectator();
    };

    public static Predicate<Entity> a(double d0, double d1, double d2, double d3) {
        double d4 = d3 * d3;
        return (entity) -> {
            return entity != null && entity.d(d0, d1, d2) <= d4;
        };
    }

    public static Predicate<Entity> a(Entity entity) {
        ScoreboardTeamBase scoreboardteambase = entity.be();
        ScoreboardTeamBase.EnumTeamPush scoreboardteambase$enumteampush = scoreboardteambase == null ? ScoreboardTeamBase.EnumTeamPush.ALWAYS : scoreboardteambase.getCollisionRule();
        return (Predicate<Entity>)(scoreboardteambase$enumteampush == ScoreboardTeamBase.EnumTeamPush.NEVER ? Predicates.alwaysFalse() : f.and((entity2) -> {
            if (!entity2.isCollidable()) {
                return false;
            } else if (!entity.world.isClientSide || entity2 instanceof EntityHuman && ((EntityHuman)entity2).dn()) {
                ScoreboardTeamBase scoreboardteambase2 = entity2.be();
                ScoreboardTeamBase.EnumTeamPush scoreboardteambase$enumteampush2 = scoreboardteambase2 == null ? ScoreboardTeamBase.EnumTeamPush.ALWAYS : scoreboardteambase2.getCollisionRule();
                if (scoreboardteambase$enumteampush2 == ScoreboardTeamBase.EnumTeamPush.NEVER) {
                    return false;
                } else {
                    boolean flag = scoreboardteambase != null && scoreboardteambase.isAlly(scoreboardteambase2);
                    if ((scoreboardteambase$enumteampush == ScoreboardTeamBase.EnumTeamPush.PUSH_OWN_TEAM || scoreboardteambase$enumteampush2 == ScoreboardTeamBase.EnumTeamPush.PUSH_OWN_TEAM) && flag) {
                        return false;
                    } else {
                        return scoreboardteambase$enumteampush != ScoreboardTeamBase.EnumTeamPush.PUSH_OTHER_TEAMS && scoreboardteambase$enumteampush2 != ScoreboardTeamBase.EnumTeamPush.PUSH_OTHER_TEAMS || flag;
                    }
                }
            } else {
                return false;
            }
        }));
    }

    public static Predicate<Entity> b(Entity entity) {
        return (entity2) -> {
            while(true) {
                if (entity2.isPassenger()) {
                    entity2 = entity2.getVehicle();
                    if (entity2 != entity) {
                        continue;
                    }

                    return false;
                }

                return true;
            }
        };
    }

    public static class EntitySelectorEquipable implements Predicate<Entity> {
        private final ItemStack a;

        public EntitySelectorEquipable(ItemStack itemstack) {
            this.a = itemstack;
        }

        public boolean a(@Nullable Entity entity) {
            if (!entity.isAlive()) {
                return false;
            } else if (!(entity instanceof EntityLiving)) {
                return false;
            } else {
                EntityLiving entityliving = (EntityLiving)entity;
                EnumItemSlot enumitemslot = EntityInsentient.e(this.a);
                if (!entityliving.getEquipment(enumitemslot).isEmpty()) {
                    return false;
                } else if (entityliving instanceof EntityInsentient) {
                    return ((EntityInsentient)entityliving).dj();
                } else if (entityliving instanceof EntityArmorStand) {
                    return !((EntityArmorStand)entityliving).c(enumitemslot);
                } else {
                    return entityliving instanceof EntityHuman;
                }
            }
        }

        // $FF: synthetic method
        public boolean test(@Nullable Object object) {
            return this.a((Entity)object);
        }
    }
}
