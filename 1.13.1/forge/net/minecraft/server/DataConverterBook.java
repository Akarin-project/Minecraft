package net.minecraft.server;

import com.google.gson.JsonParseException;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import org.apache.commons.lang3.StringUtils;

public class DataConverterBook extends DataFix {
    public DataConverterBook(Schema schema, boolean flag) {
        super(schema, flag);
    }

    public Dynamic<?> a(Dynamic<?> dynamic) {
        return (Dynamic)DataFixUtils.orElse(dynamic.get("pages").flatMap(Dynamic::getStream).map((stream) -> {
            return stream.map((dynamic1) -> {
                if (!dynamic1.getStringValue().isPresent()) {
                    return dynamic1;
                } else {
                    String s = (String)dynamic1.getStringValue().get();
                    Object object = null;
                    if (!"null".equals(s) && !StringUtils.isEmpty(s)) {
                        if (s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"' || s.charAt(0) == '{' && s.charAt(s.length() - 1) == '}') {
                            try {
                                object = (IChatBaseComponent)ChatDeserializer.a(DataConverterSignText.a, s, IChatBaseComponent.class, true);
                                if (object == null) {
                                    object = new ChatComponentText("");
                                }
                            } catch (JsonParseException var6) {
                                ;
                            }

                            if (object == null) {
                                try {
                                    object = IChatBaseComponent.ChatSerializer.a(s);
                                } catch (JsonParseException var5) {
                                    ;
                                }
                            }

                            if (object == null) {
                                try {
                                    object = IChatBaseComponent.ChatSerializer.b(s);
                                } catch (JsonParseException var4) {
                                    ;
                                }
                            }

                            if (object == null) {
                                object = new ChatComponentText(s);
                            }
                        } else {
                            object = new ChatComponentText(s);
                        }
                    } else {
                        object = new ChatComponentText("");
                    }

                    return dynamic1.createString(IChatBaseComponent.ChatSerializer.a((IChatBaseComponent)object));
                }
            });
        }).map(dynamic::createList), dynamic);
    }

    public TypeRewriteRule makeRule() {
        Type type = this.getInputSchema().getType(DataConverterTypes.ITEM_STACK);
        OpticFinder opticfinder = type.findField("tag");
        return this.fixTypeEverywhereTyped("ItemWrittenBookPagesStrictJsonFix", type, (typed) -> {
            return typed.updateTyped(opticfinder, (typed1) -> {
                return typed1.update(DSL.remainderFinder(), this::a);
            });
        });
    }
}
