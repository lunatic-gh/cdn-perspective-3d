package de.chloedev.cdnperspective.mod;

import net.minecraft.client.option.Perspective;

public class Mod {

    private float yaw;
    private float pitch;
    private boolean enabled;
    private boolean keyPressed;
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

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void toggleEnabled() {
        this.enabled = !this.enabled;
    }


    public boolean isKeyPressed() {
        return keyPressed;
    }

    public void toggleKeyPressed() {
        this.keyPressed = !this.keyPressed;
    }

    public Perspective getLastPerspective() {
        return lastPerspective;
    }

    public void setLastPerspective(Perspective lastPerspective) {
        this.lastPerspective = lastPerspective;
    }
}
