package net.minecraft.server;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.ProfileLookupCallback;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;

public class UserCache {
    public static final SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    private static boolean c;
    private final Map<String, UserCache.UserCacheEntry> d = Maps.newHashMap();
    private final Map<UUID, UserCache.UserCacheEntry> e = Maps.newHashMap();
    private final Deque<GameProfile> f = Lists.newLinkedList();
    private final GameProfileRepository g;
    protected final Gson b;
    private final File h;
    private static final ParameterizedType i = new ParameterizedType() {
        public Type[] getActualTypeArguments() {
            return new Type[]{UserCache.UserCacheEntry.class};
        }

        public Type getRawType() {
            return List.class;
        }

        public Type getOwnerType() {
            return null;
        }
    };

    public UserCache(GameProfileRepository gameprofilerepository, File file1) {
        this.g = gameprofilerepository;
        this.h = file1;
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.registerTypeHierarchyAdapter(UserCache.UserCacheEntry.class, new UserCache.BanEntrySerializer());
        this.b = gsonbuilder.create();
        this.b();
    }

    private static GameProfile a(GameProfileRepository gameprofilerepository, String s) {
        final GameProfile[] agameprofile = new GameProfile[1];
        ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
            public void onProfileLookupSucceeded(GameProfile gameprofile1) {
                agameprofile[0] = gameprofile1;
            }

            public void onProfileLookupFailed(GameProfile var1, Exception var2) {
                agameprofile[0] = null;
            }
        };
        gameprofilerepository.findProfilesByNames(new String[]{s}, Agent.MINECRAFT, profilelookupcallback);
        if (!d() && agameprofile[0] == null) {
            UUID uuid = EntityHuman.a(new GameProfile((UUID)null, s));
            GameProfile gameprofile = new GameProfile(uuid, s);
            profilelookupcallback.onProfileLookupSucceeded(gameprofile);
        }

