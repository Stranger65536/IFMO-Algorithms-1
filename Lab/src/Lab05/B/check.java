package Lab05.B;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
public class check {

    private static final String INPUT_FILE_NAME = "check.in";
    private static final String OUTPUT_FILE_NAME = "check.out";

    private static boolean isBST(Node[] tree) {
        ArrayList<Integer> traversalOrderTree = traversalOrder(tree, 1, new ArrayList<Integer>(tree.length));
        return isAscUniqueSorted(traversalOrderTree);
    }

    private static boolean isAscUniqueSorted(ArrayList<Integer> sequence) {

        if (sequence.size() == 1) {
            return true;
        }

        for (int i = 0; i < sequence.size() - 1; i++) {

            int current = sequence.get(i);
            int next = sequence.get(i + 1);

            if (current >= next) {
                return false;
            }

        }

        return true;
    }

    private static ArrayList<Integer> traversalOrder(Node[] tree, int index, ArrayList<Integer> res) {

        Node currentNode = tree[index];

        if (currentNode.leftChildIndex != 0) {
            traversalOrder(tree, currentNode.leftChildIndex, res);
        }

        res.add(currentNode.key);

        if (currentNode.rightChildIndex != 0) {
            traversalOrder(tree, currentNode.rightChildIndex, res);
        }

        return res;
    }

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);

        int n = Integer.parseInt(in.readLine());

        Node[] tree = new Node[n + 1];

        if (n == 0) {

            out.println("YES");

        } else {

            for (int i = 0; i < n; i++) {

                StringTokenizer st = new StringTokenizer(in.readLine());

                int key = Integer.parseInt(st.nextToken());
                int leftChildIndex = Integer.parseInt(st.nextToken());
                int rightChildIndex = Integer.parseInt(st.nextToken());

                tree[i + 1] = new Node(key, leftChildIndex, rightChildIndex);
            }

            out.println(isBST(tree) ? "YES" : "NO");
        }

        in.close();
        out.close();
    }

}

class Node {

    int key;
    int leftChildIndex;
    int rightChildIndex;

    Node(int key, int leftChildIndex, int rightChildIndex) {
        this.key = key;
        this.leftChildIndex = leftChildIndex;
        this.rightChildIndex = rightChildIndex;
    }

}