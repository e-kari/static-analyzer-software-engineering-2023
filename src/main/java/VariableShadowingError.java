import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class VariableShadowingError {
    public static boolean detectShadowingError(String code) throws IOException {
        try (BufferedReader reader = new BufferedReader(new StringReader(code))) {
            String line;
            boolean shadowing = false;

            while ((line = reader.readLine()) != null) {
                // checks for local variable declarations without an initializer
                if (line.matches("\\s*\\w+\\s+\\w+(\\s*=\\s*[^;]+)?\\s*;")) {
                    String[] tokens = line.trim().split("\\s+");
                    String variableName = tokens[1];

                    // assume instance variables start with 'this.' keyword
                    if (!variableName.startsWith("this.")) {
                        shadowing = true;
                        System.out.println("Potential variable shadowing detected: " + variableName);
                    }
                }
            }
            return shadowing;
        }
    }
}
