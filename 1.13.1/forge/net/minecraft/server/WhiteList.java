package net.minecraft.server;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;

public class WhiteList extends JsonList<GameProfile, WhiteListEntry> {
    public WhiteList(File file1) {
        super(file1);
    }

    protected JsonListEntry<GameProfile> a(JsonObject jsonobject) {
        return new WhiteListEntry(jsonobject);
    }

    public boolean isWhitelisted(GameProfile gameprofile) {
        return this.d(gameprofile);
    }

    public String[] getEntries() {
        String[] astring = new String[this.e().size()];
        int i = 0;

        for(JsonListEntry jsonlistentry : this.e()) {
            astring[i++] = ((GameProfile)jsonlistentry.getKey()).getName();
        }

        return astring;
    }

    protected String b(GameProfile gameprofile) {
        return gameprofile.getId().toString();
    }

    // $FF: synthetic method
    protected String a(Object object) {
        return this.b((GameProfile)object);
    }
}
