package de.chloedev.cdnperspective;

import de.chloedev.cdnperspective.network.ModDisallowedPayload;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class Server implements DedicatedServerModInitializer {

    @Override
    public void onInitializeServer() {
        PayloadTypeRegistry.playS2C().register(ModDisallowedPayload.ID, ModDisallowedPayload.CODEC);
    }
}