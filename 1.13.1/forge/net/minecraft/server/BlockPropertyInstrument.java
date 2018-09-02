package net.minecraft.server;

public enum BlockPropertyInstrument implements INamable {
    HARP("harp", SoundEffects.BLOCK_NOTE_BLOCK_HARP),
    BASEDRUM("basedrum", SoundEffects.BLOCK_NOTE_BLOCK_BASEDRUM),
    SNARE("snare", SoundEffects.BLOCK_NOTE_BLOCK_SNARE),
    HAT("hat", SoundEffects.BLOCK_NOTE_BLOCK_HAT),
    BASS("bass", SoundEffects.BLOCK_NOTE_BLOCK_BASS),
    FLUTE("flute", SoundEffects.BLOCK_NOTE_BLOCK_FLUTE),
    BELL("bell", SoundEffects.BLOCK_NOTE_BLOCK_BELL),
    GUITAR("guitar", SoundEffects.BLOCK_NOTE_BLOCK_GUITAR),
    CHIME("chime", SoundEffects.BLOCK_NOTE_BLOCK_CHIME),
    XYLOPHONE("xylophone", SoundEffects.BLOCK_NOTE_BLOCK_XYLOPHONE);

    private final String k;
    private final SoundEffect l;

    private BlockPropertyInstrument(String s1, SoundEffect soundeffect) {
        this.k = s1;
        this.l = soundeffect;
    }

    public String getName() {
        return this.k;
    }

    public SoundEffect a() {
        return this.l;
    }

    public static BlockPropertyInstrument a(IBlockData iblockdata) {
        Block block = iblockdata.getBlock();
        if (block == Blocks.CLAY) {
            return FLUTE;
        } else if (block == Blocks.GOLD_BLOCK) {
            return BELL;
        } else if (block.a(TagsBlock.WOOL)) {
            return GUITAR;
        } else if (block == Blocks.PACKED_ICE) {
            return CHIME;
        } else if (block == Blocks.BONE_BLOCK) {
            return XYLOPHONE;
        } else {
            Material material = iblockdata.getMaterial();
            if (material == Material.STONE) {
                return BASEDRUM;
            } else if (material == Material.SAND) {
                return SNARE;
            } else if (material == Material.SHATTERABLE) {
                return HAT;
            } else {
                return material == Material.WOOD ? BASS : HARP;
            }
        }
    }
}
