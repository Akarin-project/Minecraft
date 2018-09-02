package net.minecraft.server;

import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlockCommand extends BlockTileEntity {
    private static final Logger c = LogManager.getLogger();
    public static final BlockStateDirection a = BlockDirectional.FACING;
    public static final BlockStateBoolean b = BlockProperties.b;

    public BlockCommand(Block.Info block$info) {
        super(block$info);
        this.v((IBlockData)((IBlockData)(this.blockStateList.getBlockData()).set(a, EnumDirection.NORTH)).set(b, Boolean.valueOf(false)));
    }

    public TileEntity a(IBlockAccess var1) {
        TileEntityCommand tileentitycommand = new TileEntityCommand();
        tileentitycommand.b(this == Blocks.CHAIN_COMMAND_BLOCK);
        return tileentitycommand;
    }

    public void doPhysics(IBlockData var1, World world, BlockPosition blockposition, Block var4, BlockPosition var5) {
        if (!world.isClientSide) {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof TileEntityCommand) {
                TileEntityCommand tileentitycommand = (TileEntityCommand)tileentity;
                boolean flag = world.isBlockIndirectlyPowered(blockposition);
                boolean flag1 = tileentitycommand.d();
                tileentitycommand.a(flag);
                if (!flag1 && !tileentitycommand.e() && tileentitycommand.j() != TileEntityCommand.Type.SEQUENCE) {
                    if (flag) {
                        tileentitycommand.h();
                        world.J().a(blockposition, this, this.a((IWorldReader)world));
                    }

                }
            }
        }
    }

    public void a(IBlockData iblockdata, World world, BlockPosition blockposition, Random var4) {
        if (!world.isClientSide) {
            TileEntity tileentity = world.getTileEntity(blockposition);
            if (tileentity instanceof TileEntityCommand) {
                TileEntityCommand tileentitycommand = (TileEntityCommand)tileentity;
                CommandBlockListenerAbstract commandblocklistenerabstract = tileentitycommand.getCommandBlock();
                boolean flag = !UtilColor.b(commandblocklistenerabstract.getCommand());
                TileEntityCommand.Type tileentitycommand$type = tileentitycommand.j();
                boolean flag1 = tileentitycommand.f();
                if (tileentitycommand$type == TileEntityCommand.Type.AUTO) {
                    tileentitycommand.h();
                    if (flag1) {
                        this.a(iblockdata, world, blockposition, commandblocklistenerabstract, flag);
                    } else if (tileentitycommand.k()) {
                        commandblocklistenerabstract.a(0);
                    }

                    if (tileentitycommand.d() || tileentitycommand.e()) {
                        world.J().a(blockposition, this, this.a((IWorldReader)world));
                    }
                } else if (tileentitycommand$type == TileEntityCommand.Type.REDSTONE) {
                    if (flag1) {
                        this.a(iblockdata, world, blockposition, commandblocklistenerabstract, flag);
                    } else if (tileentitycommand.k()) {
                        commandblocklistenerabstract.a(0);
                    }
                }

                world.updateAdjacentComparators(blockposition, this);
            }

        }
    }

    private void a(IBlockData iblockdata, World world, BlockPosition blockposition, CommandBlockListenerAbstract commandblocklistenerabstract, boolean flag) {
        if (flag) {
            commandblocklistenerabstract.a(world);
        } else {
            commandblocklistenerabstract.a(0);
        }

        a(world, blockposition, (EnumDirection)iblockdata.get(a));
    }

    public int a(IWorldReader var1) {
        return 1;
    }

    public boolean interact(IBlockData var1, World world, BlockPosition blockposition, EntityHuman entityhuman, EnumHand var5, EnumDirection var6, float var7, float var8, float var9) {
        TileEntity tileentity = world.getTileEntity(blockposition);
        if (tileentity instanceof TileEntityCommand && entityhuman.isCreativeAndOp()) {
            entityhuman.a((TileEntityCommand)tileentity);
            return true;
        } else {
            return false;
        }
    }

    public boolean isComplexRedstone(IBlockData var1) {
        return true;
    }

    public int a(IBlockData var1, World world, BlockPosition blockposition) {
        TileEntity tileentity = world.getTileEntity(blockposition);
        return tileentity instanceof TileEntityCommand ? ((TileEntityCommand)tileentity).getCommandBlock().i() : 0;
    }

    public void postPlace(World world, BlockPosition blockposition, IBlockData var3, EntityLiving var4, ItemStack itemstack) {
        TileEntity tileentity = world.getTileEntity(blockposition);
        if (tileentity instanceof TileEntityCommand) {
            TileEntityCommand tileentitycommand = (TileEntityCommand)tileentity;
            CommandBlockListenerAbstract commandblocklistenerabstract = tileentitycommand.getCommandBlock();
            if (itemstack.hasName()) {
                commandblocklistenerabstract.setName(itemstack.getName());
            }

            if (!world.isClientSide) {
                if (itemstack.b("BlockEntityTag") == null) {
                    commandblocklistenerabstract.a(world.getGameRules().getBoolean("sendCommandFeedback"));
                    tileentitycommand.b(this == Blocks.CHAIN_COMMAND_BLOCK);
                }

                if (tileentitycommand.j() == TileEntityCommand.Type.SEQUENCE) {
                    boolean flag = world.isBlockIndirectlyPowered(blockposition);
                    tileentitycommand.a(flag);
                }
            }

        }
    }

    public int a(IBlockData var1, Random var2) {
        return 0;
    }

    public EnumRenderType c(IBlockData var1) {
        return EnumRenderType.MODEL;
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockRotation enumblockrotation) {
        return (IBlockData)iblockdata.set(a, enumblockrotation.a((EnumDirection)iblockdata.get(a)));
    }

    public IBlockData a(IBlockData iblockdata, EnumBlockMirror enumblockmirror) {
        return iblockdata.a(enumblockmirror.a((EnumDirection)iblockdata.get(a)));
    }

    protected void a(BlockStateList.a<Block, IBlockData> blockstatelist$a) {
        blockstatelist$a.a(a, b);
    }

    public IBlockData getPlacedState(BlockActionContext blockactioncontext) {
        return (IBlockData)this.getBlockData().set(a, blockactioncontext.d().opposite());
    }

    private static void a(World world, BlockPosition blockposition, EnumDirection enumdirection) {
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition(blockposition);
        GameRules gamerules = world.getGameRules();

        int i;
        IBlockData iblockdata;
        for(i = gamerules.c("maxCommandChainLength"); i-- > 0; enumdirection = (EnumDirection)iblockdata.get(a)) {
            blockposition$mutableblockposition.c(enumdirection);
            iblockdata = world.getType(blockposition$mutableblockposition);
            Block block = iblockdata.getBlock();
            if (block != Blocks.CHAIN_COMMAND_BLOCK) {
                break;
            }

            TileEntity tileentity = world.getTileEntity(blockposition$mutableblockposition);
            if (!(tileentity instanceof TileEntityCommand)) {
                break;
            }

            TileEntityCommand tileentitycommand = (TileEntityCommand)tileentity;
            if (tileentitycommand.j() != TileEntityCommand.Type.SEQUENCE) {
                break;
            }

            if (tileentitycommand.d() || tileentitycommand.e()) {
                CommandBlockListenerAbstract commandblocklistenerabstract = tileentitycommand.getCommandBlock();
                if (tileentitycommand.h()) {
                    if (!commandblocklistenerabstract.a(world)) {
                        break;
                    }

                    world.updateAdjacentComparators(blockposition$mutableblockposition, block);
                } else if (tileentitycommand.k()) {
                    commandblocklistenerabstract.a(0);
                }
            }
        }

        if (i <= 0) {
            int j = Math.max(gamerules.c("maxCommandChainLength"), 0);
            c.warn("Command Block chain tried to execute more than {} steps!", j);
        }

    }
}
