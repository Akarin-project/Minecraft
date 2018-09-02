package net.minecraft.server;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HttpUtilities {
    private static final Logger b = LogManager.getLogger();
    public static final ListeningExecutorService a = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool((new ThreadFactoryBuilder()).setDaemon(true).setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(b)).setNameFormat("Downloader %d").build()));

    public static int a() {
        try {
            ServerSocket serversocket = new ServerSocket(0);
            Throwable throwable = null;

            int i;
            try {
                i = serversocket.getLocalPort();
            } catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            } finally {
                if (serversocket != null) {
                    if (throwable != null) {
                        try {
                            serversocket.close();
                        } catch (Throwable throwable1) {
                            throwable.addSuppressed(throwable1);
                        }
                    } else {
                        serversocket.close();
                    }
                }

            }

            return i;
        } catch (IOException var14) {
            return 25564;
        }
    }
}
