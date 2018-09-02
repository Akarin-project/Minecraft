package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagEnd implements NBTBase {
    public NBTTagEnd() {
    }

    public void load(DataInput var1, int var2, NBTReadLimiter nbtreadlimiter) throws IOException {
        nbtreadlimiter.a(64L);
    }

    public void write(DataOutput var1) throws IOException {
    }

    public byte getTypeId() {
        return 0;
    }

    public String toString() {
        return "END";
    }

    public NBTTagEnd c() {
        return new NBTTagEnd();
    }

    public IChatBaseComponent a(String var1, int var2) {
        return new ChatComponentText("");
    }

    public boolean equals(Object object) {
        return object instanceof NBTTagEnd;
    }

    public int hashCode() {
        return this.getTypeId();
    }

    // $FF: synthetic method
    public NBTBase clone() {
        return this.c();
    }
}
