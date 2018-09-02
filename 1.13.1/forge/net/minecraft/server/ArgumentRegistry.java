package net.minecraft.server;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ArgumentRegistry {
    private static final Logger a = LogManager.getLogger();
    private static final Map<Class<?>, ArgumentRegistry.a<?>> b = Maps.newHashMap();
    private static final Map<MinecraftKey, ArgumentRegistry.a<?>> c = Maps.newHashMap();

    public static <T extends ArgumentType<?>> void a(MinecraftKey minecraftkey, Class<T> oclass, ArgumentSerializer<T> argumentserializer) {
        if (b.containsKey(oclass)) {
            throw new IllegalArgumentException("Class " + oclass.getName() + " already has a serializer!");
        } else if (c.containsKey(minecraftkey)) {
            throw new IllegalArgumentException("'" + minecraftkey + "' is already a registered serializer!");
        } else {
            ArgumentRegistry.a argumentregistry$a = new ArgumentRegistry.a(oclass, argumentserializer, minecraftkey);
            b.put(oclass, argumentregistry$a);
            c.put(minecraftkey, argumentregistry$a);
        }
    }

    public static void a() {
        ArgumentSerializers.a();
        a(new MinecraftKey("minecraft:entity"), ArgumentEntity.class, new ArgumentEntity.a());
        a(new MinecraftKey("minecraft:game_profile"), ArgumentProfile.class, new ArgumentSerializerVoid(ArgumentProfile::a));
        a(new MinecraftKey("minecraft:block_pos"), ArgumentPosition.class, new ArgumentSerializerVoid(ArgumentPosition::a));
        a(new MinecraftKey("minecraft:column_pos"), ArgumentVec2I.class, new ArgumentSerializerVoid(ArgumentVec2I::a));
        a(new MinecraftKey("minecraft:vec3"), ArgumentVec3.class, new ArgumentSerializerVoid(ArgumentVec3::a));
        a(new MinecraftKey("minecraft:vec2"), ArgumentVec2.class, new ArgumentSerializerVoid(ArgumentVec2::a));
        a(new MinecraftKey("minecraft:block_state"), ArgumentTile.class, new ArgumentSerializerVoid(ArgumentTile::a));
        a(new MinecraftKey("minecraft:block_predicate"), ArgumentBlockPredicate.class, new ArgumentSerializerVoid(ArgumentBlockPredicate::a));
        a(new MinecraftKey("minecraft:item_stack"), ArgumentItemStack.class, new ArgumentSerializerVoid(ArgumentItemStack::a));
        a(new MinecraftKey("minecraft:item_predicate"), ArgumentItemPredicate.class, new ArgumentSerializerVoid(ArgumentItemPredicate::a));
        a(new MinecraftKey("minecraft:color"), ArgumentChatFormat.class, new ArgumentSerializerVoid(ArgumentChatFormat::a));
        a(new MinecraftKey("minecraft:component"), ArgumentChatComponent.class, new ArgumentSerializerVoid(ArgumentChatComponent::a));
        a(new MinecraftKey("minecraft:message"), ArgumentChat.class, new ArgumentSerializerVoid(ArgumentChat::a));
        a(new MinecraftKey("minecraft:nbt"), ArgumentNBTTag.class, new ArgumentSerializerVoid(ArgumentNBTTag::a));
        a(new MinecraftKey("minecraft:nbt_path"), ArgumentNBTKey.class, new ArgumentSerializerVoid(ArgumentNBTKey::a));
        a(new MinecraftKey("minecraft:objective"), ArgumentScoreboardObjective.class, new ArgumentSerializerVoid(ArgumentScoreboardObjective::a));
        a(new MinecraftKey("minecraft:objective_criteria"), ArgumentScoreboardCriteria.class, new ArgumentSerializerVoid(ArgumentScoreboardCriteria::a));
        a(new MinecraftKey("minecraft:operation"), ArgumentMathOperation.class, new ArgumentSerializerVoid(ArgumentMathOperation::a));
        a(new MinecraftKey("minecraft:particle"), ArgumentParticle.class, new ArgumentSerializerVoid(ArgumentParticle::a));
        a(new MinecraftKey("minecraft:rotation"), ArgumentRotation.class, new ArgumentSerializerVoid(ArgumentRotation::a));
        a(new MinecraftKey("minecraft:scoreboard_slot"), ArgumentScoreboardSlot.class, new ArgumentSerializerVoid(ArgumentScoreboardSlot::a));
        a(new MinecraftKey("minecraft:score_holder"), ArgumentScoreholder.class, new ArgumentScoreholder.c());
        a(new MinecraftKey("minecraft:swizzle"), ArgumentRotationAxis.class, new ArgumentSerializerVoid(ArgumentRotationAxis::a));
        a(new MinecraftKey("minecraft:team"), ArgumentScoreboardTeam.class, new ArgumentSerializerVoid(ArgumentScoreboardTeam::a));
        a(new MinecraftKey("minecraft:item_slot"), ArgumentInventorySlot.class, new ArgumentSerializerVoid(ArgumentInventorySlot::a));
        a(new MinecraftKey("minecraft:resource_location"), ArgumentMinecraftKeyRegistered.class, new ArgumentSerializerVoid(ArgumentMinecraftKeyRegistered::a));
        a(new MinecraftKey("minecraft:mob_effect"), ArgumentMobEffect.class, new ArgumentSerializerVoid(ArgumentMobEffect::a));
        a(new MinecraftKey("minecraft:function"), ArgumentTag.class, new ArgumentSerializerVoid(ArgumentTag::a));
        a(new MinecraftKey("minecraft:entity_anchor"), ArgumentAnchor.class, new ArgumentSerializerVoid(ArgumentAnchor::a));
        a(new MinecraftKey("minecraft:int_range"), ArgumentCriterionValue.b.class, new ArgumentCriterionValue.b.a());
        a(new MinecraftKey("minecraft:float_range"), ArgumentCriterionValue.a.class, new ArgumentCriterionValue.a.a());
        a(new MinecraftKey("minecraft:item_enchantment"), ArgumentEnchantment.class, new ArgumentSerializerVoid(ArgumentEnchantment::a));
        a(new MinecraftKey("minecraft:entity_summon"), ArgumentEntitySummon.class, new ArgumentSerializerVoid(ArgumentEntitySummon::a));
        a(new MinecraftKey("minecraft:dimension"), ArgumentDimension.class, new ArgumentSerializerVoid(ArgumentDimension::a));
    }

    @Nullable
    private static ArgumentRegistry.a<?> a(MinecraftKey minecraftkey) {
        return (ArgumentRegistry.a)c.get(minecraftkey);
    }

    @Nullable
    private static ArgumentRegistry.a<?> a(ArgumentType<?> argumenttype) {
        return (ArgumentRegistry.a)b.get(argumenttype.getClass());
    }

    public static <T extends ArgumentType<?>> void a(PacketDataSerializer packetdataserializer, T argumenttype) {
        ArgumentRegistry.a argumentregistry$a = a(argumenttype);
        if (argumentregistry$a == null) {
            a.error("Could not serialize {} ({}) - will not be sent to client!", argumenttype, argumenttype.getClass());
            packetdataserializer.a(new MinecraftKey(""));
        } else {
            packetdataserializer.a(argumentregistry$a.c);
            argumentregistry$a.b.a((T)argumenttype, packetdataserializer);
        }
    }

    @Nullable
    public static ArgumentType<?> a(PacketDataSerializer packetdataserializer) {
        MinecraftKey minecraftkey = packetdataserializer.l();
        ArgumentRegistry.a argumentregistry$a = a(minecraftkey);
        if (argumentregistry$a == null) {
            a.error("Could not deserialize {}", minecraftkey);
            return null;
        } else {
            return argumentregistry$a.b.b(packetdataserializer);
        }
    }

    private static <T extends ArgumentType<?>> void a(JsonObject jsonobject, T argumenttype) {
        ArgumentRegistry.a argumentregistry$a = a(argumenttype);
        if (argumentregistry$a == null) {
            a.error("Could not serialize argument {} ({})!", argumenttype, argumenttype.getClass());
            jsonobject.addProperty("type", "unknown");
        } else {
            jsonobject.addProperty("type", "argument");
            jsonobject.addProperty("parser", argumentregistry$a.c.toString());
            JsonObject jsonobject1 = new JsonObject();
            argumentregistry$a.b.a((T)argumenttype, jsonobject1);
            if (jsonobject1.size() > 0) {
                jsonobject.add("properties", jsonobject1);
            }
        }

    }

    public static <S> JsonObject a(com.mojang.brigadier.CommandDispatcher<S> commanddispatcher, CommandNode<S> commandnode) {
        JsonObject jsonobject = new JsonObject();
        if (commandnode instanceof RootCommandNode) {
            jsonobject.addProperty("type", "root");
        } else if (commandnode instanceof LiteralCommandNode) {
            jsonobject.addProperty("type", "literal");
        } else if (commandnode instanceof ArgumentCommandNode) {
            a(jsonobject, ((ArgumentCommandNode)commandnode).getType());
        } else {
            a.error("Could not serialize node {} ({})!", commandnode, commandnode.getClass());
            jsonobject.addProperty("type", "unknown");
        }

        JsonObject jsonobject1 = new JsonObject();

        for(CommandNode commandnode1 : commandnode.getChildren()) {
            jsonobject1.add(commandnode1.getName(), a(commanddispatcher, commandnode1));
        }

        if (jsonobject1.size() > 0) {
            jsonobject.add("children", jsonobject1);
        }

        if (commandnode.getCommand() != null) {
            jsonobject.addProperty("executable", true);
        }

        if (commandnode.getRedirect() != null) {
            Collection collection = commanddispatcher.getPath(commandnode.getRedirect());
            if (!collection.isEmpty()) {
                JsonArray jsonarray = new JsonArray();

                for(String s : collection) {
                    jsonarray.add(s);
                }

                jsonobject.add("redirect", jsonarray);
            }
        }

        return jsonobject;
    }

    static class a<T extends ArgumentType<?>> {
        public final Class<T> a;
        public final ArgumentSerializer<T> b;
        public final MinecraftKey c;

        private a(Class<T> oclass, ArgumentSerializer<T> argumentserializer, MinecraftKey minecraftkey) {
            this.a = oclass;
            this.b = argumentserializer;
            this.c = minecraftkey;
        }
    }
}
