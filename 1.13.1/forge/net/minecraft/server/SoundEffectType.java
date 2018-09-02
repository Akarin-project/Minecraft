package net.minecraft.server;

public class SoundEffectType {
    public static final SoundEffectType a = new SoundEffectType(1.0F, 1.0F, SoundEffects.BLOCK_WOOD_BREAK, SoundEffects.BLOCK_WOOD_STEP, SoundEffects.BLOCK_WOOD_PLACE, SoundEffects.BLOCK_WOOD_HIT, SoundEffects.BLOCK_WOOD_FALL);
    public static final SoundEffectType b = new SoundEffectType(1.0F, 1.0F, SoundEffects.BLOCK_GRAVEL_BREAK, SoundEffects.BLOCK_GRAVEL_STEP, SoundEffects.BLOCK_GRAVEL_PLACE, SoundEffects.BLOCK_GRAVEL_HIT, SoundEffects.BLOCK_GRAVEL_FALL);
    public static final SoundEffectType c = new SoundEffectType(1.0F, 1.0F, SoundEffects.BLOCK_GRASS_BREAK, SoundEffects.BLOCK_GRASS_STEP, SoundEffects.BLOCK_GRASS_PLACE, SoundEffects.BLOCK_GRASS_HIT, SoundEffects.BLOCK_GRASS_FALL);
    public static final SoundEffectType d = new SoundEffectType(1.0F, 1.0F, SoundEffects.BLOCK_STONE_BREAK, SoundEffects.BLOCK_STONE_STEP, SoundEffects.BLOCK_STONE_PLACE, SoundEffects.BLOCK_STONE_HIT, SoundEffects.BLOCK_STONE_FALL);
    public static final SoundEffectType e = new SoundEffectType(1.0F, 1.5F, SoundEffects.BLOCK_METAL_BREAK, SoundEffects.BLOCK_METAL_STEP, SoundEffects.BLOCK_METAL_PLACE, SoundEffects.BLOCK_METAL_HIT, SoundEffects.BLOCK_METAL_FALL);
    public static final SoundEffectType f = new SoundEffectType(1.0F, 1.0F, SoundEffects.BLOCK_GLASS_BREAK, SoundEffects.BLOCK_GLASS_STEP, SoundEffects.BLOCK_GLASS_PLACE, SoundEffects.BLOCK_GLASS_HIT, SoundEffects.BLOCK_GLASS_FALL);
    public static final SoundEffectType g = new SoundEffectType(1.0F, 1.0F, SoundEffects.BLOCK_WOOL_BREAK, SoundEffects.BLOCK_WOOL_STEP, SoundEffects.BLOCK_WOOL_PLACE, SoundEffects.BLOCK_WOOL_HIT, SoundEffects.BLOCK_WOOL_FALL);
    public static final SoundEffectType h = new SoundEffectType(1.0F, 1.0F, SoundEffects.BLOCK_SAND_BREAK, SoundEffects.BLOCK_SAND_STEP, SoundEffects.BLOCK_SAND_PLACE, SoundEffects.BLOCK_SAND_HIT, SoundEffects.BLOCK_SAND_FALL);
    public static final SoundEffectType i = new SoundEffectType(1.0F, 1.0F, SoundEffects.BLOCK_SNOW_BREAK, SoundEffects.BLOCK_SNOW_STEP, SoundEffects.BLOCK_SNOW_PLACE, SoundEffects.BLOCK_SNOW_HIT, SoundEffects.BLOCK_SNOW_FALL);
    public static final SoundEffectType j = new SoundEffectType(1.0F, 1.0F, SoundEffects.BLOCK_LADDER_BREAK, SoundEffects.BLOCK_LADDER_STEP, SoundEffects.BLOCK_LADDER_PLACE, SoundEffects.BLOCK_LADDER_HIT, SoundEffects.BLOCK_LADDER_FALL);
    public static final SoundEffectType k = new SoundEffectType(0.3F, 1.0F, SoundEffects.BLOCK_ANVIL_BREAK, SoundEffects.BLOCK_ANVIL_STEP, SoundEffects.BLOCK_ANVIL_PLACE, SoundEffects.BLOCK_ANVIL_HIT, SoundEffects.BLOCK_ANVIL_FALL);
    public static final SoundEffectType l = new SoundEffectType(1.0F, 1.0F, SoundEffects.BLOCK_SLIME_BLOCK_BREAK, SoundEffects.BLOCK_SLIME_BLOCK_STEP, SoundEffects.BLOCK_SLIME_BLOCK_PLACE, SoundEffects.BLOCK_SLIME_BLOCK_HIT, SoundEffects.BLOCK_SLIME_BLOCK_FALL);
    public static final SoundEffectType m = new SoundEffectType(1.0F, 1.0F, SoundEffects.BLOCK_WET_GRASS_BREAK, SoundEffects.BLOCK_WET_GRASS_STEP, SoundEffects.BLOCK_WET_GRASS_PLACE, SoundEffects.BLOCK_WET_GRASS_HIT, SoundEffects.BLOCK_WET_GRASS_FALL);
    public static final SoundEffectType n = new SoundEffectType(1.0F, 1.0F, SoundEffects.BLOCK_CORAL_BLOCK_BREAK, SoundEffects.BLOCK_CORAL_BLOCK_STEP, SoundEffects.BLOCK_CORAL_BLOCK_PLACE, SoundEffects.BLOCK_CORAL_BLOCK_HIT, SoundEffects.BLOCK_CORAL_BLOCK_FALL);
    public final float o;
    public final float p;
    private final SoundEffect q;
    private final SoundEffect r;
    private final SoundEffect s;
    private final SoundEffect t;
    private final SoundEffect u;

    public SoundEffectType(float fx, float f1, SoundEffect soundeffect, SoundEffect soundeffect1, SoundEffect soundeffect2, SoundEffect soundeffect3, SoundEffect soundeffect4) {
        this.o = fx;
        this.p = f1;
        this.q = soundeffect;
        this.r = soundeffect1;
        this.s = soundeffect2;
        this.t = soundeffect3;
        this.u = soundeffect4;
    }

    public float a() {
        return this.o;
    }

    public float b() {
        return this.p;
    }

    public SoundEffect d() {
        return this.r;
    }

    public SoundEffect e() {
        return this.s;
    }

    public SoundEffect g() {
        return this.u;
    }
}
