package net.minecraft.server;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import com.google.gson.JsonElement;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.datafixers.DataFixer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.longs.LongIterator;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class MinecraftServer implements IAsyncTaskHandler, IMojangStatistics, ICommandListener, Runnable {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final File a = new File("usercache.json");
    public Convertable convertable;
    private final MojangStatisticsGenerator i = new MojangStatisticsGenerator("server", this, SystemUtils.b());
    public File universe;
    private final List<ITickable> k = Lists.newArrayList();
    public final MethodProfiler methodProfiler = new MethodProfiler();
    private final ServerConnection l;
    private final ServerPing m = new ServerPing();
    private final Random n = new Random();
    public final DataFixer dataConverterManager;
    private String serverIp;
    private int q = -1;
    public final Map<DimensionManager, WorldServer> worldServer = Maps.newIdentityHashMap();
    private PlayerList s;
    private boolean isRunning = true;
    private boolean isStopped;
    private int ticks;
    protected final Proxy c;
    private IChatBaseComponent w;
    private int x;
    private boolean onlineMode;
    private boolean z;
    private boolean spawnAnimals;
    private boolean spawnNPCs;
    private boolean pvpMode;
    private boolean allowFlight;
    private String motd;
    private int F;
    private int G;
    public final long[] d = new long[100];
    protected final Map<DimensionManager, long[]> e = Maps.newIdentityHashMap();
    private KeyPair H;
    private String I;
    private String J;
    private boolean demoMode;
    private boolean M;
    private String N = "";
    private String O = "";
    private boolean P;
    private long Q;
    private IChatBaseComponent R;
    private boolean S;
    private boolean T;
    private final YggdrasilAuthenticationService U;
    private final MinecraftSessionService V;
    private final GameProfileRepository W;
    private final UserCache X;
    private long Y;
    protected final Queue<FutureTask<?>> f = Queues.newConcurrentLinkedQueue();
    private Thread serverThread;
    private long aa = SystemUtils.b();
    private final IReloadableResourceManager ac = new ResourceManager(EnumResourcePackType.SERVER_DATA);
    private final ResourcePackRepository<ResourcePackLoader> resourcePackRepository = new ResourcePackRepository<ResourcePackLoader>(ResourcePackLoader::new);
    private ResourcePackSourceFolder resourcePackFolder;
    public CommandDispatcher commandDispatcher;
    private final CraftingManager ag = new CraftingManager();
    private final TagRegistry ah = new TagRegistry();
    private final ScoreboardServer ai = new ScoreboardServer(this);
    private final BossBattleCustomData aj = new BossBattleCustomData(this);
    private final LootTableRegistry ak = new LootTableRegistry();
    private final AdvancementDataWorld al = new AdvancementDataWorld();
    private final CustomFunctionData am = new CustomFunctionData(this);
    private boolean an;
    private boolean forceUpgrade;
    private float ap;

    public MinecraftServer(@Nullable File file1, Proxy proxy, DataFixer datafixer, CommandDispatcher commanddispatcher, YggdrasilAuthenticationService yggdrasilauthenticationservice, MinecraftSessionService minecraftsessionservice, GameProfileRepository gameprofilerepository, UserCache usercache) {
        this.c = proxy;
        this.commandDispatcher = commanddispatcher;
        this.U = yggdrasilauthenticationservice;
        this.V = minecraftsessionservice;
        this.W = gameprofilerepository;
        this.X = usercache;
        this.universe = file1;
        this.l = file1 == null ? null : new ServerConnection(this);
        this.convertable = file1 == null ? null : new WorldLoaderServer(file1.toPath(), file1.toPath().resolve("../backups"), datafixer);
        this.dataConverterManager = datafixer;
        this.ac.a(this.ah);
        this.ac.a(this.ag);
        this.ac.a(this.ak);
        this.ac.a(this.am);
        this.ac.a(this.al);
    }

    public abstract boolean init() throws IOException;

    public void convertWorld(String sx) {
        if (this.getConvertable().isConvertable(sx)) {
            LOGGER.info("Converting map!");
            this.b(new ChatMessage("menu.convertingLevel", new Object[0]));
            this.getConvertable().convert(sx, new IProgressUpdate() {
                private long b = SystemUtils.b();

                public void a(IChatBaseComponent var1) {
                }

                public void a(int k) {
                    if (SystemUtils.b() - this.b >= 1000L) {
                        this.b = SystemUtils.b();
                        MinecraftServer.LOGGER.info("Converting... {}%", k);
                    }

                }

                public void c(IChatBaseComponent var1) {
                }
            });
        }

        if (this.forceUpgrade) {
            LOGGER.info("Forcing world upgrade!");
            WorldData worlddata = this.getConvertable().c(this.getWorld());
            if (worlddata != null) {
                WorldUpgrader worldupgrader = new WorldUpgrader(this.getWorld(), this.getConvertable(), worlddata);
                IChatBaseComponent ichatbasecomponent = null;

                while(!worldupgrader.b()) {
                    IChatBaseComponent ichatbasecomponent1 = worldupgrader.g();
                    if (ichatbasecomponent != ichatbasecomponent1) {
                        ichatbasecomponent = ichatbasecomponent1;
                        LOGGER.info(worldupgrader.g().getString());
                    }

                    int ix = worldupgrader.d();
                    if (ix > 0) {
                        int j = worldupgrader.e() + worldupgrader.f();
                        LOGGER.info("{}% completed ({} / {} chunks)...", MathHelper.d((float)j / (float)ix * 100.0F), j, ix);
                    }

                    if (this.isStopped()) {
                        worldupgrader.a();
                    } else {
                        try {
                            Thread.sleep(1000L);
                        } catch (InterruptedException var8) {
                            ;
                        }
                    }
                }
            }
        }

    }

    protected synchronized void b(IChatBaseComponent ichatbasecomponent) {
        this.R = ichatbasecomponent;
    }

    public void a(String sx, String s1, long ix, WorldType worldtype, JsonElement jsonelement) {
        this.convertWorld(sx);
        this.b(new ChatMessage("menu.loadingLevel", new Object[0]));
        IDataManager idatamanager = this.getConvertable().a(sx, this);
        this.a(this.getWorld(), idatamanager);
        WorldData worlddata = idatamanager.getWorldData();
        WorldSettings worldsettings;
        if (worlddata == null) {
            if (this.L()) {
                worldsettings = DemoWorldServer.a;
            } else {
                worldsettings = new WorldSettings(ix, this.getGamemode(), this.getGenerateStructures(), this.isHardcore(), worldtype);
                worldsettings.setGeneratorSettings(jsonelement);
                if (this.M) {
                    worldsettings.a();
                }
            }

            worlddata = new WorldData(worldsettings, s1);
        } else {
            worlddata.a(s1);
            worldsettings = new WorldSettings(worlddata);
        }

        this.a(idatamanager.getDirectory(), worlddata);
        PersistentCollection persistentcollection = new PersistentCollection(idatamanager);
        this.a(idatamanager, persistentcollection, worlddata, worldsettings);
        this.a(this.getDifficulty());
        this.a(persistentcollection);
    }

    protected void a(IDataManager idatamanager, PersistentCollection persistentcollection, WorldData worlddata, WorldSettings worldsettings) {
        if (this.L()) {
            this.worldServer.put(DimensionManager.OVERWORLD, (new DemoWorldServer(this, idatamanager, persistentcollection, worlddata, DimensionManager.OVERWORLD, this.methodProfiler)).i_());
        } else {
            this.worldServer.put(DimensionManager.OVERWORLD, (new WorldServer(this, idatamanager, persistentcollection, worlddata, DimensionManager.OVERWORLD, this.methodProfiler)).i_());
        }

        WorldServer worldserver = this.getWorldServer(DimensionManager.OVERWORLD);
        worldserver.a(worldsettings);
        worldserver.addIWorldAccess(new WorldManager(this, worldserver));
        if (!this.H()) {
            worldserver.getWorldData().setGameType(this.getGamemode());
        }

        SecondaryWorldServer secondaryworldserver = (new SecondaryWorldServer(this, idatamanager, DimensionManager.NETHER, worldserver, this.methodProfiler)).b();
        this.worldServer.put(DimensionManager.NETHER, secondaryworldserver);
        secondaryworldserver.addIWorldAccess(new WorldManager(this, secondaryworldserver));
        if (!this.H()) {
            secondaryworldserver.getWorldData().setGameType(this.getGamemode());
        }

        SecondaryWorldServer secondaryworldserver1 = (new SecondaryWorldServer(this, idatamanager, DimensionManager.THE_END, worldserver, this.methodProfiler)).b();
        this.worldServer.put(DimensionManager.THE_END, secondaryworldserver1);
        secondaryworldserver1.addIWorldAccess(new WorldManager(this, secondaryworldserver1));
        if (!this.H()) {
            secondaryworldserver1.getWorldData().setGameType(this.getGamemode());
        }

        this.getPlayerList().setPlayerFileData(worldserver);
        if (worlddata.P() != null) {
            this.aP().a(worlddata.P());
        }

    }

    protected void a(File file1, WorldData worlddata) {
        this.resourcePackRepository.a(new ResourcePackSourceVanilla());
        this.resourcePackFolder = new ResourcePackSourceFolder(new File(file1, "datapacks"));
        this.resourcePackRepository.a(this.resourcePackFolder);
        this.resourcePackRepository.a();
        ArrayList arraylist = Lists.newArrayList();

        for(String sx : worlddata.O()) {
            ResourcePackLoader resourcepackloader = this.resourcePackRepository.a(sx);
            if (resourcepackloader != null) {
                arraylist.add(resourcepackloader);
            } else {
                LOGGER.warn("Missing data pack {}", sx);
            }
        }

        this.resourcePackRepository.a(arraylist);
        this.a(worlddata);
    }

    protected void a(PersistentCollection persistentcollection) {
        boolean flag = true;
        boolean flag1 = true;
        boolean flag2 = true;
        boolean flag3 = true;
        boolean flag4 = true;
        this.b(new ChatMessage("menu.generatingTerrain", new Object[0]));
        WorldServer worldserver = this.getWorldServer(DimensionManager.OVERWORLD);
        LOGGER.info("Preparing start region for dimension " + DimensionManager.a(worldserver.worldProvider.getDimensionManager()));
        BlockPosition blockposition = worldserver.getSpawn();
        ArrayList arraylist = Lists.newArrayList();
        Set set = Sets.newConcurrentHashSet();
        Stopwatch stopwatch = Stopwatch.createStarted();

        for(int ix = -192; ix <= 192 && this.isRunning(); ix += 16) {
            for(int j = -192; j <= 192 && this.isRunning(); j += 16) {
                arraylist.add(new ChunkCoordIntPair(blockposition.getX() + ix >> 4, blockposition.getZ() + j >> 4));
            }

            CompletableFuture completablefuture = worldserver.getChunkProviderServer().a(arraylist, (chunk) -> {
                set.add(chunk.getPos());
            });

            while(!completablefuture.isDone()) {
                try {
                    completablefuture.get(1L, TimeUnit.SECONDS);
                } catch (InterruptedException interruptedexception) {
                    throw new RuntimeException(interruptedexception);
                } catch (ExecutionException executionexception) {
                    if (executionexception.getCause() instanceof RuntimeException) {
                        throw (RuntimeException)executionexception.getCause();
                    }

                    throw new RuntimeException(executionexception.getCause());
                } catch (TimeoutException var22) {
                    this.a(new ChatMessage("menu.preparingSpawn", new Object[0]), set.size() * 100 / 625);
                }
            }

            this.a(new ChatMessage("menu.preparingSpawn", new Object[0]), set.size() * 100 / 625);
        }

        LOGGER.info("Time elapsed: {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));

        for(DimensionManager dimensionmanager : DimensionManager.b()) {
            ForcedChunk forcedchunk = (ForcedChunk)persistentcollection.get(dimensionmanager, ForcedChunk::new, "chunks");
            if (forcedchunk != null) {
                WorldServer worldserver1 = this.getWorldServer(dimensionmanager);
                LongIterator longiterator = forcedchunk.a().iterator();

                while(longiterator.hasNext()) {
                    this.a(new ChatMessage("menu.loadingForcedChunks", new Object[]{dimensionmanager}), forcedchunk.a().size() * 100 / 625);
                    long kx = longiterator.nextLong();
                    ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(kx);
                    worldserver1.getChunkProviderServer().getChunkAt(chunkcoordintpair.x, chunkcoordintpair.z, true, true);
                }
            }
        }

        this.l();
    }

    protected void a(String sx, IDataManager idatamanager) {
        File file1 = new File(idatamanager.getDirectory(), "resources.zip");
        if (file1.isFile()) {
            try {
                this.setResourcePack("level://" + URLEncoder.encode(sx, StandardCharsets.UTF_8.toString()) + "/" + "resources.zip", "");
            } catch (UnsupportedEncodingException var5) {
                LOGGER.warn("Something went wrong url encoding {}", sx);
            }
        }

    }

    public abstract boolean getGenerateStructures();

    public abstract EnumGamemode getGamemode();

    public abstract EnumDifficulty getDifficulty();

    public abstract boolean isHardcore();

    public abstract int j();

    public abstract boolean k();

    protected void a(IChatBaseComponent ichatbasecomponent, int ix) {
        this.w = ichatbasecomponent;
        this.x = ix;
        LOGGER.info("{}: {}%", ichatbasecomponent.getString(), ix);
    }

    protected void l() {
        this.w = null;
        this.x = 0;
    }

    protected void saveChunks(boolean flag) {
        for(WorldServer worldserver : this.getWorlds()) {
            if (worldserver != null) {
                if (!flag) {
                    LOGGER.info("Saving chunks for level '{}'/{}", worldserver.getWorldData().getName(), DimensionManager.a(worldserver.worldProvider.getDimensionManager()));
                }

                try {
                    worldserver.save(true, (IProgressUpdate)null);
                } catch (ExceptionWorldConflict exceptionworldconflict) {
                    LOGGER.warn(exceptionworldconflict.getMessage());
                }
            }
        }

    }

    protected void stop() {
        LOGGER.info("Stopping server");
        if (this.getServerConnection() != null) {
            this.getServerConnection().b();
        }

        if (this.s != null) {
            LOGGER.info("Saving players");
            this.s.savePlayers();
            this.s.u();
        }

        LOGGER.info("Saving worlds");

        for(WorldServer worldserver : this.getWorlds()) {
            if (worldserver != null) {
                worldserver.savingDisabled = false;
            }
        }

        this.saveChunks(false);

        for(WorldServer worldserver1 : this.getWorlds()) {
            if (worldserver1 != null) {
                worldserver1.close();
            }
        }

        if (this.i.d()) {
            this.i.e();
        }

    }

    public String getServerIp() {
        return this.serverIp;
    }

    public void b(String sx) {
        this.serverIp = sx;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void safeShutdown() {
        this.isRunning = false;
    }

    private boolean aT() {
        return SystemUtils.b() < this.aa;
    }

    public void run() {
        try {
            if (this.init()) {
                this.aa = SystemUtils.b();
                this.m.setMOTD(new ChatComponentText(this.motd));
                this.m.setServerInfo(new ServerPing.ServerData("1.13.1", 401));
                this.a(this.m);

                while(this.isRunning) {
                    long ix = SystemUtils.b() - this.aa;
                    if (ix > 2000L && this.aa - this.Q >= 15000L) {
                        long j = ix / 50L;
                        LOGGER.warn("Can't keep up! Is the server overloaded? Running {}ms or {} ticks behind", ix, j);
                        this.aa += j * 50L;
                        this.Q = this.aa;
                    }

                    this.a(this::aT);
                    this.aa += 50L;

                    while(this.aT()) {
                        Thread.sleep(1L);
                    }

                    this.P = true;
                }
            } else {
                this.a((CrashReport)null);
            }
        } catch (Throwable throwable1) {
            LOGGER.error("Encountered an unexpected exception", throwable1);
            CrashReport crashreport;
            if (throwable1 instanceof ReportedException) {
                crashreport = this.b(((ReportedException)throwable1).a());
            } else {
                crashreport = this.b(new CrashReport("Exception in server tick loop", throwable1));
            }

            File file1 = new File(new File(this.s(), "crash-reports"), "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-server.txt");
            if (crashreport.a(file1)) {
                LOGGER.error("This crash report has been saved to: {}", file1.getAbsolutePath());
            } else {
                LOGGER.error("We were unable to save this crash report to disk.");
            }

            this.a(crashreport);
        } finally {
            try {
                this.isStopped = true;
                this.stop();
            } catch (Throwable throwable) {
                LOGGER.error("Exception stopping the server", throwable);
            } finally {
                this.t();
            }

        }

    }

    public void a(ServerPing serverping) {
        File file1 = this.c("server-icon.png");
        if (!file1.exists()) {
            file1 = this.getConvertable().b(this.getWorld(), "icon.png");
        }

        if (file1.isFile()) {
            ByteBuf bytebuf = Unpooled.buffer();

            try {
                BufferedImage bufferedimage = ImageIO.read(file1);
                Validate.validState(bufferedimage.getWidth() == 64, "Must be 64 pixels wide", new Object[0]);
                Validate.validState(bufferedimage.getHeight() == 64, "Must be 64 pixels high", new Object[0]);
                ImageIO.write(bufferedimage, "PNG", new ByteBufOutputStream(bytebuf));
                ByteBuffer bytebuffer = Base64.getEncoder().encode(bytebuf.nioBuffer());
                serverping.setFavicon("data:image/png;base64," + StandardCharsets.UTF_8.decode(bytebuffer));
            } catch (Exception exception) {
                LOGGER.error("Couldn't load server icon", exception);
            } finally {
                bytebuf.release();
            }
        }

    }

    public File s() {
        return new File(".");
    }

    protected void a(CrashReport var1) {
    }

    public void t() {
    }

    protected void a(BooleanSupplier booleansupplier) {
        long ix = SystemUtils.c();
        ++this.ticks;
        if (this.S) {
            this.S = false;
            this.methodProfiler.a(this.ticks);
        }

        this.methodProfiler.a("root");
        this.b(booleansupplier);
        if (ix - this.Y >= 5000000000L) {
            this.Y = ix;
            this.m.setPlayerSample(new ServerPing.ServerPingPlayerSample(this.z(), this.y()));
            GameProfile[] agameprofile = new GameProfile[Math.min(this.y(), 12)];
            int j = MathHelper.nextInt(this.n, 0, this.y() - agameprofile.length);

            for(int kx = 0; kx < agameprofile.length; ++kx) {
                agameprofile[kx] = ((EntityPlayer)this.s.v().get(j + kx)).getProfile();
            }

            Collections.shuffle(Arrays.asList(agameprofile));
            this.m.b().a(agameprofile);
        }

        if (this.ticks % 900 == 0) {
            this.methodProfiler.a("save");
            this.s.savePlayers();
            this.saveChunks(true);
            this.methodProfiler.e();
        }

        this.methodProfiler.a("snooper");
        if (!this.i.d() && this.ticks > 100) {
            this.i.a();
        }

        if (this.ticks % 6000 == 0) {
            this.i.b();
        }

        this.methodProfiler.e();
        this.methodProfiler.a("tallying");
        long lx = this.d[this.ticks % 100] = SystemUtils.c() - ix;
        this.ap = this.ap * 0.8F + (float)lx / 1000000.0F * 0.19999999F;
        this.methodProfiler.e();
        this.methodProfiler.e();
    }

    public void b(BooleanSupplier booleansupplier) {
        this.methodProfiler.a("jobs");

        FutureTask futuretask;
        while((futuretask = (FutureTask)this.f.poll()) != null) {
            SystemUtils.a(futuretask, LOGGER);
        }

        this.methodProfiler.c("commandFunctions");
        this.getFunctionData().Y_();
        this.methodProfiler.c("levels");

        for(WorldServer worldserver : this.getWorlds()) {
            long ix = SystemUtils.c();
            if (worldserver.worldProvider.getDimensionManager() == DimensionManager.OVERWORLD || this.getAllowNether()) {
                this.methodProfiler.a(() -> {
                    return worldserver.getWorldData().getName();
                });
                if (this.ticks % 20 == 0) {
                    this.methodProfiler.a("timeSync");
                    this.s.a(new PacketPlayOutUpdateTime(worldserver.getTime(), worldserver.getDayTime(), worldserver.getGameRules().getBoolean("doDaylightCycle")), worldserver.worldProvider.getDimensionManager());
                    this.methodProfiler.e();
                }

                this.methodProfiler.a("tick");

                try {
                    worldserver.doTick(booleansupplier);
                } catch (Throwable throwable1) {
                    CrashReport crashreport = CrashReport.a(throwable1, "Exception ticking world");
                    worldserver.a(crashreport);
                    throw new ReportedException(crashreport);
                }

                try {
                    worldserver.tickEntities();
                } catch (Throwable throwable) {
                    CrashReport crashreport1 = CrashReport.a(throwable, "Exception ticking world entities");
                    worldserver.a(crashreport1);
                    throw new ReportedException(crashreport1);
                }

                this.methodProfiler.e();
                this.methodProfiler.a("tracker");
                worldserver.getTracker().updatePlayers();
                this.methodProfiler.e();
                this.methodProfiler.e();
            }

            ((long[])this.e.computeIfAbsent(worldserver.worldProvider.getDimensionManager(), (var0) -> {
                return new long[100];
            }))[this.ticks % 100] = SystemUtils.c() - ix;
        }

        this.methodProfiler.c("connection");
        this.getServerConnection().c();
        this.methodProfiler.c("players");
        this.s.tick();
        this.methodProfiler.c("tickables");

        for(int j = 0; j < this.k.size(); ++j) {
            ((ITickable)this.k.get(j)).Y_();
        }

        this.methodProfiler.e();
    }

    public boolean getAllowNether() {
        return true;
    }

    public void a(ITickable itickable) {
        this.k.add(itickable);
    }

    public static void main(String[] astring) {
        DispenserRegistry.c();

        try {
            boolean flag = true;
            String sx = null;
            String s1 = ".";
            String s2 = null;
            boolean flag1 = false;
            boolean flag2 = false;
            boolean flag3 = false;
            int ix = -1;

            for(int j = 0; j < astring.length; ++j) {
                String s3 = astring[j];
                String s4 = j == astring.length - 1 ? null : astring[j + 1];
                boolean flag4 = false;
                if (!"nogui".equals(s3) && !"--nogui".equals(s3)) {
                    if ("--port".equals(s3) && s4 != null) {
                        flag4 = true;

                        try {
                            ix = Integer.parseInt(s4);
                        } catch (NumberFormatException var15) {
                            ;
                        }
                    } else if ("--singleplayer".equals(s3) && s4 != null) {
                        flag4 = true;
                        sx = s4;
                    } else if ("--universe".equals(s3) && s4 != null) {
                        flag4 = true;
                        s1 = s4;
                    } else if ("--world".equals(s3) && s4 != null) {
                        flag4 = true;
                        s2 = s4;
                    } else if ("--demo".equals(s3)) {
                        flag1 = true;
                    } else if ("--bonusChest".equals(s3)) {
                        flag2 = true;
                    } else if ("--forceUpgrade".equals(s3)) {
                        flag3 = true;
                    }
                } else {
                    flag = false;
                }

                if (flag4) {
                    ++j;
                }
            }

            YggdrasilAuthenticationService yggdrasilauthenticationservice = new YggdrasilAuthenticationService(Proxy.NO_PROXY, UUID.randomUUID().toString());
            MinecraftSessionService minecraftsessionservice = yggdrasilauthenticationservice.createMinecraftSessionService();
            GameProfileRepository gameprofilerepository = yggdrasilauthenticationservice.createProfileRepository();
            UserCache usercache = new UserCache(gameprofilerepository, new File(s1, a.getName()));
            final DedicatedServer dedicatedserver = new DedicatedServer(new File(s1), DataConverterRegistry.a(), yggdrasilauthenticationservice, minecraftsessionservice, gameprofilerepository, usercache);
            if (sx != null) {
                dedicatedserver.h(sx);
            }

            if (s2 != null) {
                dedicatedserver.setWorld(s2);
            }

            if (ix >= 0) {
                dedicatedserver.setPort(ix);
            }

            if (flag1) {
                dedicatedserver.c(true);
            }

            if (flag2) {
                dedicatedserver.d(true);
            }

            if (flag && !GraphicsEnvironment.isHeadless()) {
                dedicatedserver.aW();
            }

            if (flag3) {
                dedicatedserver.setForceUpgrade(true);
            }

            dedicatedserver.v();
            Thread thread = new Thread("Server Shutdown Thread") {
                public void run() {
                    dedicatedserver.stop();
                }
            };
            thread.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LOGGER));
            Runtime.getRuntime().addShutdownHook(thread);
        } catch (Exception exception) {
            LOGGER.fatal("Failed to start the minecraft server", exception);
        }

    }

    protected void setForceUpgrade(boolean flag) {
        this.forceUpgrade = flag;
    }

    public void v() {
        this.serverThread = new Thread(this, "Server thread");
        this.serverThread.setUncaughtExceptionHandler((var0, throwable) -> {
            LOGGER.error(throwable);
        });
        this.serverThread.start();
    }

    public File c(String sx) {
        return new File(this.s(), sx);
    }

    public void info(String sx) {
        LOGGER.info(sx);
    }

    public void warning(String sx) {
        LOGGER.warn(sx);
    }

    public WorldServer getWorldServer(DimensionManager dimensionmanager) {
        return (WorldServer)this.worldServer.get(dimensionmanager);
    }

    public Iterable<WorldServer> getWorlds() {
        return this.worldServer.values();
    }

    public String getVersion() {
        return "1.13.1";
    }

    public int y() {
        return this.s.getPlayerCount();
    }

    public int z() {
        return this.s.getMaxPlayers();
    }

    public String[] getPlayers() {
        return this.s.f();
    }

    public boolean isDebugging() {
        return false;
    }

    public void f(String sx) {
        LOGGER.error(sx);
    }

    public void g(String sx) {
        if (this.isDebugging()) {
            LOGGER.info(sx);
        }

    }

    public String getServerModName() {
        return "vanilla";
    }

    public CrashReport b(CrashReport crashreport) {
        crashreport.g().a("Profiler Position", () -> {
            return this.methodProfiler.a() ? this.methodProfiler.f() : "N/A (disabled)";
        });
        if (this.s != null) {
            crashreport.g().a("Player Count", () -> {
                return this.s.getPlayerCount() + " / " + this.s.getMaxPlayers() + "; " + this.s.v();
            });
        }

        crashreport.g().a("Data Packs", () -> {
            StringBuilder stringbuilder = new StringBuilder();

            for(ResourcePackLoader resourcepackloader : this.resourcePackRepository.d()) {
                if (stringbuilder.length() > 0) {
                    stringbuilder.append(", ");
                }

                stringbuilder.append(resourcepackloader.e());
                if (!resourcepackloader.c().a()) {
                    stringbuilder.append(" (incompatible)");
                }
            }

            return stringbuilder.toString();
        });
        return crashreport;
    }

    public boolean D() {
        return this.universe != null;
    }

    public void sendMessage(IChatBaseComponent ichatbasecomponent) {
        LOGGER.info(ichatbasecomponent.getString());
    }

    public KeyPair E() {
        return this.H;
    }

    public int F() {
        return this.q;
    }

    public void setPort(int ix) {
        this.q = ix;
    }

    public String G() {
        return this.I;
    }

    public void h(String sx) {
        this.I = sx;
    }

    public boolean H() {
        return this.I != null;
    }

    public String getWorld() {
        return this.J;
    }

    public void setWorld(String sx) {
        this.J = sx;
    }

    public void a(KeyPair keypair) {
        this.H = keypair;
    }

    public void a(EnumDifficulty enumdifficulty) {
        for(WorldServer worldserver : this.getWorlds()) {
            if (worldserver.getWorldData().isHardcore()) {
                worldserver.getWorldData().setDifficulty(EnumDifficulty.HARD);
                worldserver.setSpawnFlags(true, true);
            } else if (this.H()) {
                worldserver.getWorldData().setDifficulty(enumdifficulty);
                worldserver.setSpawnFlags(worldserver.getDifficulty() != EnumDifficulty.PEACEFUL, true);
            } else {
                worldserver.getWorldData().setDifficulty(enumdifficulty);
                worldserver.setSpawnFlags(this.getSpawnMonsters(), this.spawnAnimals);
            }
        }

    }

    public boolean getSpawnMonsters() {
        return true;
    }

    public boolean L() {
        return this.demoMode;
    }

    public void c(boolean flag) {
        this.demoMode = flag;
    }

    public void d(boolean flag) {
        this.M = flag;
    }

    public Convertable getConvertable() {
        return this.convertable;
    }

    public String getResourcePack() {
        return this.N;
    }

    public String getResourcePackHash() {
        return this.O;
    }

    public void setResourcePack(String sx, String s1) {
        this.N = sx;
        this.O = s1;
    }

    public void a(MojangStatisticsGenerator mojangstatisticsgenerator) {
        mojangstatisticsgenerator.a("whitelist_enabled", false);
        mojangstatisticsgenerator.a("whitelist_count", 0);
        if (this.s != null) {
            mojangstatisticsgenerator.a("players_current", this.y());
            mojangstatisticsgenerator.a("players_max", this.z());
            mojangstatisticsgenerator.a("players_seen", this.s.getSeenPlayers().length);
        }

        mojangstatisticsgenerator.a("uses_auth", this.onlineMode);
        mojangstatisticsgenerator.a("gui_state", this.ag() ? "enabled" : "disabled");
        mojangstatisticsgenerator.a("run_time", (SystemUtils.b() - mojangstatisticsgenerator.g()) / 60L * 1000L);
        mojangstatisticsgenerator.a("avg_tick_ms", (int)(MathHelper.a(this.d) * 1.0E-6D));
        int ix = 0;

        for(WorldServer worldserver : this.getWorlds()) {
            if (worldserver != null) {
                WorldData worlddata = worldserver.getWorldData();
                mojangstatisticsgenerator.a("world[" + ix + "][dimension]", worldserver.worldProvider.getDimensionManager());
                mojangstatisticsgenerator.a("world[" + ix + "][mode]", worlddata.getGameType());
                mojangstatisticsgenerator.a("world[" + ix + "][difficulty]", worldserver.getDifficulty());
                mojangstatisticsgenerator.a("world[" + ix + "][hardcore]", worlddata.isHardcore());
                mojangstatisticsgenerator.a("world[" + ix + "][generator_name]", worlddata.getType().name());
                mojangstatisticsgenerator.a("world[" + ix + "][generator_version]", worlddata.getType().getVersion());
                mojangstatisticsgenerator.a("world[" + ix + "][height]", this.F);
                mojangstatisticsgenerator.a("world[" + ix + "][chunks_loaded]", worldserver.getChunkProviderServer().g());
                ++ix;
            }
        }

        mojangstatisticsgenerator.a("worlds", ix);
    }

    public boolean getSnooperEnabled() {
        return true;
    }

    public abstract boolean Q();

    public boolean getOnlineMode() {
        return this.onlineMode;
    }

    public void setOnlineMode(boolean flag) {
        this.onlineMode = flag;
    }

    public boolean S() {
        return this.z;
    }

    public void f(boolean flag) {
        this.z = flag;
    }

    public boolean getSpawnAnimals() {
        return this.spawnAnimals;
    }

    public void setSpawnAnimals(boolean flag) {
        this.spawnAnimals = flag;
    }

    public boolean getSpawnNPCs() {
        return this.spawnNPCs;
    }

    public abstract boolean V();

    public void setSpawnNPCs(boolean flag) {
        this.spawnNPCs = flag;
    }

    public boolean getPVP() {
        return this.pvpMode;
    }

    public void setPVP(boolean flag) {
        this.pvpMode = flag;
    }

    public boolean getAllowFlight() {
        return this.allowFlight;
    }

    public void setAllowFlight(boolean flag) {
        this.allowFlight = flag;
    }

    public abstract boolean getEnableCommandBlock();

    public String getMotd() {
        return this.motd;
    }

    public void setMotd(String sx) {
        this.motd = sx;
    }

    public int getMaxBuildHeight() {
        return this.F;
    }

    public void b(int ix) {
        this.F = ix;
    }

    public boolean isStopped() {
        return this.isStopped;
    }

    public PlayerList getPlayerList() {
        return this.s;
    }

    public void a(PlayerList playerlist) {
        this.s = playerlist;
    }

    public abstract boolean ad();

    public void setGamemode(EnumGamemode enumgamemode) {
        for(WorldServer worldserver : this.getWorlds()) {
            worldserver.getWorldData().setGameType(enumgamemode);
        }

    }

    public ServerConnection getServerConnection() {
        return this.l;
    }

    public boolean ag() {
        return false;
    }

    public abstract boolean a(EnumGamemode var1, boolean var2, int var3);

    public int ah() {
        return this.ticks;
    }

    public void ai() {
        this.S = true;
    }

    public int getSpawnProtection() {
        return 16;
    }

    public boolean a(World var1, BlockPosition var2, EntityHuman var3) {
        return false;
    }

    public void setForceGamemode(boolean flag) {
        this.T = flag;
    }

    public boolean getForceGamemode() {
        return this.T;
    }

    public int getIdleTimeout() {
        return this.G;
    }

    public void setIdleTimeout(int ix) {
        this.G = ix;
    }

    public MinecraftSessionService ap() {
        return this.V;
    }

    public GameProfileRepository getGameProfileRepository() {
        return this.W;
    }

    public UserCache getUserCache() {
        return this.X;
    }

    public ServerPing getServerPing() {
        return this.m;
    }

    public void at() {
        this.Y = 0L;
    }

    public int au() {
        return 29999984;
    }

    public <V> ListenableFuture<V> a(Callable<V> callable) {
        Validate.notNull(callable);
        if (!this.isMainThread() && !this.isStopped()) {
            ListenableFutureTask listenablefuturetask = ListenableFutureTask.create(callable);
            this.f.add(listenablefuturetask);
            return listenablefuturetask;
        } else {
            try {
                return Futures.immediateFuture(callable.call());
            } catch (Exception exception) {
                return Futures.immediateFailedCheckedFuture(exception);
            }
        }
    }

    public ListenableFuture<Object> postToMainThread(Runnable runnable) {
        Validate.notNull(runnable);
        return this.a(Executors.callable(runnable));
    }

    public boolean isMainThread() {
        return Thread.currentThread() == this.serverThread;
    }

    public int aw() {
        return 256;
    }

    public long ax() {
        return this.aa;
    }

    public Thread ay() {
        return this.serverThread;
    }

    public DataFixer az() {
        return this.dataConverterManager;
    }

    public int a(@Nullable WorldServer worldserver) {
        return worldserver != null ? worldserver.getGameRules().c("spawnRadius") : 10;
    }

    public AdvancementDataWorld getAdvancementData() {
        return this.al;
    }

    public CustomFunctionData getFunctionData() {
        return this.am;
    }

    public void reload() {
        if (!this.isMainThread()) {
            this.postToMainThread(this::reload);
        } else {
            this.getPlayerList().savePlayers();
            this.resourcePackRepository.a();
            this.a(this.getWorldServer(DimensionManager.OVERWORLD).getWorldData());
            this.getPlayerList().reload();
        }
    }

    private void a(WorldData worlddata) {
        ArrayList arraylist = Lists.newArrayList(this.resourcePackRepository.d());

        for(ResourcePackLoader resourcepackloader : this.resourcePackRepository.b()) {
            if (!worlddata.N().contains(resourcepackloader.e()) && !arraylist.contains(resourcepackloader)) {
                LOGGER.info("Found new data pack {}, loading it automatically", resourcepackloader.e());
                resourcepackloader.h().a(arraylist, resourcepackloader, (resourcepackloader1) -> {
                    return resourcepackloader1;
                }, false);
            }
        }

        this.resourcePackRepository.a(arraylist);
        ArrayList arraylist1 = Lists.newArrayList();
        this.resourcePackRepository.d().forEach((resourcepackloader1) -> {
            arraylist1.add(resourcepackloader1.d());
        });
        this.ac.a(arraylist1);
        worlddata.O().clear();
        worlddata.N().clear();
        this.resourcePackRepository.d().forEach((resourcepackloader1) -> {
            worlddata.O().add(resourcepackloader1.e());
        });
        this.resourcePackRepository.b().forEach((resourcepackloader1) -> {
            if (!this.resourcePackRepository.d().contains(resourcepackloader1)) {
                worlddata.N().add(resourcepackloader1.e());
            }

        });
    }

    public void a(CommandListenerWrapper commandlistenerwrapper) {
        if (this.aQ()) {
            PlayerList playerlist = commandlistenerwrapper.getServer().getPlayerList();
            WhiteList whitelist = playerlist.getWhitelist();
            if (whitelist.isEnabled()) {
                for(EntityPlayer entityplayer : Lists.newArrayList(playerlist.v())) {
                    if (!whitelist.isWhitelisted(entityplayer.getProfile())) {
                        entityplayer.playerConnection.disconnect(new ChatMessage("multiplayer.disconnect.not_whitelisted", new Object[0]));
                    }
                }

            }
        }
    }

    public IReloadableResourceManager getResourceManager() {
        return this.ac;
    }

    public ResourcePackRepository<ResourcePackLoader> getResourcePackRepository() {
        return this.resourcePackRepository;
    }

    public CommandDispatcher getCommandDispatcher() {
        return this.commandDispatcher;
    }

    public CommandListenerWrapper getServerCommandListener() {
        return new CommandListenerWrapper(this, this.getWorldServer(DimensionManager.OVERWORLD) == null ? Vec3D.a : new Vec3D(this.getWorldServer(DimensionManager.OVERWORLD).getSpawn()), Vec2F.a, this.getWorldServer(DimensionManager.OVERWORLD), 4, "Server", new ChatComponentText("Server"), this, (Entity)null);
    }

    public boolean a() {
        return true;
    }

    public boolean b() {
        return true;
    }

    public CraftingManager getCraftingManager() {
        return this.ag;
    }

    public TagRegistry getTagRegistry() {
        return this.ah;
    }

    public ScoreboardServer getScoreboard() {
        return this.ai;
    }

    public LootTableRegistry getLootTableRegistry() {
        return this.ak;
    }

    public GameRules getGameRules() {
        return this.getWorldServer(DimensionManager.OVERWORLD).getGameRules();
    }

    public BossBattleCustomData aP() {
        return this.aj;
    }

    public boolean aQ() {
        return this.an;
    }

    public void l(boolean flag) {
        this.an = flag;
    }

    public int a(GameProfile gameprofile) {
        if (this.getPlayerList().isOp(gameprofile)) {
            OpListEntry oplistentry = (OpListEntry)this.getPlayerList().getOPs().get(gameprofile);
            if (oplistentry != null) {
                return oplistentry.a();
            } else if (this.H()) {
                if (this.G().equals(gameprofile.getName())) {
                    return 4;
                } else {
                    return this.getPlayerList().x() ? 4 : 0;
                }
            } else {
                return this.j();
            }
        } else {
            return 0;
        }
    }
}
