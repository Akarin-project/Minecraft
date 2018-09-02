package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LootTable {
    private static final Logger b = LogManager.getLogger();
    public static final LootTable a = new LootTable(new LootSelector[0]);
    private final LootSelector[] c;

    public LootTable(LootSelector[] alootselector) {
        this.c = alootselector;
    }

    public List<ItemStack> a(Random random, LootTableInfo loottableinfo) {
        ArrayList arraylist = Lists.newArrayList();
        if (loottableinfo.a(this)) {
            for(LootSelector lootselector : this.c) {
                lootselector.b(arraylist, random, loottableinfo);
            }

            loottableinfo.b(this);
        } else {
            b.warn("Detected infinite loop in loot tables");
        }

        return arraylist;
    }

    public void a(IInventory iinventory, Random random, LootTableInfo loottableinfo) {
        List list = this.a(random, loottableinfo);
        List list1 = this.a(iinventory, random);
        this.a(list, list1.size(), random);

        for(ItemStack itemstack : list) {
            if (list1.isEmpty()) {
                b.warn("Tried to over-fill a container");
                return;
            }

            if (itemstack.isEmpty()) {
                iinventory.setItem(list1.remove(list1.size() - 1), ItemStack.a);
            } else {
                iinventory.setItem(list1.remove(list1.size() - 1), itemstack);
            }
        }

    }

    private void a(List<ItemStack> list, int i, Random random) {
        ArrayList arraylist = Lists.newArrayList();
        Iterator iterator = list.iterator();

        while(iterator.hasNext()) {
            ItemStack itemstack = (ItemStack)iterator.next();
            if (itemstack.isEmpty()) {
                iterator.remove();
            } else if (itemstack.getCount() > 1) {
                arraylist.add(itemstack);
                iterator.remove();
            }
        }

        while(i - list.size() - arraylist.size() > 0 && !arraylist.isEmpty()) {
            ItemStack itemstack2 = (ItemStack)arraylist.remove(MathHelper.nextInt(random, 0, arraylist.size() - 1));
            int j = MathHelper.nextInt(random, 1, itemstack2.getCount() / 2);
            ItemStack itemstack1 = itemstack2.cloneAndSubtract(j);
            if (itemstack2.getCount() > 1 && random.nextBoolean()) {
                arraylist.add(itemstack2);
            } else {
                list.add(itemstack2);
            }

            if (itemstack1.getCount() > 1 && random.nextBoolean()) {
                arraylist.add(itemstack1);
            } else {
                list.add(itemstack1);
            }
        }

        list.addAll(arraylist);
        Collections.shuffle(list, random);
    }

    private List<Integer> a(IInventory iinventory, Random random) {
        ArrayList arraylist = Lists.newArrayList();

        for(int i = 0; i < iinventory.getSize(); ++i) {
            if (iinventory.getItem(i).isEmpty()) {
                arraylist.add(i);
            }
        }

        Collections.shuffle(arraylist, random);
        return arraylist;
    }

    public static class a implements JsonDeserializer<LootTable>, JsonSerializer<LootTable> {
        public a() {
        }

        public LootTable a(JsonElement jsonelement, Type var2, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException {
            JsonObject jsonobject = ChatDeserializer.m(jsonelement, "loot table");
            LootSelector[] alootselector = (LootSelector[])ChatDeserializer.a(jsonobject, "pools", new LootSelector[0], jsondeserializationcontext, LootSelector[].class);
            return new LootTable(alootselector);
        }

        public JsonElement a(LootTable loottable, Type var2, JsonSerializationContext jsonserializationcontext) {
            JsonObject jsonobject = new JsonObject();
            jsonobject.add("pools", jsonserializationcontext.serialize(loottable.c));
            return jsonobject;
        }

        // $FF: synthetic method
        public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonserializationcontext) {
            return this.a((LootTable)object, type, jsonserializationcontext);
        }

        // $FF: synthetic method
        public Object deserialize(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException {
            return this.a(jsonelement, type, jsondeserializationcontext);
        }
    }
}
