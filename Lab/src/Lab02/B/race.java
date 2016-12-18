package Lab02.B;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
@SuppressWarnings("ManualArrayCopy")
public class race {

    private static final String INPUT_FILE_NAME = "race.in";
    private static final String OUTPUT_FILE_NAME = "race.out";

    private static void merge(Pair[] A, int p, int q, int r) {

        int n1 = q - p + 1;
        int n2 = r - q;

        Pair[] L = new Pair[n1];
        Pair[] R = new Pair[n2];

        for (int i = 0; i < n1; i++)
            L[i] = A[p + i];

        for (int j = 0; j < n2; j++)
            R[j] = A[q + j + 1];

        int left = n1, right = n2;

        for (int k = p; k <= r; k++) {

            if (left > 0) {
                if (right > 0) {
                    if (L[n1 - left].lessOrEquals(R[n2 - right])) {
                        A[k] = L[n1 - left];
                        left--;
                    } else {
                        A[k] = R[n2 - right];
                        right--;
                    }
                } else {
                    A[k] = L[n1 - left];
                    left--;
                }
            } else {
                A[k] = R[n2 - right];
                right--;
            }
        }
    }

    private static void mergeSort(Pair[] A, int fromIndex, int toIndex) {
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
        Pair[] array = new Pair[n];

        for (int i = 0; i < n; i++) {
            stringTokenizer = new StringTokenizer(in.readLine());
            Pair pair = new Pair();
            pair.county = stringTokenizer.nextToken();
            pair.lastName = stringTokenizer.nextToken();
            array[i] = pair;
        }

        mergeSort(array, 0, n - 1);

        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);

        String lastCountry = null;

        for (int i = 0; i < n; i++) {
            Pair pair = array[i];
            if (!pair.county.equals(lastCountry)) {
                lastCountry = pair.county;
                out.print("=== ");
                out.print(pair.county);
                out.println(" ===");
            }
            out.println(pair.lastName);
        }

        in.close();
        out.close();
    }
}

class Pair {
    String county;
    String lastName;

    public boolean lessOrEquals(Pair right) {
        return (this.county.compareTo(right.county) <= 0);
    }
}
