// NAME :: Nicholas Sims
// ID   :: 80713446


/*

Convert infix expression to postfix expression, making recursive sub-expression evaluation trivial.

Infix expression strings might have whitespace, so trim it.

Use regex matching liberally to simplify matching algorithms.

Will probably also have negatives, so anything that can be considered a valid negative needs a "0" attached to simplify evaluation:
  i.e. "-2" becomes "0-2", so no further negative checks are needed. Expression array should probably be a vector since it will
  need to grow in size to compensate for the difference between numbers and operators.

Once in valid postfix, recursively jump through the postfix stack until depleted, Simple jump table can handle operations.

Return the evaluated postfix expression.

*/

public class Main {
    public static void main(String [] argv) {


        // Change this to alter the expression.

        // Support operations are [ + - / * ^ % ]

        String expression_string_1 = "-2 + 16 - (10 - 6)^(4.5-2.5) + 8";

        String expression_string_2 = "(-2 + 16 - (10 - 6)^(4.5-2.5) + 8) * 2 % 5 + -2";

        String expression_string = expression_string_2;

        Expression expression = new Expression(expression_string);

        double results = expression.parse();

        System.out.printf("%s = %f\n", expression_string, results);
    }
}
