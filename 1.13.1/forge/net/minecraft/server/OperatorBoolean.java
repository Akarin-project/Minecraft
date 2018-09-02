package net.minecraft.server;

public interface OperatorBoolean {
    OperatorBoolean FALSE = (var0, var1) -> {
        return false;
    };
    OperatorBoolean NOT_OR = (flag, flag1) -> {
        return !flag && !flag1;
    };
    OperatorBoolean ONLY_SECOND = (flag, flag1) -> {
        return flag1 && !flag;
    };
    OperatorBoolean NOT_FIRST = (flag, var1) -> {
        return !flag;
    };
    OperatorBoolean ONLY_FIRST = (flag, flag1) -> {
        return flag && !flag1;
    };
    OperatorBoolean NOT_SECOND = (var0, flag) -> {
        return !flag;
    };
    OperatorBoolean NOT_SAME = (flag, flag1) -> {
        return flag != flag1;
    };
    OperatorBoolean NOT_AND = (flag, flag1) -> {
        return !flag || !flag1;
    };
    OperatorBoolean AND = (flag, flag1) -> {
        return flag && flag1;
    };
    OperatorBoolean SAME = (flag, flag1) -> {
        return flag == flag1;
    };
    OperatorBoolean SECOND = (var0, flag) -> {
        return flag;
    };
    OperatorBoolean CAUSES = (flag, flag1) -> {
        return !flag || flag1;
    };
    OperatorBoolean FIRST = (flag, var1) -> {
        return flag;
    };
    OperatorBoolean CAUSED_BY = (flag, flag1) -> {
        return flag || !flag1;
    };
    OperatorBoolean OR = (flag, flag1) -> {
        return flag || flag1;
    };
    OperatorBoolean TRUE = (var0, var1) -> {
        return true;
    };

    boolean apply(boolean var1, boolean var2);
}
