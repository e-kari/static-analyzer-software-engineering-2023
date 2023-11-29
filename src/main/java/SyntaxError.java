import java.util.Stack;

public class SyntaxError {

    private String code = "";
    private String warnings = "";

    public SyntaxError(String code) {
        this.code = code;
    }

    public String performSyntaxCheck(){
        System.out.println(code);
        checkForUnbalancedSymbols();
        checkForMissingSemi();
        return warnings;
    }

    private void checkForUnbalancedSymbols() {
        Stack<Character> stack = new Stack<>();
        Stack<Integer> lineNumbers = new Stack<>(); // to store line numbers for pushed symbols
        int lineNum = 1;

        // loop through string of code
        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);
            if (c == '\n') {
                lineNum++;
                continue;
            }

            // if char is one of these symbols, push to stack along with the line number
            if (c == '{' || c == '(' || c == '[') {
                stack.push(c);
                lineNumbers.push(lineNum);
                continue;
            }

            // check if the stack is empty before popping
            if (!stack.isEmpty()) {
                char check;
                switch (c) {
                    case ')':
                        check = stack.pop();
                        int startLine = lineNumbers.pop();
                        if (check == '{' || check == '[') {
                            warnings += "Unbalanced " + c + " at line " + startLine + "\n";
                        }
                        break;

                    case '}':
                        check = stack.pop();
                        startLine = lineNumbers.pop();
                        if (check == '(' || check == '[') {
                            warnings += "Unbalanced " + c + " at line " + startLine + "\n";
                        }
                        break;

                    case ']':
                        check = stack.pop();
                        startLine = lineNumbers.pop();
                        if (check == '(' || check == '{') {
                            warnings += "Unbalanced " + c + " at line " + startLine + "\n";
                        }
                        break;
                }
            } else {
                // If the stack is empty and a closing symbol is encountered, report an error
                if (c == ')' || c == '}' || c == ']') {
                    warnings += "Unbalanced " + c + " at line " + lineNum + "\n";
                }
            }
        }

        // Check for remaining unclosed symbols
        while (!stack.isEmpty()) {
            char remaining = stack.pop();
            int startLine = lineNumbers.pop();
            warnings += "Unclosed " + remaining + " at line " + startLine + "\n";
        }
    }


    private void checkForMissingSemi(){
        //warnings += "Line 3 missing semi colon";
    }

}
