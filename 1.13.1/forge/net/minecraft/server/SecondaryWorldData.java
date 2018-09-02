package net.minecraft.server;

import javax.annotation.Nullable;

public class SecondaryWorldData extends WorldData {
    private final WorldData b;

    public SecondaryWorldData(WorldData worlddata) {
        this.b = worlddata;
    }

    public NBTTagCompound a(@Nullable NBTTagCompound nbttagcompound) {
        return this.b.a(nbttagcompound);
    }

    public long getSeed() {
        return this.b.getSeed();
    }

    public int b() {
        return this.b.b();
    }

    public int c() {
        return this.b.c();
    }

    public int d() {
        return this.b.d();
    }

    public long getTime() {
        return this.b.getTime();
    }

    public long getDayTime() {
        return this.b.getDayTime();
    }

    public NBTTagCompound h() {
        return this.b.h();
    }

    public String getName() {
        return this.b.getName();
    }

    public int k() {
        return this.b.k();
    }

    public boolean isThundering() {
        return this.b.isThundering();
    }

    public int getThunderDuration() {
        return this.b.getThunderDuration();
    }

    public boolean hasStorm() {
        return this.b.hasStorm();
    }

    public int getWeatherDuration() {
        return this.b.getWeatherDuration();
    }

    public EnumGamemode getGameType() {
        return this.b.getGameType();
    }

    public void setTime(long var1) {
    }

    public void setDayTime(long var1) {
    }

    public void setSpawn(BlockPosition var1) {
    }

    public void a(String var1) {
    }

    public void d(int var1) {
    }

    public void setThundering(boolean var1) {
    }

    public void setThunderDuration(int var1) {
    }

    public void setStorm(boolean var1) {
    }

    public void setWeatherDuration(int var1) {
    }

    public boolean shouldGenerateMapFeatures() {
        return this.b.shouldGenerateMapFeatures();
    }

    public boolean isHardcore() {
        return this.b.isHardcore();
    }

    public WorldType getType() {
        return this.b.getType();
    }

    public void a(WorldType var1) {
    }

    public boolean u() {
        return this.b.u();
    }

    public void c(boolean var1) {
    }

    public boolean v() {
        return this.b.v();
    }

    public void d(boolean var1) {
    }

    public GameRules w() {
        return this.b.w();
    }

    public EnumDifficulty getDifficulty() {
        return this.b.getDifficulty();
    }

    public void setDifficulty(EnumDifficulty var1) {
    }

    public boolean isDifficultyLocked() {
        return this.b.isDifficultyLocked();
    }

    public void e(boolean var1) {
    }

    public void a(DimensionManager dimensionmanager, NBTTagCompound nbttagcompound) {
        this.b.a(dimensionmanager, nbttagcompound);
    }

    public NBTTagCompound a(DimensionManager dimensionmanager) {
        return this.b.a(dimensionmanager);
    }
}
