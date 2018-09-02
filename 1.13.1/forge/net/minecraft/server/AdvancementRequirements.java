package net.minecraft.server;

import java.util.Collection;

public interface AdvancementRequirements {
    AdvancementRequirements AND = (collection) -> {
        String[][] astring = new String[collection.size()][];
        int i = 0;

        for(String s : collection) {
            astring[i++] = new String[]{s};
        }

        return astring;
    };
    AdvancementRequirements OR = (collection) -> {
        return new String[][]{(String[])collection.toArray(new String[0])};
    };

    String[][] createRequirements(Collection<String> var1);
}
