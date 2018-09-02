package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GeneratorSettingsFlat extends GeneratorSettingsDefault {
    private static final Logger w = LogManager.getLogger();
    private static final WorldGenFeatureComposite<WorldGenMineshaftConfiguration, WorldGenFeatureDecoratorEmptyConfiguration> x = BiomeBase.<WorldGenMineshaftConfiguration, WorldGenFeatureDecoratorEmptyConfiguration>a(WorldGenerator.f, new WorldGenMineshaftConfiguration(0.004D, WorldGenMineshaft.Type.NORMAL), BiomeBase.n, WorldGenFeatureDecoratorConfiguration.e);
    private static final WorldGenFeatureComposite<WorldGenFeatureVillageConfiguration, WorldGenFeatureDecoratorEmptyConfiguration> y = BiomeBase.<WorldGenFeatureVillageConfiguration, WorldGenFeatureDecoratorEmptyConfiguration>a(WorldGenerator.e, new WorldGenFeatureVillageConfiguration(0, WorldGenVillagePieces.Material.OAK), BiomeBase.n, WorldGenFeatureDecoratorConfiguration.e);
    private static final WorldGenFeatureComposite<WorldGenFeatureStrongholdConfiguration, WorldGenFeatureDecoratorEmptyConfiguration> z = BiomeBase.<WorldGenFeatureStrongholdConfiguration, WorldGenFeatureDecoratorEmptyConfiguration>a(WorldGenerator.m, new WorldGenFeatureStrongholdConfiguration(), BiomeBase.n, WorldGenFeatureDecoratorConfiguration.e);
    private static final WorldGenFeatureComposite<WorldGenFeatureSwampHutConfiguration, WorldGenFeatureDecoratorEmptyConfiguration> A = BiomeBase.<WorldGenFeatureSwampHutConfiguration, WorldGenFeatureDecoratorEmptyConfiguration>a(WorldGenerator.l, new WorldGenFeatureSwampHutConfiguration(), BiomeBase.n, WorldGenFeatureDecoratorConfiguration.e);
    private static final WorldGenFeatureComposite<WorldGenFeatureDesertPyramidConfiguration, WorldGenFeatureDecoratorEmptyConfiguration> B = BiomeBase.<WorldGenFeatureDesertPyramidConfiguration, WorldGenFeatureDecoratorEmptyConfiguration>a(WorldGenerator.i, new WorldGenFeatureDesertPyramidConfiguration(), BiomeBase.n, WorldGenFeatureDecoratorConfiguration.e);
    private static final WorldGenFeatureComposite<WorldGenFeatureJunglePyramidConfiguration, WorldGenFeatureDecoratorEmptyConfiguration> C = BiomeBase.<WorldGenFeatureJunglePyramidConfiguration, WorldGenFeatureDecoratorEmptyConfiguration>a(WorldGenerator.h, new WorldGenFeatureJunglePyramidConfiguration(), BiomeBase.n, WorldGenFeatureDecoratorConfiguration.e);
    private static final WorldGenFeatureComposite<WorldGenFeatureIglooConfiguration, WorldGenFeatureDecoratorEmptyConfiguration> D = BiomeBase.<WorldGenFeatureIglooConfiguration, WorldGenFeatureDecoratorEmptyConfiguration>a(WorldGenerator.j, new WorldGenFeatureIglooConfiguration(), BiomeBase.n, WorldGenFeatureDecoratorConfiguration.e);
    private static final WorldGenFeatureComposite<WorldGenFeatureShipwreckConfiguration, WorldGenFeatureDecoratorEmptyConfiguration> E = BiomeBase.<WorldGenFeatureShipwreckConfiguration, WorldGenFeatureDecoratorEmptyConfiguration>a(WorldGenerator.k, new WorldGenFeatureShipwreckConfiguration(false), BiomeBase.n, WorldGenFeatureDecoratorConfiguration.e);
    private static final WorldGenFeatureComposite<WorldGenMonumentConfiguration, WorldGenFeatureDecoratorEmptyConfiguration> F = BiomeBase.<WorldGenMonumentConfiguration, WorldGenFeatureDecoratorEmptyConfiguration>a(WorldGenerator.n, new WorldGenMonumentConfiguration(), BiomeBase.n, WorldGenFeatureDecoratorConfiguration.e);
    private static final WorldGenFeatureComposite<WorldGenFeatureLakeConfiguration, WorldGenDecoratorLakeChanceConfiguration> G = BiomeBase.<WorldGenFeatureLakeConfiguration, WorldGenDecoratorLakeChanceConfiguration>a(WorldGenerator.am, new WorldGenFeatureLakeConfiguration(Blocks.WATER), BiomeBase.K, new WorldGenDecoratorLakeChanceConfiguration(4));
    private static final WorldGenFeatureComposite<WorldGenFeatureLakeConfiguration, WorldGenDecoratorLakeChanceConfiguration> H = BiomeBase.<WorldGenFeatureLakeConfiguration, WorldGenDecoratorLakeChanceConfiguration>a(WorldGenerator.am, new WorldGenFeatureLakeConfiguration(Blocks.LAVA), BiomeBase.J, new WorldGenDecoratorLakeChanceConfiguration(80));
    private static final WorldGenFeatureComposite<WorldGenEndCityConfiguration, WorldGenFeatureDecoratorEmptyConfiguration> I = BiomeBase.<WorldGenEndCityConfiguration, WorldGenFeatureDecoratorEmptyConfiguration>a(WorldGenerator.q, new WorldGenEndCityConfiguration(), BiomeBase.n, WorldGenFeatureDecoratorConfiguration.e);
    private static final WorldGenFeatureComposite<WorldGenMansionConfiguration, WorldGenFeatureDecoratorEmptyConfiguration> J = BiomeBase.<WorldGenMansionConfiguration, WorldGenFeatureDecoratorEmptyConfiguration>a(WorldGenerator.g, new WorldGenMansionConfiguration(), BiomeBase.n, WorldGenFeatureDecoratorConfiguration.e);
    private static final WorldGenFeatureComposite<WorldGenNetherConfiguration, WorldGenFeatureDecoratorEmptyConfiguration> K = BiomeBase.<WorldGenNetherConfiguration, WorldGenFeatureDecoratorEmptyConfiguration>a(WorldGenerator.p, new WorldGenNetherConfiguration(), BiomeBase.n, WorldGenFeatureDecoratorConfiguration.e);
    private static final WorldGenFeatureComposite<WorldGenFeatureOceanRuinConfiguration, WorldGenFeatureDecoratorEmptyConfiguration> L = BiomeBase.<WorldGenFeatureOceanRuinConfiguration, WorldGenFeatureDecoratorEmptyConfiguration>a(WorldGenerator.o, new WorldGenFeatureOceanRuinConfiguration(WorldGenFeatureOceanRuin.Temperature.COLD, 0.3F, 0.1F), BiomeBase.n, WorldGenFeatureDecoratorConfiguration.e);
    public static final Map<WorldGenFeatureComposite<?, ?>, WorldGenStage.Decoration> t = (Map)SystemUtils.a(Maps.newHashMap(), (hashmap) -> {
        hashmap.put(x, WorldGenStage.Decoration.UNDERGROUND_STRUCTURES);
        hashmap.put(y, WorldGenStage.Decoration.SURFACE_STRUCTURES);
        hashmap.put(z, WorldGenStage.Decoration.UNDERGROUND_STRUCTURES);
        hashmap.put(A, WorldGenStage.Decoration.SURFACE_STRUCTURES);
        hashmap.put(B, WorldGenStage.Decoration.SURFACE_STRUCTURES);
        hashmap.put(C, WorldGenStage.Decoration.SURFACE_STRUCTURES);
        hashmap.put(D, WorldGenStage.Decoration.SURFACE_STRUCTURES);
        hashmap.put(E, WorldGenStage.Decoration.SURFACE_STRUCTURES);
        hashmap.put(L, WorldGenStage.Decoration.SURFACE_STRUCTURES);
        hashmap.put(G, WorldGenStage.Decoration.LOCAL_MODIFICATIONS);
        hashmap.put(H, WorldGenStage.Decoration.LOCAL_MODIFICATIONS);
        hashmap.put(I, WorldGenStage.Decoration.SURFACE_STRUCTURES);
        hashmap.put(J, WorldGenStage.Decoration.SURFACE_STRUCTURES);
        hashmap.put(K, WorldGenStage.Decoration.UNDERGROUND_STRUCTURES);
        hashmap.put(F, WorldGenStage.Decoration.SURFACE_STRUCTURES);
    });
    public static final Map<String, WorldGenFeatureComposite<?, ?>[]> u = (Map)SystemUtils.a(Maps.newHashMap(), (hashmap) -> {
        hashmap.put("mineshaft", new WorldGenFeatureComposite[]{x});
        hashmap.put("village", new WorldGenFeatureComposite[]{y});
        hashmap.put("stronghold", new WorldGenFeatureComposite[]{z});
        hashmap.put("biome_1", new WorldGenFeatureComposite[]{A, B, C, D, L, E});
        hashmap.put("oceanmonument", new WorldGenFeatureComposite[]{F});
        hashmap.put("lake", new WorldGenFeatureComposite[]{G});
        hashmap.put("lava_lake", new WorldGenFeatureComposite[]{H});
        hashmap.put("endcity", new WorldGenFeatureComposite[]{I});
        hashmap.put("mansion", new WorldGenFeatureComposite[]{J});
        hashmap.put("fortress", new WorldGenFeatureComposite[]{K});
    });
    public static final Map<WorldGenFeatureComposite<?, ?>, WorldGenFeatureConfiguration> v = (Map)SystemUtils.a(Maps.newHashMap(), (hashmap) -> {
        hashmap.put(x, new WorldGenMineshaftConfiguration(0.004D, WorldGenMineshaft.Type.NORMAL));
        hashmap.put(y, new WorldGenFeatureVillageConfiguration(0, WorldGenVillagePieces.Material.OAK));
        hashmap.put(z, new WorldGenFeatureStrongholdConfiguration());
        hashmap.put(A, new WorldGenFeatureSwampHutConfiguration());
        hashmap.put(B, new WorldGenFeatureDesertPyramidConfiguration());
        hashmap.put(C, new WorldGenFeatureJunglePyramidConfiguration());
        hashmap.put(D, new WorldGenFeatureIglooConfiguration());
        hashmap.put(L, new WorldGenFeatureOceanRuinConfiguration(WorldGenFeatureOceanRuin.Temperature.COLD, 0.3F, 0.9F));
        hashmap.put(E, new WorldGenFeatureShipwreckConfiguration(false));
        hashmap.put(F, new WorldGenMonumentConfiguration());
        hashmap.put(I, new WorldGenEndCityConfiguration());
        hashmap.put(J, new WorldGenMansionConfiguration());
        hashmap.put(K, new WorldGenNetherConfiguration());
    });
    private final List<WorldGenFlatLayerInfo> M = Lists.newArrayList();
    private final Map<String, Map<String, String>> N = Maps.newHashMap();
    private BiomeBase O;
    private final IBlockData[] P = new IBlockData[256];
    private boolean Q;
    private int R;

    public GeneratorSettingsFlat() {
    }

    @Nullable
    public static Block a(String s) {
        try {
            MinecraftKey minecraftkey = new MinecraftKey(s);
            if (IRegistry.BLOCK.c(minecraftkey)) {
                return IRegistry.BLOCK.getOrDefault(minecraftkey);
            }
        } catch (IllegalArgumentException illegalargumentexception) {
            w.warn("Invalid blockstate: {}", s, illegalargumentexception);
        }

        return null;
    }

    public BiomeBase t() {
        return this.O;
    }

    public void a(BiomeBase biomebase) {
        this.O = biomebase;
    }

    public Map<String, Map<String, String>> u() {
        return this.N;
    }

    public List<WorldGenFlatLayerInfo> v() {
        return this.M;
    }

    public void w() {
        int i = 0;

        for(WorldGenFlatLayerInfo worldgenflatlayerinfo : this.M) {
            worldgenflatlayerinfo.a(i);
            i += worldgenflatlayerinfo.a();
        }

        this.R = 0;
        this.Q = true;
        i = 0;

        for(WorldGenFlatLayerInfo worldgenflatlayerinfo1 : this.M) {
            for(int j = worldgenflatlayerinfo1.c(); j < worldgenflatlayerinfo1.c() + worldgenflatlayerinfo1.a(); ++j) {
                IBlockData iblockdata = worldgenflatlayerinfo1.b();
                if (iblockdata.getBlock() != Blocks.AIR) {
                    this.Q = false;
                    this.P[j] = iblockdata;
                }
            }

            if (worldgenflatlayerinfo1.b().getBlock() == Blocks.AIR) {
                i += worldgenflatlayerinfo1.a();
            } else {
                this.R += worldgenflatlayerinfo1.a() + i;
                i = 0;
            }
        }

    }

    public String toString() {
        StringBuilder stringbuilder = new StringBuilder();

        for(int i = 0; i < this.M.size(); ++i) {
            if (i > 0) {
                stringbuilder.append(",");
            }

            stringbuilder.append(this.M.get(i));
        }

        stringbuilder.append(";");
        stringbuilder.append(IRegistry.BIOME.getKey(this.O));
        stringbuilder.append(";");
        if (!this.N.isEmpty()) {
            int k = 0;

            for(Entry entry : this.N.entrySet()) {
                if (k++ > 0) {
                    stringbuilder.append(",");
                }

                stringbuilder.append(((String)entry.getKey()).toLowerCase(Locale.ROOT));
                Map map = (Map)entry.getValue();
                if (!map.isEmpty()) {
                    stringbuilder.append("(");
                    int j = 0;

                    for(Entry entry1 : map.entrySet()) {
                        if (j++ > 0) {
                            stringbuilder.append(" ");
                        }

                        stringbuilder.append((String)entry1.getKey());
                        stringbuilder.append("=");
                        stringbuilder.append((String)entry1.getValue());
                    }

                    stringbuilder.append(")");
                }
            }
        }

        return stringbuilder.toString();
    }

    public static GeneratorSettingsFlat a(Dynamic<?> dynamic) {
        GeneratorSettingsFlat generatorsettingsflat = ChunkGeneratorType.e.b();
        List list = (List)((Stream)dynamic.get("layers").flatMap(Dynamic::getStream).orElse(Stream.empty())).map((dynamic1) -> {
            return Pair.of(dynamic1.getInt("height", 1), a(dynamic1.getString("block")));
        }).collect(Collectors.toList());
        if (list.stream().anyMatch((pair) -> {
            return pair.getSecond() == null;
        })) {
            return x();
        } else {
            List list1 = (List)list.stream().map((pair) -> {
                return new WorldGenFlatLayerInfo(pair.getFirst(), (Block)pair.getSecond());
            }).collect(Collectors.toList());
            if (list1.isEmpty()) {
                return x();
            } else {
                generatorsettingsflat.v().addAll(list1);
                generatorsettingsflat.w();
                generatorsettingsflat.a(IRegistry.BIOME.get(new MinecraftKey(dynamic.getString("biome"))));
                dynamic.get("structures").flatMap(Dynamic::getMapValues).ifPresent((map) -> {
                    map.keySet().forEach((dynamic1) -> {
                        dynamic1.getStringValue().map((s) -> {
                            return (Map)generatorsettingsflat.u().put(s, Maps.newHashMap());
                        });
                    });
                });
                return generatorsettingsflat;
            }
        }
    }

    public static GeneratorSettingsFlat x() {
        GeneratorSettingsFlat generatorsettingsflat = ChunkGeneratorType.e.b();
        generatorsettingsflat.a(Biomes.c);
        generatorsettingsflat.v().add(new WorldGenFlatLayerInfo(1, Blocks.BEDROCK));
        generatorsettingsflat.v().add(new WorldGenFlatLayerInfo(2, Blocks.DIRT));
        generatorsettingsflat.v().add(new WorldGenFlatLayerInfo(1, Blocks.GRASS_BLOCK));
        generatorsettingsflat.w();
        generatorsettingsflat.u().put("village", Maps.newHashMap());
        return generatorsettingsflat;
    }

    public boolean y() {
        return this.Q;
    }

    public IBlockData[] A() {
        return this.P;
    }
}
