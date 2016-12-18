package Lab02.D;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
public class antiqs {

    private static final String INPUT_FILE_NAME = "antiqs.in";
    private static final String OUTPUT_FILE_NAME = "antiqs.out";

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        StringTokenizer stringTokenizer = new StringTokenizer(in.readLine());

        int n = Integer.parseInt(stringTokenizer.nextToken());
        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = i + 1;
        }

        for (int i = 2; i < n; i++) {
            int t = a[i];
            a[i] = a[i / 2];
            a[i / 2] = t;
        }

        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);

        for (int i = 0; i < n; i++) {
            out.print(a[i] + " ");
        }
        in.close();
        out.close();
    }
}
