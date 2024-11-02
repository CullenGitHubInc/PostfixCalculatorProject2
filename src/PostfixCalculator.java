import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class PostfixCalculator {

    public int evaluatePostfix(String postfixExpression) {
        Stack<Integer> stack = new Stack<>();

        // Split by spaces to correctly parse each operand/operator
        String[] tokens = postfixExpression.split(" ");

        for (String token : tokens) {
            // Check if the token is an integer
            if (token.matches("-?\\d+")) {  // Matches both positive and negative integers
                int num = Integer.parseInt(token);
                stack.push(num);
                System.out.println("Pushed number: " + num);
            }
            // Check if the token is a valid operator
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

        return stack.pop(); // Return the final result
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

        // Example test cases
        System.out.println("Result 1: " + calculator.evaluatePostfix("4 2 * 3 +"));
        System.out.println("Result 2: " + calculator.evaluatePostfix("5 3 + 7 *"));
        System.out.println("Result 3: " + calculator.evaluatePostfix("8 4 - 6 *"));

        // Optionally, evaluate expressions from a file
        calculator.evaluateFromFile("expressions.txt");
    }
}

