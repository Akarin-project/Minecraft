package net.minecraft.server;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RemoteControlSession extends RemoteConnectionThread {
    private static final Logger h = LogManager.getLogger();
    private boolean i;
    private Socket j;
    private final byte[] k = new byte[1460];
    private final String l;

    RemoteControlSession(IMinecraftServer iminecraftserver, Socket socket) {
        super(iminecraftserver, "RCON Client");
        this.j = socket;

        try {
            this.j.setSoTimeout(0);
        } catch (Exception var4) {
            this.a = false;
        }

        this.l = iminecraftserver.a("rcon.password", "");
        this.b("Rcon connection from: " + socket.getInetAddress());
    }

    public void run() {
        while(true) {
            try {
                if (!this.a) {
                    return;
                }

                BufferedInputStream bufferedinputstream = new BufferedInputStream(this.j.getInputStream());
                int ix = bufferedinputstream.read(this.k, 0, 1460);
                if (10 <= ix) {
                    int jx = 0;
                    int kx = StatusChallengeUtils.b(this.k, 0, ix);
                    if (kx != ix - 4) {
                        return;
                    }

                    jx = jx + 4;
                    int lx = StatusChallengeUtils.b(this.k, jx, ix);
                    jx = jx + 4;
                    int i1 = StatusChallengeUtils.a(this.k, jx);
                    jx = jx + 4;
                    switch(i1) {
                    case 2:
                        if (this.i) {
                            String s1 = StatusChallengeUtils.a(this.k, jx, ix);

                            try {
                                this.a(lx, this.b.executeRemoteCommand(s1));
                            } catch (Exception exception) {
                                this.a(lx, "Error executing: " + s1 + " (" + exception.getMessage() + ")");
                            }
                            continue;
                        }

                        this.f();
                        continue;
                    case 3:
                        String s = StatusChallengeUtils.a(this.k, jx, ix);
                        int j1 = jx + s.length();
                        if (!s.isEmpty() && s.equals(this.l)) {
                            this.i = true;
                            this.a(lx, 2, "");
                            continue;
                        }

                        this.i = false;
                        this.f();
                        continue;
                    default:
                        this.a(lx, String.format("Unknown request %s", Integer.toHexString(i1)));
                        continue;
                    }
                }
            } catch (SocketTimeoutException var17) {
                return;
            } catch (IOException var18) {
                return;
            } catch (Exception exception1) {
                h.error("Exception whilst parsing RCON input", exception1);
                return;
            } finally {
                this.g();
            }

            return;
        }
    }

    private void a(int ix, int jx, String s) throws IOException {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(1248);
        DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);
        byte[] abyte = s.getBytes("UTF-8");
        dataoutputstream.writeInt(Integer.reverseBytes(abyte.length + 10));
        dataoutputstream.writeInt(Integer.reverseBytes(ix));
        dataoutputstream.writeInt(Integer.reverseBytes(jx));
        dataoutputstream.write(abyte);
        dataoutputstream.write(0);
        dataoutputstream.write(0);
        this.j.getOutputStream().write(bytearrayoutputstream.toByteArray());
    }

    private void f() throws IOException {
        this.a(-1, 2, "");
    }

    private void a(int ix, String s) throws IOException {
        int jx = s.length();

        while(true) {
            int kx = 4096 <= jx ? 4096 : jx;
            this.a(ix, 0, s.substring(0, kx));
            s = s.substring(kx);
            jx = s.length();
            if (0 == jx) {
                break;
            }
        }

    }

    private void g() {
        if (null != this.j) {
            try {
                this.j.close();
            } catch (IOException ioexception) {
                this.c("IO: " + ioexception.getMessage());
            }

            this.j = null;
        }
    }
}
