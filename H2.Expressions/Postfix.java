import java.util.Stack;
public class Postfix {

    public static void main(String [] argv) {
        Stack<String> exp = new Stack<>();
        for (String s : argv[0].split("\\s")) {
            exp.add(s);
        }
        System.out.println(parse(exp));
    }

    private static double parse(Stack exp) {
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
