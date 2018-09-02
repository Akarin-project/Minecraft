package net.minecraft.server;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleList;

public class VoxelShapeMergerDisjoint extends AbstractDoubleList implements VoxelShapeMerger {
    private final DoubleList a;
    private final DoubleList b;
    private final boolean c;

    public VoxelShapeMergerDisjoint(DoubleList doublelist, DoubleList doublelist1, boolean flag) {
        this.a = doublelist;
        this.b = doublelist1;
        this.c = flag;
    }

    public int size() {
        return this.a.size() + this.b.size();
    }

    public boolean a(VoxelShapeMerger.a voxelshapemerger$a) {
        return this.c ? this.b((i, j, k) -> {
            return voxelshapemerger$a.merge(j, i, k);
        }) : this.b(voxelshapemerger$a);
    }

    private boolean b(VoxelShapeMerger.a voxelshapemerger$a) {
        int i = this.a.size() - 1;

        for(int j = 0; j < i; ++j) {
            if (!voxelshapemerger$a.merge(j, -1, j)) {
                return false;
            }
        }

        if (!voxelshapemerger$a.merge(i, -1, i)) {
            return false;
        } else {
            for(int k = 0; k < this.b.size(); ++k) {
                if (!voxelshapemerger$a.merge(i, k, i + 1 + k)) {
                    return false;
                }
            }

            return true;
        }
    }

    public double getDouble(int i) {
        return i < this.a.size() ? this.a.getDouble(i) : this.b.getDouble(i - this.a.size());
    }

    public DoubleList a() {
        return this;
    }
}
