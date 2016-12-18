package Lab03.C;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
public class binsearch {

    private static final String INPUT_FILE_NAME = "binsearch.in";
    private static final String OUTPUT_FILE_NAME = "binsearch.out";

    private static int binarySearchFirst(final int[] arr, final int low, final int high, final int key) {

        if (high >= low) {

            int mid = (low + high) / 2;

            if ((mid == 0 || key > arr[mid - 1]) && arr[mid] == key) {
                return mid;
            } else if (key > arr[mid]) {
                return binarySearchFirst(arr, mid + 1, high, key);
            } else {
                return binarySearchFirst(arr, low, mid - 1, key);
            }
        }

        return -1;
    }

    private static int binarySearchLast(final int[] arr, final int low, final int high, final int key) {

        if (high >= low) {

            int mid = (low + high) / 2;

            if ((mid == arr.length - 1 || key < arr[mid + 1]) && arr[mid] == key) {
                return mid;
            } else if (key < arr[mid]) {
                return binarySearchLast(arr, low, mid - 1, key);
            } else {
                return binarySearchLast(arr, mid + 1, high, key);
            }
        }

        return -1;
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

        StringTokenizer stringTokenizer2 = new StringTokenizer(in.readLine());
        int m = Integer.parseInt(stringTokenizer2.nextToken());
        StringTokenizer stringTokenizer3 = new StringTokenizer(in.readLine());

        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);

        for (int i = 0; i < m; i++) {

            int find = Integer.parseInt(stringTokenizer3.nextToken());
            int res1 = binarySearchFirst(array, 0, n - 1, find);
            int res2 = binarySearchLast(array, 0, n - 1, find);

            out.println(((res1 == -1) ? -1 : res1 + 1) + " " + ((res2 == -1) ? -1 : res2 + 1));
        }

        in.close();
        out.close();
    }
}
