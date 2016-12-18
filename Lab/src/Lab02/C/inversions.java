package Lab02.C;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
public class inversions {

    private static final String INPUT_FILE_NAME = "inversions.in";
    private static final String OUTPUT_FILE_NAME = "inversions.out";
    private static long inversions;

    private static void merge(int[] A, int p, int q, int r) {

        int i, j;
        int n1 = q - p + 1;
        int n2 = r - q;

        int[] L = new int[n1 + 1];
        int[] R = new int[n2 + 1];

        L[n1] = Integer.MAX_VALUE;
        R[n2] = Integer.MAX_VALUE;

        for (i = 0; i < n1; i++)
            L[i] = A[p + i];

        for (j = 0; j < n2; j++)
            R[j] = A[q + j + 1];

        i = j = 0;

        for (int k = p; k <= r; k++) {
            if (L[i] <= R[j]) {
                A[k] = L[i];
                i++;
            } else {
                A[k] = R[j];
                j++;
                inversions += n1 - i;
            }
        }
    }

    private static void mergeSort(int[] A, int fromIndex, int toIndex) {
        if (fromIndex < toIndex) {
            int q = (int) Math.floor((toIndex + fromIndex) / 2.0);
            mergeSort(A, fromIndex, q);
            mergeSort(A, q + 1, toIndex);
            merge(A, fromIndex, q, toIndex);
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

        mergeSort(array, 0, n - 1);

        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);

        out.print(inversions);

        in.close();
        out.close();
    }
}