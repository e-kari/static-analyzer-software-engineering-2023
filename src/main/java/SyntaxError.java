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

    private void checkForUnbalancedSymbols(){
        

    }
    private void checkForMissingSemi(){
        //warnings += "Line 3 missing semi colon";
    }

}
