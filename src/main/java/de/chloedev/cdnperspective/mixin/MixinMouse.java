package de.chloedev.cdnperspective.mixin;

import de.chloedev.cdnperspective.Client;
import de.chloedev.cdnperspective.mod.Mod;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Mouse.class)
public class MixinMouse {

    @Inject(method = "updateMouse", at = @At(value = "INVOKE", target = "net/minecraft/client/tutorial/TutorialManager.onUpdateMouse(DD)V"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void updateMouseA(double timeDelta, CallbackInfo ci, double i, double j, double d, double e, double f, int k) {
        Mod mod = Client.getInstance().getMod();
        if (mod.isEnabled()) {
            mod.setYawAndPitch((float) (mod.getYaw() + i / 8.0D), (float) (mod.getPitch() + j * k / 8.0D));
            if (Math.abs(mod.getPitch()) > 90.0F) mod.setYawAndPitch(mod.getYaw(), (mod.getPitch() > 0.0F) ? 90.0F : -90.0F);
        }
    }

    @Inject(method = {"updateMouse"}, at = {@At(value = "INVOKE", target = "net/minecraft/client/network/ClientPlayerEntity.changeLookDirection(DD)V")}, cancellable = true)
    private void updateMouseB(CallbackInfo info) {
        if (Client.getInstance().getMod().isEnabled()) {
            info.cancel();
        }
    }
}
