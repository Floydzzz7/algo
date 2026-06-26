package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    private LibrarySystem library;

    private JTextField isbnField, titleField, authorField, copiesField;
    private JTextField studentNameField, borrowIsbnField, borrowDateField, returnDateField;
    private JCheckBox graduateCheck;
    private JTextField searchField, updateIsbnField, updateCopiesField, deleteIsbnField;
    private JTextArea outputArea;

    public Main() {
        library = new LibrarySystem();

        setTitle("Digital Library Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Manage Books", createBooksPanel());
        tabbedPane.addTab("Borrow & Return", createBorrowPanel());
        tabbedPane.addTab("Search & Operations", createOperationsPanel());

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("System Console Output"));

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabbedPane, scrollPane);
        splitPane.setDividerLocation(350);

        add(splitPane);

        redirectSystemOut();
    }

    private JPanel createBooksPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        isbnField = new JTextField(15);
        titleField = new JTextField(15);
        authorField = new JTextField(15);
        copiesField = new JTextField(15);

        addComponent(panel, new JLabel("Book ISBN:"), gbc, 0, 0);
        addComponent(panel, isbnField, gbc, 1, 0);
        addComponent(panel, new JLabel("Book Title:"), gbc, 0, 1);
        addComponent(panel, titleField, gbc, 1, 1);
        addComponent(panel, new JLabel("Author Name:"), gbc, 0, 2);
        addComponent(panel, authorField, gbc, 1, 2);
        addComponent(panel, new JLabel("Available Copies:"), gbc, 0, 3);
        addComponent(panel, copiesField, gbc, 1, 3);

        JButton addButton = new JButton("Add New Book");
        gbc.gridwidth = 2;
        addComponent(panel, addButton, gbc, 0, 4);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int isbn = Integer.parseInt(isbnField.getText());
                    String title = titleField.getText();
                    String author = authorField.getText();
                    int copies = Integer.parseInt(copiesField.getText());
                    library.addBook(isbn, title, author, copies);
                    System.out.println("Success: Added book '" + title + "' successfully.");
                    clearBookFields();
                } catch (NumberFormatException ex) {
                    System.out.println("Error: Please enter valid numerical values for ISBN and Copies.");
                }
            }
        });

        return panel;
    }

    private JPanel createBorrowPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        studentNameField = new JTextField(15);
        borrowIsbnField = new JTextField(15);
        graduateCheck = new JCheckBox("Is Graduate Student");
        borrowDateField = new JTextField("2026-06-26", 15);
        returnDateField = new JTextField("2026-07-10", 15);
        addComponent(panel, new JLabel("Student Name:"), gbc, 0, 0);
        addComponent(panel, studentNameField, gbc, 1, 0);
        addComponent(panel, new JLabel("Book ISBN:"), gbc, 0, 1);
        addComponent(panel, borrowIsbnField, gbc, 1, 1);
        addComponent(panel, graduateCheck, gbc, 1, 2);
        addComponent(panel, new JLabel("Borrow Date:"), gbc, 0, 3);
        addComponent(panel, borrowDateField, gbc, 1, 3);
        addComponent(panel, new JLabel("Return Date:"), gbc, 0, 4);
        addComponent(panel, returnDateField, gbc, 1, 4);

        JButton borrowButton = new JButton("Borrow Book");
        JButton returnButton = new JButton("Return Book");
        JButton statusButton = new JButton("Display Library Status");

        gbc.gridwidth = 1;
        addComponent(panel, borrowButton, gbc, 0, 5);
        addComponent(panel, returnButton, gbc, 1, 5);
        gbc.gridwidth = 2;
        addComponent(panel, statusButton, gbc, 0, 6);

        borrowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = studentNameField.getText();
                    int isbn = Integer.parseInt(borrowIsbnField.getText());
                    boolean isGrad = graduateCheck.isSelected();
                    String bDate = borrowDateField.getText();
                    String rDate = returnDateField.getText();
                    library.borrowBook(name, isbn, isGrad, bDate, rDate);
                } catch (NumberFormatException ex) {
                    System.out.println("Error: ISBN must be a valid number.");
                }
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int isbn = Integer.parseInt(borrowIsbnField.getText());
                    String name = studentNameField.getText();
                    library.returnBook(isbn, name);
                } catch (NumberFormatException ex) {
                    System.out.println("Error: ISBN must be a valid number.");
                }
            }
        });

        statusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                library.displayLibraryStatus();
            }
        });

        return panel;
    }

    private JPanel createOperationsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        searchField = new JTextField(15);
        updateIsbnField = new JTextField(15);
        updateCopiesField = new JTextField(10);
        deleteIsbnField = new JTextField(15);

        JButton searchButton = new JButton("Search Book");
        JButton updateButton = new JButton("Update Copies");
        JButton deleteButton = new JButton("Delete Book");
        JButton reportButton = new JButton("Generate Analytics Report");

        addComponent(panel, new JLabel("Search by ISBN:"), gbc, 0, 0);
        addComponent(panel, searchField, gbc, 1, 0);
        addComponent(panel, searchButton, gbc, 2, 0);

        addComponent(panel, new JLabel("Update Copies (ISBN):"), gbc, 0, 1);
        addComponent(panel, updateIsbnField, gbc, 1, 1);
        addComponent(panel, new JLabel("New Count:"), gbc, 2, 1);
        addComponent(panel, updateCopiesField, gbc, 3, 1);
        addComponent(panel, updateButton, gbc, 4, 1);

        addComponent(panel, new JLabel("Delete Book (ISBN):"), gbc, 0, 2);
        addComponent(panel, deleteIsbnField, gbc, 1, 2);
        addComponent(panel, deleteButton, gbc, 2, 2);

        gbc.gridwidth = 5;
        addComponent(panel, reportButton, gbc, 0, 4);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int isbn = Integer.parseInt(searchField.getText());
                    library.searchBookInSystem(isbn);
                } catch (NumberFormatException ex) {
                    System.out.println("Error: Enter a valid ISBN number.");
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int isbn = Integer.parseInt(updateIsbnField.getText());
                    int copies = Integer.parseInt(updateCopiesField.getText());
                    library.updateBookCopiesInSystem(isbn, copies);
                } catch (NumberFormatException ex) {
                    System.out.println("Error: Verify numerical values.");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int isbn = Integer.parseInt(deleteIsbnField.getText());
                    library.deleteBookFromSystem(isbn);
                } catch (NumberFormatException ex) {
                    System.out.println("Error: Enter a valid ISBN.");
                }
            }
        });

        reportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                library.generateAnalyticsReport();
            }
        });

        return panel;
    }

    private void addComponent(JPanel p, Component c, GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        p.add(c, gbc);
    }

    private void clearBookFields() {
        isbnField.setText("");
        titleField.setText("");
        authorField.setText("");
        copiesField.setText("");
    }

    private void redirectSystemOut() {
        java.io.OutputStream out = new java.io.OutputStream() {
            @Override
            public void write(int b) {
                outputArea.append(String.valueOf((char) b));
                outputArea.setCaretPosition(outputArea.getDocument().getLength());
            }
        };
        System.setOut(new java.io.PrintStream(out, true));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}