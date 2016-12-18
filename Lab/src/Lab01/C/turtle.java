package Lab01.C;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
public class turtle {

    private static final String INPUT_FILE_NAME = "turtle.in";
    private static final String OUTPUT_FILE_NAME = "turtle.out";

    private static int FIRST_COLUMN = 0, FIRST_ROW = 0, LAST_ROW, LAST_COLUMN;

    private static void calculate(int[][] grid, int[][] wave) {

        wave[LAST_ROW][0] = grid[LAST_ROW][0];

        //fill first column without last element
        for (int row = LAST_ROW - 1; row >= FIRST_ROW; row--) {
            wave[row][FIRST_COLUMN] = wave[row + 1][FIRST_COLUMN] + grid[row][FIRST_COLUMN];
        }

        //fill last row without first element
        for (int column = 1; column <= LAST_COLUMN; column++) {
            wave[LAST_ROW][column] = wave[LAST_ROW][column - 1] + grid[LAST_ROW][column];
        }

        //waving
        for (int row = LAST_ROW - 1; row >= FIRST_ROW; row--) {
            for (int column = FIRST_COLUMN + 1; column <= LAST_COLUMN; column++) {

                if (wave[row][column - 1] > wave[row + 1][column]) {
                    wave[row][column] = wave[row][column - 1] + grid[row][column];
                } else {
                    wave[row][column] = wave[row + 1][column] + grid[row][column];
                }

            }
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        StringTokenizer stringTokenizer = new StringTokenizer(in.readLine());

        int h = Integer.parseInt(stringTokenizer.nextToken());
        int w = Integer.parseInt(stringTokenizer.nextToken());
        LAST_ROW = h - 1;
        LAST_COLUMN = w - 1;

        int[][] grid = new int[h][w], wave = new int[h][w];

        for (int i = 0; i < h; i++) {
            StringTokenizer st = new StringTokenizer(in.readLine());
            for (int j = 0; j < w; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        calculate(grid, wave);

        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);

        out.print(wave[FIRST_ROW][LAST_COLUMN]);

        in.close();
        out.close();
    }
}
