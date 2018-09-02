package net.minecraft.server;

public class PacketStatusListener implements PacketStatusInListener {
    private static final IChatBaseComponent a = new ChatMessage("multiplayer.status.request_handled", new Object[0]);
    private final MinecraftServer minecraftServer;
    private final NetworkManager networkManager;
    private boolean d;

    public PacketStatusListener(MinecraftServer minecraftserver, NetworkManager networkmanager) {
        this.minecraftServer = minecraftserver;
        this.networkManager = networkmanager;
    }

    public void a(IChatBaseComponent var1) {
    }

    public void a(PacketStatusInStart var1) {
        if (this.d) {
            this.networkManager.close(a);
        } else {
            this.d = true;
            this.networkManager.sendPacket(new PacketStatusOutServerInfo(this.minecraftServer.getServerPing()));
        }
    }

    public void a(PacketStatusInPing packetstatusinping) {
        this.networkManager.sendPacket(new PacketStatusOutPong(packetstatusinping.b()));
        this.networkManager.close(a);
    }
}
