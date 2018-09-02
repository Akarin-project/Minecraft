package net.minecraft.server;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import java.util.Objects;
import java.util.Optional;

public class DataConverterObjectiveRenderType extends DataFix {
    public DataConverterObjectiveRenderType(Schema schema, boolean flag) {
        super(schema, flag);
    }

    private static IScoreboardCriteria.EnumScoreboardHealthDisplay a(String s) {
        return s.equals("health") ? IScoreboardCriteria.EnumScoreboardHealthDisplay.HEARTS : IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER;
    }

    protected TypeRewriteRule makeRule() {
        Type type = DSL.named(DataConverterTypes.t.typeName(), DSL.remainderType());
        if (!Objects.equals(type, this.getInputSchema().getType(DataConverterTypes.t))) {
            throw new IllegalStateException("Objective type is not what was expected.");
        } else {
            return this.fixTypeEverywhere("ObjectiveRenderTypeFix", type, (var0) -> {
                return (pair) -> {
                    return pair.mapSecond((dynamic) -> {
                        Optional optional = dynamic.get("RenderType").flatMap(Dynamic::getStringValue);
                        if (!optional.isPresent()) {
                            String s = dynamic.getString("CriteriaName");
                            IScoreboardCriteria.EnumScoreboardHealthDisplay iscoreboardcriteria$enumscoreboardhealthdisplay = a(s);
                            return dynamic.set("RenderType", dynamic.createString(iscoreboardcriteria$enumscoreboardhealthdisplay.a()));
                        } else {
                            return dynamic;
                        }
                    });
                };
            });
        }
    }
}