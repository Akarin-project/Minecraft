package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagFloat extends NBTNumber {
    private float data;

    NBTTagFloat() {
    }

    public NBTTagFloat(float f) {
        this.data = f;
    }

    public void write(DataOutput dataoutput) throws IOException {
        dataoutput.writeFloat(this.data);
    }

    public void load(DataInput datainput, int var2, NBTReadLimiter nbtreadlimiter) throws IOException {
        nbtreadlimiter.a(96L);
        this.data = datainput.readFloat();
    }

    public byte getTypeId() {
        return 5;
    }

    public String toString() {
        return this.data + "f";
    }

    public NBTTagFloat c() {
        return new NBTTagFloat(this.data);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else {
            return object instanceof NBTTagFloat && this.data == ((NBTTagFloat)object).data;
        }
    }

    public int hashCode() {
        return Float.floatToIntBits(this.data);
    }

    public IChatBaseComponent a(String var1, int var2) {
        IChatBaseComponent ichatbasecomponent = (new ChatComponentText("f")).a(e);
        return (new ChatComponentText(String.valueOf(this.data))).addSibling(ichatbasecomponent).a(d);
    }

    public long d() {
        return (long)this.data;
    }

    public int e() {
        return MathHelper.d(this.data);
    }

    public short f() {
        return (short)(MathHelper.d(this.data) & '\uffff');
    }

    public byte g() {
        return (byte)(MathHelper.d(this.data) & 255);
    }

    public double asDouble() {
        return (double)this.data;
    }

    public float i() {
        return this.data;
    }

    public Number j() {
        return this.data;
    }

    // $FF: synthetic method
    public NBTBase clone() {
        return this.c();
    }
}
