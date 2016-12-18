package Lab03.D;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
public class garland {

    private static final String INPUT_FILE_NAME = "garland.in";
    private static final String OUTPUT_FILE_NAME = "garland.out";
    private static final double eps = 1e-9;

    private static double getHeight(ArrayList<Double> h) {

        int n = h.size();
        double res = 1e9;
        double right = 0, left = h.get(0);

        while (less(right, left)) {

            h.set(1, (right + left) / 2);
            h.set(h.size() - 1, 0d);
            boolean isUp = false;

            for (int i = 2; i < n; i++) {

                h.set(i, h.get(i - 1) * 2 - h.get(i - 2) + 2);

                if (!greater(h.get(i), 0)) {
                    isUp = true;
                    break;
                }
            }

            if (greater(h.get(h.size() - 1), 0.0)) {
                res = Math.min(res, h.get(h.size() - 1));
            }

            if (isUp) {
                right = h.get(1);
            } else {
                left = h.get(1);
            }
        }

        return res;
    }

    private static boolean equals(double a, double b) {
        return Math.abs(a - b) <= eps;
    }

    private static boolean less(double a, double b) {
        return a < b && !equals(a, b);
    }

    private static boolean greater(double a, double b) {
        return a > b && !equals(a, b);
    }

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        StringTokenizer stringTokenizer = new StringTokenizer(in.readLine());

        int n = Integer.parseInt(stringTokenizer.nextToken());
        ArrayList<Double> garland = new ArrayList<>(Collections.nCopies(n, 0d));
        garland.set(0, Double.parseDouble(stringTokenizer.nextToken()));

        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);

        double res = getHeight(garland);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
        DecimalFormat formatter = (DecimalFormat)numberFormat;
        formatter.setGroupingUsed(false);
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        formatter.setMinimumIntegerDigits(1);

        out.println(formatter.format(res));

        in.close();
        out.close();
    }
}
