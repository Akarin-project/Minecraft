package net.minecraft.server;

import com.google.common.collect.Lists;
import com.mojang.brigadier.ResultConsumer;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.function.BinaryOperator;
import javax.annotation.Nullable;

public class CommandListenerWrapper implements ICompletionProvider {
    public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new ChatMessage("permissions.requires.player", new Object[0]));
    public static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new ChatMessage("permissions.requires.entity", new Object[0]));
    public final ICommandListener base;
    private final Vec3D d;
    private final WorldServer e;
    private final int f;
    private final String g;
    private final IChatBaseComponent h;
    private final MinecraftServer i;
    private final boolean j;
    @Nullable
    private final Entity k;
    private final ResultConsumer<CommandListenerWrapper> l;
    private final ArgumentAnchor.Anchor m;
    private final Vec2F n;

    public CommandListenerWrapper(ICommandListener icommandlistener, Vec3D vec3d, Vec2F vec2f, WorldServer worldserver, int ix, String s, IChatBaseComponent ichatbasecomponent, MinecraftServer minecraftserver, @Nullable Entity entity) {
        this(icommandlistener, vec3d, vec2f, worldserver, ix, s, ichatbasecomponent, minecraftserver, entity, false, (var0, var1, var2) -> {
        }, ArgumentAnchor.Anchor.FEET);
    }

    protected CommandListenerWrapper(ICommandListener icommandlistener, Vec3D vec3d, Vec2F vec2f, WorldServer worldserver, int ix, String s, IChatBaseComponent ichatbasecomponent, MinecraftServer minecraftserver, @Nullable Entity entity, boolean flag, ResultConsumer<CommandListenerWrapper> resultconsumer, ArgumentAnchor.Anchor argumentanchor$anchor) {
        this.base = icommandlistener;
        this.d = vec3d;
        this.e = worldserver;
        this.j = flag;
        this.k = entity;
        this.f = ix;
        this.g = s;
        this.h = ichatbasecomponent;
        this.i = minecraftserver;
        this.l = resultconsumer;
        this.m = argumentanchor$anchor;
        this.n = vec2f;
    }

    public CommandListenerWrapper a(Entity entity) {
        return this.k == entity ? this : new CommandListenerWrapper(this.base, this.d, this.n, this.e, this.f, entity.getDisplayName().getString(), entity.getScoreboardDisplayName(), this.i, entity, this.j, this.l, this.m);
    }

    public CommandListenerWrapper a(Vec3D vec3d) {
        return this.d.equals(vec3d) ? this : new CommandListenerWrapper(this.base, vec3d, this.n, this.e, this.f, this.g, this.h, this.i, this.k, this.j, this.l, this.m);
    }

    public CommandListenerWrapper a(Vec2F vec2f) {
        return this.n.c(vec2f) ? this : new CommandListenerWrapper(this.base, this.d, vec2f, this.e, this.f, this.g, this.h, this.i, this.k, this.j, this.l, this.m);
    }

    public CommandListenerWrapper a(ResultConsumer<CommandListenerWrapper> resultconsumer) {
        return this.l.equals(resultconsumer) ? this : new CommandListenerWrapper(this.base, this.d, this.n, this.e, this.f, this.g, this.h, this.i, this.k, this.j, resultconsumer, this.m);
    }

    public CommandListenerWrapper a(ResultConsumer<CommandListenerWrapper> resultconsumer, BinaryOperator<ResultConsumer<CommandListenerWrapper>> binaryoperator) {
        ResultConsumer resultconsumer1 = (ResultConsumer)binaryoperator.apply(this.l, resultconsumer);
        return this.a(resultconsumer1);
    }

    public CommandListenerWrapper a() {
        return this.j ? this : new CommandListenerWrapper(this.base, this.d, this.n, this.e, this.f, this.g, this.h, this.i, this.k, true, this.l, this.m);
    }

    public CommandListenerWrapper a(int ix) {
        return ix == this.f ? this : new CommandListenerWrapper(this.base, this.d, this.n, this.e, ix, this.g, this.h, this.i, this.k, this.j, this.l, this.m);
    }

    public CommandListenerWrapper b(int ix) {
        return ix <= this.f ? this : new CommandListenerWrapper(this.base, this.d, this.n, this.e, ix, this.g, this.h, this.i, this.k, this.j, this.l, this.m);
    }

    public CommandListenerWrapper a(ArgumentAnchor.Anchor argumentanchor$anchor) {
        return argumentanchor$anchor == this.m ? this : new CommandListenerWrapper(this.base, this.d, this.n, this.e, this.f, this.g, this.h, this.i, this.k, this.j, this.l, argumentanchor$anchor);
    }

    public CommandListenerWrapper a(WorldServer worldserver) {
        return worldserver == this.e ? this : new CommandListenerWrapper(this.base, this.d, this.n, worldserver, this.f, this.g, this.h, this.i, this.k, this.j, this.l, this.m);
    }

    public CommandListenerWrapper a(Entity entity, ArgumentAnchor.Anchor argumentanchor$anchor) throws CommandSyntaxException {
        return this.b(argumentanchor$anchor.a(entity));
    }

    public CommandListenerWrapper b(Vec3D vec3d) throws CommandSyntaxException {
        Vec3D vec3d1 = this.m.a(this);
        double d0 = vec3d.x - vec3d1.x;
        double d1 = vec3d.y - vec3d1.y;
        double d2 = vec3d.z - vec3d1.z;
        double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
        float fx = MathHelper.g((float)(-(MathHelper.c(d1, d3) * (double)(180F / (float)Math.PI))));
        float f1 = MathHelper.g((float)(MathHelper.c(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F);
        return this.a(new Vec2F(fx, f1));
    }

    public IChatBaseComponent getScoreboardDisplayName() {
        return this.h;
    }

    public String getName() {
        return this.g;
    }

    public boolean hasPermission(int ix) {
        return this.f >= ix;
    }

    public Vec3D getPosition() {
        return this.d;
    }

    public WorldServer getWorld() {
        return this.e;
    }

    @Nullable
    public Entity f() {
        return this.k;
    }

    public Entity g() throws CommandSyntaxException {
        if (this.k == null) {
            throw b.create();
        } else {
            return this.k;
        }
    }

    public EntityPlayer h() throws CommandSyntaxException {
        if (!(this.k instanceof EntityPlayer)) {
            throw a.create();
        } else {
            return (EntityPlayer)this.k;
        }
    }

    public Vec2F i() {
        return this.n;
    }

    public MinecraftServer getServer() {
        return this.i;
    }

    public ArgumentAnchor.Anchor k() {
        return this.m;
    }

    public void sendMessage(IChatBaseComponent ichatbasecomponent, boolean flag) {
        if (this.base.a() && !this.j) {
            this.base.sendMessage(ichatbasecomponent);
        }

        if (flag && this.base.B_() && !this.j) {
            this.sendAdminMessage(ichatbasecomponent);
        }

    }

    private void sendAdminMessage(IChatBaseComponent ichatbasecomponent) {
        IChatBaseComponent ichatbasecomponent1 = (new ChatMessage("chat.type.admin", new Object[]{this.getScoreboardDisplayName(), ichatbasecomponent})).a(new EnumChatFormat[]{EnumChatFormat.GRAY, EnumChatFormat.ITALIC});
        if (this.i.getGameRules().getBoolean("sendCommandFeedback")) {
            for(EntityPlayer entityplayer : this.i.getPlayerList().v()) {
                if (entityplayer != this.base && this.i.getPlayerList().isOp(entityplayer.getProfile())) {
                    entityplayer.sendMessage(ichatbasecomponent1);
                }
            }
        }

        if (this.base != this.i && this.i.getGameRules().getBoolean("logAdminCommands")) {
            this.i.sendMessage(ichatbasecomponent1);
        }

    }

    public void sendFailureMessage(IChatBaseComponent ichatbasecomponent) {
        if (this.base.b() && !this.j) {
            this.base.sendMessage((new ChatComponentText("")).addSibling(ichatbasecomponent).a(EnumChatFormat.RED));
        }

    }

    public void a(CommandContext<CommandListenerWrapper> commandcontext, boolean flag, int ix) {
        if (this.l != null) {
            this.l.onCommandComplete(commandcontext, flag, ix);
        }

    }

    public Collection<String> l() {
        return Lists.newArrayList(this.i.getPlayers());
    }

    public Collection<String> m() {
        return this.i.getScoreboard().f();
    }

    public Collection<MinecraftKey> n() {
        return IRegistry.SOUND_EVENT.keySet();
    }

    public Collection<MinecraftKey> o() {
        return this.i.getCraftingManager().c();
    }

    public CompletableFuture<Suggestions> a(CommandContext<ICompletionProvider> var1, SuggestionsBuilder var2) {
        return null;
    }

    public Collection<ICompletionProvider.a> a(boolean var1) {
        return Collections.singleton(ICompletionProvider.a.b);
    }
}
