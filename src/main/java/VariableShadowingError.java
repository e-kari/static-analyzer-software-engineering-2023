import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableShadowingError {

    private boolean shadowingError = false;
    private String warnings = "";

    public void detectShadowingError(String code) throws IOException {
        try (BufferedReader reader = new BufferedReader(new StringReader(code))) {
            String line;
            Set<String> variableNames = new HashSet<>();

            while ((line = reader.readLine()) != null) {
                // Extract variable names declared in the line
                Set<String> lineVariables = extractVariables(line);

                // Check for variable redeclaration within the same scope
                for (String variable : lineVariables) {
                    if (variableNames.contains(variable)) {
                        this.shadowingError = true;  // Variable shadowing detected
                        this.warnings += "Variable shadowing detected. Line: " + line + "\n";
                    }
                }
                // Add variables from the line to the set of known variables
                variableNames.addAll(lineVariables);
            }
        }
    }

    private static Set<String> extractVariables(String line) {
        Set<String> variables = new HashSet<>();
        // Simple regex to match variable declarations
        String regex = "\\b(int|boolean|char|byte|short|long|int|float|double)\\s+(\\w+)\\b";
        // Extract variable names from the line
        Matcher matcher = Pattern.compile(regex).matcher(line);
        while (matcher.find()) {
            variables.add(matcher.group(2));
        }
        return variables;
    }

    public boolean hasShadowingError() {
        return this.shadowingError;
    }

    public String getWarnings() {
        return this.warnings;
    }
}
