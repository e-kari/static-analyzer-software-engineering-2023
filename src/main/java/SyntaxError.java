import java.util.Stack;

public class SyntaxError {

    private String code;
    private String warnings = ""; // build warning to send back to GUI

    public SyntaxError(String code) {
        this.code = code; // code passed from GUI textbox
    }

    public String performSyntaxCheck() {
        System.out.println(code); // for debuggin purposes
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
                            warnings += "Unbalanced " + c + " at line " + startLine + "\n"; // report error line #
                        }
                        break;

                    case '}':
                        check = stack.pop();
                        startLine = lineNumbers.pop();
                        if (check == '(' || check == '[') { // should have been { for no error
                            warnings += "Unbalanced " + c + " at line " + startLine + "\n"; // report error line #
                        }
                        break;

                    case ']':
                        check = stack.pop();
                        startLine = lineNumbers.pop();
                        if (check == '(' || check == '{') { // should have been [ for no error
                            warnings += "Unbalanced " + c + " at line " + startLine + "\n"; // report error line #
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

        for (String line : lines) {
            // account for the line numbers that were applied and look for line that are just comments only //
            if (line.matches("^\\d+\\s*//.*") || line.trim().startsWith("//")
                    || line.trim().startsWith("/*") || line.trim().endsWith("*/")) {
                lineNum++;
                continue;
            }

            // Skip lines with only line numbers; that is they are just blank lines
            if (!line.matches("^\\d+\\s*$")) {
                if (!line.trim().endsWith(";") && !line.trim().endsWith("{") && !line.trim().endsWith("}")) {
                    // line didn't end with any of the above so we may assume a missing semicolon
                    // add this to our warnings report with the line num
                    warnings += "Missing semicolon at line " + lineNum + "\n";
                }
            }

            lineNum++;
        }
    }



}
