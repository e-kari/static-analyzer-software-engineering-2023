import java.util.Stack;

public class LineCountError {

    //Coding standard per the CS 307 and 305j Coding Standards
    //Length of method should be no more than 20 lines of code. Does not include whitespace, closing braces, or comments.
    //If more than 20 lines, recommend to break method into two or more sub methods
    //Goal is to return method name and the number of lines.

    private String code = "";
    private String warnings = "";
    private int lineNumTemp;

    private boolean firstBracketCheck = true;

    public LineCountError(String code) {
        this.code = code;
    }

    public String performMethodLengthCheck() {
        System.out.println(code);
        checkMethodLength();
        return warnings;
    }

    private void checkMethodLength() {
        Stack<Character> stack = new Stack<>();
        int lineNum = 1;

        //go through every character of the code
        for (int i = 0; i < code.length(); i++) {
            char c = code.charAt(i);

            //new line case
            if (c == '\n') { // if new line
                lineNum++; // increment lineNum to match current line number

                // '{' case
            } else if ((c == '{')) {
                if(firstBracketCheck){ // ignores first bracket
                    firstBracketCheck = false;

                }else if (stack.isEmpty()) { // if the stack is empty
                    lineNumTemp = lineNum; // records start of the method in lineNumTemp
                    stack.push(c); // puts the open bracket into stack

                }else if(stack.peek() == '{'){
                   stack.push(c); //puts the open bracket into stack
                }

                // '}' case
            } else if (c == '}') { // if closed bracket
                if (!stack.isEmpty() && stack.peek() == '{') { // checks stack to see if open bracket
                    stack.pop(); // pops if open bracket
                    if (stack.isEmpty()) { //check if stack is empty after pop
                        if ((lineNum - lineNumTemp + 1) > 30) { // if more than 20 lines
                            warnings += "Method on line " + (lineNumTemp) + " has a length of " + (lineNum - lineNumTemp + 1) + " lines.\n";
                        }
                    }
                } else if (!stack.isEmpty() && stack.peek() == '}') {
                    stack.push(c);
                }
            }
        }
        if (warnings != "") { //if there are long methods, recommend refactor
            warnings += "Please consider refactoring the method(s) above.\n";
        }
    }
}
