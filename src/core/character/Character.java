package core.character;

import core.camera.Camera;
import core.map.Map;

import static core.MainThread.*;
import static core.Tools.*;

public class Character {
    public static double x = 0;
    public static double y = -2;

    public static double sizeX = 30, sizeY = 40;

    public static int direction = 0;

    public static int characterColor = (100 << 16) | (215 << 8) | 1;

    public static double dropVelocity = 0, moveVelocity = 0.01 * frameInterval;

    public static double acceleration = 0.001 * frameInterval;

    public static boolean jump = false, left = false, right = false;

    public static int ableToJump = 2;

    public static boolean notLastJump = true;

    public static void main() {

        paintCharacter();

        drop();

        move();
    }

    public static void paintCharacter() {
        int[] pos = coordinateToPos(x, y);
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                if (legalPos(pos[0] + i, pos[1] + j)) {
                    screen[posTrans(pos[0] + i, pos[1] + j)] = characterColor;
                }
                if (!legalPos(pos[0] + i + 40, pos[1] + j + 40)) {
                    Camera.lock = true;
                }
            }
        }
    }

    public static void drop() {


        int[] pos = coordinateToPos(x, y);

        dropVelocity += acceleration;

        if(!moveY(dropVelocity)) {
            Camera.lock = false;
            dropVelocity = 0;
        };

        if (!moveY(0.001)) {
            ableToJump = 2;
        } else {
            y -= 0.001;
        }
    }

    public static void move() {
        if (left) {
            direction = -1;
            moveX(-moveVelocity);
        }
        if (right) {
            direction = 1;
            moveX(moveVelocity);
        }
        if (jump && ableToJump > 0 && notLastJump) {
            ableToJump -= 1;
            notLastJump = false;
            moveY(-0.01);
            dropVelocity = -0.3;
        }
        if (!left && !right) {
            direction = 0;
        }
    }

    public static boolean moveY(double distance) {
        boolean canMove = true;
        for (int i = 0; i < sizeX; i++) {
            if (distance > 0) {
                int[] pos = coordinateToPos(x, y + distance);
                if (legalPos(pos[0] + i, pos[1] + sizeY)) {
                    if (Map.fill[posTrans(pos[0] + i, pos[1] + sizeY)]) {
                        canMove = false;
                        break;
                    }
                }
            } else {
                int[] pos = coordinateToPos(x, y + distance);
                if (legalPos(pos[0], pos[1])) {
                    if (Map.fill[posTrans(pos[0] + i, pos[1])]) {
                        canMove = false;
                        break;
                    }
                }
            }
        }
        if (canMove) {
            y += distance;
        }
        return canMove;
    }

    public static boolean moveX(double distance) {
        boolean canMove = true;
        for (int i = 0; i < sizeY; i++) {
            int[] pos = coordinateToPos(x + distance, y);
            if (legalPos(pos[0] + sizeX, pos[1] + i)) {
                if (Map.fill[posTrans(pos[0] + sizeX + 1, pos[1] + i)]) {
                    canMove = false;
                    break;
                }
            }

            pos = coordinateToPos(x + distance, y);
            if (legalPos(pos[0], pos[1] + i)) {
                if (Map.fill[posTrans(pos[0] - 1, pos[1] + i)]) {
                    canMove = false;
                    break;
                }
            }
        }
        if (canMove) {
            x += distance;
        }
        return canMove;
    }
}
