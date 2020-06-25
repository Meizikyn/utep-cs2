/*

For your lab 3 modify the evaluate method to return the result of the given math expression. 

*/


import java.util.EmptyStackException;
  
public class Main 
{ 
    public static int evaluate(String expression) 
    { 
        char[] tokens = expression.toCharArray(); 
  
        Stack_int values = new Stack_int(); 
  
        Stack_char ops = new Stack_char(); 


        for (int i = 0; i < tokens.length; i++) 
        { 

        // Implement your code here.
        /* Algorithm
          if the tokens[i] == '[' or '{' or '('
            then push in ops
          if the tokens[i] == '+' or '-' or '*' or '/'
            then check if ops.peek() is higher precedence?
            for example if tokens[i] = '-' and ops.peek() = '/'
            then you need to complete the operation '/' first then push the tokens[i]

          if the tokens[i] == ')' or '}' or ']'
            then pop two numbers from stack_int, pop from ops and do the operation and push the result into stack_int

          at the end when ops become empty return the stack_int peek(). That is your result
          
        */

          System.out.println(tokens[i]);
        
        } 

  

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