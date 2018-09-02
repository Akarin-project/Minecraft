package net.minecraft.server;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;

public class CriterionTriggers {
    private static final Map<MinecraftKey, CriterionTrigger<?>> F = Maps.newHashMap();
    public static final CriterionTriggerImpossible a = (CriterionTriggerImpossible)a(new CriterionTriggerImpossible());
    public static final CriterionTriggerKilled b = (CriterionTriggerKilled)a(new CriterionTriggerKilled(new MinecraftKey("player_killed_entity")));
    public static final CriterionTriggerKilled c = (CriterionTriggerKilled)a(new CriterionTriggerKilled(new MinecraftKey("entity_killed_player")));
    public static final CriterionTriggerEnterBlock d = (CriterionTriggerEnterBlock)a(new CriterionTriggerEnterBlock());
    public static final CriterionTriggerInventoryChanged e = (CriterionTriggerInventoryChanged)a(new CriterionTriggerInventoryChanged());
    public static final CriterionTriggerRecipeUnlocked f = (CriterionTriggerRecipeUnlocked)a(new CriterionTriggerRecipeUnlocked());
    public static final CriterionTriggerPlayerHurtEntity g = (CriterionTriggerPlayerHurtEntity)a(new CriterionTriggerPlayerHurtEntity());
    public static final CriterionTriggerEntityHurtPlayer h = (CriterionTriggerEntityHurtPlayer)a(new CriterionTriggerEntityHurtPlayer());
    public static final CriterionTriggerEnchantedItem i = (CriterionTriggerEnchantedItem)a(new CriterionTriggerEnchantedItem());
    public static final CriterionTriggerFilledBucket j = (CriterionTriggerFilledBucket)a(new CriterionTriggerFilledBucket());
    public static final CriterionTriggerBrewedPotion k = (CriterionTriggerBrewedPotion)a(new CriterionTriggerBrewedPotion());
    public static final CriterionTriggerConstructBeacon l = (CriterionTriggerConstructBeacon)a(new CriterionTriggerConstructBeacon());
    public static final CriterionTriggerUsedEnderEye m = (CriterionTriggerUsedEnderEye)a(new CriterionTriggerUsedEnderEye());
    public static final CriterionTriggerSummonedEntity n = (CriterionTriggerSummonedEntity)a(new CriterionTriggerSummonedEntity());
    public static final CriterionTriggerBredAnimals o = (CriterionTriggerBredAnimals)a(new CriterionTriggerBredAnimals());
    public static final CriterionTriggerLocation p = (CriterionTriggerLocation)a(new CriterionTriggerLocation(new MinecraftKey("location")));
    public static final CriterionTriggerLocation q = (CriterionTriggerLocation)a(new CriterionTriggerLocation(new MinecraftKey("slept_in_bed")));
    public static final CriterionTriggerCuredZombieVillager r = (CriterionTriggerCuredZombieVillager)a(new CriterionTriggerCuredZombieVillager());
    public static final CriterionTriggerVillagerTrade s = (CriterionTriggerVillagerTrade)a(new CriterionTriggerVillagerTrade());
    public static final CriterionTriggerItemDurabilityChanged t = (CriterionTriggerItemDurabilityChanged)a(new CriterionTriggerItemDurabilityChanged());
    public static final CriterionTriggerLevitation u = (CriterionTriggerLevitation)a(new CriterionTriggerLevitation());
    public static final CriterionTriggerChangedDimension v = (CriterionTriggerChangedDimension)a(new CriterionTriggerChangedDimension());
    public static final CriterionTriggerTick w = (CriterionTriggerTick)a(new CriterionTriggerTick());
    public static final CriterionTriggerTamedAnimal x = (CriterionTriggerTamedAnimal)a(new CriterionTriggerTamedAnimal());
    public static final CriterionTriggerPlacedBlock y = (CriterionTriggerPlacedBlock)a(new CriterionTriggerPlacedBlock());
    public static final CriterionTriggerConsumeItem z = (CriterionTriggerConsumeItem)a(new CriterionTriggerConsumeItem());
    public static final CriterionTriggerEffectsChanged A = (CriterionTriggerEffectsChanged)a(new CriterionTriggerEffectsChanged());
    public static final CriterionTriggerUsedTotem B = (CriterionTriggerUsedTotem)a(new CriterionTriggerUsedTotem());
    public static final CriterionTriggerNetherTravel C = (CriterionTriggerNetherTravel)a(new CriterionTriggerNetherTravel());
    public static final CriterionTriggerFishingRodHooked D = (CriterionTriggerFishingRodHooked)a(new CriterionTriggerFishingRodHooked());
    public static final CriterionTriggerChanneledLightning E = (CriterionTriggerChanneledLightning)a(new CriterionTriggerChanneledLightning());

    private static <T extends CriterionTrigger<?>> T a(T criteriontrigger) {
        if (F.containsKey(criteriontrigger.a())) {
            throw new IllegalArgumentException("Duplicate criterion id " + criteriontrigger.a());
        } else {
            F.put(criteriontrigger.a(), criteriontrigger);
            return (T)criteriontrigger;
        }
    }

    @Nullable
    public static <T extends CriterionInstance> CriterionTrigger<T> a(MinecraftKey minecraftkey) {
        return (CriterionTrigger)F.get(minecraftkey);
    }

    public static Iterable<? extends CriterionTrigger<?>> a() {
        return F.values();
    }
}
