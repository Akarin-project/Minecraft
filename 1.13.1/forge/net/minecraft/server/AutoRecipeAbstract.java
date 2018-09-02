package net.minecraft.server;

import java.util.Iterator;

public interface AutoRecipeAbstract<T> {
    default void a(int i, int j, int k, IRecipe irecipe, Iterator<T> iterator, int l) {
        int i1 = i;
        int j1 = j;
        if (irecipe instanceof ShapedRecipes) {
            ShapedRecipes shapedrecipes = (ShapedRecipes)irecipe;
            i1 = shapedrecipes.g();
            j1 = shapedrecipes.h();
        }

        int k2 = 0;

        for(int k1 = 0; k1 < j; ++k1) {
            if (k2 == k) {
                ++k2;
            }

            boolean flag = (float)j1 < (float)j / 2.0F;
            int l1 = MathHelper.d((float)j / 2.0F - (float)j1 / 2.0F);
            if (flag && l1 > k1) {
                k2 += i;
                ++k1;
            }

            for(int i2 = 0; i2 < i; ++i2) {
                if (!iterator.hasNext()) {
                    return;
                }

                flag = (float)i1 < (float)i / 2.0F;
                l1 = MathHelper.d((float)i / 2.0F - (float)i1 / 2.0F);
                int j2 = i1;
                boolean flag1 = i2 < i1;
                if (flag) {
                    j2 = l1 + i1;
                    flag1 = l1 <= i2 && i2 < l1 + i1;
                }

                if (flag1) {
                    this.a(iterator, k2, l, k1, i2);
                } else if (j2 == i2) {
                    k2 += i - i2;
                    break;
                }

                ++k2;
            }
        }

    }

    void a(Iterator<T> var1, int var2, int var3, int var4, int var5);
}
