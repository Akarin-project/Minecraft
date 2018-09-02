package net.minecraft.server;

public interface IVectorPosition {
    Vec3D a(CommandListenerWrapper var1);

    Vec2F b(CommandListenerWrapper var1);

    default BlockPosition c(CommandListenerWrapper commandlistenerwrapper) {
        return new BlockPosition(this.a(commandlistenerwrapper));
    }

    boolean a();

    boolean b();

    boolean c();
}
