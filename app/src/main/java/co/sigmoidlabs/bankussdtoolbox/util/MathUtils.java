package co.sigmoidlabs.bankussdtoolbox.util;

/**
 * Created by Efe on 15/08/2016.
 */
public class MathUtils {

    private MathUtils() {
    }

    public static float constrain(float min, float max, float v) {
        return Math.max(min, Math.min(max, v));
    }
}
