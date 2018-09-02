package net.minecraft.server;

public class PlayerConnectionUtils {
    public static <T extends PacketListener> void ensureMainThread(Packet<T> packet, T packetlistener, IAsyncTaskHandler iasynctaskhandler) throws CancelledPacketHandleException {
        if (!iasynctaskhandler.isMainThread()) {
            iasynctaskhandler.postToMainThread(() -> {
                packet.a(packetlistener);
            });
            throw CancelledPacketHandleException.INSTANCE;
        }
    }
}
