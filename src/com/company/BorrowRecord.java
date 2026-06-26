package com.company;

public class BorrowRecord {

    public String borrowerName;
    public int bookIsbn;
    public String borrowDate;
    public String expectedReturnDate;
    public BorrowRecord next;

    public BorrowRecord(String borrowerName, int bookIsbn, String borrowDate, String expectedReturnDate) {
        this.borrowerName = borrowerName;
        this.bookIsbn = bookIsbn;
        this.borrowDate = borrowDate;
        this.expectedReturnDate = expectedReturnDate;
        this.next = null;
    }
}