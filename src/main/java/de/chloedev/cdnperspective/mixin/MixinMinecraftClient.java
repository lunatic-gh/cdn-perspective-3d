package de.chloedev.cdnperspective.mixin;

import de.chloedev.cdnperspective.Client;
import de.chloedev.cdnperspective.mod.Mod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.Perspective;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

    @Shadow
    @Nullable
    public ClientPlayerEntity player;

    @Shadow
    @Final
    public GameOptions options;

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        if (Client.getInstance().isServersideDisabled()) {
            return;
        }
        if (this.player == null) {
            return;
        }
        KeyBinding keyBinding = Client.getInstance().getKeyBinding();
        Mod mod = Client.getInstance().getMod();
        if (keyBinding.wasPressed()) {
            mod.toggleEnabled();
            mod.setYaw(this.player.getYaw());
            mod.setPitch(this.player.getPitch());
            if (mod.isEnabled()) {
                if (!mod.isKeyPressed()) {
                    mod.setLastPerspective(this.options.getPerspective());
                    mod.toggleKeyPressed();
                }
                if (this.options.getPerspective() == Perspective.THIRD_PERSON_FRONT) {
                    mod.setYaw(((180 + this.player.getYaw() + 180) % 360) - 180);
                    mod.setPitch(-this.player.getPitch());
                }
                this.options.setPerspective(Perspective.THIRD_PERSON_BACK);
            } else {
                this.options.setPerspective(mod.getLastPerspective());
                mod.toggleKeyPressed();
            }
        }
        if (mod.isEnabled() && this.options.getPerspective() != Perspective.THIRD_PERSON_BACK) {
            mod.toggleEnabled();
        }
    }
}
