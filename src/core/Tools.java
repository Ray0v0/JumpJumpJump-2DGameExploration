package core;

import core.camera.Camera;
import core.map.Map;

import static core.MainThread.*;

public class Tools {
    public static double centerRateX = 0.5, centerRateY = 0.7;

    public static boolean legalPos(double x, double y) {
        return legalPos(posTrans(x, y));
    }

    public static boolean legalPos(int x, int y) {
        return 0 <= x && x < screen_w && 0 <= y && y < screen_h;
    }

    public static boolean legalPos(int pos) {
        return 0 <= pos && pos < screenSize;
    }

    public static int posTrans(int x, int y) {
        return x + screen_w * y;
    }

    public static int posTrans(double x, double y) {
        return (int) (x + screen_w * y);
    }

    public static int[] coordinateToPos(double x, double y) {
        double origX = Camera.x;
        double origY = Camera.y;

        double deltaX = x - origX;
        double deltaY = y - origY;

        return new int[]{(int)(screen_w * centerRateX + deltaX * Map.blockSize), (int)(screen_h * centerRateY + deltaY * Map.blockSize)};
    }
}
