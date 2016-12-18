package Lab07.D;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author vladislav.trofimov@emc.com
 */
public class multimap {

    private static final String INPUT_FILE_NAME = "multimap.in";
    private static final String OUTPUT_FILE_NAME = "multimap.out";

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);

        SimpleMultiMap simpleMultiMap = new SimpleMultiMap(14);

        while (in.ready()) {

            StringTokenizer st = new StringTokenizer(in.readLine());
            String command = st.nextToken();
            String key = st.nextToken();
            String value;

            switch (command) {
                case "put":
                    value = st.nextToken();
                    simpleMultiMap.put(key, value);
                    break;
                case "delete":
                    value = st.nextToken();
                    simpleMultiMap.delete(key, value);
                    break;
                case "deleteall":
                    simpleMultiMap.deleteAll(key);
                    break;
                case "get":
                    LinkedList<String> list = simpleMultiMap.get(key);
                    out.print(list.size() + " ");
                    for (String s : list) {
                        out.print(s + " ");
                    }
                    out.println();
                    break;
            }

        }

        in.close();
        out.close();
    }

    static class SimpleMultiMap {

        private ArrayList<LinkedList<Pair>> buckets;

        public SimpleMultiMap(int countOfBuckets) {
            int size = 2 << (countOfBuckets - 1);
            buckets = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                buckets.add(new LinkedList<>());
            }
        }

        void put(String keyX, String keyY) {

            int bucketNumber = keyX.hashCode() & (buckets.size() - 1);
            LinkedList<Pair> chain = buckets.get(bucketNumber);
            SimpleSet chainSet = null;

            for (Pair pair : chain) {
                if (pair.key.hashCode() == keyX.hashCode()) {
                    if (pair.key.equals(keyX)) {
                        chainSet = pair.value;
                        break;
                    }
                }
            }

            if (chainSet == null) {
                chainSet = new SimpleSet(1);
                chain.add(new Pair(keyX, chainSet));
            }

            chainSet.insert(keyY);
        }

        void delete(String keyX, String keyY) {

            int bucketNumber = keyX.hashCode() & (buckets.size() - 1);
            LinkedList<Pair> chain = buckets.get(bucketNumber);
            SimpleSet chainSet = null;

            for (Pair pair : chain) {
                if (pair.key.hashCode() == keyX.hashCode()) {
                    if (pair.key.equals(keyX)) {
                        chainSet = pair.value;
                        break;
                    }
                }
            }

            if (chainSet != null) {
                chainSet.delete(keyY);
            }

        }

        void deleteAll(String keyX) {

            int bucketNumber = keyX.hashCode() & (buckets.size() - 1);
            LinkedList<Pair> chain = buckets.get(bucketNumber);
            ListIterator<Pair> iterator = chain.listIterator();

            while (iterator.hasNext()) {
                Pair pair = iterator.next();
                if (pair.key.hashCode() == keyX.hashCode()) {
                    if (pair.key.equals(keyX)) {
                        iterator.remove();
                        break;
                    }
                }
            }

        }

        LinkedList<String> get(String keyX) {

            int bucketNumber = keyX.hashCode() & (buckets.size() - 1);
            LinkedList<Pair> chain = buckets.get(bucketNumber);
            LinkedList<String> result = new LinkedList<>();

            for (Pair pair : chain) {
                if (pair.key.hashCode() == keyX.hashCode()) {
                    if (pair.key.equals(keyX)) {

                        SimpleSet chainSet = pair.value;
                        ArrayList<LinkedList<String>> buckets = chainSet.getAllValues();
                        buckets.forEach(result :: addAll);

                        break;
                    }
                }
            }

            return result;
        }

        static class Pair {
            String key;
            SimpleSet value;

            public Pair(String key, SimpleSet value) {
                this.key = key;
                this.value = value;
            }
        }

    }

    static class SimpleSet {

        private ArrayList<LinkedList<String>> buckets;

        public SimpleSet(int countOfBuckets) {
            int size = 2 << (countOfBuckets - 1);
            buckets = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                buckets.add(new LinkedList<>());
            }
        }

        public void insert(String value) {

            int bucketNumber = value.hashCode() & (buckets.size() - 1);
            LinkedList<String> chain = buckets.get(bucketNumber);

            for (String key : chain) {
                if (key.hashCode() == value.hashCode()) {
                    if (key.equals(value)) {
                        return;
                    }
                }
            }

            chain.add(value);
        }

        public void delete(String value) {
            int bucketNumber = value.hashCode() & (buckets.size() - 1);
            LinkedList<String> chain = buckets.get(bucketNumber);
            chain.removeFirstOccurrence(value);
        }

        public boolean exists(String value) {
            int bucketNumber = value.hashCode() & (buckets.size() - 1);
            LinkedList<String> chain = buckets.get(bucketNumber);
            return chain.contains(value);
        }

        public ArrayList<LinkedList<String>> getAllValues() {
            return buckets;
        }

    }

}
