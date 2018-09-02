package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

public class NBTTagIntArray extends NBTList<NBTTagInt> {
    private int[] data;

    NBTTagIntArray() {
    }

    public NBTTagIntArray(int[] aint) {
        this.data = aint;
    }

    public NBTTagIntArray(List<Integer> list) {
        this(a(list));
    }

    private static int[] a(List<Integer> list) {
        int[] aint = new int[list.size()];

        for(int i = 0; i < list.size(); ++i) {
            Integer integer = (Integer)list.get(i);
            aint[i] = integer == null ? 0 : integer;
        }

        return aint;
    }

    public void write(DataOutput dataoutput) throws IOException {
        dataoutput.writeInt(this.data.length);

        for(int i : this.data) {
            dataoutput.writeInt(i);
        }

    }

    public void load(DataInput datainput, int var2, NBTReadLimiter nbtreadlimiter) throws IOException {
        nbtreadlimiter.a(192L);
        int i = datainput.readInt();
        nbtreadlimiter.a((long)(32 * i));
        this.data = new int[i];

        for(int j = 0; j < i; ++j) {
            this.data[j] = datainput.readInt();
        }

    }

    public byte getTypeId() {
        return 11;
    }

    public String toString() {
        StringBuilder stringbuilder = new StringBuilder("[I;");

        for(int i = 0; i < this.data.length; ++i) {
            if (i != 0) {
                stringbuilder.append(',');
            }

            stringbuilder.append(this.data[i]);
        }

        return stringbuilder.append(']').toString();
    }

    public NBTTagIntArray c() {
        int[] aint = new int[this.data.length];
        System.arraycopy(this.data, 0, aint, 0, this.data.length);
        return new NBTTagIntArray(aint);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else {
            return object instanceof NBTTagIntArray && Arrays.equals(this.data, ((NBTTagIntArray)object).data);
        }
    }

    public int hashCode() {
        return Arrays.hashCode(this.data);
    }

    public int[] d() {
        return this.data;
    }

    public IChatBaseComponent a(String var1, int var2) {
        IChatBaseComponent ichatbasecomponent = (new ChatComponentText("I")).a(e);
        IChatBaseComponent ichatbasecomponent1 = (new ChatComponentText("[")).addSibling(ichatbasecomponent).a(";");

        for(int i = 0; i < this.data.length; ++i) {
            ichatbasecomponent1.a(" ").addSibling((new ChatComponentText(String.valueOf(this.data[i]))).a(d));
            if (i != this.data.length - 1) {
                ichatbasecomponent1.a(",");
            }
        }

        ichatbasecomponent1.a("]");
        return ichatbasecomponent1;
    }

    public int size() {
        return this.data.length;
    }

    public NBTTagInt a(int i) {
        return new NBTTagInt(this.data[i]);
    }

    public void a(int i, NBTBase nbtbase) {
        this.data[i] = ((NBTNumber)nbtbase).e();
    }

    public void b(int i) {
        this.data = ArrayUtils.remove(this.data, i);
    }

    // $FF: synthetic method
    public NBTBase c(int i) {
        return this.a(i);
    }

    // $FF: synthetic method
    public NBTBase clone() {
        return this.c();
    }
}
