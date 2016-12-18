package Lab01.E;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
public class sortland {

    private static final String INPUT_FILE_NAME = "sortland.in";
    private static final String OUTPUT_FILE_NAME = "sortland.out";


    private static void insertionSort(Pair[] a) {

        for (int i = 1; i < a.length; i++) {
            Pair current = a[i];
            int j = i - 1;
            while (j >= 0 && a[j].value > current.value) {
                a[j + 1] = a[j].getClone();
                j--;
            }
            a[j + 1] = current;
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        StringTokenizer stringTokenizer = new StringTokenizer(in.readLine());

        int n = Integer.parseInt(stringTokenizer.nextToken());
        Pair[] array = new Pair[n];

        StringTokenizer stringTokenizer1 = new StringTokenizer(in.readLine());

        for (int i = 0; i < n; i++) {

            double value = Double.parseDouble(stringTokenizer1.nextToken());

            Pair pair = new Pair();
            pair.key = i + 1;
            pair.value = value;

            array[i] = pair;
        }

        insertionSort(array);

        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);

        out.print(array[0].key + " " + array[array.length / 2].key + " " + array[array.length - 1].key);

        in.close();
        out.close();
    }
}

class Pair {
    int key;
    double value;

    Pair getClone() {
        Pair pair = new Pair();
        pair.key = key;
        pair.value = value;
        return pair;
    }
}

