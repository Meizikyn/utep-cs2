public class Main {
    public static void main(String [] argv) {


        // Change this to alter the expression.

        // Support operations are [ + - / * ^ % ]

        String expression_string_1 = "-2 + 16 - (10 - 6)^(4.5-2.5) + 8";

        String expression_string_2 = "(-2 + 16 - (10 - 6)^(4.5-2.5) + 8) * 2 % 5 + -2";

        String expression_string_3 = "-2 + 16 - (10 - 6)^(4.5-2.5) + 8";

        String expression_string = expression_string_2;

        Expression expression = new Expression(expression_string);

        double results = expression.parse();

        System.out.printf("%s = %f\n", expression_string, results);
    }
}
