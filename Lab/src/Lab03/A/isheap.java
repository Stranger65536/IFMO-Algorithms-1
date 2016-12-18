package Lab03.A;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
public class isheap {

    private static final String INPUT_FILE_NAME = "isheap.in";
    private static final String OUTPUT_FILE_NAME = "isheap.out";

    private static boolean isHeap(final int[] a) {

        boolean res = true;
        int i = 1;

        while (res && i << 2 <= a.length - 1 && (i << 2) + 1 <= a.length - 1) {
            if (a[i] > a[i << 1] || a[i] > a[i++ << 1 + 1]) {
                res = false;
            }
        }

        return res;
    }

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        StringTokenizer stringTokenizer = new StringTokenizer(in.readLine());

        int n = Integer.parseInt(stringTokenizer.nextToken());
        int[] array = new int[n + 1];

        StringTokenizer stringTokenizer1 = new StringTokenizer(in.readLine());
        for (int i = 1; i < n; i++) {
            array[i] = Integer.parseInt(stringTokenizer1.nextToken());
        }

        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);

        out.println((isHeap(array)) ? "YES" : "NO");

        in.close();
        out.close();
    }
}
