package net.minecraft.server;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Resource implements IResource {
    private static final Logger b = LogManager.getLogger();
    public static final Executor a = Executors.newSingleThreadExecutor((new ThreadFactoryBuilder()).setDaemon(true).setNameFormat("Resource IO {0}").setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(b)).build());
    private final String c;
    private final MinecraftKey d;
    private final InputStream e;
    private final InputStream f;

    public Resource(String s, MinecraftKey minecraftkey, InputStream inputstream, @Nullable InputStream inputstream1) {
        this.c = s;
        this.d = minecraftkey;
        this.e = inputstream;
        this.f = inputstream1;
    }

    public InputStream b() {
        return this.e;
    }

    public String d() {
        return this.c;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (!(object instanceof Resource)) {
            return false;
        } else {
            Resource resource1 = (Resource)object;
            if (this.d != null) {
                if (!this.d.equals(resource1.d)) {
                    return false;
                }
            } else if (resource1.d != null) {
                return false;
            }

            if (this.c != null) {
                if (!this.c.equals(resource1.c)) {
                    return false;
                }
            } else if (resource1.c != null) {
                return false;
            }

            return true;
        }
    }

    public int hashCode() {
        int i = this.c != null ? this.c.hashCode() : 0;
        i = 31 * i + (this.d != null ? this.d.hashCode() : 0);
        return i;
    }

    public void close() throws IOException {
        this.e.close();
        if (this.f != null) {
            this.f.close();
        }

    }
}
