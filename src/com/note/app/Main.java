package com.note.app;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Note");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create JTextArea with padding
        JTextArea textArea = new JTextArea();
        textArea.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Enable dropping files to open
        textArea.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(java.awt.dnd.DropTargetDropEvent evt) {
                try {
                    evt.acceptDrop(java.awt.dnd.DnDConstants.ACTION_COPY);
                    List<File> droppedFiles = (List<File>) evt.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                        if (file.getName().toLowerCase().endsWith(".txt")) {
                            openFile(file, textArea); // Open the dropped .txt file
                            break; // Only open the first .txt file dropped
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton saveButton = new JButton("Save Note");
        saveButton.setPreferredSize(new Dimension(100, 30));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String noteText = textArea.getText();
                // Save note text to file
                String fileName = FileSaver.saveToFile(noteText); // Save note and get the filename
                updateWindowTitle(frame, fileName); // Update window title
            }
        });
        buttonPanel.add(saveButton);

        JButton openButton = new JButton("Open Note");
        openButton.setPreferredSize(new Dimension(100, 30));
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open dialog to choose file
                String fileName = FileOpener.openFile(textArea); // Open note and get the filename
                updateWindowTitle(frame, fileName); // Update window title
            }
        });
        buttonPanel.add(openButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private static void updateWindowTitle(JFrame frame, String fileName) {
        if (fileName != null) {
            frame.setTitle("Note - " + fileName);
        }
    }

    private static void openFile(File file, JTextArea textArea) {
        try {
            StringBuilder sb = new StringBuilder();
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            reader.close();

            // Replace text of the JTextArea with the content of the opened file
            textArea.setText(sb.toString());

            // Update window title with the opened file name
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(textArea);
            updateWindowTitle(frame, file.getName());
        } catch (IOException e) {
            System.out.println("An error occurred while opening the file: " + e.getMessage());
        }
    }
}
