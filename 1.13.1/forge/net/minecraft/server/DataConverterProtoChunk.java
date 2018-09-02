package net.minecraft.server;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DataConverterProtoChunk extends DataFix {
    public DataConverterProtoChunk(Schema schema, boolean flag) {
        super(schema, flag);
    }

    public TypeRewriteRule makeRule() {
        Type type = this.getInputSchema().getType(DataConverterTypes.c);
        Type type1 = this.getOutputSchema().getType(DataConverterTypes.c);
        Type type2 = type.findFieldType("Level");
        Type type3 = type1.findFieldType("Level");
        Type type4 = type2.findFieldType("TileTicks");
        OpticFinder opticfinder = DSL.fieldFinder("Level", type2);
        OpticFinder opticfinder1 = DSL.fieldFinder("TileTicks", type4);
        return TypeRewriteRule.seq(this.fixTypeEverywhereTyped("ChunkToProtoChunkFix", type, this.getOutputSchema().getType(DataConverterTypes.c), (typed) -> {
            return typed.updateTyped(opticfinder, type3, (typed1) -> {
                Optional optional = typed1.getOptionalTyped(opticfinder1).map(Typed::write).flatMap(Dynamic::getStream);
                Dynamic dynamic = (Dynamic)typed1.get(DSL.remainderFinder());
                boolean flag = dynamic.getBoolean("TerrainPopulated") && (!dynamic.get("LightPopulated").flatMap(Dynamic::getNumberValue).isPresent() || dynamic.getBoolean("LightPopulated"));
                dynamic = dynamic.set("Status", dynamic.createString(flag ? "mobs_spawned" : "empty"));
                dynamic = dynamic.set("hasLegacyStructureData", dynamic.createBoolean(true));
                Dynamic dynamic1;
                if (flag) {
                    Optional optional1 = dynamic.get("Biomes").flatMap(Dynamic::getByteBuffer);
                    if (optional1.isPresent()) {
                        ByteBuffer bytebuffer = (ByteBuffer)optional1.get();
                        int[] aint = new int[256];

                        for(int i = 0; i < aint.length; ++i) {
                            if (i < bytebuffer.capacity()) {
                                aint[i] = bytebuffer.get(i) & 255;
                            }
                        }

                        dynamic = dynamic.set("Biomes", dynamic.createIntList(Arrays.stream(aint)));
                    }

                    List list = (List)IntStream.range(0, 16).mapToObj((var1) -> {
                        return dynamic.createList(Stream.empty());
                    }).collect(Collectors.toList());
                    if (optional.isPresent()) {
                        ((Stream)optional.get()).forEach((dynamic3) -> {
                            int j = dynamic3.getInt("x");
                            int k = dynamic3.getInt("y");
                            int l = dynamic3.getInt("z");
                            short short1 = a(j, k, l);
                            list.set(k >> 4, ((Dynamic)list.get(k >> 4)).merge(dynamic.createShort(short1)));
                        });
                        dynamic = dynamic.set("ToBeTicked", dynamic.createList(list.stream()));
                    }

                    dynamic1 = typed1.set(DSL.remainderFinder(), dynamic).write();
                } else {
                    dynamic1 = dynamic;
                }

                return (Typed)((Optional)type3.readTyped(dynamic1).getSecond()).orElseThrow(() -> {
                    return new IllegalStateException("Could not read the new chunk");
                });
            });
        }), this.writeAndRead("Structure biome inject", this.getInputSchema().getType(DataConverterTypes.s), this.getOutputSchema().getType(DataConverterTypes.s)));
    }

    private static short a(int i, int j, int k) {
        return (short)(i & 15 | (j & 15) << 4 | (k & 15) << 8);
    }
}
