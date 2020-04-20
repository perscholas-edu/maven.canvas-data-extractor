package com.github.perscholas.engine;

import com.github.perscholas.utils.io.DirectoryReference;

import javax.swing.*;
import java.io.File;

/**
 * Created by leon on 4/15/2020.
 */ // TODO - Replace this class with a web client
@Deprecated
public class GradeParserClientRunner implements Runnable {

    private File currentDirectory;

    public GradeParserClientRunner(File currentDirectory) {
        this.currentDirectory = currentDirectory;
    }

    public GradeParserClientRunner() {
        this.currentDirectory = getDirectoryFile("Please locate and select the base folder begin operating on.");
    }

    @Override
    public void run() {
        String localDirectory = System.getProperty("user.home");
        File source = getFile("Please locate and select the `grades.csv` that has been exported from canvas.");
        File destination = DirectoryReference.TARGETDIRECTORY.getDuplicateFile(source.getName());
        File excelFileToClone = getFile("Please locate and select the `java-developer-philly-rubric-template.xlsx` that has been provided by PerScholas curriculum team.");
        File excelFileDestination = new File(new StringBuilder()
                .append(getDirectoryFile(new StringBuilder()
                        .append("Please enter folder destination of the output excel file.\n")
                        .append("For example:\n\t")
                        .append(localDirectory)
                        .toString())
                        .getAbsoluteFile())
                .append("/PARSED-")
                .append("java-developer-philly-rubric-template_")
                .append(System.nanoTime())
                .append(".xlsx")
                .toString());

        GradeParserConfigurator engine = new GradeParserConfigurator(
                source,
                destination,
                excelFileToClone,
                excelFileDestination);

        engine.run();
    }


    private File getDirectoryFile(String message) {
        showMessage(message, "Input File");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(currentDirectory);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        JOptionPane.showMessageDialog(null, "Unable to use selected file.\nPlease try again.", "Input File Error", JOptionPane.INFORMATION_MESSAGE);
        return getFile(message);

    }

    private void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    private File getFile(String message) {
        showMessage(message, "Input File");
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(currentDirectory);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        JOptionPane.showMessageDialog(null, "Unable to use selected file.\nPlease try again.", "Input File Error", JOptionPane.INFORMATION_MESSAGE);
        return getFile(message);
    }

    private File enterFile(String message) {
        return new File(JOptionPane.showInputDialog(message));
    }
}
