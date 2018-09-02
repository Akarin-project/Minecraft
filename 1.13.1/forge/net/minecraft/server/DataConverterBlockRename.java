package net.minecraft.server;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public abstract class DataConverterBlockRename extends DataFix {
    private final String a;

    public DataConverterBlockRename(Schema schema, String s) {
        super(schema, false);
        this.a = s;
    }

    public TypeRewriteRule makeRule() {
        Type type = this.getInputSchema().getType(DataConverterTypes.p);
        Type type1 = DSL.named(DataConverterTypes.p.typeName(), DSL.namespacedString());
        if (!Objects.equals(type, type1)) {
            throw new IllegalStateException("block type is not what was expected.");
        } else {
            TypeRewriteRule typerewriterule = this.fixTypeEverywhere(this.a + " for block", type1, (var1) -> {
                return (pair) -> {
                    return pair.mapSecond(this::a);
                };
            });
            TypeRewriteRule typerewriterule1 = this.fixTypeEverywhereTyped(this.a + " for block_state", this.getInputSchema().getType(DataConverterTypes.l), (typed) -> {
                return typed.update(DSL.remainderFinder(), (dynamic) -> {
                    Optional optional = dynamic.get("Name").flatMap(Dynamic::getStringValue);
                    return optional.isPresent() ? dynamic.set("Name", dynamic.createString(this.a((String)optional.get()))) : dynamic;
                });
            });
            return TypeRewriteRule.seq(typerewriterule, typerewriterule1);
        }
    }

    protected abstract String a(String var1);

    public static DataFix a(Schema schema, String s, final Function<String, String> function) {
        return new DataConverterBlockRename(schema, s) {
            protected String a(String s1) {
                return (String)function.apply(s1);
            }
        };
    }
}
