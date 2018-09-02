package net.minecraft.server;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import java.util.Objects;

public abstract class DataConverterEntityRenameAbstract extends DataFix {
    private final String a;

    public DataConverterEntityRenameAbstract(String s, Schema schema, boolean flag) {
        super(schema, flag);
        this.a = s;
    }

    public TypeRewriteRule makeRule() {
        TaggedChoiceType taggedchoicetype = this.getInputSchema().findChoiceType(DataConverterTypes.ENTITY);
        TaggedChoiceType taggedchoicetype1 = this.getOutputSchema().findChoiceType(DataConverterTypes.ENTITY);
        Type type = DSL.named(DataConverterTypes.m.typeName(), DSL.namespacedString());
        if (!Objects.equals(this.getOutputSchema().getType(DataConverterTypes.m), type)) {
            throw new IllegalStateException("Entity name type is not what was expected.");
        } else {
            return TypeRewriteRule.seq(this.fixTypeEverywhere(this.a, taggedchoicetype, taggedchoicetype1, (var3) -> {
                return (pair) -> {
                    return pair.mapFirst((s) -> {
                        String s1 = this.a(s);
                        Type type1 = (Type)taggedchoicetype.types().get(s);
                        Type type2 = (Type)taggedchoicetype1.types().get(s1);
                        if (!type2.equals(type1, true, true)) {
                            throw new IllegalStateException(String.format("Dynamic type check failed: %s not equal to %s", type2, type1));
                        } else {
                            return s1;
                        }
                    });
                };
            }), this.fixTypeEverywhere(this.a + " for entity name", type, (var1) -> {
                return (pair) -> {
                    return pair.mapSecond(this::a);
                };
            }));
        }
    }

    protected abstract String a(String var1);
}
