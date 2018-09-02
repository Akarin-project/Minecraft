package net.minecraft.server;

public abstract class BlockStemmed extends Block {
    public BlockStemmed(Block.Info block$info) {
        super(block$info);
    }

    public abstract BlockStem d();

    public abstract BlockStemAttached e();
}
