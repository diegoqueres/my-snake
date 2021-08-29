package net.diegoqueres.mysnake.utils;

import com.badlogic.gdx.Gdx;

public final class DeviceUtils {
    public static final boolean isMobileDevice() {
        switch (Gdx.app.getType()) {
            case Android:
            case iOS:
                return true;

            default:
                return false;
        }
    }
}
