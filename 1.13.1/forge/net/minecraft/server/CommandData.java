package net.minecraft.server;

import com.google.common.collect.Lists;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.List;
import java.util.function.Function;

public class CommandData {
    private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ChatMessage("commands.data.merge.failed", new Object[0]));
    private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType((object) -> {
        return new ChatMessage("commands.data.get.invalid", new Object[]{object});
    });
    private static final DynamicCommandExceptionType d = new DynamicCommandExceptionType((object) -> {
        return new ChatMessage("commands.data.get.unknown", new Object[]{object});
    });
    public static final List<CommandData.a> a = Lists.newArrayList(new CommandData.a[]{CommandDataAccessorEntity.a, CommandDataAccessorTile.a});

    public static void a(com.mojang.brigadier.CommandDispatcher<CommandListenerWrapper> commanddispatcher) {
        LiteralArgumentBuilder literalargumentbuilder = (LiteralArgumentBuilder)CommandDispatcher.a("data").requires((commandlistenerwrapper) -> {
            return commandlistenerwrapper.hasPermission(2);
        });

        for(CommandData.a commanddata$a : a) {
            ((LiteralArgumentBuilder)((LiteralArgumentBuilder)literalargumentbuilder.then(commanddata$a.a(CommandDispatcher.a("merge"), (argumentbuilder) -> {
                return argumentbuilder.then(CommandDispatcher.a("nbt", ArgumentNBTTag.a()).executes((commandcontext) -> {
                    return a((CommandListenerWrapper)commandcontext.getSource(), commanddata$a.a(commandcontext), ArgumentNBTTag.a(commandcontext, "nbt"));
                }));
            }))).then(commanddata$a.a(CommandDispatcher.a("get"), (argumentbuilder) -> {
                return argumentbuilder.executes((commandcontext) -> {
                    return a((CommandListenerWrapper)commandcontext.getSource(), commanddata$a.a(commandcontext));
                }).then(((RequiredArgumentBuilder)CommandDispatcher.a("path", ArgumentNBTKey.a()).executes((commandcontext) -> {
                    return b((CommandListenerWrapper)commandcontext.getSource(), commanddata$a.a(commandcontext), ArgumentNBTKey.a(commandcontext, "path"));
                })).then(CommandDispatcher.a("scale", DoubleArgumentType.doubleArg()).executes((commandcontext) -> {
                    return a((CommandListenerWrapper)commandcontext.getSource(), commanddata$a.a(commandcontext), ArgumentNBTKey.a(commandcontext, "path"), DoubleArgumentType.getDouble(commandcontext, "scale"));
                })));
            }))).then(commanddata$a.a(CommandDispatcher.a("remove"), (argumentbuilder) -> {
                return argumentbuilder.then(CommandDispatcher.a("path", ArgumentNBTKey.a()).executes((commandcontext) -> {
                    return a((CommandListenerWrapper)commandcontext.getSource(), commanddata$a.a(commandcontext), ArgumentNBTKey.a(commandcontext, "path"));
                }));
            }));
        }

        commanddispatcher.register(literalargumentbuilder);
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, CommandDataAccessor commanddataaccessor, ArgumentNBTKey.c argumentnbtkey$c) throws CommandSyntaxException {
        NBTTagCompound nbttagcompound = commanddataaccessor.a();
        NBTTagCompound nbttagcompound1 = nbttagcompound.clone();
        argumentnbtkey$c.b(nbttagcompound);
        if (nbttagcompound1.equals(nbttagcompound)) {
            throw b.create();
        } else {
            commanddataaccessor.a(nbttagcompound);
            commandlistenerwrapper.sendMessage(commanddataaccessor.b(), true);
            return 1;
        }
    }

    private static int b(CommandListenerWrapper commandlistenerwrapper, CommandDataAccessor commanddataaccessor, ArgumentNBTKey.c argumentnbtkey$c) throws CommandSyntaxException {
        NBTBase nbtbase = argumentnbtkey$c.a(commanddataaccessor.a());
        int i;
        if (nbtbase instanceof NBTNumber) {
            i = MathHelper.floor(((NBTNumber)nbtbase).asDouble());
        } else if (nbtbase instanceof NBTList) {
            i = ((NBTList)nbtbase).size();
        } else if (nbtbase instanceof NBTTagCompound) {
            i = ((NBTTagCompound)nbtbase).d();
        } else {
            if (!(nbtbase instanceof NBTTagString)) {
                throw d.create(argumentnbtkey$c.toString());
            }

            i = ((NBTTagString)nbtbase).b_().length();
        }

        commandlistenerwrapper.sendMessage(commanddataaccessor.a(nbtbase), false);
        return i;
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, CommandDataAccessor commanddataaccessor, ArgumentNBTKey.c argumentnbtkey$c, double d0) throws CommandSyntaxException {
        NBTBase nbtbase = argumentnbtkey$c.a(commanddataaccessor.a());
        if (!(nbtbase instanceof NBTNumber)) {
            throw c.create(argumentnbtkey$c.toString());
        } else {
            int i = MathHelper.floor(((NBTNumber)nbtbase).asDouble() * d0);
            commandlistenerwrapper.sendMessage(commanddataaccessor.a(argumentnbtkey$c, d0, i), false);
            return i;
        }
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, CommandDataAccessor commanddataaccessor) throws CommandSyntaxException {
        commandlistenerwrapper.sendMessage(commanddataaccessor.a((NBTBase)commanddataaccessor.a()), false);
        return 1;
    }

    private static int a(CommandListenerWrapper commandlistenerwrapper, CommandDataAccessor commanddataaccessor, NBTTagCompound nbttagcompound) throws CommandSyntaxException {
        NBTTagCompound nbttagcompound1 = commanddataaccessor.a();
        NBTTagCompound nbttagcompound2 = nbttagcompound1.clone().a(nbttagcompound);
        if (nbttagcompound1.equals(nbttagcompound2)) {
            throw b.create();
        } else {
            commanddataaccessor.a(nbttagcompound2);
            commandlistenerwrapper.sendMessage(commanddataaccessor.b(), true);
            return 1;
        }
    }

    public interface a {
        CommandDataAccessor a(CommandContext<CommandListenerWrapper> var1) throws CommandSyntaxException;

        ArgumentBuilder<CommandListenerWrapper, ?> a(ArgumentBuilder<CommandListenerWrapper, ?> var1, Function<ArgumentBuilder<CommandListenerWrapper, ?>, ArgumentBuilder<CommandListenerWrapper, ?>> var2);
    }
}
