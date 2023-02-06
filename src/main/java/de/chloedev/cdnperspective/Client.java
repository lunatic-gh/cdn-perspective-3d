package de.chloedev.cdnperspective;


import de.chloedev.cdnperspective.mod.Mod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.impl.client.keybinding.KeyBindingRegistryImpl;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class Client implements ClientModInitializer {

    private static Client instance;
    private KeyBinding keyBinding;
    private Mod mod;
    private boolean forceDisabled = false;

    @Override
    public void onInitializeClient() {
        instance = this;
        KeyBindingRegistryImpl.addCategory("key.cdnperspective.category");
        KeyBindingRegistryImpl.registerKeyBinding(keyBinding = new KeyBinding("key.cdnperspective.toggle", InputUtil.Type.KEYSYM, 293, "key.cdnperspective.category"));
        this.mod = new Mod(0, 0, false);
        ClientPlayNetworking.registerGlobalReceiver(new Identifier("cdnperspective", "is_disallowed"), (client, handler, buf, responseSender) -> {
            this.forceDisabled = true;
            if (client.player != null) {
                client.player.sendMessage(Text.literal("§7[§bCDNPerspective§7] §6Oh! This server disabled the use of this mod. This means you can't use this feature on this server!"));
            }
        });

    }

    public static Client getInstance() {
        return instance;
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