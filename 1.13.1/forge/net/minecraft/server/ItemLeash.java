package net.minecraft.server;

public class ItemLeash extends Item {
    public ItemLeash(Item.Info item$info) {
        super(item$info);
    }

    public EnumInteractionResult a(ItemActionContext itemactioncontext) {
        World world = itemactioncontext.getWorld();
        BlockPosition blockposition = itemactioncontext.getClickPosition();
        Block block = world.getType(blockposition).getBlock();
        if (block instanceof BlockFence) {
            EntityHuman entityhuman = itemactioncontext.getEntity();
            if (!world.isClientSide && entityhuman != null) {
                a(entityhuman, world, blockposition);
            }

            return EnumInteractionResult.SUCCESS;
        } else {
            return EnumInteractionResult.PASS;
        }
    }

    public static boolean a(EntityHuman entityhuman, World world, BlockPosition blockposition) {
        EntityLeash entityleash = EntityLeash.b(world, blockposition);
        boolean flag = false;
        double d0 = 7.0D;
        int i = blockposition.getX();
        int j = blockposition.getY();
        int k = blockposition.getZ();

        for(EntityInsentient entityinsentient : world.a(EntityInsentient.class, new AxisAlignedBB((double)i - 7.0D, (double)j - 7.0D, (double)k - 7.0D, (double)i + 7.0D, (double)j + 7.0D, (double)k + 7.0D))) {
            if (entityinsentient.isLeashed() && entityinsentient.getLeashHolder() == entityhuman) {
                if (entityleash == null) {
                    entityleash = EntityLeash.a(world, blockposition);
                }

                entityinsentient.setLeashHolder(entityleash, true);
                flag = true;
            }
        }

        return flag;
    }
}
