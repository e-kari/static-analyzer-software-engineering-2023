import java.util.Stack;

public class SyntaxError {

    private String code = "";
    private String warnings = "";

    public SyntaxError(String code) {
        this.code = code;
    }

    public String performSyntaxCheck() {
        System.out.println(code);
        checkForUnbalancedSymbols();
        checkForMissingSemi();
        return warnings;
    }

    private void checkForUnbalancedSymbols() {
        Stack<Character> stack = new Stack<>();
        Stack<Integer> lineNumbers = new Stack<>();
        int lineNum = 1;

        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);
            if (c == '\n') {
                lineNum++;
                continue;
            }

            if (c == '{' || c == '(' || c == '[') {
                stack.push(c);
                lineNumbers.push(lineNum);
                continue;
            }

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
                if (c == ')' || c == '}' || c == ']') {
                    warnings += "Unbalanced " + c + " at line " + lineNum + "\n";
                }
            }
        }

        while (!stack.isEmpty()) {
            char remaining = stack.pop();
            int startLine = lineNumbers.pop();
            warnings += "Unclosed " + remaining + " at line " + startLine + "\n";
        }
    }

    private void checkForMissingSemi() {
        int lineNum = 1;
        String[] lines = code.split("\n");

        for (String line : lines) {
            // account for line numbers and look for comment lines //
            if (line.matches("^\\d+\\s*//.*")) {
                lineNum++;
                continue;
            }

            // Skip lines with only line numbers (no code)
            if (!line.matches("^\\d+\\s*$")) {
                if (!line.trim().endsWith(";") && !line.trim().endsWith("{") && !line.trim().endsWith("}")) {
                    warnings += "Missing semicolon at line " + lineNum + "\n";
                }
            }

            lineNum++;
        }
    }



}
