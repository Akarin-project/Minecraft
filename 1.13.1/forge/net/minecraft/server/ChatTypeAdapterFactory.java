package net.minecraft.server;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.annotation.Nullable;

public class ChatTypeAdapterFactory implements TypeAdapterFactory {
    public ChatTypeAdapterFactory() {
    }

    @Nullable
    public <T> TypeAdapter<T> create(Gson var1, TypeToken<T> typetoken) {
        Class oclass = typetoken.getRawType();
        if (!oclass.isEnum()) {
            return null;
        } else {
            final HashMap hashmap = Maps.newHashMap();

            for(Object object : oclass.getEnumConstants()) {
                hashmap.put(this.a(object), object);
            }

            return new TypeAdapter<T>() {
                public void write(JsonWriter jsonwriter, T object1) throws IOException {
                    if (object1 == null) {
                        jsonwriter.nullValue();
                    } else {
                        jsonwriter.value(ChatTypeAdapterFactory.this.a(object1));
                    }

                }

                @Nullable
                public T read(JsonReader jsonreader) throws IOException {
                    if (jsonreader.peek() == JsonToken.NULL) {
                        jsonreader.nextNull();
                        return (T)null;
                    } else {
                        return (T)hashmap.get(jsonreader.nextString());
                    }
                }
            };
        }
    }

    private String a(Object object) {
        return object instanceof Enum ? ((Enum)object).name().toLowerCase(Locale.ROOT) : object.toString().toLowerCase(Locale.ROOT);
    }
}
