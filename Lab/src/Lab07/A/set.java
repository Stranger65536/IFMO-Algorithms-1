package Lab07.A;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
public class set {

    private static final String INPUT_FILE_NAME = "set.in";
    private static final String OUTPUT_FILE_NAME = "set.out";

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);

        SimpleSet simpleSet = new SimpleSet(16);

        while (in.ready()) {

            StringTokenizer st = new StringTokenizer(in.readLine());
            String command = st.nextToken();
            int key = Integer.parseInt(st.nextToken());

            switch (command) {
                case "insert":
                    simpleSet.insert(key);
                    break;
                case "exists":
                    out.println(simpleSet.exists(key));
                    break;
                case "delete":
                    simpleSet.delete(key);
                    break;
            }

        }

        in.close();
        out.close();
    }

    static class SimpleSet {

        private ArrayList<LinkedList<Integer>> buckets;

        public SimpleSet(int countOfBuckets) {
            int size = 2 << (countOfBuckets - 1);
            buckets = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                buckets.add(new LinkedList<>());
            }
        }

        public void insert(int value) {

            int bucketNumber = value & (buckets.size() - 1);
            LinkedList<Integer> chain = buckets.get(bucketNumber);

            for (Integer key : chain) {
                if (key == value) {
                    return;
                }
            }

            chain.add(value);
        }

        public void delete(int value) {
            int bucketNumber = value & (buckets.size() - 1);
            LinkedList<Integer> chain = buckets.get(bucketNumber);
            chain.removeFirstOccurrence(value);
        }

        public boolean exists(int value) {
            int bucketNumber = value & (buckets.size() - 1);
            LinkedList<Integer> chain = buckets.get(bucketNumber);
            return chain.contains(value);
        }
    }
}