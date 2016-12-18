package Lab02.A;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
public class sort {

    private static final String INPUT_FILE_NAME = "sort.in";
    private static final String OUTPUT_FILE_NAME = "sort.out";
    private static Random rnd = new Random();

    private static void qSort(int[] a, int low, int high) {

        if (high - low < 1)
            return;

        int separator = a[low + rnd.nextInt(high - low + 1)];

        int i = low;
        int j = high;

        while (i <= j) {

            while (a[i] < separator)
                ++i;

            while (a[j] > separator)
                --j;

            if (i > j)
                break;

            int t = a[i];
            a[i] = a[j];
            a[j] = t;

            ++i;
            --j;
        }

        qSort(a, low, j);
        qSort(a, i, high);
    }

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        StringTokenizer stringTokenizer = new StringTokenizer(in.readLine());

        int n = Integer.parseInt(stringTokenizer.nextToken());
        int[] array = new int[n];

        StringTokenizer stringTokenizer1 = new StringTokenizer(in.readLine());
        for (int i = 0; i < n; i++) {
            array[i] = Integer.parseInt(stringTokenizer1.nextToken());
        }

        qSort(array, 0, n - 1);

        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);

        for (int i = 0; i < n; i++) {
            out.print(array[i] + " ");
        }

        in.close();
        out.close();
    }
}
