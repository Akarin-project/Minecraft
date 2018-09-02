package net.minecraft.server;

import com.google.common.collect.UnmodifiableIterator;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class DebugReportBlocks implements DebugReportProvider {
    private final DebugReportGenerator b;

    public DebugReportBlocks(DebugReportGenerator debugreportgenerator) {
        this.b = debugreportgenerator;
    }

    public void a(HashCache var1) throws IOException {
        JsonObject jsonobject = new JsonObject();

        for(Block block : IRegistry.BLOCK) {
            MinecraftKey minecraftkey = IRegistry.BLOCK.getKey(block);
            JsonObject jsonobject1 = new JsonObject();
            BlockStateList blockstatelist = block.getStates();
            if (!blockstatelist.d().isEmpty()) {
                JsonObject jsonobject2 = new JsonObject();

                for(IBlockState iblockstate : blockstatelist.d()) {
                    JsonArray jsonarray = new JsonArray();

                    for(Comparable comparable : iblockstate.d()) {
                        jsonarray.add(SystemUtils.a(iblockstate, comparable));
                    }

                    jsonobject2.add(iblockstate.a(), jsonarray);
                }

                jsonobject1.add("properties", jsonobject2);
            }

            JsonArray jsonarray1 = new JsonArray();

            JsonObject jsonobject3;
            for(UnmodifiableIterator unmodifiableiterator = blockstatelist.a().iterator(); unmodifiableiterator.hasNext(); jsonarray1.add(jsonobject3)) {
                IBlockData iblockdata = (IBlockData)unmodifiableiterator.next();
                jsonobject3 = new JsonObject();
                JsonObject jsonobject4 = new JsonObject();

                for(IBlockState iblockstate1 : blockstatelist.d()) {
                    jsonobject4.addProperty(iblockstate1.a(), SystemUtils.a(iblockstate1, iblockdata.get(iblockstate1)));
                }

                if (jsonobject4.size() > 0) {
                    jsonobject3.add("properties", jsonobject4);
                }

                jsonobject3.addProperty("id", Block.getCombinedId(iblockdata));
                if (iblockdata == block.getBlockData()) {
                    jsonobject3.addProperty("default", true);
                }
            }

            jsonobject1.add("states", jsonarray1);
            jsonobject.add(minecraftkey.toString(), jsonobject1);
        }

        java.nio.file.Path path = this.b.b().resolve("reports/blocks.json");
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
        return "Block List";
    }
}
