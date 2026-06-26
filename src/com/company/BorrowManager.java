package com.company;

public class BorrowManager {
    private BorrowRecord head;

    public void addRecord(String borrowerName, int bookIsbn, String borrowDate, String expectedReturnDate) {
        BorrowRecord newRecord = new BorrowRecord(borrowerName, bookIsbn, borrowDate, expectedReturnDate);
        if (head == null) {
            head = newRecord;
        } else {
            BorrowRecord current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newRecord;
        }
    }

    public BorrowRecord searchRecord(String borrowerName, int bookIsbn) {
        BorrowRecord current = head;
        while (current != null) {
            if (current.borrowerName.equals(borrowerName) && current.bookIsbn == bookIsbn) {
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public boolean updateReturnDate(String borrowerName, int bookIsbn, String newExpectedReturnDate) {
        BorrowRecord record = searchRecord(borrowerName, bookIsbn);
        if (record != null) {
            record.expectedReturnDate = newExpectedReturnDate;
            return true;
        }
        return false;
    }

    public int getBorrowCountForStudent(String borrowerName) {
        int count = 0;
        BorrowRecord current = head;
        while (current != null) {
            if (current.borrowerName.equals(borrowerName)) {
                count++;
            }
            current = current.next;
        }
        return count;
    }

    public void displayRecords() {
        BorrowRecord current = head;
        while (current != null) {
            System.out.println("Borrower: " + current.borrowerName + " | ISBN: " + current.bookIsbn + " | Date: " + current.borrowDate + " | Expected Return: " + current.expectedReturnDate);
            current = current.next;
        }
    }
}