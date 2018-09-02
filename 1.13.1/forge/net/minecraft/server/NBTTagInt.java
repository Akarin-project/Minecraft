package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagInt extends NBTNumber {
    private int data;

    NBTTagInt() {
    }

    public NBTTagInt(int i) {
        this.data = i;
    }

    public void write(DataOutput dataoutput) throws IOException {
        dataoutput.writeInt(this.data);
    }

    public void load(DataInput datainput, int var2, NBTReadLimiter nbtreadlimiter) throws IOException {
        nbtreadlimiter.a(96L);
        this.data = datainput.readInt();
    }

    public byte getTypeId() {
        return 3;
    }

    public String toString() {
        return String.valueOf(this.data);
    }

    public NBTTagInt c() {
        return new NBTTagInt(this.data);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else {
            return object instanceof NBTTagInt && this.data == ((NBTTagInt)object).data;
        }
    }

    public int hashCode() {
        return this.data;
    }

    public IChatBaseComponent a(String var1, int var2) {
        return (new ChatComponentText(String.valueOf(this.data))).a(d);
    }

    public long d() {
        return (long)this.data;
    }

    public int e() {
        return this.data;
    }

    public short f() {
        return (short)(this.data & '\uffff');
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
