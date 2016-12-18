package Lab06.B;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
public class rotation {

    private static final String INPUT_FILE_NAME = "rotation.in";
    private static final String OUTPUT_FILE_NAME = "rotation.out";
    private static int nodeCount = 1;

    private static void walkTree(Node[] tree, int nodeIndex) {

        Node currentNode = tree[nodeIndex];

        if (currentNode.leftChildIndex == 0) {
            currentNode.leftChildHeight = 0;
        } else {
            walkTree(tree, currentNode.leftChildIndex);
            currentNode.leftNode = tree[currentNode.leftChildIndex];
            currentNode.leftChildHeight = tree[currentNode.leftChildIndex].height + 1;
        }

        if (currentNode.rightChildIndex == 0) {
            currentNode.rightChildHeight = 0;
        } else {
            walkTree(tree, currentNode.rightChildIndex);
            currentNode.rightNode = tree[currentNode.rightChildIndex];
            currentNode.rightChildHeight = tree[currentNode.rightChildIndex].height + 1;
        }

        currentNode.height = Math.max(currentNode.leftChildHeight, currentNode.rightChildHeight);
    }

    private static void makeLeftRotation(Node[] tree) {

        Node root = tree[1];
        Node rightChild = root.rightNode;
        int rightChildBalance = rightChild.rightChildHeight - rightChild.leftChildHeight;

        if (rightChildBalance == -1) {
            makeBigLeftRotation(tree);
        } else {
            makeSmallLeftRotation(tree);
        }

        reSerializeTree(tree);
    }

    private static void reSerializeTree(Node[] tree) {
        walkTree(tree, tree[1]);
    }

    private static void walkTree(Node[] tree, Node root) {

        if (root.leftNode != null) {
            tree[++nodeCount] = root.leftNode;
            root.leftChildIndex = nodeCount;
        } else {
            root.leftChildIndex = 0;
        }

        if (root.rightNode != null) {
            tree[++nodeCount] = root.rightNode;
            root.rightChildIndex = nodeCount;
        } else {
            root.rightChildIndex = 0;
        }

        if (root.leftNode != null) {
            walkTree(tree, root.leftNode);
        }

        if (root.rightNode != null) {
            walkTree(tree, root.rightNode);
        }

    }

    private static void makeSmallLeftRotation(Node[] tree) {
        Node a = tree[1];
        Node b = a.rightNode;
        Node x = a.leftNode;
        //noinspection SuspiciousNameCombination
        Node y = b.leftNode;
        Node z = b.rightNode;
        b.leftNode = a;
        b.rightNode = z;
        a.leftNode = x;
        //noinspection SuspiciousNameCombination
        a.rightNode = y;
        tree[1] = b;
    }

    private static void makeBigLeftRotation(Node[] tree) {
        Node a = tree[1];
        Node w = a.leftNode;
        Node b = a.rightNode;
        Node c = b.leftNode;
        Node z = b.rightNode;
        Node x = c.leftNode;
        //noinspection SuspiciousNameCombination
        Node y = c.rightNode;
        c.leftNode = a;
        c.rightNode = b;
        a.leftNode = w;
        a.rightNode = x;
        //noinspection SuspiciousNameCombination
        b.leftNode = y;
        b.rightNode = z;
        tree[1] = c;
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
        makeLeftRotation(tree);

        out.println(n);

        for (int i = 0; i < n; i++) {
            Node node = tree[i + 1];
            out.println(node.key + " " + node.leftChildIndex + " " + node.rightChildIndex);
        }

        in.close();
        out.close();
    }

    static class Node {

        int key;
        int height;
        Node leftNode;
        Node rightNode;
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
}