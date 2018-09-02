package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class TileEntityStructure extends TileEntity {
    private MinecraftKey structureName;
    public String author = "";
    public String metadata = "";
    public BlockPosition relativePosition = new BlockPosition(0, 1, 0);
    public BlockPosition size = BlockPosition.ZERO;
    public EnumBlockMirror mirror = EnumBlockMirror.NONE;
    public EnumBlockRotation rotation = EnumBlockRotation.NONE;
    private BlockPropertyStructureMode usageMode = BlockPropertyStructureMode.DATA;
    public boolean ignoreEntities = true;
    private boolean powered;
    public boolean showAir;
    public boolean showBoundingBox = true;
    public float integrity = 1.0F;
    public long seed;

    public TileEntityStructure() {
        super(TileEntityTypes.STRUCTURE_BLOCK);
    }

    public NBTTagCompound save(NBTTagCompound nbttagcompound) {
        super.save(nbttagcompound);
        nbttagcompound.setString("name", this.getStructureName());
        nbttagcompound.setString("author", this.author);
        nbttagcompound.setString("metadata", this.metadata);
        nbttagcompound.setInt("posX", this.relativePosition.getX());
        nbttagcompound.setInt("posY", this.relativePosition.getY());
        nbttagcompound.setInt("posZ", this.relativePosition.getZ());
        nbttagcompound.setInt("sizeX", this.size.getX());
        nbttagcompound.setInt("sizeY", this.size.getY());
        nbttagcompound.setInt("sizeZ", this.size.getZ());
        nbttagcompound.setString("rotation", this.rotation.toString());
        nbttagcompound.setString("mirror", this.mirror.toString());
        nbttagcompound.setString("mode", this.usageMode.toString());
        nbttagcompound.setBoolean("ignoreEntities", this.ignoreEntities);
        nbttagcompound.setBoolean("powered", this.powered);
        nbttagcompound.setBoolean("showair", this.showAir);
        nbttagcompound.setBoolean("showboundingbox", this.showBoundingBox);
        nbttagcompound.setFloat("integrity", this.integrity);
        nbttagcompound.setLong("seed", this.seed);
        return nbttagcompound;
    }

    public void load(NBTTagCompound nbttagcompound) {
        super.load(nbttagcompound);
        this.setStructureName(nbttagcompound.getString("name"));
        this.author = nbttagcompound.getString("author");
        this.metadata = nbttagcompound.getString("metadata");
        int i = MathHelper.clamp(nbttagcompound.getInt("posX"), -32, 32);
        int j = MathHelper.clamp(nbttagcompound.getInt("posY"), -32, 32);
        int k = MathHelper.clamp(nbttagcompound.getInt("posZ"), -32, 32);
        this.relativePosition = new BlockPosition(i, j, k);
        int l = MathHelper.clamp(nbttagcompound.getInt("sizeX"), 0, 32);
        int i1 = MathHelper.clamp(nbttagcompound.getInt("sizeY"), 0, 32);
        int j1 = MathHelper.clamp(nbttagcompound.getInt("sizeZ"), 0, 32);
        this.size = new BlockPosition(l, i1, j1);

        try {
            this.rotation = EnumBlockRotation.valueOf(nbttagcompound.getString("rotation"));
        } catch (IllegalArgumentException var11) {
            this.rotation = EnumBlockRotation.NONE;
        }

        try {
            this.mirror = EnumBlockMirror.valueOf(nbttagcompound.getString("mirror"));
        } catch (IllegalArgumentException var10) {
            this.mirror = EnumBlockMirror.NONE;
        }

        try {
            this.usageMode = BlockPropertyStructureMode.valueOf(nbttagcompound.getString("mode"));
        } catch (IllegalArgumentException var9) {
            this.usageMode = BlockPropertyStructureMode.DATA;
        }

        this.ignoreEntities = nbttagcompound.getBoolean("ignoreEntities");
        this.powered = nbttagcompound.getBoolean("powered");
        this.showAir = nbttagcompound.getBoolean("showair");
        this.showBoundingBox = nbttagcompound.getBoolean("showboundingbox");
        if (nbttagcompound.hasKey("integrity")) {
            this.integrity = nbttagcompound.getFloat("integrity");
        } else {
            this.integrity = 1.0F;
        }

        this.seed = nbttagcompound.getLong("seed");
        this.J();
    }

    private void J() {
        if (this.world != null) {
            BlockPosition blockposition = this.getPosition();
            IBlockData iblockdata = this.world.getType(blockposition);
            if (iblockdata.getBlock() == Blocks.STRUCTURE_BLOCK) {
                this.world.setTypeAndData(blockposition, (IBlockData)iblockdata.set(BlockStructure.a, this.usageMode), 2);
            }

        }
    }

    @Nullable
    public PacketPlayOutTileEntityData getUpdatePacket() {
        return new PacketPlayOutTileEntityData(this.position, 7, this.aa_());
    }

    public NBTTagCompound aa_() {
        return this.save(new NBTTagCompound());
    }

    public boolean a(EntityHuman entityhuman) {
        if (!entityhuman.isCreativeAndOp()) {
            return false;
        } else {
            if (entityhuman.getWorld().isClientSide) {
                entityhuman.a(this);
            }

            return true;
        }
    }

    public String getStructureName() {
        return this.structureName == null ? "" : this.structureName.toString();
    }

    public boolean d() {
        return this.structureName != null;
    }

    public void setStructureName(@Nullable String s) {
        this.a(UtilColor.b(s) ? null : MinecraftKey.a(s));
    }

    public void a(@Nullable MinecraftKey minecraftkey) {
        this.structureName = minecraftkey;
    }

    public void setAuthor(EntityLiving entityliving) {
        this.author = entityliving.getDisplayName().getString();
    }

    public void b(BlockPosition blockposition) {
        this.relativePosition = blockposition;
    }

    public void c(BlockPosition blockposition) {
        this.size = blockposition;
    }

    public void b(EnumBlockMirror enumblockmirror) {
        this.mirror = enumblockmirror;
    }

    public void b(EnumBlockRotation enumblockrotation) {
        this.rotation = enumblockrotation;
    }

    public void b(String s) {
        this.metadata = s;
    }

    public BlockPropertyStructureMode getUsageMode() {
        return this.usageMode;
    }

    public void setUsageMode(BlockPropertyStructureMode blockpropertystructuremode) {
        this.usageMode = blockpropertystructuremode;
        IBlockData iblockdata = this.world.getType(this.getPosition());
        if (iblockdata.getBlock() == Blocks.STRUCTURE_BLOCK) {
            this.world.setTypeAndData(this.getPosition(), (IBlockData)iblockdata.set(BlockStructure.a, blockpropertystructuremode), 2);
        }

    }

    public void a(boolean flag) {
        this.ignoreEntities = flag;
    }

    public void a(float f) {
        this.integrity = f;
    }

    public void a(long i) {
        this.seed = i;
    }

    public boolean p() {
        if (this.usageMode != BlockPropertyStructureMode.SAVE) {
            return false;
        } else {
            BlockPosition blockposition = this.getPosition();
            boolean flag = true;
            BlockPosition blockposition1 = new BlockPosition(blockposition.getX() - 80, 0, blockposition.getZ() - 80);
            BlockPosition blockposition2 = new BlockPosition(blockposition.getX() + 80, 255, blockposition.getZ() + 80);
            List list = this.a(blockposition1, blockposition2);
            List list1 = this.a(list);
            if (list1.size() < 1) {
                return false;
            } else {
                StructureBoundingBox structureboundingbox = this.a(blockposition, list1);
                if (structureboundingbox.d - structureboundingbox.a > 1 && structureboundingbox.e - structureboundingbox.b > 1 && structureboundingbox.f - structureboundingbox.c > 1) {
                    this.relativePosition = new BlockPosition(structureboundingbox.a - blockposition.getX() + 1, structureboundingbox.b - blockposition.getY() + 1, structureboundingbox.c - blockposition.getZ() + 1);
                    this.size = new BlockPosition(structureboundingbox.d - structureboundingbox.a - 1, structureboundingbox.e - structureboundingbox.b - 1, structureboundingbox.f - structureboundingbox.c - 1);
                    this.update();
                    IBlockData iblockdata = this.world.getType(blockposition);
                    this.world.notify(blockposition, iblockdata, iblockdata, 3);
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    private List<TileEntityStructure> a(List<TileEntityStructure> list) {
        Predicate predicate = (tileentitystructure2) -> {
            return tileentitystructure2.usageMode == BlockPropertyStructureMode.CORNER && Objects.equals(this.structureName, tileentitystructure2.structureName);
        };
        return (List)list.stream().filter(predicate).collect(Collectors.toList());
    }

    private List<TileEntityStructure> a(BlockPosition blockposition, BlockPosition blockposition1) {
        ArrayList arraylist = Lists.newArrayList();

        for(BlockPosition.MutableBlockPosition blockposition$mutableblockposition : BlockPosition.b(blockposition, blockposition1)) {
            IBlockData iblockdata = this.world.getType(blockposition$mutableblockposition);
            if (iblockdata.getBlock() == Blocks.STRUCTURE_BLOCK) {
                TileEntity tileentity = this.world.getTileEntity(blockposition$mutableblockposition);
                if (tileentity != null && tileentity instanceof TileEntityStructure) {
                    arraylist.add((TileEntityStructure)tileentity);
                }
            }
        }

        return arraylist;
    }

    private StructureBoundingBox a(BlockPosition blockposition, List<TileEntityStructure> list) {
        StructureBoundingBox structureboundingbox;
        if (list.size() > 1) {
            BlockPosition blockposition1 = ((TileEntityStructure)list.get(0)).getPosition();
            structureboundingbox = new StructureBoundingBox(blockposition1, blockposition1);
        } else {
            structureboundingbox = new StructureBoundingBox(blockposition, blockposition);
        }

        for(TileEntityStructure tileentitystructure : list) {
            BlockPosition blockposition2 = tileentitystructure.getPosition();
            if (blockposition2.getX() < structureboundingbox.a) {
                structureboundingbox.a = blockposition2.getX();
            } else if (blockposition2.getX() > structureboundingbox.d) {
                structureboundingbox.d = blockposition2.getX();
            }

            if (blockposition2.getY() < structureboundingbox.b) {
                structureboundingbox.b = blockposition2.getY();
            } else if (blockposition2.getY() > structureboundingbox.e) {
                structureboundingbox.e = blockposition2.getY();
            }

            if (blockposition2.getZ() < structureboundingbox.c) {
                structureboundingbox.c = blockposition2.getZ();
            } else if (blockposition2.getZ() > structureboundingbox.f) {
                structureboundingbox.f = blockposition2.getZ();
            }
        }

        return structureboundingbox;
    }

    public boolean q() {
        return this.b(true);
    }

    public boolean b(boolean flag) {
        if (this.usageMode == BlockPropertyStructureMode.SAVE && !this.world.isClientSide && this.structureName != null) {
            BlockPosition blockposition = this.getPosition().a(this.relativePosition);
            WorldServer worldserver = (WorldServer)this.world;
            DefinedStructureManager definedstructuremanager = worldserver.D();

            DefinedStructure definedstructure;
            try {
                definedstructure = definedstructuremanager.a(this.structureName);
            } catch (ResourceKeyInvalidException var8) {
                return false;
            }

            definedstructure.a(this.world, blockposition, this.size, !this.ignoreEntities, Blocks.STRUCTURE_VOID);
            definedstructure.a(this.author);
            if (flag) {
                try {
                    return definedstructuremanager.c(this.structureName);
                } catch (ResourceKeyInvalidException var7) {
                    return false;
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean r() {
        return this.c(true);
    }

    public boolean c(boolean flag) {
        if (this.usageMode == BlockPropertyStructureMode.LOAD && !this.world.isClientSide && this.structureName != null) {
            BlockPosition blockposition = this.getPosition();
            BlockPosition blockposition1 = blockposition.a(this.relativePosition);
            WorldServer worldserver = (WorldServer)this.world;
            DefinedStructureManager definedstructuremanager = worldserver.D();

            DefinedStructure definedstructure;
            try {
                definedstructure = definedstructuremanager.b(this.structureName);
            } catch (ResourceKeyInvalidException var10) {
                return false;
            }

            if (definedstructure == null) {
                return false;
            } else {
                if (!UtilColor.b(definedstructure.b())) {
                    this.author = definedstructure.b();
                }

                BlockPosition blockposition2 = definedstructure.a();
                boolean flag1 = this.size.equals(blockposition2);
                if (!flag1) {
                    this.size = blockposition2;
                    this.update();
                    IBlockData iblockdata = this.world.getType(blockposition);
                    this.world.notify(blockposition, iblockdata, iblockdata, 3);
                }

                if (flag && !flag1) {
                    return false;
                } else {
                    DefinedStructureInfo definedstructureinfo = (new DefinedStructureInfo()).a(this.mirror).a(this.rotation).a(this.ignoreEntities).a((ChunkCoordIntPair)null).a((Block)null).c(false);
                    if (this.integrity < 1.0F) {
                        definedstructureinfo.a(MathHelper.a(this.integrity, 0.0F, 1.0F)).a(this.seed);
                    }

                    definedstructure.a(this.world, blockposition1, definedstructureinfo);
                    return true;
                }
            }
        } else {
            return false;
        }
    }

    public void s() {
        if (this.structureName != null) {
            WorldServer worldserver = (WorldServer)this.world;
            DefinedStructureManager definedstructuremanager = worldserver.D();
            definedstructuremanager.d(this.structureName);
        }
    }

    public boolean D() {
        if (this.usageMode == BlockPropertyStructureMode.LOAD && !this.world.isClientSide && this.structureName != null) {
            WorldServer worldserver = (WorldServer)this.world;
            DefinedStructureManager definedstructuremanager = worldserver.D();

            try {
                return definedstructuremanager.b(this.structureName) != null;
            } catch (ResourceKeyInvalidException var4) {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean E() {
        return this.powered;
    }

    public void d(boolean flag) {
        this.powered = flag;
    }

    public void e(boolean flag) {
        this.showAir = flag;
    }

    public void f(boolean flag) {
        this.showBoundingBox = flag;
    }

    public static enum UpdateType {
        UPDATE_DATA,
        SAVE_AREA,
        LOAD_AREA,
        SCAN_AREA;

        private UpdateType() {
        }
    }
}
