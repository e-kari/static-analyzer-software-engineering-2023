public class Errors {
    
    // features that describe an error. errors should list its type, a message and the line number
    private ErrorType errorType;
    private String errorMessage;
    private int lineNumber;

    // create enum types for the type of errors which are: syntax (brackets, ;) & long/verbose methods:
    public enum ErrorType {
        SYNTAX_ERROR,
        LONG_METHOD
    }

    // constructor
    public Errors(ErrorType errorType, String errorMessage, int lineNumber) {
        this.errorType = errorType;
        this.errorMessage = errorMessage;
        this.lineNumber = lineNumber;
    }

    // getter methods
    public ErrorType getErrorType() {
        return this.errorType;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    // setter methods
    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
}
