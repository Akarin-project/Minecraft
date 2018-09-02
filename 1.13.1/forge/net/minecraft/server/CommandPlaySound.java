package net.minecraft.server;

import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Iterator;

public class CommandPlaySound {
    private static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ChatMessage("commands.playsound.failed", new Object[0]));

    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        RequiredArgumentBuilder requiredargumentbuilder = CommandDispatcher.a("sound", ArgumentMinecraftKeyRegistered.a()).suggests(CompletionProviders.c);

        for(SoundCategory soundcategory : SoundCategory.values()) {
            requiredargumentbuilder.then(a(soundcategory));
        }

        commanddispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandDispatcher.a("playsound").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(2);
        })).then(requiredargumentbuilder));
    }

    private static LiteralArgumentBuilder<CommandListenerWrapper> a(SoundCategory soundcategory) {
        return (LiteralArgumentBuilder)CommandDispatcher.a(soundcategory.a()).then(((RequiredArgumentBuilder)CommandDispatcher.a("targets", ArgumentEntity.d()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), ArgumentMinecraftKeyRegistered.c(commandcontext, "sound"), soundcategory, ((CommandListenerWrapper)commandcontext.getSource()).getPosition(), 1.0F, 1.0F, 0.0F);
        })).then(((RequiredArgumentBuilder)CommandDispatcher.a("pos", ArgumentVec3.a()).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), ArgumentMinecraftKeyRegistered.c(commandcontext, "sound"), soundcategory, ArgumentVec3.a(commandcontext, "pos"), 1.0F, 1.0F, 0.0F);
        })).then(((RequiredArgumentBuilder)CommandDispatcher.a("volume", FloatArgumentType.floatArg(0.0F)).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), ArgumentMinecraftKeyRegistered.c(commandcontext, "sound"), soundcategory, ArgumentVec3.a(commandcontext, "pos"), commandcontext.getArgument("volume", Float.class), 1.0F, 0.0F);
        })).then(((RequiredArgumentBuilder)CommandDispatcher.a("pitch", FloatArgumentType.floatArg(0.0F, 2.0F)).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), ArgumentMinecraftKeyRegistered.c(commandcontext, "sound"), soundcategory, ArgumentVec3.a(commandcontext, "pos"), commandcontext.getArgument("volume", Float.class), commandcontext.getArgument("pitch", Float.class), 0.0F);
        })).then(CommandDispatcher.a("minVolume", FloatArgumentType.floatArg(0.0F, 1.0F)).executes((commandcontext) -> {
            return a((CommandListenerWrapper)commandcontext.getSource(), ArgumentEntity.f(commandcontext, "targets"), ArgumentMinecraftKeyRegistered.c(commandcontext, "sound"), soundcategory, ArgumentVec3.a(commandcontext, "pos"), commandcontext.getArgument("volume", Float.class), commandcontext.getArgument("pitch", Float.class), commandcontext.getArgument("minVolume", Float.class));
        }))))));
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, Collection<EntityPlayer> collection, MinecraftKey minecraftkey, SoundCategory soundcategory, Vec3D vec3d, float f, float f1, float f2) throws CommandSyntaxException {
        double d0 = Math.pow(f > 1.0F ? (double)(f * 16.0F) : 16.0D, 2.0D);
        int i = 0;
        Iterator iterator = collection.iterator();

        while(true) {
            EntityPlayer entityplayer;
            Vec3D vec3d1;
            float f3;
            while(true) {
                if (!iterator.hasNext()) {
                    if (i == 0) {
                        throw a.create();
                    }

                    if (collection.size() == 1) {
                        commandlistenerwrapper.sendMessage(new ChatMessage("commands.playsound.success.single", new Object[]{minecraftkey, ((EntityPlayer)collection.iterator().next()).getScoreboardDisplayName()}), true);
                    } else {
                        commandlistenerwrapper.sendMessage(new ChatMessage("commands.playsound.success.single", new Object[]{minecraftkey, ((EntityPlayer)collection.iterator().next()).getScoreboardDisplayName()}), true);
                    }

                    return i;
                }

                entityplayer = (EntityPlayer)iterator.next();
                double d1 = vec3d.x - entityplayer.locX;
                double d2 = vec3d.y - entityplayer.locY;
                double d3 = vec3d.z - entityplayer.locZ;
                double d4 = d1 * d1 + d2 * d2 + d3 * d3;
                vec3d1 = vec3d;
                f3 = f;
                if (!(d4 > d0)) {
                    break;
                }

                if (!(f2 <= 0.0F)) {
                    double d5 = (double)MathHelper.sqrt(d4);
                    vec3d1 = new Vec3D(entityplayer.locX + d1 / d5 * 2.0D, entityplayer.locY + d2 / d5 * 2.0D, entityplayer.locZ + d3 / d5 * 2.0D);
                    f3 = f2;
                    break;
                }
            }

            entityplayer.playerConnection.sendPacket(new PacketPlayOutCustomSoundEffect(minecraftkey, soundcategory, vec3d1, f3, f1));
            ++i;
        }
    }
}
