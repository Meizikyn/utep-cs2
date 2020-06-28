import java.util.Arrays;
import java.util.Stack;
import java.util.Vector;
public class Convert {

    public static void main(String [] argv) {

        Stack<String> postfix = new Stack<>();;
        Stack<String> stack = new Stack<>();
        String infix = "";

        for (String s : argv[0].split("\\s"))
            infix = infix.concat(s);

        // Split by operator and preserve via. regex look-around
        Vector<String> exp = new Vector<String>(
            Arrays.asList(infix.split("((?<=\\+)|(?=\\+)|(?<=\\/)|(?=\\/)|"  +
                                      "(?<=\\*)|(?=\\*)|(?<=-)|(?=-)|"       +
                                      "(?<=%)|(?=%)|(?<=\\()|(?=\\()|"       +
                                      "(?<=\\))|(?=\\))|(?<=\\^)|(?=\\^)"    +
                                      ")")));

        // Handle possible negative at beginning
        if (exp.get(0).matches("-"))
            exp.add(0,"0");

        // Handle remaining possible negatives
        for (int i=1; i<exp.size(); ++i) {
            if (exp.get(i).matches("-")    &&
                (exp.get(i-1).matches("\\+") ||
                 exp.get(i-1).matches("\\(")))
                exp.add(i,"0");
        }

        // Begin postfix conversion
        for (String s : exp) {

            // Acquire numbers with one or less decimals
            if (s.matches("^(\\d*\\.)?\\d+$")) {
                postfix.add(s);
                continue;
            }

            switch(s.charAt(0)) {

                // Acquire sub-expressions
            case '(':
                stack.push(s);
                continue;

                // Handle sub-expressions
            case ')':
                for (;!stack.isEmpty() && !stack.peek().matches("\\(");) {
                    postfix.add(stack.pop());
                }
                stack.pop();
                continue;

                // Handle operators
            default:
                for (;!stack.isEmpty() && order(s) <= order(stack.peek());)
                    postfix.add(stack.pop());
                stack.push(s);
                continue;
            }
        }

        // Pull remaining higher order operators
        for(;!stack.isEmpty();)
            postfix.add(stack.pop());

        String output = "";
        for (int i = 0; i<postfix.size(); ++i)
            output += postfix.get(i) + " ";
        System.out.println(output);
    }

    private static int order(String s) {
        char c = s.charAt(0);
        switch(c) {
        case '^':
            return 2;
        case '*':
        case '/':
        case '%':
            return 1;
        case '+':
        case '-':
            return 0;
        }
        return -1;
    }
}
