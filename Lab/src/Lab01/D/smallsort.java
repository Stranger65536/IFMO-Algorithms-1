package Lab01.D;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
public class smallsort {

    private static final String INPUT_FILE_NAME = "smallsort.in";
    private static final String OUTPUT_FILE_NAME = "smallsort.out";

    private static void insertionSort(int[] a) {

        for (int i = 1; i < a.length; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= 0 && a[j] > key) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
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

        insertionSort(array);

        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);

        for (int i = 0; i < n; i++) {
            out.print(array[i] + " ");
        }

        in.close();
        out.close();
    }
}
