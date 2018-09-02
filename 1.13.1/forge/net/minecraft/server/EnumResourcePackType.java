package net.minecraft.server;

public enum EnumResourcePackType {
    CLIENT_RESOURCES("assets"),
    SERVER_DATA("data");

    private final String c;

    private EnumResourcePackType(String s1) {
        this.c = s1;
    }

    public String a() {
        return this.c;
    }
}
