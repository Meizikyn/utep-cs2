import java.util.Arrays;
import java.util.Vector;
import java.util.Stack;
public class Expression {

    private Stack<String> postfix;    // Storage for postfix conversion

    public Expression(String infix) {

        // Some boilerplate to remove whitespace

        String _infix = "";                     // Empty String to append to
        Stack<String> stack = new Stack<>();    // Stack to hold temporary operators and parenthesis
        this.postfix = new Stack<>();           // Initialize postfix stack
        for (String s : infix.split("\\s"))     // Loop over whitespace separated string array
            _infix = _infix.concat(s);          // Rebuild new infix expression string without whitespace

        // Use regex to split "around" special operators and delimiters, preserving them in place,
        // makes processing algorithm simpler. Vector makes tricking postfix for negative numbers easier.
        Vector<String> exp = new Vector<String>(Arrays.asList(_infix.split("((?<=\\+)|(?=\\+)|(?<=\\/)|(?=\\/)|"  +
                                                                           "(?<=\\*)|(?=\\*)|(?<=-)|(?=-)|"       +
                                                                           "(?<=%)|(?=%)|(?<=\\()|(?=\\()|"       +
                                                                           "(?<=\\))|(?=\\))|(?<=\\^)|(?=\\^)"    +
                                                                           ")")));

        // Compensate for possible negative number at the beginning of the expression
        if (exp.get(0).matches("-"))
            exp.add(0,"0");

        // Compensate for all other negative numbers, close alignment to how Java actually evaluates expressions internally
        for (int i=1; i<exp.size(); ++i) {
            if (exp.get(i).matches("-")    &&   // Match negative operator
                (exp.get(i-1).matches("\\+") || // But only if it is preceded by a + operator
                 exp.get(i-1).matches("\\(")))  // or at the beginning of a sub-expression
                exp.add(i,"0");                 // Place a zero, this lazily enables for "subtract from 0" evaluation so no further
                                                // work is needed from here
        }
        try {
            for (String s : exp) {
                if (s.matches("^(\\d*\\.)?\\d+$")) { // Match numbers with decimals, but only up to one decimal, any more should throw a stack error later
                    this.postfix.add(s);
                    continue;
                }
                switch(s.charAt(0)) {
                case '(':
                    stack.push(s);       // Handle sub-expressions
                    continue;
                case ')':                // Once the sub-expr ends, pull all the operators found
                    for (;!stack.isEmpty() && !stack.peek().matches("\\(");) {
                        this.postfix.add(stack.pop());
                    }
                    stack.pop();         // Toss the final parenthesis, it isn't needed once in postfix
                    continue;
                default:                 // Operators check order of operations against one another and add themselves relative to that order
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

    @SuppressWarnings("unchecked")  // Ignore clone return type cast, since typecasts are runtime warnings
    public double parse() {
        Stack<String> exp = (Stack<String>)this.postfix.clone(); // Prevent mucking the original postfix
        return parse(exp);                                       // Begin recursive call
    }

    private double parse(Stack exp) {
        double v; // Storage for the "right hand side" of the equation at each stage
        String s = (String)exp.pop();       // cast required since initial clone returns an object container of more objects
        if (s.matches("(\\d*\\.)?\\d+$")) { // check for numbers with one or zero decimals
            return Double.parseDouble(s);   // Return any numbers found
        }                                   // otherwise..

        v = parse(exp);                      // Capture "right hand side" evaluation
        switch(s.charAt(0)) {
        case '^':                            // For each operator, run the CPU stack for cached sub-expressions based on order method
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
        System.out.println(exp);                                   // Print operator error for debugging
        throw new ArithmeticException("Invalid Operator Found!");
    }
}
