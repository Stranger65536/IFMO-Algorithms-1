package Lab03.B;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
public class sort {

    private static final String INPUT_FILE_NAME = "sort.in";
    private static final String OUTPUT_FILE_NAME = "sort.out";

    private static void heapSort(int[] a) {

        int n = a.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            pushDown(a, i, n);
        }

        while (n > 1) {
            swap(a, 0, n - 1);
            pushDown(a, 0, --n);
        }
    }

    private static void pushDown(int[] h, int pos, int size) {

        while (true) {

            int child = 2 * pos + 1;

            if (child >= size) {
                break;
            }

            if (child + 1 < size && h[child + 1] > h[child]) {
                child++;
            }

            if (h[pos] >= h[child]) {
                break;
            }

            swap(h, pos, child);
            pos = child;
        }
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[j];
        a[j] = a[i];
        a[i] = t;
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

        heapSort(array);

        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);

        for (int a : array) {
            out.print(a + " ");
        }

        in.close();
        out.close();
    }
}
