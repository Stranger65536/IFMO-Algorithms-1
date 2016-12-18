package Lab05.A;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
public class height {

    private static final String INPUT_FILE_NAME = "height.in";
    private static final String OUTPUT_FILE_NAME = "height.out";

    private static int treeHeight = 0;

    private static int walkTree(Node[] tree, int nodeIndex, int height) {

        Node currentNode = tree[nodeIndex];

        treeHeight = (height > treeHeight) ? height : treeHeight;

        if (currentNode.leftChildIndex != 0) {
            walkTree(tree, currentNode.leftChildIndex, height + 1);
        }

        if (currentNode.rightChildIndex != 0) {
            walkTree(tree, currentNode.rightChildIndex, height + 1);
        }

        return treeHeight;

    }

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);

        int n = Integer.parseInt(in.readLine());

        Node[] tree = new Node[n + 1];

        if (n == 0) {

            out.println("0");

        } else {

            for (int i = 0; i < n; i++) {

                StringTokenizer st = new StringTokenizer(in.readLine());

                int key = Integer.parseInt(st.nextToken());
                int leftChildIndex = Integer.parseInt(st.nextToken());
                int rightChildIndex = Integer.parseInt(st.nextToken());

                tree[i + 1] = new Node(key, leftChildIndex, rightChildIndex);
            }

            out.println(walkTree(tree, 1, 1));
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