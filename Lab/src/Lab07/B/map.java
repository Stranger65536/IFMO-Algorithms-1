package Lab07.B;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
public class map {

    private static final String INPUT_FILE_NAME = "map.in";
    private static final String OUTPUT_FILE_NAME = "map.out";

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);

        SimpleMap simpleMap = new SimpleMap(16);

        while (in.ready()) {

            StringTokenizer st = new StringTokenizer(in.readLine());
            String command = st.nextToken();
            String key = st.nextToken();

            switch (command) {
                case "put":
                    String value = st.nextToken();
                    simpleMap.put(key, value);
                    break;
                case "get":
                    String result = simpleMap.get(key);
                    out.println(result == null ? "none" : result);
                    break;
                case "delete":
                    simpleMap.delete(key);
                    break;
            }

        }

        in.close();
        out.close();
    }

    static class SimpleMap {

        private ArrayList<LinkedList<Pair>> buckets;

        public SimpleMap(int countOfBuckets) {
            int size = 2 << (countOfBuckets - 1);
            buckets = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                buckets.add(new LinkedList<>());
            }
        }

        public void put(String key, String value) {

            int bucketNumber = key.hashCode() & (buckets.size() - 1);
            LinkedList<Pair> chain = buckets.get(bucketNumber);
            boolean inserted = false;

            for (Pair pair : chain) {
                if (pair.key.hashCode() == key.hashCode()) {
                    if (pair.key.equals(key)) {
                        pair.value = value;
                        inserted = true;
                        break;
                    }
                }
            }

            if (!inserted) {
                chain.add(new Pair(key, value));
            }
        }

        public void delete(String key) {

            int bucketNumber = key.hashCode() & (buckets.size() - 1);
            LinkedList<Pair> chain = buckets.get(bucketNumber);

            Iterator<Pair> iterator = chain.iterator();

            while (iterator.hasNext()) {
                Pair pair = iterator.next();
                if (pair.key.hashCode() == key.hashCode()) {
                    if (pair.key.equals(key)) {
                        iterator.remove();
                        break;
                    }
                }
            }
        }

        public String get(String key) {

            int bucketNumber = key.hashCode() & (buckets.size() - 1);
            LinkedList<Pair> chain = buckets.get(bucketNumber);

            for (Pair pair : chain) {
                if (pair.key.hashCode() == key.hashCode()) {
                    if (pair.key.equals(key)) {
                        return pair.value;
                    }
                }
            }

            return null;
        }

        static class Pair {
            String key;
            String value;

            public Pair(String key, String value) {
                this.key = key;
                this.value = value;
            }
        }

    }
}