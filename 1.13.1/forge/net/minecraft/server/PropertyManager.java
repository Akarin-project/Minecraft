package net.minecraft.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropertyManager {
    private static final Logger a = LogManager.getLogger();
    public final Properties properties = new Properties();
    private final File file;

    public PropertyManager(File file1) {
        this.file = file1;
        if (file1.exists()) {
            FileInputStream fileinputstream = null;

            try {
                fileinputstream = new FileInputStream(file1);
                this.properties.load(fileinputstream);
            } catch (Exception exception) {
                a.warn("Failed to load {}", file1, exception);
                this.a();
            } finally {
                if (fileinputstream != null) {
                    try {
                        fileinputstream.close();
                    } catch (IOException var11) {
                        ;
                    }
                }

            }
        } else {
            a.warn("{} does not exist", file1);
            this.a();
        }

    }

    public void a() {
        a.info("Generating new properties file");
        this.savePropertiesFile();
    }

    public void savePropertiesFile() {
        FileOutputStream fileoutputstream = null;

        try {
            fileoutputstream = new FileOutputStream(this.file);
            this.properties.store(fileoutputstream, "Minecraft server properties");
        } catch (Exception exception) {
            a.warn("Failed to save {}", this.file, exception);
            this.a();
        } finally {
            if (fileoutputstream != null) {
                try {
                    fileoutputstream.close();
                } catch (IOException var10) {
                    ;
                }
            }

        }

    }

    public File c() {
        return this.file;
    }

    public String getString(String s, String s1) {
        if (!this.properties.containsKey(s)) {
            this.properties.setProperty(s, s1);
            this.savePropertiesFile();
            this.savePropertiesFile();
        }

        return this.properties.getProperty(s, s1);
    }

    public int getInt(String s, int i) {
        try {
            return Integer.parseInt(this.getString(s, "" + i));
        } catch (Exception var4) {
            this.properties.setProperty(s, "" + i);
            this.savePropertiesFile();
            return i;
        }
    }

    public long getLong(String s, long i) {
        try {
            return Long.parseLong(this.getString(s, "" + i));
        } catch (Exception var5) {
            this.properties.setProperty(s, "" + i);
            this.savePropertiesFile();
            return i;
        }
    }

    public boolean getBoolean(String s, boolean flag) {
        try {
            return Boolean.parseBoolean(this.getString(s, "" + flag));
        } catch (Exception var4) {
            this.properties.setProperty(s, "" + flag);
            this.savePropertiesFile();
            return flag;
        }
    }

    public void setProperty(String s, Object object) {
        this.properties.setProperty(s, "" + object);
    }

    public boolean a(String s) {
        return this.properties.containsKey(s);
    }

    public void b(String s) {
        this.properties.remove(s);
    }
}
