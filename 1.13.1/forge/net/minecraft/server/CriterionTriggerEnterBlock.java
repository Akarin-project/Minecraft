package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public class CriterionTriggerEnterBlock implements CriterionTrigger<CriterionTriggerEnterBlock.b> {
    private static final MinecraftKey a = new MinecraftKey("enter_block");
    private final Map<AdvancementDataPlayer, CriterionTriggerEnterBlock.a> b = Maps.newHashMap();

    public CriterionTriggerEnterBlock() {
    }

    public MinecraftKey a() {
        return a;
    }

    public void a(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerEnterBlock.b> criteriontrigger$a) {
        CriterionTriggerEnterBlock.a criteriontriggerenterblock$a = (CriterionTriggerEnterBlock.a)this.b.get(advancementdataplayer);
        if (criteriontriggerenterblock$a == null) {
            criteriontriggerenterblock$a = new CriterionTriggerEnterBlock.a(advancementdataplayer);
            this.b.put(advancementdataplayer, criteriontriggerenterblock$a);
        }

        criteriontriggerenterblock$a.a(criteriontrigger$a);
    }

    public void b(AdvancementDataPlayer advancementdataplayer, CriterionTrigger.a<CriterionTriggerEnterBlock.b> criteriontrigger$a) {
        CriterionTriggerEnterBlock.a criteriontriggerenterblock$a = (CriterionTriggerEnterBlock.a)this.b.get(advancementdataplayer);
        if (criteriontriggerenterblock$a != null) {
            criteriontriggerenterblock$a.b(criteriontrigger$a);
            if (criteriontriggerenterblock$a.a()) {
                this.b.remove(advancementdataplayer);
            }
        }

    }

    public void a(AdvancementDataPlayer advancementdataplayer) {
        this.b.remove(advancementdataplayer);
    }

    public CriterionTriggerEnterBlock.b b(JsonObject jsonobject, JsonDeserializationContext var2) {
        Block block = null;
        if (jsonobject.has("block")) {
            MinecraftKey minecraftkey = new MinecraftKey(ChatDeserializer.h(jsonobject, "block"));
            if (!IRegistry.BLOCK.c(minecraftkey)) {
                throw new JsonSyntaxException("Unknown block type '" + minecraftkey + "'");
            }

            block = IRegistry.BLOCK.getOrDefault(minecraftkey);
        }

        HashMap hashmap = null;
        if (jsonobject.has("state")) {
            if (block == null) {
                throw new JsonSyntaxException("Can't define block state without a specific block type");
            }

            BlockStateList blockstatelist = block.getStates();

            for(Entry entry : ChatDeserializer.t(jsonobject, "state").entrySet()) {
                IBlockState iblockstate = blockstatelist.a((String)entry.getKey());
                if (iblockstate == null) {
                    throw new JsonSyntaxException("Unknown block state property '" + (String)entry.getKey() + "' for block '" + IRegistry.BLOCK.getKey(block) + "'");
                }

                String s = ChatDeserializer.a((JsonElement)entry.getValue(), (String)entry.getKey());
                Optional optional = iblockstate.b(s);
                if (!optional.isPresent()) {
                    throw new JsonSyntaxException("Invalid block state value '" + s + "' for property '" + (String)entry.getKey() + "' on block '" + IRegistry.BLOCK.getKey(block) + "'");
                }

                if (hashmap == null) {
                    hashmap = Maps.newHashMap();
                }

                hashmap.put(iblockstate, optional.get());
            }
        }

        return new CriterionTriggerEnterBlock.b(block, hashmap);
    }

    public void a(EntityPlayer entityplayer, IBlockData iblockdata) {
        CriterionTriggerEnterBlock.a criteriontriggerenterblock$a = (CriterionTriggerEnterBlock.a)this.b.get(entityplayer.getAdvancementData());
        if (criteriontriggerenterblock$a != null) {
            criteriontriggerenterblock$a.a(iblockdata);
        }

    }

    // $FF: synthetic method
    public CriterionInstance a(JsonObject jsonobject, JsonDeserializationContext jsondeserializationcontext) {
        return this.b(jsonobject, jsondeserializationcontext);
    }

    static class a {
        private final AdvancementDataPlayer a;
        private final Set<CriterionTrigger.a<CriterionTriggerEnterBlock.b>> b = Sets.newHashSet();

        public a(AdvancementDataPlayer advancementdataplayer) {
            this.a = advancementdataplayer;
        }

        public boolean a() {
            return this.b.isEmpty();
        }

        public void a(CriterionTrigger.a<CriterionTriggerEnterBlock.b> criteriontrigger$a) {
            this.b.add(criteriontrigger$a);
        }

        public void b(CriterionTrigger.a<CriterionTriggerEnterBlock.b> criteriontrigger$a) {
            this.b.remove(criteriontrigger$a);
        }

        public void a(IBlockData iblockdata) {
            ArrayList arraylist = null;

            for(CriterionTrigger.a criteriontrigger$a : this.b) {
                if (((CriterionTriggerEnterBlock.b)criteriontrigger$a.a()).a(iblockdata)) {
                    if (arraylist == null) {
                        arraylist = Lists.newArrayList();
                    }

                    arraylist.add(criteriontrigger$a);
                }
            }

            if (arraylist != null) {
                for(CriterionTrigger.a criteriontrigger$a1 : arraylist) {
                    criteriontrigger$a1.a(this.a);
                }
            }

        }
    }

    public static class b extends CriterionInstanceAbstract {
        private final Block a;
        private final Map<IBlockState<?>, Object> b;

        public b(@Nullable Block block, @Nullable Map<IBlockState<?>, Object> map) {
            super(CriterionTriggerEnterBlock.a);
            this.a = block;
            this.b = map;
        }

        public static CriterionTriggerEnterBlock.b a(Block block) {
            return new CriterionTriggerEnterBlock.b(block, (Map)null);
        }

        public JsonElement b() {
            JsonObject jsonobject = new JsonObject();
            if (this.a != null) {
                jsonobject.addProperty("block", IRegistry.BLOCK.getKey(this.a).toString());
                if (this.b != null && !this.b.isEmpty()) {
                    JsonObject jsonobject1 = new JsonObject();

                    for(Entry entry : this.b.entrySet()) {
                        jsonobject1.addProperty(((IBlockState)entry.getKey()).a(), SystemUtils.a((IBlockState)entry.getKey(), entry.getValue()));
                    }

                    jsonobject.add("state", jsonobject1);
                }
            }

            return jsonobject;
        }

        public boolean a(IBlockData iblockdata) {
            if (this.a != null && iblockdata.getBlock() != this.a) {
                return false;
            } else {
                if (this.b != null) {
                    for(Entry entry : this.b.entrySet()) {
                        if (iblockdata.get((IBlockState)entry.getKey()) != entry.getValue()) {
                            return false;
                        }
                    }
                }

                return true;
            }
        }
    }
}
