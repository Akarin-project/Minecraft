package net.minecraft.server;

import java.util.function.Predicate;
import javax.annotation.Nullable;

public class MaterialPredicate implements Predicate<IBlockData> {
    private static final MaterialPredicate a = new MaterialPredicate(Material.AIR) {
        public boolean a(@Nullable IBlockData iblockdata) {
            return iblockdata != null && iblockdata.isAir();
        }

        // $FF: synthetic method
        public boolean test(@Nullable Object object) {
            return this.a((IBlockData)object);
        }
    };
    private final Material b;

    private MaterialPredicate(Material material) {
        this.b = material;
    }

    public static MaterialPredicate a(Material material) {
        return material == Material.AIR ? a : new MaterialPredicate(material);
    }

    public boolean a(@Nullable IBlockData iblockdata) {
        return iblockdata != null && iblockdata.getMaterial() == this.b;
    }

    // $FF: synthetic method
    public boolean test(@Nullable Object object) {
        return this.a((IBlockData)object);
    }
}
