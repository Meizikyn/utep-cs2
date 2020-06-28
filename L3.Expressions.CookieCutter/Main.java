// + NAME :: Nicholas Sims
// + ID   :: 80713446

/*

  Intention: Expression String --> Postfix Representation --> small postfix parser to get value.

  Calculating expressions by infix is very error prone and messy, postfix makes expression parsing
   tiny affair and allows the use of the natural stack-like behavior of recursion to evaluate.

  Expression strings given in main do not include negatives, no support for negatives needed.

  Third expression string has incorrect brackets, will need a small matching function to check
  for proper brackets.

  Create a small order of operations jump table to get precedence.

  Gut whitespace from the expression string and split it into an array to make postfix conversion simple.

  Walk through the infix expression array, push all operands to the postfix stack, push all higher order
  operators to the stack, and branch if lower order operators or sub-expressions are encountered.

  Sub-expressions, and brackets, need not be accounted for in order since bracket matching occurs first.

  Postfix representation doesn't need brackets.

  Parse postfix recursively, cache right hand value and recursively acquire sub expressions for the left.

*/

import java.util.EmptyStackException;
public class Main
{
    public static int evaluate(String expression)
    {

        // Acquire brackets from expression
        String brackets = expression.replaceAll("[^\\(\\[\\{\\)\\]\\}]+","");


        // Determine if brackets are a match, makes checking them without ordering
        // a simple affair during postfix conversion.
        if (!match(brackets)) {
            System.out.printf("![E] -- %s\n", "Invalid Expression, Brackets do not match.");
            return 0;
        }

        // Prepare an evaluation string slice
        String exp = "";

        // Slice out whitespace characters.
        for (String s : expression.split("\\s"))
            exp = exp.concat(s);

        // Prepare a postfix representation of the expression.
        // A modified class was made as a char stack is insufficient
        // to properly handle integers of more than a single digit.
        Stack_string postfix = new Stack_string();

        // Prepare a temporary stack to hold operators
        Stack_string ops = new Stack_string();

        // Prepare an array representation of the expression
        String [] infix;
        infix = exp.split("((?<=\\+)|(?=\\+)|(?<=\\/)|(?=\\/)|"  +
                          "(?<=\\*)|(?=\\*)|(?<=-)|(?=-)|"       +
                          "(?<=%)|(?=%)|(?<=\\()|(?=\\()|"       +
                          "(?<=\\))|(?=\\))|(?<=\\^)|(?=\\^)|"   +
                          "(?<=\\[)|(?=\\[)|(?<=\\{)|(?=\\{)|"   +
                          "(?<=\\])|(?=\\])|(?<=\\})|(?=\\})"    +
                          ")");

        // Begin converting from infix to postfix
        for (String s : infix) {

            // Determine if next unit is a number
            if (s.matches("^(\\d*)$")) {

                // If so, push to postfix and reset the loop
                postfix.push(s);
                continue;
            }

            switch(s.charAt(0)) {

                // Otherwise, check for any valid sub-expressions
            case '(':
            case '[':
            case '{':
                ops.push(s);
                continue;

                // If closing a sub-expression, pull from the stack of operators
                // until it clears, and eliminate the closing brace
            case ')':
            case ']':
            case '}':
                for (;!ops.isEmpty() && !ops.peek().matches("\\(|\\[|\\{");) {
                    postfix.push(ops.pop());
                }
                ops.pop();
                continue;

                // Otherwise, the next unit is an operator, check it's precedence
            default:
                for (;!ops.isEmpty() && order(s) <= order(ops.peek());)
                    postfix.push(ops.pop());
                ops.push(s);
                continue;
            }
        }

        // Acquire remaining operators
        for(;!ops.isEmpty();)
            postfix.push(ops.pop());

        // Return a parsed value, a recursion is used to make the postfix
        // evaluation code considerably simpler to read of a loop-based
        // solution, so an entry point is called here.

        // The value is cast to int as to not modify the (evaluate)
        try {
            return (int)parse(postfix);
        } catch(Exception e) {
            System.out.printf("![E] -- %s\n", "Invalid Expression.");
            return 0;
        }
    }


    // Match brackets, if all brackets match then checking against their match
    // later becomes irrelevant, reducing the infix--postfix conversion.
    private static int match(String s, int i, char t) throws Exception {

        // Begin checking against a target bracket $t
        while (s.charAt(i) != t) {

            // In the case of each bracket, follow the call graph
            // until a taget is reached
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

        // If a target is reached proper, return one unit from the call graph
        // and advance the search index to continue searching from the most recent
        // targeted bracket.
        return i+1;
    }

    // Entry point into match implementation
    private static boolean match(String s) {

        // If any exception is reached, it is safe to assume that
        // a mismatch occurred.
        try {

            // In each case, establish the first target and begin the search
            // by advancing the call graph and search index.
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

    // Determine the precedence of a given operator
    private static int order(String s) {
        switch(s.charAt(0)) {
        case '*':
        case '/':
            return 1;
        case '+':
        case '-':
            return 0;
        }
        return -1;
    }


    // This method was created to evaluate the generated postfix recursively
    // as it makes the resulting code easier to read.

    // (parse) cannot exist as an <int> return type, as sub-expressions may
    // result in decimal values during evaluation. To make this an int will
    // cause the pre-fabricated expression strings called in main return
    // the incorrect values since ( #14 / #5 == #2.8 ) and the lossy conversion results
    // in a #2, making the final value after complete evaluation incorrect.

    // to solve this, doubles are used internally and the entry-point call
    // is cast back as an integer.
    private static double parse(Stack_string exp) throws ArithmeticException {

        // v is used as the "right hand side" in each sub-expression
        double v;

        // Acquire the next element of the expression
        String s = exp.pop();

        // If element is a valid number, with or without decimals, return.
        if (s.matches("(\\d*\\.)?\\d+$")) {
            return Double.parseDouble(s);
        }

        // Cache the "right hand side"
        v = parse(exp);

        // Determine the operator a call for next operand that may
        // result in an evaluation of the call's sub-expression.
        switch(s.charAt(0)) {
        case '*':
            return parse(exp) * v;
        case '/':
            return parse(exp) / v;
        case '+':
            return parse(exp) + v;
        case '-':
            return parse(exp) - v;
        }

        // An invalid operator was found.
        throw new ArithmeticException("Invalid Operator Found!");
    }

    public static void main(String[] args)
    {
      //
        String str1 = "(10 + 2) * 6";
        String str2 = "[100 * {( 2 + 12 )/5}] / 14";
        String str3 = "[100 * {( 2 + 12 }/5)] / 14";
        //String str4 = "(100 *  2 + 12 )) / 14";

        System.out.println(Main.evaluate(str1));
        System.out.println(Main.evaluate(str2));
        System.out.println(Main.evaluate(str3));
    }
}