        return agameprofile[0];
    }

    public static void a(boolean flag) {
        c = flag;
    }

    private static boolean d() {
        return c;
    }

    public void a(GameProfile gameprofile) {
        this.a(gameprofile, (Date)null);
    }

    private void a(GameProfile gameprofile, Date date) {
        UUID uuid = gameprofile.getId();
        if (date == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(2, 1);
            date = calendar.getTime();
        }

        UserCache.UserCacheEntry usercache$usercacheentry1 = new UserCache.UserCacheEntry(gameprofile, date);
        if (this.e.containsKey(uuid)) {
            UserCache.UserCacheEntry usercache$usercacheentry = (UserCache.UserCacheEntry)this.e.get(uuid);
            this.d.remove(usercache$usercacheentry.a().getName().toLowerCase(Locale.ROOT));
            this.f.remove(gameprofile);
        }

        this.d.put(gameprofile.getName().toLowerCase(Locale.ROOT), usercache$usercacheentry1);
        this.e.put(uuid, usercache$usercacheentry1);
        this.f.addFirst(gameprofile);
        this.c();
    }

    @Nullable
    public GameProfile getProfile(String s) {
        String s1 = s.toLowerCase(Locale.ROOT);
        UserCache.UserCacheEntry usercache$usercacheentry = (UserCache.UserCacheEntry)this.d.get(s1);
        if (usercache$usercacheentry != null && (new Date()).getTime() >= usercache$usercacheentry.c.getTime()) {
            this.e.remove(usercache$usercacheentry.a().getId());
            this.d.remove(usercache$usercacheentry.a().getName().toLowerCase(Locale.ROOT));
            this.f.remove(usercache$usercacheentry.a());
            usercache$usercacheentry = null;
        }

        if (usercache$usercacheentry != null) {
            GameProfile gameprofile = usercache$usercacheentry.a();
            this.f.remove(gameprofile);
            this.f.addFirst(gameprofile);
        } else {
            GameProfile gameprofile1 = a(this.g, s1);
            if (gameprofile1 != null) {
                this.a(gameprofile1);
                usercache$usercacheentry = (UserCache.UserCacheEntry)this.d.get(s1);
            }
        }

        this.c();
        return usercache$usercacheentry == null ? null : usercache$usercacheentry.a();
    }

    @Nullable
    public GameProfile a(UUID uuid) {
        UserCache.UserCacheEntry usercache$usercacheentry = (UserCache.UserCacheEntry)this.e.get(uuid);
        return usercache$usercacheentry == null ? null : usercache$usercacheentry.a();
    }

    private UserCache.UserCacheEntry b(UUID uuid) {
        UserCache.UserCacheEntry usercache$usercacheentry = (UserCache.UserCacheEntry)this.e.get(uuid);
        if (usercache$usercacheentry != null) {
            GameProfile gameprofile = usercache$usercacheentry.a();
            this.f.remove(gameprofile);
            this.f.addFirst(gameprofile);
        }

        return usercache$usercacheentry;
    }

    public void b() {
        BufferedReader bufferedreader = null;

        try {
            bufferedreader = Files.newReader(this.h, StandardCharsets.UTF_8);
            List list = (List)ChatDeserializer.a(this.b, bufferedreader, i);
            this.d.clear();
            this.e.clear();
            this.f.clear();
            if (list != null) {
                for(UserCache.UserCacheEntry usercache$usercacheentry : Lists.reverse(list)) {
                    if (usercache$usercacheentry != null) {
                        this.a(usercache$usercacheentry.a(), usercache$usercacheentry.b());
                    }
                }
            }
        } catch (FileNotFoundException var9) {
            ;
        } catch (JsonParseException var10) {
            ;
        } finally {
            IOUtils.closeQuietly(bufferedreader);
        }

    }

    public void c() {
        String s = this.b.toJson(this.a(1000));
        BufferedWriter bufferedwriter = null;

        try {
            bufferedwriter = Files.newWriter(this.h, StandardCharsets.UTF_8);
            bufferedwriter.write(s);
            return;
        } catch (FileNotFoundException var8) {
            ;
        } catch (IOException var9) {
            return;
        } finally {
            IOUtils.closeQuietly(bufferedwriter);
        }

    }

    private List<UserCache.UserCacheEntry> a(int ix) {
        ArrayList arraylist = Lists.newArrayList();

        for(GameProfile gameprofile : Lists.newArrayList(Iterators.limit(this.f.iterator(), ix))) {
            UserCache.UserCacheEntry usercache$usercacheentry = this.b(gameprofile.getId());
            if (usercache$usercacheentry != null) {
                arraylist.add(usercache$usercacheentry);
            }
        }

        return arraylist;
    }

    class BanEntrySerializer implements JsonDeserializer<UserCache.UserCacheEntry>, JsonSerializer<UserCache.UserCacheEntry> {
        private BanEntrySerializer() {
        }

        public JsonElement a(UserCache.UserCacheEntry usercache$usercacheentry, Type var2, JsonSerializationContext var3) {
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("name", usercache$usercacheentry.a().getName());
            UUID uuid = usercache$usercacheentry.a().getId();
            jsonobject.addProperty("uuid", uuid == null ? "" : uuid.toString());
            jsonobject.addProperty("expiresOn", UserCache.a.format(usercache$usercacheentry.b()));
            return jsonobject;
        }

        public UserCache.UserCacheEntry a(JsonElement jsonelement, Type var2, JsonDeserializationContext var3) throws JsonParseException {
            if (jsonelement.isJsonObject()) {
                JsonObject jsonobject = jsonelement.getAsJsonObject();
                JsonElement jsonelement1 = jsonobject.get("name");
                JsonElement jsonelement2 = jsonobject.get("uuid");
                JsonElement jsonelement3 = jsonobject.get("expiresOn");
                if (jsonelement1 != null && jsonelement2 != null) {
                    String s = jsonelement2.getAsString();
                    String s1 = jsonelement1.getAsString();
                    Date date = null;
                    if (jsonelement3 != null) {
                        try {
                            date = UserCache.a.parse(jsonelement3.getAsString());
                        } catch (ParseException var14) {
                            date = null;
                        }
                    }

                    if (s1 != null && s != null) {
                        UUID uuid;
                        try {
                            uuid = UUID.fromString(s);
                        } catch (Throwable var13) {
                            return null;
                        }

                        return UserCache.this.new UserCacheEntry(new GameProfile(uuid, s1), date);
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }

        // $FF: synthetic method
        public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonserializationcontext) {
            return this.a((UserCache.UserCacheEntry)object, type, jsonserializationcontext);
        }

        // $FF: synthetic method
        public Object deserialize(JsonElement jsonelement, Type type, JsonDeserializationContext jsondeserializationcontext) throws JsonParseException {
            return this.a(jsonelement, type, jsondeserializationcontext);
        }
    }

    class UserCacheEntry {
        private final GameProfile b;
        private final Date c;

        private UserCacheEntry(GameProfile gameprofile, Date date) {
            this.b = gameprofile;
            this.c = date;
        }

        public GameProfile a() {
            return this.b;
        }

        public Date b() {
            return this.c;
        }
    }
}
