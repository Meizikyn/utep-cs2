import java.util.Arrays;
import java.util.Vector;
import java.util.Stack;
public class Expression {
    private Stack<String> postfix;
    public Expression(String infix) {
        String _infix = "";
        Stack<String> stack = new Stack<>();
        String brackets = infix.replaceAll("[^\\(\\[\\{\\)\\]\\}]+","");
        this.postfix = new Stack<>();
        for (String s : infix.split("\\s"))
            _infix = _infix.concat(s);
        Vector<String> exp = new Vector<String>(Arrays.asList(_infix.split("((?<=\\+)|(?=\\+)|(?<=\\/)|(?=\\/)|"  +
                                                                           "(?<=\\*)|(?=\\*)|(?<=-)|(?=-)|"       +
                                                                           "(?<=%)|(?=%)|(?<=\\()|(?=\\()|"       +
                                                                           "(?<=\\))|(?=\\))|(?<=\\^)|(?=\\^)|"   +
                                                                           "(?<=\\[)|(?=\\[)|(?<=\\{)|(?=\\{)|"   +
                                                                           "(?<=\\])|(?=\\])|(?<=\\})|(?=\\})"  +
                                                                           ")")));
        if (exp.get(0).matches("-"))
            exp.add(0,"0");
        for (int i=1; i<exp.size(); ++i) {
            if (exp.get(i).matches("-")    &&
                (exp.get(i-1).matches("\\+") ||
                 exp.get(i-1).matches("\\(") ||
                 exp.get(i-1).matches("\\[") ||
                 exp.get(i-1).matches("\\{")
                 ))
                exp.add(i,"0");
        }
        try {
            if (!match(brackets))
                throw new ArithmeticException("Brackets Invalid!");
            for (String s : exp) {
                if (s.matches("^(\\d*\\.)?\\d+$")) {
                    this.postfix.add(s);
                    continue;
                }
                switch(s.charAt(0)) {
                case '(':
                case '[':
                case '{':
                    stack.push(s);
                    continue;
                case ')':
                case ']':
                case '}':
                    for (;!stack.isEmpty() && !stack.peek().matches("\\(|\\[|\\{");) {
                        this.postfix.add(stack.pop());
                    }
                    stack.pop();
                    continue;
                default:
                    for (;!stack.isEmpty() && order(s) <= order(stack.peek());)
                        this.postfix.add(stack.pop());
                    stack.push(s);
                    continue;
                }
            }

            // Pull the rest of the higher order operators left behind
            for(;!stack.isEmpty();)
                this.postfix.add(stack.pop());

            // Check if the expression is valid and toss the value
            parse();

        } catch(Exception e) {                                        // Expression wasn't valid java syntax
            System.out.printf("\n\nInvalid Expression String!\n\n");
            System.exit(1);
        }
    }

    private boolean match(String s) {
        try {
            switch(s.charAt(0)) {
            case '(':
                match(s,1,')');
                break;
            case '[':
                match(s,1,']');
                break;
            case '{':
                match(s,1,'}');
                break;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static int match(String s, int i, char t) throws Exception {
        while (s.charAt(i) != t) {
            switch(s.charAt(i)) {
            case '(':
                i = match(s,i+1,')');
                continue;
            case '[':
                i = match(s,i+1,']');
                continue;
            case '{':
                i = match(s,i+1,'}');
                continue;
            default:
                throw new Exception();
            }
        }
        return i+1;
    }

    // Check the order of operations
    private int order(String s) {
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

    @SuppressWarnings("unchecked")
    public double parse() {
        Stack<String> exp = (Stack<String>)this.postfix.clone();
        return parse(exp);
    }

    private double parse(Stack exp) {
        double v;
        String s = (String)exp.pop();
        if (s.matches("(\\d*\\.)?\\d+$")) {
            return Double.parseDouble(s);
        }

        v = parse(exp);
        switch(s.charAt(0)) {
        case '^':
            return Math.pow(parse(exp), v);
        case '*':
            return parse(exp) * v;
        case '/':
            return parse(exp) / v;
        case '+':
            return parse(exp) + v;
        case '-':
            return parse(exp) - v;
        case '%':
            return parse(exp) % v;
        }
        throw new ArithmeticException("Invalid Operator Found!");
    }
}
