package net.minecraft.server;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import java.util.Objects;

public class DataConverterTeamDisplayName extends DataFix {
    public DataConverterTeamDisplayName(Schema schema, boolean flag) {
        super(schema, flag);
    }

    protected TypeRewriteRule makeRule() {
        Type type = DSL.named(DataConverterTypes.u.typeName(), DSL.remainderType());
        if (!Objects.equals(type, this.getInputSchema().getType(DataConverterTypes.u))) {
            throw new IllegalStateException("Team type is not what was expected.");
        } else {
            return this.fixTypeEverywhere("TeamDisplayNameFix", type, (var0) -> {
                return (pair) -> {
                    return pair.mapSecond((dynamic) -> {
                        return dynamic.update("DisplayName", (dynamic2) -> {
                            return (Dynamic)DataFixUtils.orElse(dynamic2.getStringValue().map((s) -> {
                                return IChatBaseComponent.ChatSerializer.a(new ChatComponentText(s));
                            }).map(dynamic::createString), dynamic2);
                        });
                    });
                };
            });
        }
    }
}
