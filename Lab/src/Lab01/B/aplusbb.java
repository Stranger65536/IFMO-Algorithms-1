package Lab01.B;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
public class aplusbb {

    private static final String INPUT_FILE_NAME = "aplusbb.in";
    private static final String OUTPUT_FILE_NAME = "aplusbb.out";

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        StringTokenizer stringTokenizer = new StringTokenizer(in.readLine());

        long a = Long.parseLong(stringTokenizer.nextToken()), b = Long.parseLong(stringTokenizer.nextToken());

        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);
        out.print(a + b * b);

        in.close();
        out.close();
    }
}
