package net.minecraft.server;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;

public class EntityPositionTypes {
    private static final Map<EntityTypes<?>, EntityPositionTypes.a> a = Maps.newHashMap();

    private static void a(EntityTypes<?> entitytypes, EntityPositionTypes.Surface entitypositiontypes$surface, HeightMap.Type heightmap$type) {
        a(entitytypes, entitypositiontypes$surface, heightmap$type, (Tag)null);
    }

    private static void a(EntityTypes<?> entitytypes, EntityPositionTypes.Surface entitypositiontypes$surface, HeightMap.Type heightmap$type, @Nullable Tag<Block> tag) {
        a.put(entitytypes, new EntityPositionTypes.a(heightmap$type, entitypositiontypes$surface, tag));
    }

    @Nullable
    public static EntityPositionTypes.Surface a(EntityTypes<? extends EntityInsentient> entitytypes) {
        EntityPositionTypes.a entitypositiontypes$a = (EntityPositionTypes.a)a.get(entitytypes);
        return entitypositiontypes$a == null ? null : entitypositiontypes$a.b;
    }

    public static HeightMap.Type b(@Nullable EntityTypes<? extends EntityInsentient> entitytypes) {
        EntityPositionTypes.a entitypositiontypes$a = (EntityPositionTypes.a)a.get(entitytypes);
        return entitypositiontypes$a == null ? HeightMap.Type.MOTION_BLOCKING_NO_LEAVES : entitypositiontypes$a.a;
    }

    public static boolean a(EntityTypes<? extends EntityInsentient> entitytypes, IBlockData iblockdata) {
        EntityPositionTypes.a entitypositiontypes$a = (EntityPositionTypes.a)a.get(entitytypes);
        if (entitypositiontypes$a == null) {
            return false;
        } else {
            return entitypositiontypes$a.c != null && iblockdata.a(entitypositiontypes$a.c);
        }
    }

    static {
        a(EntityTypes.COD, EntityPositionTypes.Surface.IN_WATER, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.DOLPHIN, EntityPositionTypes.Surface.IN_WATER, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.DROWNED, EntityPositionTypes.Surface.IN_WATER, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.GUARDIAN, EntityPositionTypes.Surface.IN_WATER, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.PUFFERFISH, EntityPositionTypes.Surface.IN_WATER, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.SALMON, EntityPositionTypes.Surface.IN_WATER, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.SQUID, EntityPositionTypes.Surface.IN_WATER, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.TROPICAL_FISH, EntityPositionTypes.Surface.IN_WATER, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.OCELOT, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING, TagsBlock.LEAVES);
        a(EntityTypes.PARROT, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING, TagsBlock.LEAVES);
        a(EntityTypes.POLAR_BEAR, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES, TagsBlock.ICE);
        a(EntityTypes.BAT, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.BLAZE, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.CAVE_SPIDER, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.CHICKEN, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.COW, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.CREEPER, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.DONKEY, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.ENDERMAN, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.ENDERMITE, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.ENDER_DRAGON, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.GHAST, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.GIANT, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.HORSE, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.HUSK, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.LLAMA, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.MAGMA_CUBE, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.MOOSHROOM, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.MULE, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.PIG, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.RABBIT, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.SHEEP, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.SILVERFISH, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.SKELETON, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.SKELETON_HORSE, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.SLIME, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.SNOW_GOLEM, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.SPIDER, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.STRAY, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.TURTLE, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.VILLAGER, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.IRON_GOLEM, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.WITCH, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.WITHER, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.WITHER_SKELETON, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.WOLF, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.ZOMBIE, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.ZOMBIE_HORSE, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.ZOMBIE_PIGMAN, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
        a(EntityTypes.ZOMBIE_VILLAGER, EntityPositionTypes.Surface.ON_GROUND, HeightMap.Type.MOTION_BLOCKING_NO_LEAVES);
    }

    public static enum Surface {
        ON_GROUND,
        IN_WATER;

        private Surface() {
        }
    }

    static class a {
        private final HeightMap.Type a;
        private final EntityPositionTypes.Surface b;
        @Nullable
        private final Tag<Block> c;

        public a(HeightMap.Type heightmap$type, EntityPositionTypes.Surface entitypositiontypes$surface, @Nullable Tag<Block> tag) {
            this.a = heightmap$type;
            this.b = entitypositiontypes$surface;
            this.c = tag;
        }
    }
}
