package net.minecraft.server;

public interface PacketListenerPlayIn extends PacketListener {
    void a(PacketPlayInArmAnimation var1);

    void a(PacketPlayInChat var1);

    void a(PacketPlayInClientCommand var1);

    void a(PacketPlayInSettings var1);

    void a(PacketPlayInTransaction var1);

    void a(PacketPlayInEnchantItem var1);

    void a(PacketPlayInWindowClick var1);

    void a(PacketPlayInAutoRecipe var1);

    void a(PacketPlayInCloseWindow var1);

    void a(PacketPlayInCustomPayload var1);

    void a(PacketPlayInUseEntity var1);

    void a(PacketPlayInKeepAlive var1);

    void a(PacketPlayInFlying var1);

    void a(PacketPlayInAbilities var1);

    void a(PacketPlayInBlockDig var1);

    void a(PacketPlayInEntityAction var1);

    void a(PacketPlayInSteerVehicle var1);

    void a(PacketPlayInHeldItemSlot var1);

    void a(PacketPlayInSetCreativeSlot var1);

    void a(PacketPlayInUpdateSign var1);

    void a(PacketPlayInUseItem var1);

    void a(PacketPlayInBlockPlace var1);

    void a(PacketPlayInSpectate var1);

    void a(PacketPlayInResourcePackStatus var1);

    void a(PacketPlayInBoatMove var1);

    void a(PacketPlayInVehicleMove var1);

    void a(PacketPlayInTeleportAccept var1);

    void a(PacketPlayInRecipeDisplayed var1);

    void a(PacketPlayInAdvancements var1);

    void a(PacketPlayInTabComplete var1);

    void a(PacketPlayInSetCommandBlock var1);

    void a(PacketPlayInSetCommandMinecart var1);

    void a(PacketPlayInPickItem var1);

    void a(PacketPlayInItemName var1);

    void a(PacketPlayInBeacon var1);

    void a(PacketPlayInStruct var1);

    void a(PacketPlayInTrSel var1);

    void a(PacketPlayInBEdit var1);

    void a(PacketPlayInEntityNBTQuery var1);

    void a(PacketPlayInTileNBTQuery var1);
}
