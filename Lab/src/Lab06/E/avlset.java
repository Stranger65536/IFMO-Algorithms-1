package Lab06.E;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
public class avlset {

    private static final String INPUT_FILE_NAME = "avlset.in";
    private static final String OUTPUT_FILE_NAME = "avlset.out";

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);

        int n = Integer.parseInt(in.readLine());
        Node treeRoot = null;

        for (int i = 0; i < n; i++) {

            StringTokenizer st = new StringTokenizer(in.readLine());
            String command = st.nextToken();
            int value = Integer.parseInt(st.nextToken());

            switch (command) {
                case "A":
                    if (!containsKey(treeRoot, value)) {
                        treeRoot = addKey(treeRoot, value);
                    }
                    out.println(getBalance(treeRoot));
                    break;
                case "D":
                    if (containsKey(treeRoot, value)) {
                        treeRoot = deleteKey(treeRoot, value);
                    }
                    out.println(treeRoot == null ? "0" : getBalance(treeRoot));
                    break;
                case "C":
                    out.println(containsKey(treeRoot, value) ? "Y" : "N");
                    break;
            }

        }

        in.close();
        out.close();
    }

    private static Node makeRightRotation(Node p) {
        Node q = p.leftNode;
        p.leftNode = q.rightNode;
        q.rightNode = p;

        fixHeight(p);
        fixHeight(q);

        return q;
    }

    private static Node makeLeftRotation(Node q) {
        Node p = q.rightNode;
        q.rightNode = p.leftNode;
        p.leftNode = q;

        fixHeight(q);
        fixHeight(p);

        return p;
    }

    private static int getHeight(Node root) {
        return (root != null) ? root.height : 0;
    }

    private static int getBalance(Node root) {
        return getHeight(root.rightNode) - getHeight(root.leftNode);
    }

    private static void fixHeight(Node root) {
        int heightLeft = getHeight(root.leftNode);
        int heightRight = getHeight(root.rightNode);
        root.height = (heightLeft > heightRight ? heightLeft : heightRight) + 1;
    }

    private static Node balance(Node root) {

        fixHeight(root);

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
        } else if (key > root.key) {
            root.rightNode = addKey(root.rightNode, key);
        }

        return balance(root);
    }

    private static boolean containsKey(Node root, int key) {

        if (root == null) {
            return false;
        } else if (root.key == key) {
            return true;
        } else if (key < root.key) {
            return containsKey(root.leftNode, key);
        } else {
            return containsKey(root.rightNode, key);
        }

    }

    private static int getTheMostRightChild(Node root) {
        while (root.rightNode != null) {
            root = root.rightNode;
        }
        return root.key;
    }

    private static Node deleteKey(Node root, int key) {

        if (root == null) {
            return null;
        }

        if (root.key == key) {

            if (root.leftNode == null && root.rightNode == null) {
                return null;
            }

            if (root.rightNode == null) {
                return root.leftNode;
            }

            if (root.leftNode == null) {
                return root.rightNode;
            }

            root.key = getTheMostRightChild(root.leftNode);
            root.leftNode = deleteKey(root.leftNode, root.key);
            return balance(root);

        }

        if (root.key > key) {
            root.leftNode = deleteKey(root.leftNode, key);
            return balance(root);
        }

        if (root.key < key) {
            root.rightNode = deleteKey(root.rightNode, key);
            return balance(root);
        }

        return balance(root);
    }

    static class Node {
        int key;
        int height;
        Node leftNode;
        Node rightNode;

        Node(int key) {
            this.key = key;
            this.height = 1;
        }
    }

}