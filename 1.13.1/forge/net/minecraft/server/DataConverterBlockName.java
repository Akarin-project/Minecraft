package net.minecraft.server;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import java.util.Objects;

public class DataConverterBlockName extends DataFix {
    public DataConverterBlockName(Schema schema, boolean flag) {
        super(schema, flag);
    }

    public TypeRewriteRule makeRule() {
        Type type = this.getInputSchema().getType(DataConverterTypes.p);
        Type type1 = this.getOutputSchema().getType(DataConverterTypes.p);
        Type type2 = DSL.named(DataConverterTypes.p.typeName(), DSL.or(DSL.intType(), DSL.namespacedString()));
        Type type3 = DSL.named(DataConverterTypes.p.typeName(), DSL.namespacedString());
        if (Objects.equals(type, type2) && Objects.equals(type1, type3)) {
            return this.fixTypeEverywhere("BlockNameFlatteningFix", type2, type3, (var0) -> {
                return (pair) -> {
                    return pair.mapSecond((either) -> {
                        return (String)either.map(DataConverterFlattenData::a, (s) -> {
                            return DataConverterFlattenData.a(DataConverterSchemaNamed.a(s));
                        });
                    });
                };
            });
        } else {
            throw new IllegalStateException("Expected and actual types don't match.");
        }
    }
}
