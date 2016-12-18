package Lab02.E;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
public class kth {

    private static final String INPUT_FILE_NAME = "kth.in";
    private static final String OUTPUT_FILE_NAME = "kth.out";

    private static int kth_n(int[] a, int left, int right, int k) {

        int x = a[(left + right) / 2];
        int i = left, j = right;

        while (i <= j) {

            while (a[i] < x) {
                i++;
            }

            while (a[j] > x) {
                j--;
            }

            if (i <= j) {
                swap(a, i, j);
                i++;
                j--;
            }
        }

        if ((left <= k) && (k <= j)) return kth_n(a, left, j, k);
        else if ((i <= k) && (k <= right)) return kth_n(a, i, right, k);
        else return a[k];
    }

    static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }


    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        StringTokenizer stringTokenizer = new StringTokenizer(in.readLine());

        int n = Integer.parseInt(stringTokenizer.nextToken());
        int k = Integer.parseInt(stringTokenizer.nextToken());
        int[] array = new int[n];

        stringTokenizer = new StringTokenizer(in.readLine());

        int a = Integer.parseInt(stringTokenizer.nextToken());
        int b = Integer.parseInt(stringTokenizer.nextToken());
        int c = Integer.parseInt(stringTokenizer.nextToken());
        array[0] = Integer.parseInt(stringTokenizer.nextToken());
        array[1] = Integer.parseInt(stringTokenizer.nextToken());

        for (int i = 2; i < n; i++) {
            array[i] = a * array[i - 2] + b * array[i - 1] + c;
        }

        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);

        out.print(kth_n(array, 0, n - 1, k - 1));

        in.close();
        out.close();
    }
}
