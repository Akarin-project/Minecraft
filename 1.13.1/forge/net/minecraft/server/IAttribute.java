package net.minecraft.server;

import javax.annotation.Nullable;

public interface IAttribute {
    String getName();

    double a(double var1);

    double getDefault();

    boolean c();

    @Nullable
    IAttribute d();
}
