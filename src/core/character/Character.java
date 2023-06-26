package core.character;

import core.map.Map;

import static core.MainThread.frameInterval;
import static core.MainThread.screen;
import static core.Tools.*;

public class Character {
    public static double x = 0;
    public static double y = -2;

    public static double sizeX = 30, sizeY = 40;

    public static int direction = 0;

    public static int characterColor = (100 << 16) | (215 << 8) | 1;

    public static double dropVelocity = 0, moveVelocity = 0.01 * frameInterval;

    public static double acceleration = 0.001 * frameInterval;

    public static boolean ableToJump = true, jump = false, left = false, right = false;

    public static void main() {

        paintCharacter();

        drop();

        move();
    }

    public static void paintCharacter() {
        int[] pos = coordinateToPos(x, y);
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                screen[posTrans(pos[0] + i, pos[1] + j)] = characterColor;
            }
        }
    }

    public static void drop() {


        int[] pos = coordinateToPos(x, y);

        if (legalPos(pos[0], pos[1] + sizeY + 1)) {
            if (Map.fill[posTrans(pos[0], pos[1] + sizeY + 1)]) {
                ableToJump = true;
                dropVelocity = 0;

                while (Map.fill[posTrans(pos[0], pos[1] + sizeY)]) {
                    y -= 0.001;
                    pos = coordinateToPos(x, y);

                }

            } else {
                dropVelocity += acceleration;
            }


        } else {

        }


        y += dropVelocity;
    }

    public static void move() {
        if (left) {
            direction = -1;
            x -= moveVelocity;
        }
        if (right) {
            direction = 1;
            x += moveVelocity;
        }
        if (jump && ableToJump) {
            ableToJump = false;
            y -= 0.01;
            dropVelocity = -0.3;
        }
    }

    public static void moveY(double distance) {
        boolean canMove = true;
        for (int i = 0; i < sizeX; i++) {
            int[] pos = coordinateToPos(x, y + sizeY + distance);
            if (Map.fill[posTrans(pos[0], pos[1])]) {
                canMove = false;
                break;
            }
            pos = coordinateToPos(x, y + distance);
            if (Map.fill[posTrans(pos[0], pos[1])]) {
                canMove = false;
                break;
            }
        }
        if (canMove) {
            y += distance;
        }
    }

    public static void moveX(double distance) {
        boolean canMove = true;
        for (int i = 0; i < sizeY; i++) {
            int[] pos = coordinateToPos(x + sizeX + distance, y);
            if (Map.fill[posTrans(pos[0], pos[1])]) {
                canMove = false;
                break;
            }
            pos = coordinateToPos(x + distance, y);
            if (Map.fill[posTrans(pos[0], pos[1])]) {
                canMove = false;
                break;
            }
        }
        if (canMove) {
            x += distance;
        }
    }
}
