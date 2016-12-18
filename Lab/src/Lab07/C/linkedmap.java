package Lab07.C;

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
public class linkedmap {

    private static final String INPUT_FILE_NAME = "linkedmap.in";
    private static final String OUTPUT_FILE_NAME = "linkedmap.out";

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);

        SimpleLinkedMap simpleLinkedMap = new SimpleLinkedMap(16);

        while (in.ready()) {

            StringTokenizer st = new StringTokenizer(in.readLine());
            String command = st.nextToken();
            String key = st.nextToken();
            String result;

            switch (command) {
                case "put":
                    String value = st.nextToken();
                    simpleLinkedMap.put(key, value);
                    break;
                case "get":
                    result = simpleLinkedMap.get(key);
                    out.println(result == null ? "none" : result);
                    break;
                case "delete":
                    simpleLinkedMap.delete(key);
                    break;
                case "prev":
                    result = simpleLinkedMap.prev(key);
                    out.println(result == null ? "none" : result);
                    break;
                case "next":
                    result = simpleLinkedMap.next(key);
                    out.println(result == null ? "none" : result);
                    break;
            }

        }

        in.close();
        out.close();
    }

    static class SimpleLinkedMap {

        private ArrayList<LinkedList<Pair>> buckets;
        private Pair lastAddedPair;

        public SimpleLinkedMap(int countOfBuckets) {
            int size = 2 << (countOfBuckets - 1);
            buckets = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                buckets.add(new LinkedList<>());
            }
        }

        public void put(String key, String value) {

            int bucketNumber = key.hashCode() & (buckets.size() - 1);
            LinkedList<Pair> chain = buckets.get(bucketNumber);

            for (Pair pair : chain) {
                if (pair.key.hashCode() == key.hashCode()) {
                    if (pair.key.equals(key)) {
                        pair.value = value;
                        return;
                    }
                }
            }

            Pair pair = new Pair(key, value);
            linkPair(pair);
            buckets.get(bucketNumber).add(pair);

        }

        public void delete(String key) {

            int bucketNumber = key.hashCode() & (buckets.size() - 1);
            LinkedList<Pair> chain = buckets.get(bucketNumber);

            Iterator<Pair> iterator = chain.iterator();

            while (iterator.hasNext()) {
                Pair pair = iterator.next();
                if (pair.key.hashCode() == key.hashCode()) {
                    if (pair.key.equals(key)) {

                        unlinkPair(pair);
                        iterator.remove();

                        break;
                    }
                }
            }
        }

        public String get(String key) {
            Pair result = getPair(key);
            return (result == null) ? null : result.value;
        }

        private Pair getPair(String key) {

            int bucketNumber = key.hashCode() & (buckets.size() - 1);
            LinkedList<Pair> chain = buckets.get(bucketNumber);

            for (Pair pair : chain) {
                if (pair.key.hashCode() == key.hashCode()) {
                    if (pair.key.equals(key)) {
                        return pair;
                    }
                }
            }

            return null;
        }

        public String prev(String key) {
            Pair current = getPair(key);
            return (current == null || current.prev == null) ? null : current.prev.value;
        }

        public String next(String key) {
            Pair current = getPair(key);
            return (current == null || current.next == null) ? null : current.next.value;
        }

        private void linkPair(Pair pair) {

            if (lastAddedPair == null) {
                pair.prev = null;
                pair.next = null;
                lastAddedPair = pair;
            } else {
                lastAddedPair.next = pair;
                pair.prev = lastAddedPair;
                pair.next = null;
                lastAddedPair = pair;
            }

        }

        private void unlinkPair(Pair pair) {

            Pair prev = pair.prev;
            Pair next = pair.next;

            //one in list
            if (prev == null && next == null) {
                lastAddedPair = null;
                return;
            }

            //last in list
            if (next == null) {
                prev.next = null;
                pair.prev = null;
                lastAddedPair = prev;
                return;
            }

            //first in list
            if (prev == null) {
                next.prev = null;
                pair.next = null;
                return;
            }

            //in the middle
            pair.next = null;
            pair.prev = null;
            prev.next = next;
            next.prev = prev;

        }

        static class Pair {
            String key;
            String value;
            Pair prev;
            Pair next;

            public Pair(String key, String value) {
                this.key = key;
                this.value = value;
            }
        }

    }
}