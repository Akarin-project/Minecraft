package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.ProfileLookupCallback;
import com.mojang.authlib.yggdrasil.ProfileNotFoundException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NameReferencingFileConverter {
    private static final Logger e = LogManager.getLogger();
    public static final File a = new File("banned-ips.txt");
    public static final File b = new File("banned-players.txt");
    public static final File c = new File("ops.txt");
    public static final File d = new File("white-list.txt");

    static List<String> a(File file1, Map<String, String[]> map) throws IOException {
        List list = Files.readLines(file1, StandardCharsets.UTF_8);

        for(String s : list) {
            s = s.trim();
            if (!s.startsWith("#") && s.length() >= 1) {
                String[] astring = s.split("\\|");
                map.put(astring[0].toLowerCase(Locale.ROOT), astring);
            }
        }

        return list;
    }

    private static void a(MinecraftServer minecraftserver, Collection<String> collection, ProfileLookupCallback profilelookupcallback) {
        String[] astring = (String[])collection.stream().filter((s1) -> {
            return !UtilColor.b(s1);
        }).toArray((i) -> {
            return new String[i];
        });
        if (minecraftserver.getOnlineMode()) {
            minecraftserver.getGameProfileRepository().findProfilesByNames(astring, Agent.MINECRAFT, profilelookupcallback);
        } else {
            for(String s : astring) {
                UUID uuid = EntityHuman.a(new GameProfile((UUID)null, s));
                GameProfile gameprofile = new GameProfile(uuid, s);
                profilelookupcallback.onProfileLookupSucceeded(gameprofile);
            }
        }

    }

    public static boolean a(final MinecraftServer minecraftserver) {
        final GameProfileBanList gameprofilebanlist = new GameProfileBanList(PlayerList.a);
        if (b.exists() && b.isFile()) {
            if (gameprofilebanlist.c().exists()) {
                try {
                    gameprofilebanlist.load();
                } catch (FileNotFoundException filenotfoundexception) {
                    e.warn("Could not load existing file {}", gameprofilebanlist.c().getName(), filenotfoundexception);
                }
            }

            try {
                final HashMap hashmap = Maps.newHashMap();
                a(b, hashmap);
                ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
                    public void onProfileLookupSucceeded(GameProfile gameprofile) {
                        minecraftserver.getUserCache().a(gameprofile);
                        String[] astring = (String[])hashmap.get(gameprofile.getName().toLowerCase(Locale.ROOT));
                        if (astring == null) {
                            NameReferencingFileConverter.e.warn("Could not convert user banlist entry for {}", gameprofile.getName());
                            throw new NameReferencingFileConverter.FileConversionException("Profile not in the conversionlist");
                        } else {
                            Date date = astring.length > 1 ? NameReferencingFileConverter.b(astring[1], (Date)null) : null;
                            String s = astring.length > 2 ? astring[2] : null;
                            Date date1 = astring.length > 3 ? NameReferencingFileConverter.b(astring[3], (Date)null) : null;
                            String s1 = astring.length > 4 ? astring[4] : null;
                            gameprofilebanlist.add(new GameProfileBanEntry(gameprofile, date, s, date1, s1));
                        }
                    }

                    public void onProfileLookupFailed(GameProfile gameprofile, Exception exception) {
                        NameReferencingFileConverter.e.warn("Could not lookup user banlist entry for {}", gameprofile.getName(), exception);
                        if (!(exception instanceof ProfileNotFoundException)) {
                            throw new NameReferencingFileConverter.FileConversionException("Could not request user " + gameprofile.getName() + " from backend systems", exception);
                        }
                    }
                };
                a(minecraftserver, hashmap.keySet(), profilelookupcallback);
                gameprofilebanlist.save();
                c(b);
                return true;
            } catch (IOException ioexception) {
                e.warn("Could not read old user banlist to convert it!", ioexception);
                return false;
            } catch (NameReferencingFileConverter.FileConversionException namereferencingfileconverter$fileconversionexception) {
                e.error("Conversion failed, please try again later", namereferencingfileconverter$fileconversionexception);
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean b(MinecraftServer var0) {
        IpBanList ipbanlist = new IpBanList(PlayerList.b);
        if (a.exists() && a.isFile()) {
            if (ipbanlist.c().exists()) {
                try {
                    ipbanlist.load();
                } catch (FileNotFoundException filenotfoundexception) {
                    e.warn("Could not load existing file {}", ipbanlist.c().getName(), filenotfoundexception);
                }
            }

            try {
                HashMap hashmap = Maps.newHashMap();
                a(a, hashmap);

                for(String s : hashmap.keySet()) {
                    String[] astring = (String[])hashmap.get(s);
                    Date date = astring.length > 1 ? b(astring[1], (Date)null) : null;
                    String s1 = astring.length > 2 ? astring[2] : null;
                    Date date1 = astring.length > 3 ? b(astring[3], (Date)null) : null;
                    String s2 = astring.length > 4 ? astring[4] : null;
                    ipbanlist.add(new IpBanEntry(s, date, s1, date1, s2));
                }

                ipbanlist.save();
                c(a);
                return true;
            } catch (IOException ioexception) {
                e.warn("Could not parse old ip banlist to convert it!", ioexception);
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean c(final MinecraftServer minecraftserver) {
        final OpList oplist = new OpList(PlayerList.c);
        if (c.exists() && c.isFile()) {
            if (oplist.c().exists()) {
                try {
                    oplist.load();
                } catch (FileNotFoundException filenotfoundexception) {
                    e.warn("Could not load existing file {}", oplist.c().getName(), filenotfoundexception);
                }
            }

            try {
                List list = Files.readLines(c, StandardCharsets.UTF_8);
                ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
                    public void onProfileLookupSucceeded(GameProfile gameprofile) {
                        minecraftserver.getUserCache().a(gameprofile);
                        oplist.add(new OpListEntry(gameprofile, minecraftserver.j(), false));
                    }

                    public void onProfileLookupFailed(GameProfile gameprofile, Exception exception) {
                        NameReferencingFileConverter.e.warn("Could not lookup oplist entry for {}", gameprofile.getName(), exception);
                        if (!(exception instanceof ProfileNotFoundException)) {
                            throw new NameReferencingFileConverter.FileConversionException("Could not request user " + gameprofile.getName() + " from backend systems", exception);
                        }
                    }
                };
                a(minecraftserver, list, profilelookupcallback);
                oplist.save();
                c(c);
                return true;
            } catch (IOException ioexception) {
                e.warn("Could not read old oplist to convert it!", ioexception);
                return false;
            } catch (NameReferencingFileConverter.FileConversionException namereferencingfileconverter$fileconversionexception) {
                e.error("Conversion failed, please try again later", namereferencingfileconverter$fileconversionexception);
                return false;
            }
        } else {
            return true;
        }
    }

    public static boolean d(final MinecraftServer minecraftserver) {
        final WhiteList whitelist = new WhiteList(PlayerList.d);
        if (d.exists() && d.isFile()) {
            if (whitelist.c().exists()) {
                try {
                    whitelist.load();
                } catch (FileNotFoundException filenotfoundexception) {
                    e.warn("Could not load existing file {}", whitelist.c().getName(), filenotfoundexception);
                }
            }

            try {
                List list = Files.readLines(d, StandardCharsets.UTF_8);
                ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
                    public void onProfileLookupSucceeded(GameProfile gameprofile) {
                        minecraftserver.getUserCache().a(gameprofile);
                        whitelist.add(new WhiteListEntry(gameprofile));
                    }

                    public void onProfileLookupFailed(GameProfile gameprofile, Exception exception) {
                        NameReferencingFileConverter.e.warn("Could not lookup user whitelist entry for {}", gameprofile.getName(), exception);
                        if (!(exception instanceof ProfileNotFoundException)) {
                            throw new NameReferencingFileConverter.FileConversionException("Could not request user " + gameprofile.getName() + " from backend systems", exception);
                        }
                    }
                };
                a(minecraftserver, list, profilelookupcallback);
                whitelist.save();
                c(d);
                return true;
            } catch (IOException ioexception) {
                e.warn("Could not read old whitelist to convert it!", ioexception);
                return false;
            } catch (NameReferencingFileConverter.FileConversionException namereferencingfileconverter$fileconversionexception) {
                e.error("Conversion failed, please try again later", namereferencingfileconverter$fileconversionexception);
                return false;
            }
        } else {
            return true;
        }
    }

    public static String a(final MinecraftServer minecraftserver, String s) {
        if (!UtilColor.b(s) && s.length() <= 16) {
            GameProfile gameprofile = minecraftserver.getUserCache().getProfile(s);
            if (gameprofile != null && gameprofile.getId() != null) {
                return gameprofile.getId().toString();
            } else if (!minecraftserver.H() && minecraftserver.getOnlineMode()) {
                final ArrayList arraylist = Lists.newArrayList();
                ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
                    public void onProfileLookupSucceeded(GameProfile gameprofile1) {
                        minecraftserver.getUserCache().a(gameprofile1);
                        arraylist.add(gameprofile1);
                    }

                    public void onProfileLookupFailed(GameProfile gameprofile1, Exception exception) {
                        NameReferencingFileConverter.e.warn("Could not lookup user whitelist entry for {}", gameprofile1.getName(), exception);
                    }
                };
                a(minecraftserver, Lists.newArrayList(new String[]{s}), profilelookupcallback);
                return !arraylist.isEmpty() && ((GameProfile)arraylist.get(0)).getId() != null ? ((GameProfile)arraylist.get(0)).getId().toString() : "";
            } else {
                return EntityHuman.a(new GameProfile((UUID)null, s)).toString();
            }
        } else {
            return s;
        }
    }

    public static boolean a(final DedicatedServer dedicatedserver, PropertyManager propertymanager) {
        final File file1 = d(propertymanager);
        final File file2 = new File(file1.getParentFile(), "playerdata");
        final File file3 = new File(file1.getParentFile(), "unknownplayers");
        if (file1.exists() && file1.isDirectory()) {
            File[] afile = file1.listFiles();
            ArrayList arraylist = Lists.newArrayList();

            for(File file4 : afile) {
                String s = file4.getName();
                if (s.toLowerCase(Locale.ROOT).endsWith(".dat")) {
                    String s1 = s.substring(0, s.length() - ".dat".length());
                    if (!s1.isEmpty()) {
                        arraylist.add(s1);
                    }
                }
            }

            try {
                final String[] astring = (String[])arraylist.toArray(new String[arraylist.size()]);
                ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
                    public void onProfileLookupSucceeded(GameProfile gameprofile) {
                        dedicatedserver.getUserCache().a(gameprofile);
                        UUID uuid = gameprofile.getId();
                        if (uuid == null) {
                            throw new NameReferencingFileConverter.FileConversionException("Missing UUID for user profile " + gameprofile.getName());
                        } else {
                            this.a(file2, this.a(gameprofile), uuid.toString());
                        }
                    }

                    public void onProfileLookupFailed(GameProfile gameprofile, Exception exception) {
                        NameReferencingFileConverter.e.warn("Could not lookup user uuid for {}", gameprofile.getName(), exception);
                        if (exception instanceof ProfileNotFoundException) {
                            String s2 = this.a(gameprofile);
                            this.a(file3, s2, s2);
                        } else {
                            throw new NameReferencingFileConverter.FileConversionException("Could not request user " + gameprofile.getName() + " from backend systems", exception);
                        }
                    }

                    private void a(File file5, String s2, String s3) {
                        File file6 = new File(file1, s2 + ".dat");
                        File file7 = new File(file5, s3 + ".dat");
                        NameReferencingFileConverter.b(file5);
                        if (!file6.renameTo(file7)) {
                            throw new NameReferencingFileConverter.FileConversionException("Could not convert file for " + s2);
                        }
                    }

                    private String a(GameProfile gameprofile) {
                        String s2 = null;

                        for(String s3 : astring) {
                            if (s3 != null && s3.equalsIgnoreCase(gameprofile.getName())) {
                                s2 = s3;
                                break;
                            }
                        }

                        if (s2 == null) {
                            throw new NameReferencingFileConverter.FileConversionException("Could not find the filename for " + gameprofile.getName() + " anymore");
                        } else {
                            return s2;
                        }
                    }
                };
                a(dedicatedserver, Lists.newArrayList(astring), profilelookupcallback);
                return true;
            } catch (NameReferencingFileConverter.FileConversionException namereferencingfileconverter$fileconversionexception) {
                e.error("Conversion failed, please try again later", namereferencingfileconverter$fileconversionexception);
                return false;
            }
        } else {
            return true;
        }
    }

    private static void b(File file1) {
        if (file1.exists()) {
            if (!file1.isDirectory()) {
                throw new NameReferencingFileConverter.FileConversionException("Can't create directory " + file1.getName() + " in world save directory.");
            }
        } else if (!file1.mkdirs()) {
            throw new NameReferencingFileConverter.FileConversionException("Can't create directory " + file1.getName() + " in world save directory.");
        }
    }

    public static boolean a(PropertyManager propertymanager) {
        boolean flag = b(propertymanager);
        flag = flag && c(propertymanager);
        return flag;
    }

    private static boolean b(PropertyManager var0) {
        boolean flag = false;
        if (b.exists() && b.isFile()) {
            flag = true;
        }

        boolean flag1 = false;
        if (a.exists() && a.isFile()) {
            flag1 = true;
        }

        boolean flag2 = false;
        if (c.exists() && c.isFile()) {
            flag2 = true;
        }

        boolean flag3 = false;
        if (d.exists() && d.isFile()) {
            flag3 = true;
        }

        if (!flag && !flag1 && !flag2 && !flag3) {
            return true;
        } else {
            e.warn("**** FAILED TO START THE SERVER AFTER ACCOUNT CONVERSION!");
            e.warn("** please remove the following files and restart the server:");
            if (flag) {
                e.warn("* {}", b.getName());
            }

            if (flag1) {
                e.warn("* {}", a.getName());
            }

            if (flag2) {
                e.warn("* {}", c.getName());
            }

            if (flag3) {
                e.warn("* {}", d.getName());
            }

            return false;
        }
    }

    private static boolean c(PropertyManager propertymanager) {
        File file1 = d(propertymanager);
        if (!file1.exists() || !file1.isDirectory() || file1.list().length <= 0 && file1.delete()) {
            return true;
        } else {
            e.warn("**** DETECTED OLD PLAYER DIRECTORY IN THE WORLD SAVE");
            e.warn("**** THIS USUALLY HAPPENS WHEN THE AUTOMATIC CONVERSION FAILED IN SOME WAY");
            e.warn("** please restart the server and if the problem persists, remove the directory '{}'", file1.getPath());
            return false;
        }
    }

    private static File d(PropertyManager propertymanager) {
        String s = propertymanager.getString("level-name", "world");
        File file1 = new File(s);
        return new File(file1, "players");
    }

    private static void c(File file1) {
        File file2 = new File(file1.getName() + ".converted");
        file1.renameTo(file2);
    }

    private static Date b(String s, Date date) {
        Date date1;
        try {
            date1 = ExpirableListEntry.a.parse(s);
        } catch (ParseException var4) {
            date1 = date;
        }

        return date1;
    }

    static class FileConversionException extends RuntimeException {
        private FileConversionException(String s, Throwable throwable) {
            super(s, throwable);
        }

        private FileConversionException(String s) {
            super(s);
        }
    }
}
