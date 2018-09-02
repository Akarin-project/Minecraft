package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

public class PersistentVillage extends PersistentBase {
    private World world;
    private final List<BlockPosition> b = Lists.newArrayList();
    private final List<VillageDoor> c = Lists.newArrayList();
    private final List<Village> villages = Lists.newArrayList();
    private int time;

    public PersistentVillage(String s) {
        super(s);
    }

    public PersistentVillage(World worldx) {
        super(a(worldx.worldProvider));
        this.world = worldx;
        this.c();
    }

    public void a(World worldx) {
        this.world = worldx;

        for(Village village : this.villages) {
            village.a(worldx);
        }

    }

    public void a(BlockPosition blockposition) {
        if (this.b.size() <= 64) {
            if (!this.d(blockposition)) {
                this.b.add(blockposition);
            }

        }
    }

    public void tick() {
        ++this.time;

        for(Village village : this.villages) {
            village.a(this.time);
        }

        this.f();
        this.g();
        this.h();
        if (this.time % 400 == 0) {
            this.c();
        }

    }

    private void f() {
        Iterator iterator = this.villages.iterator();

        while(iterator.hasNext()) {
            Village village = (Village)iterator.next();
            if (village.g()) {
                iterator.remove();
                this.c();
            }
        }

    }

    public List<Village> getVillages() {
        return this.villages;
    }

    public Village getClosestVillage(BlockPosition blockposition, int i) {
        Village village = null;
        double d0 = (double)Float.MAX_VALUE;

        for(Village village1 : this.villages) {
            double d1 = village1.a().n(blockposition);
            if (!(d1 >= d0)) {
                float f = (float)(i + village1.b());
                if (!(d1 > (double)(f * f))) {
                    village = village1;
                    d0 = d1;
                }
            }
        }

        return village;
    }

    private void g() {
        if (!this.b.isEmpty()) {
            this.b((BlockPosition)this.b.remove(0));
        }
    }

    private void h() {
        for(int i = 0; i < this.c.size(); ++i) {
            VillageDoor villagedoor = (VillageDoor)this.c.get(i);
            Village village = this.getClosestVillage(villagedoor.d(), 32);
            if (village == null) {
                village = new Village(this.world);
                this.villages.add(village);
                this.c();
            }

            village.a(villagedoor);
        }

        this.c.clear();
    }

    private void b(BlockPosition blockposition) {
        boolean flag = true;
        boolean flag1 = true;
        boolean flag2 = true;
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

        for(int i = -16; i < 16; ++i) {
            for(int j = -4; j < 4; ++j) {
                for(int k = -16; k < 16; ++k) {
                    blockposition$mutableblockposition.g(blockposition).d(i, j, k);
                    IBlockData iblockdata = this.world.getType(blockposition$mutableblockposition);
                    if (this.a(iblockdata)) {
                        VillageDoor villagedoor = this.c(blockposition$mutableblockposition);
                        if (villagedoor == null) {
                            this.a(iblockdata, blockposition$mutableblockposition);
                        } else {
                            villagedoor.a(this.time);
                        }
                    }
                }
            }
        }

    }

    @Nullable
    private VillageDoor c(BlockPosition blockposition) {
        for(VillageDoor villagedoor : this.c) {
            if (villagedoor.d().getX() == blockposition.getX() && villagedoor.d().getZ() == blockposition.getZ() && Math.abs(villagedoor.d().getY() - blockposition.getY()) <= 1) {
                return villagedoor;
            }
        }

        for(Village village : this.villages) {
            VillageDoor villagedoor1 = village.e(blockposition);
            if (villagedoor1 != null) {
                return villagedoor1;
            }
        }

        return null;
    }

    private void a(IBlockData iblockdata, BlockPosition blockposition) {
        EnumDirection enumdirection = (EnumDirection)iblockdata.get(BlockDoor.FACING);
        EnumDirection enumdirection1 = enumdirection.opposite();
        int i = this.a(blockposition, enumdirection, 5);
        int j = this.a(blockposition, enumdirection1, i + 1);
        if (i != j) {
            this.c.add(new VillageDoor(blockposition, i < j ? enumdirection : enumdirection1, this.time));
        }

    }

    private int a(BlockPosition blockposition, EnumDirection enumdirection, int i) {
        int j = 0;

        for(int k = 1; k <= 5; ++k) {
            if (this.world.e(blockposition.shift(enumdirection, k))) {
                ++j;
                if (j >= i) {
                    return j;
                }
            }
        }

        return j;
    }

    private boolean d(BlockPosition blockposition) {
        for(BlockPosition blockposition1 : this.b) {
            if (blockposition1.equals(blockposition)) {
                return true;
            }
        }

        return false;
    }

    private boolean a(IBlockData iblockdata) {
        return iblockdata.getBlock() instanceof BlockDoor && iblockdata.getMaterial() == Material.WOOD;
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.time = nbttagcompound.getInt("Tick");
        NBTTagList nbttaglist = nbttagcompound.getList("Villages", 10);

        for(int i = 0; i < nbttaglist.size(); ++i) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompound(i);
            Village village = new Village();
            village.a(nbttagcompound1);
            this.villages.add(village);
        }

    }

    public NBTTagCompound b(NBTTagCompound nbttagcompound) {
        nbttagcompound.setInt("Tick", this.time);
        NBTTagList nbttaglist = new NBTTagList();

        for(Village village : this.villages) {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            village.b(nbttagcompound1);
            nbttaglist.add((NBTBase)nbttagcompound1);
        }

        nbttagcompound.set("Villages", nbttaglist);
        return nbttagcompound;
    }

    public static String a(WorldProvider worldprovider) {
        return "villages" + worldprovider.getDimensionManager().d();
    }
}
