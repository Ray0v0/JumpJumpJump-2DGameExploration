package core.camera;

import core.character.Character;

public class Camera {
    public static double x;
    public static double y;

    public static double targetX;
    public static double targetY;

    public static double deltaX;
    public static double deltaY;

    public static double pushRate = 0.03;
    public static double skewDist = 6;

    public static boolean lock = false;

    public static void main() {
        if (lock) {
            lock();
        } else {
            unlock();
        }
    }

    public static void unlock() {
        targetX = Character.x + skewDist * Character.direction;
        targetY = Character.y;


        x += (targetX - x) * pushRate;
        y += (targetY - y) * pushRate;

        deltaX = Character.x - x;
        deltaY = Character.y - y;
    }

    public static void lock() {
        x = Character.x - deltaX;
        y = Character.y - deltaY;

        if (deltaX != 0) {
            deltaX /= 1.1;
        }
        if (deltaY != 0) {
            deltaY /= 1.1;
        }
    }
}
