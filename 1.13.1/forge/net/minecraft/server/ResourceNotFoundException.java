package net.minecraft.server;

import java.io.File;
import java.io.FileNotFoundException;

public class ResourceNotFoundException extends FileNotFoundException {
    public ResourceNotFoundException(File file1, String s) {
        super(String.format("'%s' in ResourcePack '%s'", s, file1));
    }
}
