package net.minecraft.server;

public interface AreaFactory<A extends Area> {
    A make(AreaDimension var1);
}
