package Lab04.B;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
public class stack {

    private static final String INPUT_FILE_NAME = "stack.in";
    private static final String OUTPUT_FILE_NAME = "stack.out";

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        StringTokenizer stringTokenizer = new StringTokenizer(in.readLine());

        int n = Integer.parseInt(stringTokenizer.nextToken());

        Stack<String> stack = new Stack<>();

        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);

        for (int i = 0; i < n; i++) {

            StringTokenizer st = new StringTokenizer(in.readLine());
            String operation = st.nextToken();

            switch (operation) {
                case "+":
                    stack.push(st.nextToken());
                    break;
                case "-":
                    out.println(stack.pop());
                    break;
                default:
                    out.println("Ololo, bad test");
                    out.close();
                    System.exit(0);
            }

        }

        in.close();
        out.close();
    }

    static class Stack<T> {

        LinkedList<T> data;

        public Stack() {
            data = new LinkedList<>();
        }

        public void push(T value) {
            data.addLast(value);
        }

        public T pop() {
            return data.removeLast();
        }

        public boolean isEmpty() {
            return data.isEmpty();
        }

    }
}