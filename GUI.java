// ChatGPT referenced for making GUI

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
//test

public class GUI extends JFrame {

    private JTextArea codeTextArea;

    public GUI() {
        setTitle("Static Analyzer"); // title
        setSize(700, 700); // size of frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit when x-button selected

        codeTextArea = new JTextArea();
        codeTextArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(codeTextArea);

        JButton openFileButton = new JButton("Open Java File");
        openFileButton.addActionListener(e -> openFile());

        JButton analyzeButton = new JButton("Analyze");
        analyzeButton.addActionListener(e -> checkRefactoring());

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

    private void checkRefactoring() {
        // Implement code analysis for refactoring checks
        String code = codeTextArea.getText();
        // Add your refactoring analysis logic here
        // Example: Check for variable renaming
        if (code.contains("oldVariable")) {
            JOptionPane.showMessageDialog(this, "Potential refactoring: Rename 'oldVariable'");
        }
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Java Files", "java");
        fileChooser.setFileFilter(filter);

        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String code = readJavaFile(selectedFile);
            codeTextArea.setText(code);
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


}
