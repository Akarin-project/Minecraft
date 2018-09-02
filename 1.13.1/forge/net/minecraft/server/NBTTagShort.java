package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagShort extends NBTNumber {
    private short data;

    public NBTTagShort() {
    }

    public NBTTagShort(short short1) {
        this.data = short1;
    }

    public void write(DataOutput dataoutput) throws IOException {
        dataoutput.writeShort(this.data);
    }

    public void load(DataInput datainput, int var2, NBTReadLimiter nbtreadlimiter) throws IOException {
        nbtreadlimiter.a(80L);
        this.data = datainput.readShort();
    }

    public byte getTypeId() {
        return 2;
    }

    public String toString() {
        return this.data + "s";
    }

    public NBTTagShort c() {
        return new NBTTagShort(this.data);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else {
            return object instanceof NBTTagShort && this.data == ((NBTTagShort)object).data;
        }
    }

    public int hashCode() {
        return this.data;
    }

    public IChatBaseComponent a(String var1, int var2) {
        IChatBaseComponent ichatbasecomponent = (new ChatComponentText("s")).a(e);
        return (new ChatComponentText(String.valueOf(this.data))).addSibling(ichatbasecomponent).a(d);
    }

    public long d() {
        return (long)this.data;
    }

    public int e() {
        return this.data;
    }

    public short f() {
        return this.data;
    }

    public byte g() {
        return (byte)(this.data & 255);
    }

    public double asDouble() {
        return (double)this.data;
    }

    public float i() {
        return (float)this.data;
    }

    public Number j() {
        return this.data;
    }

    // $FF: synthetic method
    public NBTBase clone() {
        return this.c();
    }
}
