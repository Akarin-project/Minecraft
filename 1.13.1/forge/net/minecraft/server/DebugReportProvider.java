package net.minecraft.server;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import java.io.IOException;

public interface DebugReportProvider {
    HashFunction a = Hashing.sha1();

    void a(HashCache var1) throws IOException;

    String a();
}
