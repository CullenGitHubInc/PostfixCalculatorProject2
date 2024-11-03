import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class PostfixCalculator {

    public int evaluatePostfix(String postfixExpression) {
        Stack<Integer> stack = new Stack<>();

        // breaking down the postfix expression (split by spaces) into operands and operators so they process seperately 
        String[] tokens = postfixExpression.split(" ");

        for (String token : tokens) {
            // Check to see whether the token is an integer
            if (token.matches("-?\\d+")) {  // this shows there is match for both pos and neg integers 
                int num = Integer.parseInt(token);
                stack.push(num);
                System.out.println("Pushed number: " + num);
            }
            // this checks whether the token is a valid operator or not 
            else if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/") || token.equals("%")) {
                if (stack.size() < 2) {
                    System.out.println("Error: Not enough operands for operation '" + token + "'");
                    return Integer.MIN_VALUE;
                }
                int b = stack.pop();
                int a = stack.pop();
                int result = 0;

                switch (token) {
                    case "+": result = a + b; break;
                    case "-": result = a - b; break;
                    case "*": result = a * b; break;
                    case "/":
                        if (b == 0) {
                            System.out.println("Error: Division by zero");
                            return Integer.MIN_VALUE;
                        }
                        result = a / b;
                        break;
                    case "%": result = a % b; break;
                }
                stack.push(result);
                System.out.println("Applied operator " + token + ": Result = " + result);
            } else {
                System.out.println("Error: Invalid token '" + token + "'");
                return Integer.MIN_VALUE;
            }
        }

        if (stack.size() != 1) {
            System.out.println("Error: Invalid postfix expression - multiple items left in stack.");
            return Integer.MIN_VALUE;
        }

        return stack.pop(); // returning the final result
    }

    public void evaluateFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String expression;
            while ((expression = reader.readLine()) != null) {
                System.out.println("Expression: " + expression);
                int result = evaluatePostfix(expression);
                if (result != Integer.MIN_VALUE) {
                    System.out.println("Result: " + result);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        PostfixCalculator calculator = new PostfixCalculator();

        // these are example test runs of digit operands, using some from module instructions
        System.out.println("Result 1: " + calculator.evaluatePostfix("4 2 * 3 +"));
        System.out.println("Result 2: " + calculator.evaluatePostfix("5 3 + 7 *"));
        System.out.println("Result 3: " + calculator.evaluatePostfix("8 4 - 6 *"));

        // trying to evaluate expressions from a file 
        calculator.evaluateFromFile("expressions.txt");
    }
}

