import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;


import java.io.File;
import java.io.FileNotFoundException;


public class Parser {

    public Parser(File file) {
        // Parse the Java source file
        CompilationUnit cu = null; // set to null for testing
        {
            try {
                cu = StaticJavaParser.parse(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        System.out.println(cu);
    }
}
