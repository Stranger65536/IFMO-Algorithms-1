package Lab05.E;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
public class priorityqueue {

    private static final String INPUT_FILE_NAME = "priorityqueue.in";
    private static final String OUTPUT_FILE_NAME = "priorityqueue.out";

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);
        HeapPriorityQueue pq = new HeapPriorityQueue(1000001);
        int count = 0;

        while (in.ready()) {

            StringTokenizer stringTokenizer = new StringTokenizer(in.readLine());
            String command = stringTokenizer.nextToken();

            count++;

            switch (command) {

                case "push":
                    int value = Integer.parseInt(stringTokenizer.nextToken());
                    pq.push(count, value);
                    break;

                case "extract-min":
                    out.println(pq.extractMin());
                    break;

                default:
                    int oldKey = Integer.parseInt(stringTokenizer.nextToken());
                    int newKey = Integer.parseInt(stringTokenizer.nextToken());
                    int oldIndex = pq.find(oldKey);
                    pq.decreaseKey(oldIndex, newKey);
                    break;
            }

        }

        in.close();
        out.close();
    }

}

class HeapPriorityQueue {

    private int heapSize = 0;
    private int[][] heap;

    public HeapPriorityQueue(int capacity) {
        this.heap = new int[capacity][];
    }

    public void push(int step, int value) {

        heap[heapSize] = new int[2];
        heap[heapSize][0] = Integer.MAX_VALUE;
        heap[heapSize][1] = step;
        ++heapSize;
        decreaseKey(heapSize - 1, value);

    }

    public int find(int key) {

        int index = 0;

        for (int i = 0; i < heapSize; ++i) {
            if (heap[i][1] == key) {
                index = i;
                break;
            }
        }

        return index;
    }

    public void buildHeap(int start, int n) {

        int left = start * 2 + 1;
        int right = start * 2 + 2;
        int min = start;

        if (left < n && heap[left][0] < heap[min][0]) {
            min = left;
        }

        if (right < n && heap[right][0] < heap[min][0]) {
            min = right;
        }

        if (start != min) {
            int[] t = heap[min];
            heap[min] = heap[start];
            heap[start] = t;
            buildHeap(min, n);
        }

    }

    public String extractMin() {

        if (heapSize == 0) {
            return "*";
        } else {
            int first = heap[0][0];
            heap[0] = heap[heapSize - 1];
            buildHeap(0, --heapSize);
            return Integer.toString(first);
        }
    }

    public void decreaseKey(int index, int value) {

        if (value < heap[index][0]) {

            heap[index][0] = value;

            while (index > 0 && (heap[(index - 1) / 2][0] > heap[index][0])) {
                int[] t = heap[index];
                heap[index] = heap[(index - 1) / 2];
                heap[(index - 1) / 2] = t;
                index = (index - 1) / 2;
            }

        } else {

            if (value > heap[index][0]) {
                heap[index][0] = value;
                buildHeap(index, heapSize);
            }

        }
    }

}
