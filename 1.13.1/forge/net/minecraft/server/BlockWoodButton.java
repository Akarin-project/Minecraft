package net.minecraft.server;

public class BlockWoodButton extends BlockButtonAbstract {
    protected BlockWoodButton(Block.Info block$info) {
        super(true, block$info);
    }

    protected SoundEffect a(boolean flag) {
        return flag ? SoundEffects.BLOCK_WOODEN_BUTTON_CLICK_ON : SoundEffects.BLOCK_WOODEN_BUTTON_CLICK_OFF;
    }
}
