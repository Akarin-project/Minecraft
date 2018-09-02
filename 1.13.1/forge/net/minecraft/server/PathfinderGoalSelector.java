package net.minecraft.server;

import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PathfinderGoalSelector {
    private static final Logger a = LogManager.getLogger();
    private final Set<PathfinderGoalSelector.PathfinderGoalSelectorItem> b = Sets.newLinkedHashSet();
    private final Set<PathfinderGoalSelector.PathfinderGoalSelectorItem> c = Sets.newLinkedHashSet();
    private final MethodProfiler d;
    private int e;
    private int f = 3;
    private int g;

    public PathfinderGoalSelector(MethodProfiler methodprofiler) {
        this.d = methodprofiler;
    }

    public void a(int i, PathfinderGoal pathfindergoal) {
        this.b.add(new PathfinderGoalSelector.PathfinderGoalSelectorItem(i, pathfindergoal));
    }

    public void a(PathfinderGoal pathfindergoal) {
        Iterator iterator = this.b.iterator();

        while(iterator.hasNext()) {
            PathfinderGoalSelector.PathfinderGoalSelectorItem pathfindergoalselector$pathfindergoalselectoritem = (PathfinderGoalSelector.PathfinderGoalSelectorItem)iterator.next();
            PathfinderGoal pathfindergoal1 = pathfindergoalselector$pathfindergoalselectoritem.a;
            if (pathfindergoal1 == pathfindergoal) {
                if (pathfindergoalselector$pathfindergoalselectoritem.c) {
                    pathfindergoalselector$pathfindergoalselectoritem.c = false;
                    pathfindergoalselector$pathfindergoalselectoritem.a.d();
                    this.c.remove(pathfindergoalselector$pathfindergoalselectoritem);
                }

                iterator.remove();
                return;
            }
        }

    }

    public void doTick() {
        this.d.a("goalSetup");
        if (this.e++ % this.f == 0) {
            for(PathfinderGoalSelector.PathfinderGoalSelectorItem pathfindergoalselector$pathfindergoalselectoritem : this.b) {
                if (pathfindergoalselector$pathfindergoalselectoritem.c) {
                    if (!this.b(pathfindergoalselector$pathfindergoalselectoritem) || !this.a(pathfindergoalselector$pathfindergoalselectoritem)) {
                        pathfindergoalselector$pathfindergoalselectoritem.c = false;
                        pathfindergoalselector$pathfindergoalselectoritem.a.d();
                        this.c.remove(pathfindergoalselector$pathfindergoalselectoritem);
                    }
                } else if (this.b(pathfindergoalselector$pathfindergoalselectoritem) && pathfindergoalselector$pathfindergoalselectoritem.a.a()) {
                    pathfindergoalselector$pathfindergoalselectoritem.c = true;
                    pathfindergoalselector$pathfindergoalselectoritem.a.c();
                    this.c.add(pathfindergoalselector$pathfindergoalselectoritem);
                }
            }
        } else {
            Iterator iterator = this.c.iterator();

            while(iterator.hasNext()) {
                PathfinderGoalSelector.PathfinderGoalSelectorItem pathfindergoalselector$pathfindergoalselectoritem1 = (PathfinderGoalSelector.PathfinderGoalSelectorItem)iterator.next();
                if (!this.a(pathfindergoalselector$pathfindergoalselectoritem1)) {
                    pathfindergoalselector$pathfindergoalselectoritem1.c = false;
                    pathfindergoalselector$pathfindergoalselectoritem1.a.d();
                    iterator.remove();
                }
            }
        }

        this.d.e();
        if (!this.c.isEmpty()) {
            this.d.a("goalTick");

            for(PathfinderGoalSelector.PathfinderGoalSelectorItem pathfindergoalselector$pathfindergoalselectoritem2 : this.c) {
                pathfindergoalselector$pathfindergoalselectoritem2.a.e();
            }

            this.d.e();
        }

    }

    private boolean a(PathfinderGoalSelector.PathfinderGoalSelectorItem pathfindergoalselector$pathfindergoalselectoritem) {
        return pathfindergoalselector$pathfindergoalselectoritem.a.b();
    }

    private boolean b(PathfinderGoalSelector.PathfinderGoalSelectorItem pathfindergoalselector$pathfindergoalselectoritem) {
        if (this.c.isEmpty()) {
            return true;
        } else if (this.b(pathfindergoalselector$pathfindergoalselectoritem.a.h())) {
            return false;
        } else {
            for(PathfinderGoalSelector.PathfinderGoalSelectorItem pathfindergoalselector$pathfindergoalselectoritem1 : this.c) {
                if (pathfindergoalselector$pathfindergoalselectoritem1 != pathfindergoalselector$pathfindergoalselectoritem) {
                    if (pathfindergoalselector$pathfindergoalselectoritem.b >= pathfindergoalselector$pathfindergoalselectoritem1.b) {
                        if (!this.a(pathfindergoalselector$pathfindergoalselectoritem, pathfindergoalselector$pathfindergoalselectoritem1)) {
                            return false;
                        }
                    } else if (!pathfindergoalselector$pathfindergoalselectoritem1.a.f()) {
                        return false;
                    }
                }
            }

            return true;
        }
    }

    private boolean a(PathfinderGoalSelector.PathfinderGoalSelectorItem pathfindergoalselector$pathfindergoalselectoritem, PathfinderGoalSelector.PathfinderGoalSelectorItem pathfindergoalselector$pathfindergoalselectoritem1) {
        return (pathfindergoalselector$pathfindergoalselectoritem.a.h() & pathfindergoalselector$pathfindergoalselectoritem1.a.h()) == 0;
    }

    public boolean b(int i) {
        return (this.g & i) > 0;
    }

    public void c(int i) {
        this.g |= i;
    }

    public void d(int i) {
        this.g &= ~i;
    }

    public void a(int i, boolean flag) {
        if (flag) {
            this.d(i);
        } else {
            this.c(i);
        }

    }

    class PathfinderGoalSelectorItem {
        public final PathfinderGoal a;
        public final int b;
        public boolean c;

        public PathfinderGoalSelectorItem(int i, PathfinderGoal pathfindergoal) {
            this.b = i;
            this.a = pathfindergoal;
        }

        public boolean equals(@Nullable Object object) {
            if (this == object) {
                return true;
            } else {
                return object != null && this.getClass() == object.getClass() ? this.a.equals(((PathfinderGoalSelector.PathfinderGoalSelectorItem)object).a) : false;
            }
        }

        public int hashCode() {
            return this.a.hashCode();
        }
    }
}
