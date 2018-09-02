package net.minecraft.server;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class DebugReportItems implements DebugReportProvider {
    private final DebugReportGenerator b;

    public DebugReportItems(DebugReportGenerator debugreportgenerator) {
        this.b = debugreportgenerator;
    }

    public void a(HashCache var1) throws IOException {
        JsonObject jsonobject = new JsonObject();

        for(Item item : IRegistry.ITEM) {
            MinecraftKey minecraftkey = IRegistry.ITEM.getKey(item);
            JsonObject jsonobject1 = new JsonObject();
            jsonobject1.addProperty("protocol_id", Item.getId(item));
            jsonobject.add(minecraftkey.toString(), jsonobject1);
        }

        java.nio.file.Path path = this.b.b().resolve("reports/items.json");
        Files.createDirectories(path.getParent());
        BufferedWriter bufferedwriter = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
        Throwable throwable2 = null;

        try {
            String s = (new GsonBuilder()).setPrettyPrinting().create().toJson(jsonobject);
            bufferedwriter.write(s);
        } catch (Throwable throwable1) {
            throwable2 = throwable1;
            throw throwable1;
        } finally {
            if (bufferedwriter != null) {
                if (throwable2 != null) {
                    try {
                        bufferedwriter.close();
                    } catch (Throwable throwable) {
                        throwable2.addSuppressed(throwable);
                    }
                } else {
                    bufferedwriter.close();
                }
            }

        }

    }

    public String a() {
        return "Item List";
    }
}
