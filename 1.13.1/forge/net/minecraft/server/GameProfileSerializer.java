package net.minecraft.server;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.DSL.TypeReference;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class GameProfileSerializer {
    private static final Logger a = LogManager.getLogger();

    @Nullable
    public static GameProfile deserialize(NBTTagCompound nbttagcompound) {
        String s = null;
        String s1 = null;
        if (nbttagcompound.hasKeyOfType("Name", 8)) {
            s = nbttagcompound.getString("Name");
        }

        if (nbttagcompound.hasKeyOfType("Id", 8)) {
            s1 = nbttagcompound.getString("Id");
        }

        try {
            UUID uuid;
            try {
                uuid = UUID.fromString(s1);
            } catch (Throwable var12) {
                uuid = null;
            }

            GameProfile gameprofile = new GameProfile(uuid, s);
            if (nbttagcompound.hasKeyOfType("Properties", 10)) {
                NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Properties");

                for(String s2 : nbttagcompound1.getKeys()) {
                    NBTTagList nbttaglist = nbttagcompound1.getList(s2, 10);

                    for(int i = 0; i < nbttaglist.size(); ++i) {
                        NBTTagCompound nbttagcompound2 = nbttaglist.getCompound(i);
                        String s3 = nbttagcompound2.getString("Value");
                        if (nbttagcompound2.hasKeyOfType("Signature", 8)) {
                            gameprofile.getProperties().put(s2, new Property(s2, s3, nbttagcompound2.getString("Signature")));
                        } else {
                            gameprofile.getProperties().put(s2, new Property(s2, s3));
                        }
                    }
                }
            }

            return gameprofile;
        } catch (Throwable var13) {
            return null;
        }
    }

    public static NBTTagCompound serialize(NBTTagCompound nbttagcompound, GameProfile gameprofile) {
        if (!UtilColor.b(gameprofile.getName())) {
            nbttagcompound.setString("Name", gameprofile.getName());
        }

        if (gameprofile.getId() != null) {
            nbttagcompound.setString("Id", gameprofile.getId().toString());
        }

        if (!gameprofile.getProperties().isEmpty()) {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();

            for(String s : gameprofile.getProperties().keySet()) {
                NBTTagList nbttaglist = new NBTTagList();

                for(Property property : gameprofile.getProperties().get(s)) {
                    NBTTagCompound nbttagcompound2 = new NBTTagCompound();
                    nbttagcompound2.setString("Value", property.getValue());
                    if (property.hasSignature()) {
                        nbttagcompound2.setString("Signature", property.getSignature());
                    }

                    nbttaglist.add((NBTBase)nbttagcompound2);
                }

                nbttagcompound1.set(s, nbttaglist);
            }

            nbttagcompound.set("Properties", nbttagcompound1);
        }

        return nbttagcompound;
    }

    @VisibleForTesting
    public static boolean a(@Nullable NBTBase nbtbase, @Nullable NBTBase nbtbase1, boolean flag) {
        if (nbtbase == nbtbase1) {
            return true;
        } else if (nbtbase == null) {
            return true;
        } else if (nbtbase1 == null) {
            return false;
        } else if (!nbtbase.getClass().equals(nbtbase1.getClass())) {
            return false;
        } else if (nbtbase instanceof NBTTagCompound) {
            NBTTagCompound nbttagcompound = (NBTTagCompound)nbtbase;
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbtbase1;

            for(String s : nbttagcompound.getKeys()) {
                NBTBase nbtbase3 = nbttagcompound.get(s);
                if (!a(nbtbase3, nbttagcompound1.get(s), flag)) {
                    return false;
                }
            }

            return true;
        } else if (nbtbase instanceof NBTTagList && flag) {
            NBTTagList nbttaglist = (NBTTagList)nbtbase;
            NBTTagList nbttaglist1 = (NBTTagList)nbtbase1;
            if (nbttaglist.isEmpty()) {
                return nbttaglist1.isEmpty();
            } else {
                for(int i = 0; i < nbttaglist.size(); ++i) {
                    NBTBase nbtbase2 = nbttaglist.get(i);
                    boolean flag1 = false;

                    for(int j = 0; j < nbttaglist1.size(); ++j) {
                        if (a(nbtbase2, nbttaglist1.get(j), flag)) {
                            flag1 = true;
                            break;
                        }
                    }

                    if (!flag1) {
                        return false;
                    }
                }

                return true;
            }
        } else {
            return nbtbase.equals(nbtbase1);
        }
    }

    public static NBTTagCompound a(UUID uuid) {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setLong("M", uuid.getMostSignificantBits());
        nbttagcompound.setLong("L", uuid.getLeastSignificantBits());
        return nbttagcompound;
    }

    public static UUID b(NBTTagCompound nbttagcompound) {
        return new UUID(nbttagcompound.getLong("M"), nbttagcompound.getLong("L"));
    }

    public static BlockPosition c(NBTTagCompound nbttagcompound) {
        return new BlockPosition(nbttagcompound.getInt("X"), nbttagcompound.getInt("Y"), nbttagcompound.getInt("Z"));
    }

    public static NBTTagCompound a(BlockPosition blockposition) {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setInt("X", blockposition.getX());
        nbttagcompound.setInt("Y", blockposition.getY());
        nbttagcompound.setInt("Z", blockposition.getZ());
        return nbttagcompound;
    }

    public static IBlockData d(NBTTagCompound nbttagcompound) {
        if (!nbttagcompound.hasKeyOfType("Name", 8)) {
            return Blocks.AIR.getBlockData();
        } else {
            Block block = IRegistry.BLOCK.getOrDefault(new MinecraftKey(nbttagcompound.getString("Name")));
            IBlockData iblockdata = block.getBlockData();
            if (nbttagcompound.hasKeyOfType("Properties", 10)) {
                NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Properties");
                BlockStateList blockstatelist = block.getStates();

                for(String s : nbttagcompound1.getKeys()) {
                    IBlockState iblockstate = blockstatelist.a(s);
                    if (iblockstate != null) {
                        iblockdata = (IBlockData)a(iblockdata, iblockstate, s, nbttagcompound1, nbttagcompound);
                    }
                }
            }

            return iblockdata;
        }
    }

    private static <S extends IBlockDataHolder<S>, T extends Comparable<T>> S a(S iblockdataholder, IBlockState<T> iblockstate, String s, NBTTagCompound nbttagcompound, NBTTagCompound nbttagcompound1) {
        Optional optional = iblockstate.b(nbttagcompound.getString(s));
        if (optional.isPresent()) {
            return (S)(iblockdataholder.set(iblockstate, (Comparable)optional.get()));
        } else {
            a.warn("Unable to read property: {} with value: {} for blockstate: {}", s, nbttagcompound.getString(s), nbttagcompound1.toString());
            return (S)iblockdataholder;
        }
    }

    public static NBTTagCompound a(IBlockData iblockdata) {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        nbttagcompound.setString("Name", IRegistry.BLOCK.getKey(iblockdata.getBlock()).toString());
        ImmutableMap immutablemap = iblockdata.b();
        if (!immutablemap.isEmpty()) {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            UnmodifiableIterator unmodifiableiterator = immutablemap.entrySet().iterator();

            while(unmodifiableiterator.hasNext()) {
                Entry entry = (Entry)unmodifiableiterator.next();
                IBlockState iblockstate = (IBlockState)entry.getKey();
                nbttagcompound1.setString(iblockstate.a(), a(iblockstate, (Comparable)entry.getValue()));
            }

            nbttagcompound.set("Properties", nbttagcompound1);
        }

        return nbttagcompound;
    }

    private static <T extends Comparable<T>> String a(IBlockState<T> iblockstate, Comparable<?> comparable) {
        return iblockstate.a(comparable);
    }

    public static NBTTagCompound a(DataFixer datafixer, TypeReference typereference, NBTTagCompound nbttagcompound, int i) {
        return a(datafixer, typereference, nbttagcompound, i, 1628);
    }

    public static NBTTagCompound a(DataFixer datafixer, TypeReference typereference, NBTTagCompound nbttagcompound, int i, int j) {
        return (NBTTagCompound)datafixer.update(typereference, new Dynamic(DynamicOpsNBT.a, nbttagcompound), i, j).getValue();
    }
}
