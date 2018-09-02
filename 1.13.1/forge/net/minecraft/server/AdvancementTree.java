package net.minecraft.server;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;

public class AdvancementTree {
    private final Advancement a;
    private final AdvancementTree b;
    private final AdvancementTree c;
    private final int d;
    private final List<AdvancementTree> e = Lists.newArrayList();
    private AdvancementTree f;
    private AdvancementTree g;
    private int h;
    private float i;
    private float j;
    private float k;
    private float l;

    public AdvancementTree(Advancement advancement, @Nullable AdvancementTree advancementtree1, @Nullable AdvancementTree advancementtree2, int ix, int jx) {
        if (advancement.c() == null) {
            throw new IllegalArgumentException("Can't position an invisible advancement!");
        } else {
            this.a = advancement;
            this.b = advancementtree1;
            this.c = advancementtree2;
            this.d = ix;
            this.f = this;
            this.h = jx;
            this.i = -1.0F;
            AdvancementTree advancementtree3 = null;

            for(Advancement advancement1 : advancement.e()) {
                advancementtree3 = this.a(advancement1, advancementtree3);
            }

        }
    }

    @Nullable
    private AdvancementTree a(Advancement advancement, @Nullable AdvancementTree advancementtree1) {
        if (advancement.c() != null) {
            advancementtree1 = new AdvancementTree(advancement, this, advancementtree1, this.e.size() + 1, this.h + 1);
            this.e.add(advancementtree1);
        } else {
            for(Advancement advancement1 : advancement.e()) {
                advancementtree1 = this.a(advancement1, advancementtree1);
            }
        }

        return advancementtree1;
    }

    private void a() {
        if (this.e.isEmpty()) {
            if (this.c != null) {
                this.i = this.c.i + 1.0F;
            } else {
                this.i = 0.0F;
            }

        } else {
            AdvancementTree advancementtree1 = null;

            for(AdvancementTree advancementtree2 : this.e) {
                advancementtree2.a();
                advancementtree1 = advancementtree2.a(advancementtree1 == null ? advancementtree2 : advancementtree1);
            }

            this.b();
            float fx = (((AdvancementTree)this.e.get(0)).i + ((AdvancementTree)this.e.get(this.e.size() - 1)).i) / 2.0F;
            if (this.c != null) {
                this.i = this.c.i + 1.0F;
                this.j = this.i - fx;
            } else {
                this.i = fx;
            }

        }
    }

    private float a(float fx, int ix, float f1) {
        this.i += fx;
        this.h = ix;
        if (this.i < f1) {
            f1 = this.i;
        }

        for(AdvancementTree advancementtree1 : this.e) {
            f1 = advancementtree1.a(fx + this.j, ix + 1, f1);
        }

        return f1;
    }

    private void a(float fx) {
        this.i += fx;

        for(AdvancementTree advancementtree1 : this.e) {
            advancementtree1.a(fx);
        }

    }

    private void b() {
        float fx = 0.0F;
        float f1 = 0.0F;

        for(int ix = this.e.size() - 1; ix >= 0; --ix) {
            AdvancementTree advancementtree1 = (AdvancementTree)this.e.get(ix);
            advancementtree1.i += fx;
            advancementtree1.j += fx;
            f1 += advancementtree1.k;
            fx += advancementtree1.l + f1;
        }

    }

    @Nullable
    private AdvancementTree c() {
        if (this.g != null) {
            return this.g;
        } else {
            return !this.e.isEmpty() ? (AdvancementTree)this.e.get(0) : null;
        }
    }

    @Nullable
    private AdvancementTree d() {
        if (this.g != null) {
            return this.g;
        } else {
            return !this.e.isEmpty() ? (AdvancementTree)this.e.get(this.e.size() - 1) : null;
        }
    }

    private AdvancementTree a(AdvancementTree advancementtree1) {
        if (this.c == null) {
            return advancementtree1;
        } else {
            AdvancementTree advancementtree2 = this;
            AdvancementTree advancementtree3 = this;
            AdvancementTree advancementtree4 = this.c;
            AdvancementTree advancementtree5 = (AdvancementTree)this.b.e.get(0);
            float fx = this.j;
            float f1 = this.j;
            float f2 = advancementtree4.j;

            float f3;
            for(f3 = advancementtree5.j; advancementtree4.d() != null && advancementtree2.c() != null; f1 += advancementtree3.j) {
                advancementtree4 = advancementtree4.d();
                advancementtree2 = advancementtree2.c();
                advancementtree5 = advancementtree5.c();
                advancementtree3 = advancementtree3.d();
                advancementtree3.f = this;
                float f4 = advancementtree4.i + f2 - (advancementtree2.i + fx) + 1.0F;
                if (f4 > 0.0F) {
                    advancementtree4.a(this, advancementtree1).a(this, f4);
                    fx += f4;
                    f1 += f4;
                }

                f2 += advancementtree4.j;
                fx += advancementtree2.j;
                f3 += advancementtree5.j;
            }

            if (advancementtree4.d() != null && advancementtree3.d() == null) {
                advancementtree3.g = advancementtree4.d();
                advancementtree3.j += f2 - f1;
            } else {
                if (advancementtree2.c() != null && advancementtree5.c() == null) {
                    advancementtree5.g = advancementtree2.c();
                    advancementtree5.j += fx - f3;
                }

                advancementtree1 = this;
            }

            return advancementtree1;
        }
    }

    private void a(AdvancementTree advancementtree1, float fx) {
        float f1 = (float)(advancementtree1.d - this.d);
        if (f1 != 0.0F) {
            advancementtree1.k -= fx / f1;
            this.k += fx / f1;
        }

        advancementtree1.l += fx;
        advancementtree1.i += fx;
        advancementtree1.j += fx;
    }

    private AdvancementTree a(AdvancementTree advancementtree1, AdvancementTree advancementtree2) {
        return this.f != null && advancementtree1.b.e.contains(this.f) ? this.f : advancementtree2;
    }

    private void e() {
        if (this.a.c() != null) {
            this.a.c().a((float)this.h, this.i);
        }

        if (!this.e.isEmpty()) {
            for(AdvancementTree advancementtree1 : this.e) {
                advancementtree1.e();
            }
        }

    }

    public static void a(Advancement advancement) {
        if (advancement.c() == null) {
            throw new IllegalArgumentException("Can't position children of an invisible root!");
        } else {
            AdvancementTree advancementtree = new AdvancementTree(advancement, (AdvancementTree)null, (AdvancementTree)null, 1, 0);
            advancementtree.a();
            float fx = advancementtree.a(0.0F, 0, advancementtree.i);
            if (fx < 0.0F) {
                advancementtree.a(-fx);
            }

            advancementtree.e();
        }
    }
}
