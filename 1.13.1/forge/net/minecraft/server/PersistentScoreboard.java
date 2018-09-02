package net.minecraft.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PersistentScoreboard extends PersistentBase {
    private static final Logger a = LogManager.getLogger();
    private Scoreboard b;
    private NBTTagCompound c;

    public PersistentScoreboard() {
        this("scoreboard");
    }

    public PersistentScoreboard(String s) {
        super(s);
    }

    public void a(Scoreboard scoreboard) {
        this.b = scoreboard;
        if (this.c != null) {
            this.a(this.c);
        }

    }

    public void a(NBTTagCompound nbttagcompound) {
        if (this.b == null) {
            this.c = nbttagcompound;
        } else {
            this.b(nbttagcompound.getList("Objectives", 10));
            this.b.a(nbttagcompound.getList("PlayerScores", 10));
            if (nbttagcompound.hasKeyOfType("DisplaySlots", 10)) {
                this.c(nbttagcompound.getCompound("DisplaySlots"));
            }

            if (nbttagcompound.hasKeyOfType("Teams", 9)) {
                this.a(nbttagcompound.getList("Teams", 10));
            }

        }
    }

    protected void a(NBTTagList nbttaglist) {
        for(int i = 0; i < nbttaglist.size(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompound(i);
            String s = nbttagcompound.getString("Name");
            if (s.length() > 16) {
                s = s.substring(0, 16);
            }

            ScoreboardTeam scoreboardteam = this.b.createTeam(s);
            IChatBaseComponent ichatbasecomponent = IChatBaseComponent.ChatSerializer.a(nbttagcompound.getString("DisplayName"));
            if (ichatbasecomponent != null) {
                scoreboardteam.setDisplayName(ichatbasecomponent);
            }

            if (nbttagcompound.hasKeyOfType("TeamColor", 8)) {
                scoreboardteam.setColor(EnumChatFormat.c(nbttagcompound.getString("TeamColor")));
            }

            if (nbttagcompound.hasKeyOfType("AllowFriendlyFire", 99)) {
                scoreboardteam.setAllowFriendlyFire(nbttagcompound.getBoolean("AllowFriendlyFire"));
            }

            if (nbttagcompound.hasKeyOfType("SeeFriendlyInvisibles", 99)) {
                scoreboardteam.setCanSeeFriendlyInvisibles(nbttagcompound.getBoolean("SeeFriendlyInvisibles"));
            }

            if (nbttagcompound.hasKeyOfType("MemberNamePrefix", 8)) {
                IChatBaseComponent ichatbasecomponent1 = IChatBaseComponent.ChatSerializer.a(nbttagcompound.getString("MemberNamePrefix"));
                if (ichatbasecomponent1 != null) {
                    scoreboardteam.setPrefix(ichatbasecomponent1);
                }
            }

            if (nbttagcompound.hasKeyOfType("MemberNameSuffix", 8)) {
                IChatBaseComponent ichatbasecomponent2 = IChatBaseComponent.ChatSerializer.a(nbttagcompound.getString("MemberNameSuffix"));
                if (ichatbasecomponent2 != null) {
                    scoreboardteam.setSuffix(ichatbasecomponent2);
                }
            }

            if (nbttagcompound.hasKeyOfType("NameTagVisibility", 8)) {
                ScoreboardTeamBase.EnumNameTagVisibility scoreboardteambase$enumnametagvisibility = ScoreboardTeamBase.EnumNameTagVisibility.a(nbttagcompound.getString("NameTagVisibility"));
                if (scoreboardteambase$enumnametagvisibility != null) {
                    scoreboardteam.setNameTagVisibility(scoreboardteambase$enumnametagvisibility);
                }
            }

            if (nbttagcompound.hasKeyOfType("DeathMessageVisibility", 8)) {
                ScoreboardTeamBase.EnumNameTagVisibility scoreboardteambase$enumnametagvisibility1 = ScoreboardTeamBase.EnumNameTagVisibility.a(nbttagcompound.getString("DeathMessageVisibility"));
                if (scoreboardteambase$enumnametagvisibility1 != null) {
                    scoreboardteam.setDeathMessageVisibility(scoreboardteambase$enumnametagvisibility1);
                }
            }

            if (nbttagcompound.hasKeyOfType("CollisionRule", 8)) {
                ScoreboardTeamBase.EnumTeamPush scoreboardteambase$enumteampush = ScoreboardTeamBase.EnumTeamPush.a(nbttagcompound.getString("CollisionRule"));
                if (scoreboardteambase$enumteampush != null) {
                    scoreboardteam.setCollisionRule(scoreboardteambase$enumteampush);
                }
            }

            this.a(scoreboardteam, nbttagcompound.getList("Players", 8));
        }

    }

    protected void a(ScoreboardTeam scoreboardteam, NBTTagList nbttaglist) {
        for(int i = 0; i < nbttaglist.size(); ++i) {
            this.b.addPlayerToTeam(nbttaglist.getString(i), scoreboardteam);
        }

    }

    protected void c(NBTTagCompound nbttagcompound) {
        for(int i = 0; i < 19; ++i) {
            if (nbttagcompound.hasKeyOfType("slot_" + i, 8)) {
                String s = nbttagcompound.getString("slot_" + i);
                ScoreboardObjective scoreboardobjective = this.b.getObjective(s);
                this.b.setDisplaySlot(i, scoreboardobjective);
            }
        }

    }

    protected void b(NBTTagList nbttaglist) {
        for(int i = 0; i < nbttaglist.size(); ++i) {
            NBTTagCompound nbttagcompound = nbttaglist.getCompound(i);
            IScoreboardCriteria iscoreboardcriteria = IScoreboardCriteria.a(nbttagcompound.getString("CriteriaName"));
            if (iscoreboardcriteria != null) {
                String s = nbttagcompound.getString("Name");
                if (s.length() > 16) {
                    s = s.substring(0, 16);
                }

                IChatBaseComponent ichatbasecomponent = IChatBaseComponent.ChatSerializer.a(nbttagcompound.getString("DisplayName"));
                IScoreboardCriteria.EnumScoreboardHealthDisplay iscoreboardcriteria$enumscoreboardhealthdisplay = IScoreboardCriteria.EnumScoreboardHealthDisplay.a(nbttagcompound.getString("RenderType"));
                this.b.registerObjective(s, iscoreboardcriteria, ichatbasecomponent, iscoreboardcriteria$enumscoreboardhealthdisplay);
            }
        }

    }

    public NBTTagCompound b(NBTTagCompound nbttagcompound) {
        if (this.b == null) {
            a.warn("Tried to save scoreboard without having a scoreboard...");
            return nbttagcompound;
        } else {
            nbttagcompound.set("Objectives", this.b());
            nbttagcompound.set("PlayerScores", this.b.i());
            nbttagcompound.set("Teams", this.a());
            this.d(nbttagcompound);
            return nbttagcompound;
        }
    }

    protected NBTTagList a() {
        NBTTagList nbttaglist = new NBTTagList();

        for(ScoreboardTeam scoreboardteam : this.b.getTeams()) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setString("Name", scoreboardteam.getName());
            nbttagcompound.setString("DisplayName", IChatBaseComponent.ChatSerializer.a(scoreboardteam.getDisplayName()));
            if (scoreboardteam.getColor().b() >= 0) {
                nbttagcompound.setString("TeamColor", scoreboardteam.getColor().g());
            }

            nbttagcompound.setBoolean("AllowFriendlyFire", scoreboardteam.allowFriendlyFire());
            nbttagcompound.setBoolean("SeeFriendlyInvisibles", scoreboardteam.canSeeFriendlyInvisibles());
            nbttagcompound.setString("MemberNamePrefix", IChatBaseComponent.ChatSerializer.a(scoreboardteam.getPrefix()));
            nbttagcompound.setString("MemberNameSuffix", IChatBaseComponent.ChatSerializer.a(scoreboardteam.getSuffix()));
            nbttagcompound.setString("NameTagVisibility", scoreboardteam.getNameTagVisibility().e);
            nbttagcompound.setString("DeathMessageVisibility", scoreboardteam.getDeathMessageVisibility().e);
            nbttagcompound.setString("CollisionRule", scoreboardteam.getCollisionRule().e);
            NBTTagList nbttaglist1 = new NBTTagList();

            for(String s : scoreboardteam.getPlayerNameSet()) {
                nbttaglist1.add((NBTBase)(new NBTTagString(s)));
            }

            nbttagcompound.set("Players", nbttaglist1);
            nbttaglist.add((NBTBase)nbttagcompound);
        }

        return nbttaglist;
    }

    protected void d(NBTTagCompound nbttagcompound) {
        NBTTagCompound nbttagcompound1 = new NBTTagCompound();
        boolean flag = false;

        for(int i = 0; i < 19; ++i) {
            ScoreboardObjective scoreboardobjective = this.b.getObjectiveForSlot(i);
            if (scoreboardobjective != null) {
                nbttagcompound1.setString("slot_" + i, scoreboardobjective.getName());
                flag = true;
            }
        }

        if (flag) {
            nbttagcompound.set("DisplaySlots", nbttagcompound1);
        }

    }

    protected NBTTagList b() {
        NBTTagList nbttaglist = new NBTTagList();

        for(ScoreboardObjective scoreboardobjective : this.b.getObjectives()) {
            if (scoreboardobjective.getCriteria() != null) {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setString("Name", scoreboardobjective.getName());
                nbttagcompound.setString("CriteriaName", scoreboardobjective.getCriteria().getName());
                nbttagcompound.setString("DisplayName", IChatBaseComponent.ChatSerializer.a(scoreboardobjective.getDisplayName()));
                nbttagcompound.setString("RenderType", scoreboardobjective.f().a());
                nbttaglist.add((NBTBase)nbttagcompound);
            }
        }

        return nbttaglist;
    }
}
