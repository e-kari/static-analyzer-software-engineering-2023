// ChatGPT referenced for making GUI

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GUI extends JFrame {
        // where the code will be displayed and be editable
    private JTextArea codeTextArea;
        // string to put all warnings in
    private String warnings = "";

    public GUI() {
        setTitle("Static Analyzer"); // title
        setSize(700, 700); // size of frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit when x-button selected

        codeTextArea = new JTextArea(); // display editable code
        codeTextArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(codeTextArea);

        JButton openFileButton = new JButton("Open Java File");
        openFileButton.addActionListener(e -> openFile());

        JButton analyzeButton = new JButton("Analyze");
        analyzeButton.addActionListener(e -> {
            try {
                checkRefactoring();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // button panel in layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        // add buttons to panel
        buttonPanel.add(openFileButton);
        buttonPanel.add(analyzeButton);
        // button and scroll layout position
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true); // make frame visible 

    }

    private void checkRefactoring() throws IOException {
        // set warnings to empty
        warnings = "";

        // grab java code text from gui text box
        String code = codeTextArea.getText();

        // create an instance of VariableShadowingError
        VariableShadowingError shadowingErr = new VariableShadowingError();

        // Attempt to detect variable shadowing
        shadowingErr.detectShadowingError(code);

        // If variable shadowing is detected, append the warning to the overall warnings
        if (shadowingErr.hasShadowingError()) {
            warnings += shadowingErr.getWarnings() + "\n";
        }

        // send code to SyntaxError Class as a String
        SyntaxError synErr = new SyntaxError(code);
        // return warning messages from syntax check
        String syntaxWarnings = synErr.performSyntaxCheck();

        // send code to LineCountError as a String
        LineCountError lcError = new LineCountError(code);
        // return warning messages from LineCount Error;
        String lineCountWarnings = lcError.performMethodLengthCheck();

        // Append syntax and line count warnings to the overall warnings
        warnings += syntaxWarnings + lineCountWarnings;

        // Display warnings in the GUI
        if (warnings.isEmpty()) {
            warnings = "No errors were found!";
        }

        // Display all warnings in the JOptionPane pop-up
        JOptionPane.showMessageDialog(this, warnings);

        // Clear the warnings for the next analysis
        warnings = "";
    }

    private void openFile() {
        // clear text area in case this is replacing an already open file
        codeTextArea.setText("");

        JFileChooser fileChooser = new JFileChooser();

        // For testing purposes will be opened to specific folder for ease of test
        // Set the initial directory to the user directory then the resources
        String projectDirectory = System.getProperty("user.dir");
        String resourcesPath = projectDirectory + "/src/main/resources";

        File resourcesDirectory = new File(resourcesPath);
        fileChooser.setCurrentDirectory(resourcesDirectory);

        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String code = readJavaFile(selectedFile);
            codeTextArea.setText(code); // display file in text area
            addLineNumbers(); // line numbers for better referencing later on
        }
    }

    private String readJavaFile(File file) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private void addLineNumbers() {
        String text = codeTextArea.getText();
        String[] lines = text.split("\n");

        StringBuilder newText = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            newText.append(i + 1).append("  ").append(lines[i]);
            if (i < lines.length - 1) {
                newText.append("\n");
            }
        }

        codeTextArea.setText(newText.toString());
    }


}
