package Lab06.A;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
public class balance {

    private static final String INPUT_FILE_NAME = "balance.in";
    private static final String OUTPUT_FILE_NAME = "balance.out";

    private static void walkTree(Node[] tree, int nodeIndex) {

        Node currentNode = tree[nodeIndex];

        if (currentNode.leftChildIndex == 0) {
            currentNode.leftChildHeight = 0;
        } else {
            walkTree(tree, currentNode.leftChildIndex);
            currentNode.leftChildHeight = tree[currentNode.leftChildIndex].height + 1;
        }

        if (currentNode.rightChildIndex == 0) {
            currentNode.rightChildHeight = 0;
        } else {
            walkTree(tree, currentNode.rightChildIndex);
            currentNode.rightChildHeight = tree[currentNode.rightChildIndex].height + 1;
        }

        currentNode.height = Math.max(currentNode.leftChildHeight, currentNode.rightChildHeight);
    }

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);

        int n = Integer.parseInt(in.readLine());

        Node[] tree = new Node[n + 1];

        for (int i = 0; i < n; i++) {

            StringTokenizer st = new StringTokenizer(in.readLine());

            int key = Integer.parseInt(st.nextToken());
            int leftChildIndex = Integer.parseInt(st.nextToken());
            int rightChildIndex = Integer.parseInt(st.nextToken());

            tree[i + 1] = new Node(key, leftChildIndex, rightChildIndex);
        }

        walkTree(tree, 1);

        for (int i = 0; i < n; i++) {
            Node node = tree[i + 1];
            out.println(node.rightChildHeight - node.leftChildHeight);
        }

        in.close();
        out.close();
    }
}

class Node {

    int key;
    int height;
    int leftChildIndex;
    int rightChildIndex;
    int leftChildHeight;
    int rightChildHeight;

    Node(int key, int leftChildIndex, int rightChildIndex) {
        this.key = key;
        this.leftChildIndex = leftChildIndex;
        this.rightChildIndex = rightChildIndex;
    }

}