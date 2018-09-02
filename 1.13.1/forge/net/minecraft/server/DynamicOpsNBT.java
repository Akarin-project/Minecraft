package net.minecraft.server;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.types.DynamicOps;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class DynamicOpsNBT implements DynamicOps<NBTBase> {
    public static final DynamicOpsNBT a = new DynamicOpsNBT();

    protected DynamicOpsNBT() {
    }

    public NBTBase a() {
        return new NBTTagEnd();
    }

    public Type<?> a(NBTBase nbtbase) {
        switch(nbtbase.getTypeId()) {
        case 0:
            return DSL.nilType();
        case 1:
            return DSL.byteType();
        case 2:
            return DSL.shortType();
        case 3:
            return DSL.intType();
        case 4:
            return DSL.longType();
        case 5:
            return DSL.floatType();
        case 6:
            return DSL.doubleType();
        case 7:
            return DSL.list(DSL.byteType());
        case 8:
            return DSL.string();
        case 9:
            return DSL.list(DSL.remainderType());
        case 10:
            return DSL.compoundList(DSL.remainderType(), DSL.remainderType());
        case 11:
            return DSL.list(DSL.intType());
        case 12:
            return DSL.list(DSL.longType());
        default:
            return DSL.remainderType();
        }
    }

    public Optional<Number> b(NBTBase nbtbase) {
        return nbtbase instanceof NBTNumber ? Optional.of(((NBTNumber)nbtbase).j()) : Optional.empty();
    }

    public NBTBase a(Number number) {
        return new NBTTagDouble(number.doubleValue());
    }

    public NBTBase a(byte b0) {
        return new NBTTagByte(b0);
    }

    public NBTBase a(short short1) {
        return new NBTTagShort(short1);
    }

    public NBTBase a(int i) {
        return new NBTTagInt(i);
    }

    public NBTBase a(long i) {
        return new NBTTagLong(i);
    }

    public NBTBase a(float f) {
        return new NBTTagFloat(f);
    }

    public NBTBase a(double d0) {
        return new NBTTagDouble(d0);
    }

    public Optional<String> c(NBTBase nbtbase) {
        return nbtbase instanceof NBTTagString ? Optional.of(nbtbase.b_()) : Optional.empty();
    }

    public NBTBase a(String s) {
        return new NBTTagString(s);
    }

    public NBTBase a(NBTBase nbtbase, NBTBase nbtbase1) {
        if (nbtbase1 instanceof NBTTagEnd) {
            return nbtbase;
        } else if (!(nbtbase instanceof NBTTagCompound)) {
            if (nbtbase instanceof NBTTagEnd) {
                throw new IllegalArgumentException("mergeInto called with a null input.");
            } else if (nbtbase instanceof NBTList) {
                NBTTagList nbttaglist = new NBTTagList();
                NBTList nbtlist = (NBTList)nbtbase;
                nbttaglist.addAll(nbtlist);
                nbttaglist.add(nbtbase1);
                return nbttaglist;
            } else {
                return nbtbase;
            }
        } else if (!(nbtbase1 instanceof NBTTagCompound)) {
            return nbtbase;
        } else {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbtbase;

            for(String s : nbttagcompound1.getKeys()) {
                nbttagcompound.set(s, nbttagcompound1.get(s));
            }

            NBTTagCompound nbttagcompound2 = (NBTTagCompound)nbtbase1;

            for(String s1 : nbttagcompound2.getKeys()) {
                nbttagcompound.set(s1, nbttagcompound2.get(s1));
            }

            return nbttagcompound;
        }
    }

    public NBTBase a(NBTBase nbtbase, NBTBase nbtbase1, NBTBase nbtbase2) {
        NBTTagCompound nbttagcompound;
        if (nbtbase instanceof NBTTagEnd) {
            nbttagcompound = new NBTTagCompound();
        } else {
            if (!(nbtbase instanceof NBTTagCompound)) {
                return nbtbase;
            }

            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbtbase;
            nbttagcompound = new NBTTagCompound();
            nbttagcompound1.getKeys().forEach((s) -> {
                nbttagcompound.set(s, nbttagcompound1.get(s));
            });
        }

        nbttagcompound.set(nbtbase1.b_(), nbtbase2);
        return nbttagcompound;
    }

    public NBTBase b(NBTBase nbtbase, NBTBase nbtbase1) {
        if (nbtbase instanceof NBTTagEnd) {
            return nbtbase1;
        } else if (nbtbase1 instanceof NBTTagEnd) {
            return nbtbase;
        } else {
            if (nbtbase instanceof NBTTagCompound && nbtbase1 instanceof NBTTagCompound) {
                NBTTagCompound nbttagcompound = (NBTTagCompound)nbtbase;
                NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbtbase1;
                NBTTagCompound nbttagcompound2 = new NBTTagCompound();
                nbttagcompound.getKeys().forEach((s) -> {
                    nbttagcompound2.set(s, nbttagcompound.get(s));
                });
                nbttagcompound1.getKeys().forEach((s) -> {
                    nbttagcompound2.set(s, nbttagcompound1.get(s));
                });
            }

            if (nbtbase instanceof NBTList && nbtbase1 instanceof NBTList) {
                NBTTagList nbttaglist = new NBTTagList();
                nbttaglist.addAll((NBTList)nbtbase);
                nbttaglist.addAll((NBTList)nbtbase1);
                return nbttaglist;
            } else {
                throw new IllegalArgumentException("Could not merge " + nbtbase + " and " + nbtbase1);
            }
        }
    }

    public Optional<Map<NBTBase, NBTBase>> d(NBTBase nbtbase) {
        if (nbtbase instanceof NBTTagCompound) {
            NBTTagCompound nbttagcompound = (NBTTagCompound)nbtbase;
            return Optional.of(nbttagcompound.getKeys().stream().map((s) -> {
                return Pair.of(this.a(s), nbttagcompound.get(s));
            }).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond)));
        } else {
            return Optional.empty();
        }
    }

    public NBTBase a(Map<NBTBase, NBTBase> map) {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        for(Entry entry : map.entrySet()) {
            nbttagcompound.set(((NBTBase)entry.getKey()).b_(), (NBTBase)entry.getValue());
        }

        return nbttagcompound;
    }

    public Optional<Stream<NBTBase>> e(NBTBase nbtbase) {
        return nbtbase instanceof NBTList ? Optional.of(((NBTList)nbtbase).stream().map((nbtbase1) -> {
            return nbtbase1;
        })) : Optional.empty();
    }

    public Optional<ByteBuffer> f(NBTBase nbtbase) {
        return nbtbase instanceof NBTTagByteArray ? Optional.of(ByteBuffer.wrap(((NBTTagByteArray)nbtbase).c())) : super.getByteBuffer(nbtbase);
    }

    public NBTBase a(ByteBuffer bytebuffer) {
        return new NBTTagByteArray(DataFixUtils.toArray(bytebuffer));
    }

    public Optional<IntStream> g(NBTBase nbtbase) {
        return nbtbase instanceof NBTTagIntArray ? Optional.of(Arrays.stream(((NBTTagIntArray)nbtbase).d())) : super.getIntStream(nbtbase);
    }

    public NBTBase a(IntStream intstream) {
        return new NBTTagIntArray(intstream.toArray());
    }

    public Optional<LongStream> h(NBTBase nbtbase) {
        return nbtbase instanceof NBTTagLongArray ? Optional.of(Arrays.stream(((NBTTagLongArray)nbtbase).d())) : super.getLongStream(nbtbase);
    }

    public NBTBase a(LongStream longstream) {
        return new NBTTagLongArray(longstream.toArray());
    }

    public NBTBase a(Stream<NBTBase> stream) {
        PeekingIterator peekingiterator = Iterators.peekingIterator(stream.iterator());
        if (!peekingiterator.hasNext()) {
            return new NBTTagList();
        } else {
            NBTBase nbtbase = (NBTBase)peekingiterator.peek();
            if (nbtbase instanceof NBTTagByte) {
                ArrayList arraylist2 = Lists.newArrayList(Iterators.transform(peekingiterator, (nbtbase2) -> {
                    return ((NBTTagByte)nbtbase2).g();
                }));
                return new NBTTagByteArray(arraylist2);
            } else if (nbtbase instanceof NBTTagInt) {
                ArrayList arraylist1 = Lists.newArrayList(Iterators.transform(peekingiterator, (nbtbase2) -> {
                    return ((NBTTagInt)nbtbase2).e();
                }));
                return new NBTTagIntArray(arraylist1);
            } else if (nbtbase instanceof NBTTagLong) {
                ArrayList arraylist = Lists.newArrayList(Iterators.transform(peekingiterator, (nbtbase2) -> {
                    return ((NBTTagLong)nbtbase2).d();
                }));
                return new NBTTagLongArray(arraylist);
            } else {
                NBTTagList nbttaglist = new NBTTagList();

                while(peekingiterator.hasNext()) {
                    NBTBase nbtbase1 = (NBTBase)peekingiterator.next();
                    if (!(nbtbase1 instanceof NBTTagEnd)) {
                        nbttaglist.add(nbtbase1);
                    }
                }

                return nbttaglist;
            }
        }
    }

    public NBTBase a(NBTBase nbtbase, String s) {
        if (nbtbase instanceof NBTTagCompound) {
            NBTTagCompound nbttagcompound = (NBTTagCompound)nbtbase;
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            nbttagcompound.getKeys().stream().filter((s2) -> {
                return !Objects.equals(s2, s);
            }).forEach((s1) -> {
                nbttagcompound1.set(s1, nbttagcompound.get(s1));
            });
            return nbttagcompound1;
        } else {
            return nbtbase;
        }
    }

    public String toString() {
        return "NBT";
    }

    // $FF: synthetic method
    public Object remove(Object object, String s) {
        return this.a((NBTBase)object, s);
    }

    // $FF: synthetic method
    public Object createLongList(LongStream longstream) {
        return this.a(longstream);
    }

    // $FF: synthetic method
    public Optional getLongStream(Object object) {
        return this.h((NBTBase)object);
    }

    // $FF: synthetic method
    public Object createIntList(IntStream intstream) {
        return this.a(intstream);
    }

    // $FF: synthetic method
    public Optional getIntStream(Object object) {
        return this.g((NBTBase)object);
    }

    // $FF: synthetic method
    public Object createByteList(ByteBuffer bytebuffer) {
        return this.a(bytebuffer);
    }

    // $FF: synthetic method
    public Optional getByteBuffer(Object object) {
        return this.f((NBTBase)object);
    }

    // $FF: synthetic method
    public Object createList(Stream stream) {
        return this.a(stream);
    }

    // $FF: synthetic method
    public Optional getStream(Object object) {
        return this.e((NBTBase)object);
    }

    // $FF: synthetic method
    public Object createMap(Map map) {
        return this.a(map);
    }

    // $FF: synthetic method
    public Optional getMapValues(Object object) {
        return this.d((NBTBase)object);
    }

    // $FF: synthetic method
    public Object merge(Object object, Object object1) {
        return this.b((NBTBase)object, (NBTBase)object1);
    }

    // $FF: synthetic method
    public Object mergeInto(Object object, Object object1, Object object2) {
        return this.a((NBTBase)object, (NBTBase)object1, (NBTBase)object2);
    }

    // $FF: synthetic method
    public Object mergeInto(Object object, Object object1) {
        return this.a((NBTBase)object, (NBTBase)object1);
    }

    // $FF: synthetic method
    public Object createString(String s) {
        return this.a(s);
    }

    // $FF: synthetic method
    public Optional getStringValue(Object object) {
        return this.c((NBTBase)object);
    }

    // $FF: synthetic method
    public Object createDouble(double d0) {
        return this.a(d0);
    }

    // $FF: synthetic method
    public Object createFloat(float f) {
        return this.a(f);
    }

    // $FF: synthetic method
    public Object createLong(long i) {
        return this.a(i);
    }

    // $FF: synthetic method
    public Object createInt(int i) {
        return this.a(i);
    }

    // $FF: synthetic method
    public Object createShort(short short1) {
        return this.a(short1);
    }

    // $FF: synthetic method
    public Object createByte(byte b0) {
        return this.a(b0);
    }

    // $FF: synthetic method
    public Object createNumeric(Number number) {
        return this.a(number);
    }

    // $FF: synthetic method
    public Optional getNumberValue(Object object) {
        return this.b((NBTBase)object);
    }

    // $FF: synthetic method
    public Type getType(Object object) {
        return this.a((NBTBase)object);
    }

    // $FF: synthetic method
    public Object empty() {
        return this.a();
    }
}
