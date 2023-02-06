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
    private void updateMouseA(CallbackInfo ci, double d, double e, double x, double y, double f, double g, double h, int invert) {
        Mod mod = Client.getInstance().getMod();
        if (mod.isEnabled()) {
            mod.setYaw((float) (mod.getYaw() + x / 8.0D));
            mod.setPitch((float) (mod.getPitch() + y * invert / 8.0D));
            if (Math.abs(mod.getPitch()) > 90.0F) mod.setPitch((mod.getPitch() > 0.0F) ? 90.0F : -90.0F);
        }
    }

    @Inject(method = {"updateMouse"}, at = {@At(value = "INVOKE", target = "net/minecraft/client/network/ClientPlayerEntity.changeLookDirection(DD)V")}, cancellable = true)
    private void updateMouseB(CallbackInfo info) {
        if (Client.getInstance().getMod().isEnabled()) {
            info.cancel();
        }
    }
}
