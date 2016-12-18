package Lab04.D;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * @author vladislav.trofimov@emc.com
 */
public class brackets {

    private static final String INPUT_FILE_NAME = "brackets.in";
    private static final String OUTPUT_FILE_NAME = "brackets.out";

    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader(INPUT_FILE_NAME));

        PrintWriter out = new PrintWriter(OUTPUT_FILE_NAME);

        while (in.ready()) {

            String sequence = in.readLine();

            boolean flag = true;

            Stack<Character> stack = new Stack<>();

            for (char c : sequence.toCharArray()) {

                if (c == '(' || c == '[') {
                    stack.push(c);
                } else if (c == ')' || c == ']') {
                    try {

                        char pair = stack.pop();

                        if (c == ')' && pair != '(' || c == ']' && pair != '[') {
                            throw new NoSuchElementException();
                        }


                    } catch (NoSuchElementException e) {
                        flag = false;
                        break;
                    }
                }
            }


            if (stack.isEmpty() && flag) {
                out.println("YES");
            } else {
                out.println("NO");
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