package net.minecraft.server;

public abstract class ContainerRecipeBook extends Container {
    public ContainerRecipeBook() {
    }

    public abstract void a(AutoRecipeStackManager var1);

    public abstract void d();

    public abstract boolean a(IRecipe var1);

    public abstract int e();

    public abstract int f();

    public abstract int g();
}
