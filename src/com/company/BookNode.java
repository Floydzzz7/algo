package com.company;

public class BookNode {
    int isbn;
    String title;
    String author;
    int availableCopies;
    int borrowCount;
    BookNode left,right;

    public BookNode(int isbn, String title, String author, int availableCopies) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.availableCopies = availableCopies;
        this.borrowCount = 0;
        this.left = this.right = null;

    }

}
