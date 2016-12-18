package Lab06.C;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
public class addition {

    private static final String INPUT_FILE_NAME = "addition.in";
    private static final String OUTPUT_FILE_NAME = "addition.out";

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);

        int n = Integer.parseInt(in.readLine());

        ArrayNode[] m = new ArrayNode[n + 1];

        for (int i = 0; i < n; i++) {

            StringTokenizer st = new StringTokenizer(in.readLine());

            int key = Integer.parseInt(st.nextToken());
            int leftChildIndex = Integer.parseInt(st.nextToken());
            int rightChildIndex = Integer.parseInt(st.nextToken());

            m[i + 1] = new ArrayNode(key, leftChildIndex, rightChildIndex);
        }

        int x = Integer.parseInt(in.readLine());

        if (n == 0) {
            out.println("1");
            out.println(x + " 0 0");
        } else {

            Node tree = buildTree(m, 1);
            tree = addKey(tree, x);

            out.println(n + 1);

            rebuildIndex(tree);
            printTree(tree, out);
        }

        in.close();
        out.close();
    }

    private static Node makeRightRotation(Node p) {
        Node q = p.leftNode;
        p.leftNode = q.rightNode;
        q.rightNode = p;

        return q;
    }

    private static Node makeLeftRotation(Node q) {
        Node p = q.rightNode;
        q.rightNode = p.leftNode;
        p.leftNode = q;

        return p;
    }

    private static int getTreeHeight(Node root, int height) {
        if (root == null) {
            return height;
        } else {
            return Math.max(getTreeHeight(root.leftNode, height + 1), getTreeHeight(root.rightNode, height + 1));
        }
    }

    private static int getBalance(Node root) {
        return getTreeHeight(root.rightNode, 1) - getTreeHeight(root.leftNode, 1);
    }

    private static Node balance(Node root) {

        if (getBalance(root) == 2) {
            if (getBalance(root.rightNode) < 0) {
                root.rightNode = makeRightRotation(root.rightNode);
            }
            return makeLeftRotation(root);
        }

        if (getBalance(root) == -2) {
            if (getBalance(root.leftNode) > 0) {
                root.leftNode = makeLeftRotation(root.leftNode);
            }
            return makeRightRotation(root);
        }

        return root;
    }

    private static Node addKey(Node root, int key) {

        if (root == null) {
            return new Node(key);
        }

        if (key < root.key) {
            root.leftNode = addKey(root.leftNode, key);
        } else {
            root.rightNode = addKey(root.rightNode, key);
        }

        return balance(root);
    }

    private static void rebuildIndex(Node root) {

        int n = 1;
        Queue<Node> q = new LinkedList<>();
        q.add(root);

        while (!q.isEmpty()) {

            Node node = q.remove();
            node.arrayIndex = n++;

            if (node.leftNode != null) {
                q.add(node.leftNode);
            }

            if (node.rightNode != null) {
                q.add(node.rightNode);
            }

        }
    }

    private static void printTree(Node root, PrintWriter out) {

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {

            Node node = queue.remove();

            out.print(node.key + " ");

            if (node.leftNode != null) {
                queue.add(node.leftNode);
                out.print(node.leftNode.arrayIndex + " ");
            } else {
                out.print("0 ");
            }

            if (node.rightNode != null) {
                queue.add(node.rightNode);
                out.println(node.rightNode.arrayIndex + " ");
            } else {
                out.println("0");
            }

        }
    }

    private static Node buildTree(ArrayNode[] sourceTree, int rootIndex) {

        Node root = new Node(sourceTree[rootIndex].key);

        if (sourceTree[rootIndex].leftNodeIndex != 0) {
            root.leftNode = buildTree(sourceTree, sourceTree[rootIndex].leftNodeIndex);
        }

        if (sourceTree[rootIndex].rightNodeIndex != 0) {
            root.rightNode = buildTree(sourceTree, sourceTree[rootIndex].rightNodeIndex);
        }

        return root;
    }

    static class Node {
        int key;
        int arrayIndex;
        Node leftNode;
        Node rightNode;

        Node(int key) {
            this.key = key;
        }
    }

    static class ArrayNode {
        int key;
        int leftNodeIndex;
        int rightNodeIndex;

        public ArrayNode(int key, int leftNodeIndex, int rightNodeIndex) {
            this.key = key;
            this.leftNodeIndex = leftNodeIndex;
            this.rightNodeIndex = rightNodeIndex;
        }
    }

}