import java.util.Stack;

public class SyntaxError {

    private String code;
    private String warnings = ""; // build warning to send back to GUI

    public SyntaxError(String code) {
        this.code = code; // code passed from GUI textbox
    }

    public String performSyntaxCheck() {
        System.out.println(code); // for debugging purposes
        checkForUnbalancedSymbols(); // first method looking for balanced symbols
        checkForMissingSemi(); // method for finding missing semicolons
        return warnings; // return warnings to GUI
    }

    private void checkForUnbalancedSymbols() {
        Stack<Character> stack = new Stack<>(); // for holding symbols
        Stack<Integer> lineNumbers = new Stack<>(); // keep track of line numbers for reporting
        int lineNum = 1;

        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);
            if (c == '\n') { // new line so increment line count
                lineNum++;
                continue;
            }

            if (c == '{' || c == '(' || c == '[') { // begging symbols get pushed to stack
                stack.push(c);
                lineNumbers.push(lineNum);
                continue;
            }

            if (!stack.isEmpty()) { // make sure stack is not empty
                char check;
                switch (c) {
                    case ')':
                        check = stack.pop();
                        int startLine = lineNumbers.pop();
                        if (check == '{' || check == '[') { // should have been ( for no error
                            warnings += "Unbalanced " + check + " at line " + startLine + "\n"; // report error line #
                        }
                        break;

                    case '}':
                        check = stack.pop();
                        startLine = lineNumbers.pop();
                        if (check == '(' || check == '[') { // should have been { for no error
                            warnings += "Unbalanced " + check + " at line " + startLine + "\n"; // report error line #
                        }
                        break;

                    case ']':
                        check = stack.pop();
                        startLine = lineNumbers.pop();
                        if (check == '(' || check == '{') { // should have been [ for no error
                            warnings += "Unbalanced " + check + " at line " + startLine + "\n"; // report error line #
                        }
                        break;
                }
            } else { // means we are missing the beginning half of these symbols: ( { [
                if (c == ')' || c == '}' || c == ']') {
                    warnings += "Unbalanced " + c + " at line " + lineNum + "\n";
                }
            }
        }

        while (!stack.isEmpty()) { // our stack is not empty when it should be
            char remaining = stack.pop();
            int startLine = lineNumbers.pop();
            warnings += "Unclosed " + remaining + " at line " + startLine + "\n";
        }
    }

    private void checkForMissingSemi() {
        int lineNum = 1;
        String[] lines = code.split("\n"); // go line by line

        boolean inMultiLineComment = false;

        for (String line : lines) {
            // Skip lines that are just comments or empty
            if (line.matches("^\\d+\\s*//.*") || line.trim().startsWith("//")) {
                lineNum++;
                continue;
            }
            // Check if the line contains a multi-line comment start ("/*")
            if (line.contains("/*")) {
                inMultiLineComment = true;
            }

            // Check if the line contains a multi-line comment end ("*/")
            if (line.contains("*/")) {
                inMultiLineComment = false;
                continue;
            }

            // Skip lines with only line numbers or lines within a multi-line comment
            if (!inMultiLineComment && !line.matches("^\\d+\\s*$")) {
                // Check if the line ends with a semicolon, an opening brace, or a closing brace
                if (!line.trim().endsWith(";") && !line.trim().endsWith("{") && !line.trim().endsWith("}")) {
                    // Line didn't end with any of the above, so we may assume a missing semicolon
                    // Add this to our warnings report with the line number
                    warnings += "Missing semicolon at line " + lineNum + "\n";
                }
            }

            lineNum++;
        }
    }

}
