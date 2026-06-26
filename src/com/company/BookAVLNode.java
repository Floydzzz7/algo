package com.company;

public class BookAVLNode {
    int isbn;
    String title;
    String author;
    int availableCopies;
    int borrowCount;
    int height;
    BookAVLNode left, right;

    public BookAVLNode(int isbn, String title, String author, int availableCopies) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.availableCopies = availableCopies;
        this.borrowCount = 0;
        this.height = 1;
        this.left = this.right = null;
    }

}
