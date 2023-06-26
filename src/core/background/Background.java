package core.background;

import core.MainThread;
import core.character.Character;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import static core.MainThread.*;
import static core.Tools.*;

public class Background {
    public static int skyblue = (120 << 16) | (160 << 8) | 200;
    public static int groundYellow = (140 << 16) | (81 << 8) | 25;
    public static int snowWhite = (255 << 16) | (250 << 8) | 250;

    public static double skyRate = 1;

    public static double[] snowSpeed = new double[]{-0.1 * frameInterval, 0.1 * frameInterval}; // 关联帧率

    public static ArrayList<double[]> snowPoses = new ArrayList<>();

    public static HashMap<Integer, ArrayList<Integer>> snows = new HashMap<>();

    public static Random random = new Random();

    public static void init() {

    }

    public static void main() {
        paintSky();

        paintGround();

        paintSnows(100);
    }

    public static void paintSky() {
        Arrays.fill(screen, 0, ((int) (skyRate * screen_h)) * screen_w, skyblue);
    }

    public static void paintGround() {
        Arrays.fill(screen, ((int) (skyRate * screen_h)) * screen_w, screenSize, groundYellow);
    }

    public static void paintSnows(int num) {
        while (snowPoses.size() < num) {
            double x = random.nextDouble(screen_w);
            double y = random.nextDouble(screen_h);

            snowPoses.add(new double[]{x, y});
        }

        for (int i = 0; i < num; i++) {

            double[] pos = snowPoses.get(i);

            if (!legalPos(pos[0], pos[1])) {
                pos[0] = random.nextDouble(screen_w);
                pos[1] = 0;
            }

            paintSnow((int)pos[0], (int)pos[1], 2);

            pos[0] += snowSpeed[0] - Character.direction * Character.moveVelocity * frameInterval;
            pos[1] += snowSpeed[1] - Character.dropVelocity * frameInterval;
        }
    }

    public static void paintSnow(int x, int y, int size) {
        paintSnow(posTrans(x, y), size);
    }

    public static void paintSnow(int pos, int size) {
        if (!snows.containsKey(size)) {
            snows.put(size, snowIndex(size));
        }

        ArrayList<Integer> snow = snows.get(size);

        for (int i = 0; i < snow.size(); i++) {
            int paintPos = pos + snow.get(i);
            if (legalPos(paintPos)) {
                screen[paintPos] = snowWhite;
            }
        }
    }

    public static ArrayList<Integer> snowIndex(int size) {
        ArrayList<Integer> indexes = new ArrayList<>();

        for (int x = -size; x <= size; x++) {
            for (int y = -size; y <= size; y++) {
                if (x * x + y * y <= size * size) {
                    indexes.add(posTrans(x, y));
                }
            }
        }

        return indexes;
    }
}
