package Lab05.D;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
public class queuemin {

    private static final String INPUT_FILE_NAME = "queuemin.in";
    private static final String OUTPUT_FILE_NAME = "queuemin.out";

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);

        StringTokenizer stringTokenizer = new StringTokenizer(in.readLine());

        int n = Integer.parseInt(stringTokenizer.nextToken());

        QueueMin<Integer> q = new QueueMin<>();

        for (int i = 0; i < n; i++) {

            String command = in.readLine();

            switch (command) {
                case "?":
                    out.println(q.min());
                    break;
                case "-":
                    q.removeFirst();
                    break;
                default:
                    int value = Integer.parseInt(command.substring(2));
                    q.addLast(value);
                    break;
            }
        }

        in.close();
        out.close();
    }

    static class QueueMin<E extends Comparable<E>> {

        List<E[]> s1 = new ArrayList<>();
        List<E[]> s2 = new ArrayList<>();

        E min(E a, E b) {
            return a.compareTo(b) < 0 ? a : b;
        }

        public E min() {

            if (s1.isEmpty()) {
                return s2.get(s2.size() - 1)[1];
            }

            if (s2.isEmpty()) {
                return s1.get(s1.size() - 1)[1];
            }

            return min(s1.get(s1.size() - 1)[1], s2.get(s2.size() - 1)[1]);
        }

        public void addLast(E x) {

            E minimal = x;

            if (!s1.isEmpty()) {
                minimal = min(minimal, s1.get(s1.size() - 1)[1]);
            }

            s1.add((E[]) new Comparable[]{x, minimal});
        }

        public E removeFirst() {

            if (s2.isEmpty()) {

                E minimal = null;

                while (!s1.isEmpty()) {
                    E x = s1.remove(s1.size() - 1)[0];
                    minimal = minimal == null ? x : min(minimal, x);
                    s2.add((E[]) new Comparable[]{x, minimal});
                }

            }

            return s2.remove(s2.size() - 1)[0];
        }
    }
}