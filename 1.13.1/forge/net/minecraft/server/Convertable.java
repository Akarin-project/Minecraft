package net.minecraft.server;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import javax.annotation.Nullable;

public interface Convertable {
    DateTimeFormatter d = (new DateTimeFormatterBuilder()).appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD).appendLiteral('-').appendValue(ChronoField.MONTH_OF_YEAR, 2).appendLiteral('-').appendValue(ChronoField.DAY_OF_MONTH, 2).appendLiteral('_').appendValue(ChronoField.HOUR_OF_DAY, 2).appendLiteral('-').appendValue(ChronoField.MINUTE_OF_HOUR, 2).appendLiteral('-').appendValue(ChronoField.SECOND_OF_MINUTE, 2).toFormatter();

    IDataManager a(String var1, @Nullable MinecraftServer var2);

    @Nullable
    WorldData c(String var1);

    boolean isConvertable(String var1);

    boolean convert(String var1, IProgressUpdate var2);

    File b(String var1, String var2);
}
