package net.minecraft.server;

public class BlockStoneButton extends BlockButtonAbstract {
    protected BlockStoneButton(Block.Info block$info) {
        super(false, block$info);
    }

    protected SoundEffect a(boolean flag) {
        return flag ? SoundEffects.BLOCK_STONE_BUTTON_CLICK_ON : SoundEffects.BLOCK_STONE_BUTTON_CLICK_OFF;
    }
}