import java.io.*;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        int count = Integer.parseInt(reader.readLine());

        while (count-- > 0) {
            String inputString = reader.readLine() + "\n";
            Stack<Character> charStack = new Stack<>();

            for (char ch: inputString.toCharArray()) {
                if (ch == ' ' || ch == '\n' ){
                    while(!charStack.isEmpty()) {
                        writer.write(charStack.pop());
                    }
                    writer.write(ch);
                } else {
                    charStack.push(ch);
                }
            }
        }
        writer.flush();
    }
}
