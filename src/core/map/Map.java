package core.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import static core.MainThread.screen;
import static core.MainThread.screenSize;
import static core.Tools.*;

public class Map {
    public static int blockSize = 30;
    public static int groundColor = (230 << 16) | (215 << 8) | 1;

    public static String path = "map/test.txt";

    public static int readCondition = -1;
    public static ArrayList<int[]> grounds = new ArrayList<>();

    public static boolean[] fill = new boolean[screenSize];

    public static void init() {
        try {
            loadMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main() {
        cleanFill();

        paintGrounds();

    }

    public static void cleanFill() {
        Arrays.fill(fill, false);
    }

    public static void paintGrounds() {
        for (int[] ground : grounds) {
            int[] pos = coordinateToPos(ground[0], ground[1]);
            for (int i = 0; i < blockSize; i++) {
                for (int j = 0; j < blockSize; j++) {
                    if (legalPos(pos[0] + i, pos[1] + j)) {
                        int fillPos = posTrans(pos[0] + i, pos[1] + j);
                        screen[fillPos] = groundColor;
                        fill[fillPos] = true;
                    }
                }
            }
        }
    }

    public static void loadMap() throws Exception{
        BufferedReader readerMap = new BufferedReader(new FileReader(path));
        String line;
        while ((line = readerMap.readLine()) != null) {
            if (line.equals("ground")) {
                readCondition = 1;
            } else if (line.equals("end")) {
                break;
            } else {
                if (readCondition == 1) {
                    String[] nums = line.split(" ");
                    int x = Integer.parseInt(nums[0]);
                    int y = Integer.parseInt(nums[1]);
                    grounds.add(new int[]{x, y});
                }
            }
        }
        readerMap.close();
    }
}
