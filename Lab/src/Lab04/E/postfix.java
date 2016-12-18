package Lab04.E;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * @author vladislav.trofimov@emc.com
 */
public class postfix {

    private static final String INPUT_FILE_NAME = "postfix.in";
    private static final String OUTPUT_FILE_NAME = "postfix.out";

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(INPUT_FILE_NAME));

        Stack<Integer> stack = new Stack<>();

        StringTokenizer st = new StringTokenizer(in.readLine());

        while (st.hasMoreTokens()) {

            char c = st.nextToken().charAt(0);

            if (c != '+' && c != '-' && c != '*') {

                int number = (int) c - (int) '0';
                stack.push(number);

            } else if (c == '+') {

                int first = stack.pop();
                int second = stack.pop();
                int res = first + second;
                stack.push(res);

            } else if (c == '-') {

                int second = stack.pop();
                int first = stack.pop();
                int res = first - second;
                stack.push(res);

            } else {

                int first = stack.pop();
                int second = stack.pop();
                int res = first * second;
                stack.push(res);

            }
        }

        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);

        out.println(stack.pop());

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
