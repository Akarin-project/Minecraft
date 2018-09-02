package net.minecraft.server;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import java.io.File;

public class OpList extends JsonList<GameProfile, OpListEntry> {
    public OpList(File file1) {
        super(file1);
    }

    protected JsonListEntry<GameProfile> a(JsonObject jsonobject) {
        return new OpListEntry(jsonobject);
    }

    public String[] getEntries() {
        String[] astring = new String[this.e().size()];
        int i = 0;

        for(JsonListEntry jsonlistentry : this.e()) {
            astring[i++] = ((GameProfile)jsonlistentry.getKey()).getName();
        }

        return astring;
    }

    public boolean b(GameProfile gameprofile) {
        OpListEntry oplistentry = (OpListEntry)this.get(gameprofile);
        return oplistentry != null ? oplistentry.b() : false;
    }

    protected String c(GameProfile gameprofile) {
        return gameprofile.getId().toString();
    }

    // $FF: synthetic method
    protected String a(Object object) {
        return this.c((GameProfile)object);
    }
}
