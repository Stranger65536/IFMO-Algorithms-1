package Lab05.C;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
public class bstsimple {

    private static final String INPUT_FILE_NAME = "bstsimple.in";
    private static final String OUTPUT_FILE_NAME = "bstsimple.out";

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);

        BinarySearchTree binarySearchTree = new BinarySearchTree();

        while (in.ready()) {

            StringTokenizer st = new StringTokenizer(in.readLine());
            String command = st.nextToken();
            int value = Integer.parseInt(st.nextToken());
            int res;

            switch (command) {
                case "insert":
                    binarySearchTree.insert(value, value);
                    break;
                case "delete":
                    binarySearchTree.remove(value);
                    break;
                case "exists":
                    Node result = binarySearchTree.search(value);
                    out.println(result != null);
                    break;
                case "next":
                    res = binarySearchTree.next(value);
                    out.println(res == -1 ? "none" : res);
                    break;
                case "prev":
                    res = binarySearchTree.prev(value);
                    out.println(res == -1 ? "none" : res);
                    break;
                default:
                    out.println("lel, broken test");
                    break;
            }

        }

        in.close();
        out.close();
    }

}

class BinarySearchTree {

    private static Node root;

    private static ArrayList<Integer> traversalOrder(Node node, ArrayList<Integer> res) {

        if (node != null) {
            traversalOrder(node.leftChild, res);
            res.add(node.key);
            traversalOrder(node.rightChild, res);
        }

        return res;
    }

    Node search(Node t, int key) {

        if (t == null || t.key == key) {
            return t;
        }

        if (key < t.key) {
            return search(t.leftChild, key);
        } else {
            return search(t.rightChild, key);
        }
    }

    public Node search(int key) {
        return search(root, key);
    }

    Node insert(Node t, Node p, int key, int value) {

        if (t == null) {
            t = new Node(key, value, p);
        } else {

            if (key == t.key) {
                return t;
            }

            if (key < t.key) {
                t.leftChild = insert(t.leftChild, t, key, value);
            } else {
                t.rightChild = insert(t.rightChild, t, key, value);
            }
        }

        return t;
    }

    public void insert(int key, int value) {
        root = insert(root, null, key, value);
    }

    void replace(Node a, Node b) {

        if (a.parent == null) {
            root = b;
        } else if (a == a.parent.leftChild) {
            a.parent.leftChild = b;
        } else {
            a.parent.rightChild = b;
        }

        if (b != null) {
            b.parent = a.parent;
        }

    }

    void remove(Node t, int key) {

        if (t == null) {
            return;
        }

        if (key < t.key) {
            remove(t.leftChild, key);
        } else if (key > t.key) {
            remove(t.rightChild, key);
        } else if (t.leftChild != null && t.rightChild != null) {

            Node m = t.rightChild;

            while (m.leftChild != null) {
                m = m.leftChild;
            }

            t.key = m.key;
            t.value = m.value;
            replace(m, m.rightChild);

        } else if (t.leftChild != null) {
            replace(t, t.leftChild);
        } else if (t.rightChild != null) {
            replace(t, t.rightChild);
        } else {
            replace(t, null);
        }
    }

    public void remove(int key) {
        remove(root, key);
    }

    public int next(int key) {

        ArrayList<Integer> traversalOrderTree = traversalOrder(root, new ArrayList<Integer>(100));

        for (int i : traversalOrderTree) {
            if (i > key) {
                return i;
            }
        }

        return -1;
    }

    public int prev(int key) {

        ArrayList<Integer> traversalOrderTree = traversalOrder(root, new ArrayList<Integer>(100));
        Collections.reverse(traversalOrderTree);

        for (int i : traversalOrderTree) {
            if (i < key) {
                return i;
            }
        }

        return -1;

    }

}

class Node {

    int key;
    int value;
    Node leftChild;
    Node rightChild;
    Node parent;

    Node(int key, int value, Node parent) {
        this.key = key;
        this.value = value;
        this.parent = parent;
    }
}
