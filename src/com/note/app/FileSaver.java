package com.note.app;

import javax.swing.*;
import java.io.*;

public class FileSaver {
    public static String saveToFile(String content) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                // Get selected file
                String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();

                // Ensure file has .txt extension
                if (!selectedFilePath.toLowerCase().endsWith(".txt")) {
                    selectedFilePath += ".txt";
                }

                // Create FileWriter with the modified file path
                FileWriter writer = new FileWriter(selectedFilePath);
                writer.write(content);
                writer.close();
                System.out.println("File saved successfully: " + selectedFilePath);

                // Return the filename
                return fileChooser.getSelectedFile().getName();
            } catch (IOException e) {
                System.out.println("An error occurred while saving the file: " + e.getMessage());
            }
        }

        // Return null if the file was not saved or an error occurred
        return null;
    }
}
