package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public class WorldMap extends PersistentBase {
    public int centerX;
    public int centerZ;
    public DimensionManager map;
    public boolean track;
    public boolean unlimitedTracking;
    public byte scale;
    public byte[] colors = new byte[16384];
    public List<WorldMap.WorldMapHumanTracker> h = Lists.newArrayList();
    private final Map<EntityHuman, WorldMap.WorldMapHumanTracker> j = Maps.newHashMap();
    private final Map<String, MapIconBanner> k = Maps.newHashMap();
    public Map<String, MapIcon> decorations = Maps.newLinkedHashMap();
    private final Map<String, WorldMapFrame> l = Maps.newHashMap();

    public WorldMap(String s) {
        super(s);
    }

    public void a(int i, int jx, int kx, boolean flag, boolean flag1, DimensionManager dimensionmanager) {
        this.scale = (byte)kx;
        this.a((double)i, (double)jx, this.scale);
        this.map = dimensionmanager;
        this.track = flag;
        this.unlimitedTracking = flag1;
        this.c();
    }

    public void a(double d0, double d1, int i) {
        int jx = 128 * (1 << i);
        int kx = MathHelper.floor((d0 + 64.0D) / (double)jx);
        int lx = MathHelper.floor((d1 + 64.0D) / (double)jx);
        this.centerX = kx * jx + jx / 2 - 64;
        this.centerZ = lx * jx + jx / 2 - 64;
    }

    public void a(NBTTagCompound nbttagcompound) {
        this.map = DimensionManager.a(nbttagcompound.getInt("dimension"));
        this.centerX = nbttagcompound.getInt("xCenter");
        this.centerZ = nbttagcompound.getInt("zCenter");
        this.scale = (byte)MathHelper.clamp(nbttagcompound.getByte("scale"), 0, 4);
        this.track = !nbttagcompound.hasKeyOfType("trackingPosition", 1) || nbttagcompound.getBoolean("trackingPosition");
        this.unlimitedTracking = nbttagcompound.getBoolean("unlimitedTracking");
        this.colors = nbttagcompound.getByteArray("colors");
        if (this.colors.length != 16384) {
            this.colors = new byte[16384];
        }

        NBTTagList nbttaglist = nbttagcompound.getList("banners", 10);

        for(int i = 0; i < nbttaglist.size(); ++i) {
            MapIconBanner mapiconbanner = MapIconBanner.a(nbttaglist.getCompound(i));
            this.k.put(mapiconbanner.f(), mapiconbanner);
            this.a(mapiconbanner.c(), (GeneratorAccess)null, mapiconbanner.f(), (double)mapiconbanner.a().getX(), (double)mapiconbanner.a().getZ(), 180.0D, mapiconbanner.d());
        }

        NBTTagList nbttaglist1 = nbttagcompound.getList("frames", 10);

        for(int jx = 0; jx < nbttaglist1.size(); ++jx) {
            WorldMapFrame worldmapframe = WorldMapFrame.a(nbttaglist1.getCompound(jx));
            this.l.put(worldmapframe.e(), worldmapframe);
            this.a(MapIcon.Type.FRAME, (GeneratorAccess)null, "frame-" + worldmapframe.d(), (double)worldmapframe.b().getX(), (double)worldmapframe.b().getZ(), (double)worldmapframe.c(), (IChatBaseComponent)null);
        }

    }

    public NBTTagCompound b(NBTTagCompound nbttagcompound) {
        nbttagcompound.setInt("dimension", this.map.getDimensionID());
        nbttagcompound.setInt("xCenter", this.centerX);
        nbttagcompound.setInt("zCenter", this.centerZ);
        nbttagcompound.setByte("scale", this.scale);
        nbttagcompound.setByteArray("colors", this.colors);
        nbttagcompound.setBoolean("trackingPosition", this.track);
        nbttagcompound.setBoolean("unlimitedTracking", this.unlimitedTracking);
        NBTTagList nbttaglist = new NBTTagList();

        for(MapIconBanner mapiconbanner : this.k.values()) {
            nbttaglist.add((NBTBase)mapiconbanner.e());
        }

        nbttagcompound.set("banners", nbttaglist);
        NBTTagList nbttaglist1 = new NBTTagList();

        for(WorldMapFrame worldmapframe : this.l.values()) {
            nbttaglist1.add((NBTBase)worldmapframe.a());
        }

        nbttagcompound.set("frames", nbttaglist1);
        return nbttagcompound;
    }

    public void a(EntityHuman entityhuman, ItemStack itemstack) {
        if (!this.j.containsKey(entityhuman)) {
            WorldMap.WorldMapHumanTracker worldmap$worldmaphumantracker = new WorldMap.WorldMapHumanTracker(entityhuman);
            this.j.put(entityhuman, worldmap$worldmaphumantracker);
            this.h.add(worldmap$worldmaphumantracker);
        }

        if (!entityhuman.inventory.h(itemstack)) {
            this.decorations.remove(entityhuman.getDisplayName().getString());
        }

        for(int i = 0; i < this.h.size(); ++i) {
            WorldMap.WorldMapHumanTracker worldmap$worldmaphumantracker1 = (WorldMap.WorldMapHumanTracker)this.h.get(i);
            String s = worldmap$worldmaphumantracker1.trackee.getDisplayName().getString();
            if (!worldmap$worldmaphumantracker1.trackee.dead && (worldmap$worldmaphumantracker1.trackee.inventory.h(itemstack) || itemstack.x())) {
                if (!itemstack.x() && worldmap$worldmaphumantracker1.trackee.dimension == this.map && this.track) {
                    this.a(MapIcon.Type.PLAYER, worldmap$worldmaphumantracker1.trackee.world, s, worldmap$worldmaphumantracker1.trackee.locX, worldmap$worldmaphumantracker1.trackee.locZ, (double)worldmap$worldmaphumantracker1.trackee.yaw, (IChatBaseComponent)null);
                }
            } else {
                this.j.remove(worldmap$worldmaphumantracker1.trackee);
                this.h.remove(worldmap$worldmaphumantracker1);
                this.decorations.remove(s);
            }
        }

        if (itemstack.x() && this.track) {
            EntityItemFrame entityitemframe = itemstack.y();
            BlockPosition blockposition = entityitemframe.getBlockPosition();
            WorldMapFrame worldmapframe1 = (WorldMapFrame)this.l.get(WorldMapFrame.a(blockposition));
            if (worldmapframe1 != null && entityitemframe.getId() != worldmapframe1.d() && this.l.containsKey(worldmapframe1.e())) {
                this.decorations.remove("frame-" + worldmapframe1.d());
            }

            WorldMapFrame worldmapframe = new WorldMapFrame(blockposition, entityitemframe.direction.get2DRotationValue() * 90, entityitemframe.getId());
            this.a(MapIcon.Type.FRAME, entityhuman.world, "frame-" + entityitemframe.getId(), (double)blockposition.getX(), (double)blockposition.getZ(), (double)(entityitemframe.direction.get2DRotationValue() * 90), (IChatBaseComponent)null);
            this.l.put(worldmapframe.e(), worldmapframe);
        }

        NBTTagCompound nbttagcompound = itemstack.getTag();
        if (nbttagcompound != null && nbttagcompound.hasKeyOfType("Decorations", 9)) {
            NBTTagList nbttaglist = nbttagcompound.getList("Decorations", 10);

            for(int jx = 0; jx < nbttaglist.size(); ++jx) {
                NBTTagCompound nbttagcompound1 = nbttaglist.getCompound(jx);
                if (!this.decorations.containsKey(nbttagcompound1.getString("id"))) {
                    this.a(MapIcon.Type.a(nbttagcompound1.getByte("type")), entityhuman.world, nbttagcompound1.getString("id"), nbttagcompound1.getDouble("x"), nbttagcompound1.getDouble("z"), nbttagcompound1.getDouble("rot"), (IChatBaseComponent)null);
                }
            }
        }

    }

    public static void a(ItemStack itemstack, BlockPosition blockposition, String s, MapIcon.Type mapicon$type) {
        NBTTagList nbttaglist;
        if (itemstack.hasTag() && itemstack.getTag().hasKeyOfType("Decorations", 9)) {
            nbttaglist = itemstack.getTag().getList("Decorations", 10);
        } else {
            nbttaglist = new NBTTagList();
            itemstack.a("Decorations", nbttaglist);
        }

        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setByte("type", mapicon$type.a());
        nbttagcompound.setString("id", s);
        nbttagcompound.setDouble("x", (double)blockposition.getX());
        nbttagcompound.setDouble("z", (double)blockposition.getZ());
        nbttagcompound.setDouble("rot", 180.0D);
        nbttaglist.add((NBTBase)nbttagcompound);
        if (mapicon$type.c()) {
            NBTTagCompound nbttagcompound1 = itemstack.a("display");
            nbttagcompound1.setInt("MapColor", mapicon$type.d());
        }

    }

    private void a(MapIcon.Type mapicon$type, @Nullable GeneratorAccess generatoraccess, String s, double d0, double d1, double d2, @Nullable IChatBaseComponent ichatbasecomponent) {
        int i = 1 << this.scale;
        float f = (float)(d0 - (double)this.centerX) / (float)i;
        float f1 = (float)(d1 - (double)this.centerZ) / (float)i;
        byte b0 = (byte)((int)((double)(f * 2.0F) + 0.5D));
        byte b1 = (byte)((int)((double)(f1 * 2.0F) + 0.5D));
        boolean flag = true;
        byte b2;
        if (f >= -63.0F && f1 >= -63.0F && f <= 63.0F && f1 <= 63.0F) {
            d2 = d2 + (d2 < 0.0D ? -8.0D : 8.0D);
            b2 = (byte)((int)(d2 * 16.0D / 360.0D));
            if (this.map == DimensionManager.NETHER && generatoraccess != null) {
                int jx = (int)(generatoraccess.getWorldData().getDayTime() / 10L);
                b2 = (byte)(jx * jx * 34187121 + jx * 121 >> 15 & 15);
            }
        } else {
            if (mapicon$type != MapIcon.Type.PLAYER) {
                this.decorations.remove(s);
                return;
            }

            boolean flag1 = true;
            if (Math.abs(f) < 320.0F && Math.abs(f1) < 320.0F) {
                mapicon$type = MapIcon.Type.PLAYER_OFF_MAP;
            } else {
                if (!this.unlimitedTracking) {
                    this.decorations.remove(s);
                    return;
                }

                mapicon$type = MapIcon.Type.PLAYER_OFF_LIMITS;
            }

            b2 = 0;
            if (f <= -63.0F) {
                b0 = -128;
            }

            if (f1 <= -63.0F) {
                b1 = -128;
            }

            if (f >= 63.0F) {
                b0 = 127;
            }

            if (f1 >= 63.0F) {
                b1 = 127;
            }
        }

        this.decorations.put(s, new MapIcon(mapicon$type, b0, b1, b2, ichatbasecomponent));
    }

    @Nullable
    public Packet<?> a(ItemStack itemstack, IBlockAccess var2, EntityHuman entityhuman) {
        WorldMap.WorldMapHumanTracker worldmap$worldmaphumantracker = (WorldMap.WorldMapHumanTracker)this.j.get(entityhuman);
        return worldmap$worldmaphumantracker == null ? null : worldmap$worldmaphumantracker.a(itemstack);
    }

    public void flagDirty(int i, int jx) {
        this.c();

        for(WorldMap.WorldMapHumanTracker worldmap$worldmaphumantracker : this.h) {
            worldmap$worldmaphumantracker.a(i, jx);
        }

    }

    public WorldMap.WorldMapHumanTracker a(EntityHuman entityhuman) {
        WorldMap.WorldMapHumanTracker worldmap$worldmaphumantracker = (WorldMap.WorldMapHumanTracker)this.j.get(entityhuman);
        if (worldmap$worldmaphumantracker == null) {
            worldmap$worldmaphumantracker = new WorldMap.WorldMapHumanTracker(entityhuman);
            this.j.put(entityhuman, worldmap$worldmaphumantracker);
            this.h.add(worldmap$worldmaphumantracker);
        }

        return worldmap$worldmaphumantracker;
    }

    public void a(GeneratorAccess generatoraccess, BlockPosition blockposition) {
        float f = (float)blockposition.getX() + 0.5F;
        float f1 = (float)blockposition.getZ() + 0.5F;
        int i = 1 << this.scale;
        float f2 = (f - (float)this.centerX) / (float)i;
        float f3 = (f1 - (float)this.centerZ) / (float)i;
        boolean flag = true;
        boolean flag1 = false;
        if (f2 >= -63.0F && f3 >= -63.0F && f2 <= 63.0F && f3 <= 63.0F) {
            MapIconBanner mapiconbanner = MapIconBanner.a(generatoraccess, blockposition);
            if (mapiconbanner == null) {
                return;
            }

            boolean flag2 = true;
            if (this.k.containsKey(mapiconbanner.f()) && ((MapIconBanner)this.k.get(mapiconbanner.f())).equals(mapiconbanner)) {
                this.k.remove(mapiconbanner.f());
                this.decorations.remove(mapiconbanner.f());
                flag2 = false;
                flag1 = true;
            }

            if (flag2) {
                this.k.put(mapiconbanner.f(), mapiconbanner);
                this.a(mapiconbanner.c(), generatoraccess, mapiconbanner.f(), (double)f, (double)f1, 180.0D, mapiconbanner.d());
                flag1 = true;
            }

            if (flag1) {
                this.c();
            }
        }

    }

    public void a(IBlockAccess iblockaccess, int i, int jx) {
        Iterator iterator = this.k.values().iterator();

        while(iterator.hasNext()) {
            MapIconBanner mapiconbanner = (MapIconBanner)iterator.next();
            if (mapiconbanner.a().getX() == i && mapiconbanner.a().getZ() == jx) {
                MapIconBanner mapiconbanner1 = MapIconBanner.a(iblockaccess, mapiconbanner.a());
                if (!mapiconbanner.equals(mapiconbanner1)) {
                    iterator.remove();
                    this.decorations.remove(mapiconbanner.f());
                }
            }
        }

    }

    public void a(BlockPosition blockposition, int i) {
        this.decorations.remove("frame-" + i);
        this.l.remove(WorldMapFrame.a(blockposition));
    }

    public class WorldMapHumanTracker {
        public final EntityHuman trackee;
        private boolean d = true;
        private int e;
        private int f;
        private int g = 127;
        private int h = 127;
        private int i;
        public int b;

        public WorldMapHumanTracker(EntityHuman entityhuman) {
            this.trackee = entityhuman;
        }

        @Nullable
        public Packet<?> a(ItemStack itemstack) {
            if (this.d) {
                this.d = false;
                return new PacketPlayOutMap(ItemWorldMap.e(itemstack), WorldMap.this.scale, WorldMap.this.track, WorldMap.this.decorations.values(), WorldMap.this.colors, this.e, this.f, this.g + 1 - this.e, this.h + 1 - this.f);
            } else {
                return this.i++ % 5 == 0 ? new PacketPlayOutMap(ItemWorldMap.e(itemstack), WorldMap.this.scale, WorldMap.this.track, WorldMap.this.decorations.values(), WorldMap.this.colors, 0, 0, 0, 0) : null;
            }
        }

        public void a(int ix, int j) {
            if (this.d) {
                this.e = Math.min(this.e, ix);
                this.f = Math.min(this.f, j);
                this.g = Math.max(this.g, ix);
                this.h = Math.max(this.h, j);
            } else {
                this.d = true;
                this.e = ix;
                this.f = j;
                this.g = ix;
                this.h = j;
            }

        }
    }
}
