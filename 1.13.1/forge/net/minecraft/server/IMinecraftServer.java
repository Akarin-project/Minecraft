package net.minecraft.server;

public interface IMinecraftServer {
    int a(String var1, int var2);

    String a(String var1, String var2);

    void a(String var1, Object var2);

    void c_();

    String d_();

    String e();

    int e_();

    String m();

    String getVersion();

    int y();

    int z();

    String[] getPlayers();

    String getWorld();

    String getPlugins();

    String executeRemoteCommand(String var1);

    boolean isDebugging();

    void info(String var1);

    void warning(String var1);

    void f(String var1);

    void g(String var1);
}
