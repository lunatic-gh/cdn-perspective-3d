package de.chloedev.cdnperspective;


import de.chloedev.cdnperspective.mod.Mod;
import de.chloedev.cdnperspective.network.ModDisallowedPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class Client implements ClientModInitializer {

    private static Client instance;
    private KeyBinding keyBinding;
    private Mod mod;
    private boolean forceDisabled = false;

    public static Client getInstance() {
        return instance;
    }

    @SuppressWarnings("resource")
    @Override
    public void onInitializeClient() {
        instance = this;
        KeyBindingHelper.registerKeyBinding(keyBinding = new KeyBinding("key.cdnperspective.toggle", InputUtil.Type.KEYSYM, 293, "key.categories.cdnperspective"));
        this.mod = new Mod(0, 0, false);
        PayloadTypeRegistry.playS2C().register(ModDisallowedPayload.ID, ModDisallowedPayload.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(ModDisallowedPayload.ID, (payload, context) -> {
            MinecraftClient client = context.client();
            this.forceDisabled = true;
            if (client.player != null) {
                client.player.sendMessage(Text.literal("§7[§bCDNPerspective§7] §6Oh! This server disabled the use of this mod. This means you can't use this feature on this server!"), false);
            }
        });
    }

    public KeyBinding getKeyBinding() {
        return keyBinding;
    }

    public Mod getMod() {
        return mod;
    }

    public boolean isForceDisabled() {
        return forceDisabled;
    }

    public void setForceDisabled(boolean forceDisabled) {
        this.forceDisabled = forceDisabled;
    }
}