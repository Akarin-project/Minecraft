package net.minecraft.server;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Lists;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnderDragonBattle {
    private static final Logger a = LogManager.getLogger();
    private static final Predicate<Entity> b = IEntitySelector.a.and(IEntitySelector.a(0.0D, 128.0D, 0.0D, 192.0D));
    private final BossBattleServer c = (BossBattleServer)(new BossBattleServer(new ChatMessage("entity.minecraft.ender_dragon", new Object[0]), BossBattle.BarColor.PINK, BossBattle.BarStyle.PROGRESS)).setPlayMusic(true).c(true);
    private final WorldServer d;
    private final List<Integer> e = Lists.newArrayList();
    private final ShapeDetector f;
    private int g;
    private int h;
    private int i;
    private int j;
    private boolean k;
    private boolean l;
    private UUID m;
    private boolean n = true;
    private BlockPosition o;
    private EnumDragonRespawn p;
    private int q;
    private List<EntityEnderCrystal> r;

    public EnderDragonBattle(WorldServer worldserver, NBTTagCompound nbttagcompound) {
        this.d = worldserver;
        if (nbttagcompound.hasKeyOfType("DragonKilled", 99)) {
            if (nbttagcompound.b("DragonUUID")) {
                this.m = nbttagcompound.a("DragonUUID");
            }

            this.k = nbttagcompound.getBoolean("DragonKilled");
            this.l = nbttagcompound.getBoolean("PreviouslyKilled");
            if (nbttagcompound.getBoolean("IsRespawning")) {
                this.p = EnumDragonRespawn.START;
            }

            if (nbttagcompound.hasKeyOfType("ExitPortalLocation", 10)) {
                this.o = GameProfileSerializer.c(nbttagcompound.getCompound("ExitPortalLocation"));
            }
        } else {
            this.k = true;
            this.l = true;
        }

        if (nbttagcompound.hasKeyOfType("Gateways", 9)) {
            NBTTagList nbttaglist = nbttagcompound.getList("Gateways", 3);

            for(int ix = 0; ix < nbttaglist.size(); ++ix) {
                this.e.add(nbttaglist.h(ix));
            }
        } else {
            this.e.addAll(ContiguousSet.create(Range.closedOpen(0, 20), DiscreteDomain.integers()));
            Collections.shuffle(this.e, new Random(worldserver.getSeed()));
        }

        this.f = ShapeDetectorBuilder.a().a("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").a("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").a("       ", "       ", "       ", "   #   ", "       ", "       ", "       ").a("  ###  ", " #   # ", "#     #", "#  #  #", "#     #", " #   # ", "  ###  ").a("       ", "  ###  ", " ##### ", " ##### ", " ##### ", "  ###  ", "       ").a('#', ShapeDetectorBlock.a(BlockPredicate.a(Blocks.BEDROCK))).b();
    }

    public NBTTagCompound a() {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        if (this.m != null) {
            nbttagcompound.a("DragonUUID", this.m);
        }

        nbttagcompound.setBoolean("DragonKilled", this.k);
        nbttagcompound.setBoolean("PreviouslyKilled", this.l);
        if (this.o != null) {
            nbttagcompound.set("ExitPortalLocation", GameProfileSerializer.a(this.o));
        }

        NBTTagList nbttaglist = new NBTTagList();

        for(int ix : this.e) {
            nbttaglist.add((NBTBase)(new NBTTagInt(ix)));
        }

        nbttagcompound.set("Gateways", nbttaglist);
        return nbttagcompound;
    }

    public void b() {
        this.c.setVisible(!this.k);
        if (++this.j >= 20) {
            this.k();
            this.j = 0;
        }

        EnderDragonBattle.b enderdragonbattle$b = new EnderDragonBattle.b();
        if (!this.c.getPlayers().isEmpty()) {
            if (this.n && enderdragonbattle$b.a()) {
                this.g();
                this.n = false;
            }

            if (this.p != null) {
                if (this.r == null && enderdragonbattle$b.a()) {
                    this.p = null;
                    this.e();
                }

                this.p.a(this.d, this, this.r, this.q++, this.o);
            }

            if (!this.k) {
                if ((this.m == null || ++this.g >= 1200) && enderdragonbattle$b.a()) {
                    this.h();
                    this.g = 0;
                }

                if (++this.i >= 100 && enderdragonbattle$b.a()) {
                    this.l();
                    this.i = 0;
                }
            }
        }

    }

    private void g() {
        a.info("Scanning for legacy world dragon fight...");
        boolean flag = this.i();
        if (flag) {
            a.info("Found that the dragon has been killed in this world already.");
            this.l = true;
        } else {
            a.info("Found that the dragon has not yet been killed in this world.");
            this.l = false;
            this.a(false);
        }

        List list = this.d.a(EntityEnderDragon.class, IEntitySelector.a);
        if (list.isEmpty()) {
            this.k = true;
        } else {
            EntityEnderDragon entityenderdragon = (EntityEnderDragon)list.get(0);
            this.m = entityenderdragon.getUniqueID();
            a.info("Found that there's a dragon still alive ({})", entityenderdragon);
            this.k = false;
            if (!flag) {
                a.info("But we didn't have a portal, let's remove it.");
                entityenderdragon.die();
                this.m = null;
            }
        }

        if (!this.l && this.k) {
            this.k = false;
        }

    }

    private void h() {
        List list = this.d.a(EntityEnderDragon.class, IEntitySelector.a);
        if (list.isEmpty()) {
            a.debug("Haven't seen the dragon, respawning it");
            this.n();
        } else {
            a.debug("Haven't seen our dragon, but found another one to use.");
            this.m = ((EntityEnderDragon)list.get(0)).getUniqueID();
        }

    }

    protected void a(EnumDragonRespawn enumdragonrespawn) {
        if (this.p == null) {
            throw new IllegalStateException("Dragon respawn isn't in progress, can't skip ahead in the animation.");
        } else {
            this.q = 0;
            if (enumdragonrespawn == EnumDragonRespawn.END) {
                this.p = null;
                this.k = false;
                EntityEnderDragon entityenderdragon = this.n();

                for(EntityPlayer entityplayer : this.c.getPlayers()) {
                    CriterionTriggers.n.a(entityplayer, entityenderdragon);
                }
            } else {
                this.p = enumdragonrespawn;
            }

        }
    }

    private boolean i() {
        for(int ix = -8; ix <= 8; ++ix) {
            for(int jx = -8; jx <= 8; ++jx) {
                Chunk chunk = this.d.getChunkAt(ix, jx);

                for(TileEntity tileentity : chunk.getTileEntities().values()) {
                    if (tileentity instanceof TileEntityEnderPortal) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Nullable
    private ShapeDetector.ShapeDetectorCollection j() {
        for(int ix = -8; ix <= 8; ++ix) {
            for(int jx = -8; jx <= 8; ++jx) {
                Chunk chunk = this.d.getChunkAt(ix, jx);

                for(TileEntity tileentity : chunk.getTileEntities().values()) {
                    if (tileentity instanceof TileEntityEnderPortal) {
                        ShapeDetector.ShapeDetectorCollection shapedetector$shapedetectorcollection = this.f.a(this.d, tileentity.getPosition());
                        if (shapedetector$shapedetectorcollection != null) {
                            BlockPosition blockposition = shapedetector$shapedetectorcollection.a(3, 3, 3).getPosition();
                            if (this.o == null && blockposition.getX() == 0 && blockposition.getZ() == 0) {
                                this.o = blockposition;
                            }

                            return shapedetector$shapedetectorcollection;
                        }
                    }
                }
            }
        }

        int kx = this.d.getHighestBlockYAt(HeightMap.Type.MOTION_BLOCKING, WorldGenEndTrophy.a).getY();

        for(int lx = kx; lx >= 0; --lx) {
            ShapeDetector.ShapeDetectorCollection shapedetector$shapedetectorcollection1 = this.f.a(this.d, new BlockPosition(WorldGenEndTrophy.a.getX(), lx, WorldGenEndTrophy.a.getZ()));
            if (shapedetector$shapedetectorcollection1 != null) {
                if (this.o == null) {
                    this.o = shapedetector$shapedetectorcollection1.a(3, 3, 3).getPosition();
                }

                return shapedetector$shapedetectorcollection1;
            }
        }

        return null;
    }

    private boolean a(int ix, int jx, int kx, int lx) {
        if (this.b(ix, jx, kx, lx)) {
            return true;
        } else {
            this.c(ix, jx, kx, lx);
            return false;
        }
    }

    private boolean b(int ix, int jx, int kx, int lx) {
        boolean flag = true;

        for(int i1 = ix; i1 <= jx; ++i1) {
            for(int j1 = kx; j1 <= lx; ++j1) {
                Chunk chunk = this.d.getChunkAt(i1, j1);
                flag &= chunk.i() == ChunkStatus.POSTPROCESSED;
            }
        }

        return flag;
    }

    private void c(int ix, int jx, int kx, int lx) {
        for(int i1 = ix - 1; i1 <= jx + 1; ++i1) {
            this.d.getChunkAt(i1, kx - 1);
            this.d.getChunkAt(i1, lx + 1);
        }

        for(int j1 = kx - 1; j1 <= lx + 1; ++j1) {
            this.d.getChunkAt(ix - 1, j1);
            this.d.getChunkAt(jx + 1, j1);
        }

    }

    private void k() {
        HashSet hashset = Sets.newHashSet();

        for(EntityPlayer entityplayer : this.d.b(EntityPlayer.class, b)) {
            this.c.addPlayer(entityplayer);
            hashset.add(entityplayer);
        }

        HashSet hashset1 = Sets.newHashSet(this.c.getPlayers());
        hashset1.removeAll(hashset);

        for(EntityPlayer entityplayer1 : hashset1) {
            this.c.removePlayer(entityplayer1);
        }

    }

    private void l() {
        this.i = 0;
        this.h = 0;

        for(WorldGenEnder.Spike worldgenender$spike : WorldGenDecoratorSpike.a(this.d)) {
            this.h += this.d.a(EntityEnderCrystal.class, worldgenender$spike.f()).size();
        }

        a.debug("Found {} end crystals still alive", this.h);
    }

    public void a(EntityEnderDragon entityenderdragon) {
        if (entityenderdragon.getUniqueID().equals(this.m)) {
            this.c.setProgress(0.0F);
            this.c.setVisible(false);
            this.a(true);
            this.m();
            if (!this.l) {
                this.d.setTypeUpdate(this.d.getHighestBlockYAt(HeightMap.Type.MOTION_BLOCKING, WorldGenEndTrophy.a), Blocks.DRAGON_EGG.getBlockData());
            }

            this.l = true;
            this.k = true;
        }

    }

    private void m() {
        if (!this.e.isEmpty()) {
            int ix = this.e.remove(this.e.size() - 1);
            int jx = (int)(96.0D * Math.cos(2.0D * (-Math.PI + 0.15707963267948966D * (double)ix)));
            int kx = (int)(96.0D * Math.sin(2.0D * (-Math.PI + 0.15707963267948966D * (double)ix)));
            this.a(new BlockPosition(jx, 75, kx));
        }
    }

    private void a(BlockPosition blockposition) {
        this.d.triggerEffect(3000, blockposition, 0);
        WorldGenerator.ax.generate(this.d, this.d.getChunkProviderServer().getChunkGenerator(), new Random(), blockposition, new WorldGenEndGatewayConfiguration(false));
    }

    private void a(boolean flag) {
        WorldGenEndTrophy worldgenendtrophy = new WorldGenEndTrophy(flag);
        if (this.o == null) {
            for(this.o = this.d.getHighestBlockYAt(HeightMap.Type.MOTION_BLOCKING_NO_LEAVES, WorldGenEndTrophy.a).down(); this.d.getType(this.o).getBlock() == Blocks.BEDROCK && this.o.getY() > this.d.getSeaLevel(); this.o = this.o.down()) {
                ;
            }
        }

        worldgenendtrophy.a(this.d, this.d.getChunkProviderServer().getChunkGenerator(), new Random(), this.o, WorldGenFeatureConfiguration.e);
    }

    private EntityEnderDragon n() {
        this.d.getChunkAtWorldCoords(new BlockPosition(0, 128, 0));
        EntityEnderDragon entityenderdragon = new EntityEnderDragon(this.d);
        entityenderdragon.getDragonControllerManager().setControllerPhase(DragonControllerPhase.a);
        entityenderdragon.setPositionRotation(0.0D, 128.0D, 0.0D, this.d.random.nextFloat() * 360.0F, 0.0F);
        this.d.addEntity(entityenderdragon);
        this.m = entityenderdragon.getUniqueID();
        return entityenderdragon;
    }

    public void b(EntityEnderDragon entityenderdragon) {
        if (entityenderdragon.getUniqueID().equals(this.m)) {
            this.c.setProgress(entityenderdragon.getHealth() / entityenderdragon.getMaxHealth());
            this.g = 0;
            if (entityenderdragon.hasCustomName()) {
                this.c.a(entityenderdragon.getScoreboardDisplayName());
            }
        }

    }

    public int c() {
        return this.h;
    }

    public void a(EntityEnderCrystal entityendercrystal, DamageSource damagesource) {
        if (this.p != null && this.r.contains(entityendercrystal)) {
            a.debug("Aborting respawn sequence");
            this.p = null;
            this.q = 0;
            this.f();
            this.a(true);
        } else {
            this.l();
            Entity entity = this.d.getEntity(this.m);
            if (entity instanceof EntityEnderDragon) {
                ((EntityEnderDragon)entity).a(entityendercrystal, new BlockPosition(entityendercrystal), damagesource);
            }
        }

    }

    public boolean d() {
        return this.l;
    }

    public void e() {
        if (this.k && this.p == null) {
            BlockPosition blockposition = this.o;
            if (blockposition == null) {
                a.debug("Tried to respawn, but need to find the portal first.");
                ShapeDetector.ShapeDetectorCollection shapedetector$shapedetectorcollection = this.j();
                if (shapedetector$shapedetectorcollection == null) {
                    a.debug("Couldn't find a portal, so we made one.");
                    this.a(true);
                } else {
                    a.debug("Found the exit portal & temporarily using it.");
                }

                blockposition = this.o;
            }

            ArrayList arraylist = Lists.newArrayList();
            BlockPosition blockposition1 = blockposition.up(1);

            for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                List list = this.d.a(EntityEnderCrystal.class, new AxisAlignedBB(blockposition1.shift(enumdirection, 2)));
                if (list.isEmpty()) {
                    return;
                }

                arraylist.addAll(list);
            }

            a.debug("Found all crystals, respawning dragon.");
            this.a(arraylist);
        }

    }

    private void a(List<EntityEnderCrystal> list) {
        if (this.k && this.p == null) {
            for(ShapeDetector.ShapeDetectorCollection shapedetector$shapedetectorcollection = this.j(); shapedetector$shapedetectorcollection != null; shapedetector$shapedetectorcollection = this.j()) {
                for(int ix = 0; ix < this.f.c(); ++ix) {
                    for(int jx = 0; jx < this.f.b(); ++jx) {
                        for(int kx = 0; kx < this.f.a(); ++kx) {
                            ShapeDetectorBlock shapedetectorblock = shapedetector$shapedetectorcollection.a(ix, jx, kx);
                            if (shapedetectorblock.a().getBlock() == Blocks.BEDROCK || shapedetectorblock.a().getBlock() == Blocks.END_PORTAL) {
                                this.d.setTypeUpdate(shapedetectorblock.getPosition(), Blocks.END_STONE.getBlockData());
                            }
                        }
                    }
                }
            }

            this.p = EnumDragonRespawn.START;
            this.q = 0;
            this.a(false);
            this.r = list;
        }

    }

    public void f() {
        for(WorldGenEnder.Spike worldgenender$spike : WorldGenDecoratorSpike.a(this.d)) {
            for(EntityEnderCrystal entityendercrystal : this.d.a(EntityEnderCrystal.class, worldgenender$spike.f())) {
                entityendercrystal.setInvulnerable(false);
                entityendercrystal.setBeamTarget((BlockPosition)null);
            }
        }

    }

    static enum LoadState {
        UNKNOWN,
        NOT_LOADED,
        LOADED;

        private LoadState() {
        }
    }

    class b {
        private EnderDragonBattle.LoadState b;

        private b() {
            this.b = EnderDragonBattle.LoadState.UNKNOWN;
        }

        private boolean a() {
            if (this.b == EnderDragonBattle.LoadState.UNKNOWN) {
                this.b = EnderDragonBattle.this.a(-8, 8, -8, 8) ? EnderDragonBattle.LoadState.LOADED : EnderDragonBattle.LoadState.NOT_LOADED;
            }

            return this.b == EnderDragonBattle.LoadState.LOADED;
        }
    }
}
