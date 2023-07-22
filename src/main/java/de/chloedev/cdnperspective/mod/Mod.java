package de.chloedev.cdnperspective.mod;

import net.minecraft.client.option.Perspective;

public class Mod {

    private float yaw;
    private float pitch;
    private boolean enabled;
    private Perspective lastPerspective;

    public Mod(float yaw, float pitch, boolean enabled) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.enabled = enabled;
        this.lastPerspective = Perspective.THIRD_PERSON_BACK;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setYawAndPitch(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Perspective getLastPerspective() {
        return lastPerspective;
    }

    public void setLastPerspective(Perspective lastPerspective) {
        this.lastPerspective = lastPerspective;
    }
}
