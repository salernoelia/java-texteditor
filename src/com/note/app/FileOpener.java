package com.note.app;

import javax.swing.*;
import java.io.*;

public class FileOpener {
    public static String openFile(JTextArea textArea) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                StringBuilder sb = new StringBuilder();
                String line;
                BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                reader.close();

                // Replace text of the JTextArea with the content of the opened file
                textArea.setText(sb.toString());

                // Return the filename
                return fileChooser.getSelectedFile().getName();
            } catch (IOException e) {
                System.out.println("An error occurred while opening the file: " + e.getMessage());
            }
        }

        // Return null if the file was not opened or an error occurred
        return null;
    }
}
