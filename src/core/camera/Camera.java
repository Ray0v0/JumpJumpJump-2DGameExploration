package core.camera;

import core.character.Character;

public class Camera {
    public static double x;
    public static double y;

    public static double targetX;
    public static double targetY;

    public static double pushRate = 0.05;
    public static double skewDist = 7;

    public static void main() {
        targetX = Character.x + skewDist * Character.direction;
        targetY = Character.y;

        x += (targetX - x) * pushRate;
        y += (targetY - y) * pushRate;
    }
}
