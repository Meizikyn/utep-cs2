import java.util.Arrays;
import java.util.Vector;
import java.util.Stack;
public class Expression {
    private Stack<String> postfix;
    public Expression(String infix) {
        String _infix = "";
        Stack<String> stack = new Stack<>();
        this.postfix = new Stack<>();
        for (String s : infix.split("\\s"))
            _infix = _infix.concat(s);
        Vector<String> exp = new Vector<String>(Arrays.asList(_infix.split("((?<=\\+)|(?=\\+)|(?<=\\/)|(?=\\/)|"  +
                                                                           "(?<=\\*)|(?=\\*)|(?<=-)|(?=-)|"       +
                                                                           "(?<=%)|(?=%)|(?<=\\()|(?=\\()|"       +
                                                                           "(?<=\\))|(?=\\))|(?<=\\^)|(?=\\^)"    +
                                                                           ")")));
        if (exp.get(0).matches("-"))
            exp.add(0,"0");
        for (int i=1; i<exp.size(); ++i) {
            if (exp.get(i).matches("-")    &&
                (exp.get(i-1).matches("\\+") ||
                 exp.get(i-1).matches("\\(")))
                exp.add(i,"0");
        }
        try {
            for (String s : exp) {
                if (s.matches("^(\\d*\\.)?\\d+$")) {
                    this.postfix.add(s);
                    continue;
                }
                switch(s.charAt(0)) {
                case '(':
                    stack.push(s);
                    continue;
                case ')':
                    for (;!stack.isEmpty() && !stack.peek().matches("\\(");) {
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
            for(;!stack.isEmpty();)
                this.postfix.add(stack.pop());
            parse();
        } catch(Exception e) {
            System.out.printf("\n\nInvalid Expression String!\n\n");
            System.exit(1);
        }
    }
    public Expression() {}
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
        System.out.println(exp);
        throw new ArithmeticException("Invalid Operator Found!");
    }
}
