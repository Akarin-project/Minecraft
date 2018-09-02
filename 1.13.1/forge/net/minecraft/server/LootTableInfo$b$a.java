package net.minecraft.server;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

public class LootTableInfo$b$a extends TypeAdapter<LootTableInfo.EntityTarget> {
    public LootTableInfo$b$a() {
    }

    public void a(JsonWriter jsonwriter, LootTableInfo.EntityTarget loottableinfo$entitytarget) throws IOException {
        jsonwriter.value(LootTableInfo.EntityTarget.a(loottableinfo$entitytarget));
    }

    public LootTableInfo.EntityTarget a(JsonReader jsonreader) throws IOException {
        return LootTableInfo.EntityTarget.a(jsonreader.nextString());
    }

    // $FF: synthetic method
    public Object read(JsonReader jsonreader) throws IOException {
        return this.a(jsonreader);
    }

    // $FF: synthetic method
    public void write(JsonWriter jsonwriter, Object object) throws IOException {
        this.a(jsonwriter, (LootTableInfo.EntityTarget)object);
    }
}
