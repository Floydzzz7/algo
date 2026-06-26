package com.company;

public class LibrarySystem {
    private BookAVLTree libraryBooks;
    private BorrowManager borrowManager;
    private BookWaitlistQueue waitlistQueue;

    public LibrarySystem() {
        this.libraryBooks = new BookAVLTree();
        this.borrowManager = new BorrowManager();
        this.waitlistQueue = new BookWaitlistQueue();
    }

    public void addBook(int isbn, String title, String author, int availableCopies) {
        libraryBooks.insert(isbn, title, author, availableCopies);
    }

    public void borrowBook(String studentName, int isbn, boolean isGraduate, String borrowDate, String expectedReturnDate) {
        BookAVLNode book = libraryBooks.search(isbn);

        if (book == null) {
            System.out.println("Operation Failed: Book with ISBN " + isbn + " does not exist in the library.");
            return;
        }

        if (borrowManager.getBorrowCountForStudent(studentName) >= 3) {
            System.out.println("Operation Failed: " + studentName + " has reached the maximum limit of 3 borrowed books.");
            return;
        }

        if (book.availableCopies > 0) {
            book.availableCopies--;
            book.borrowCount++;
            borrowManager.addRecord(studentName, isbn, borrowDate, expectedReturnDate);
            System.out.println("Success: " + studentName + " has successfully borrowed '" + book.title + "'.");
        } else {
            System.out.println("Book Unavailable: Adding " + studentName + " to the waitlist queue.");
            waitlistQueue.enqueue(studentName, isbn, isGraduate);
        }
    }

    public void returnBook(int isbn, String studentName) {
        BookAVLNode book = libraryBooks.search(isbn);
        if (book == null) return;

        BorrowRecord record = borrowManager.searchRecord(studentName, isbn);
        if (record == null) {
            System.out.println("No matching borrow record found for this student and book.");
            return;
        }

        System.out.println("Success: '" + book.title + "' has been returned by " + studentName + ".");

        WaitlistNode nextStudent = waitlistQueue.dequeue();
        if (nextStudent != null && nextStudent.bookIsbn == isbn) {
            book.borrowCount++;
            borrowManager.addRecord(nextStudent.studentName, isbn, "2026-06-24", "2026-07-08");
            System.out.println("Waitlist Alert: Book automatically assigned to waiting student: " + nextStudent.studentName);
        } else {
            book.availableCopies++;
        }
    }

    public void searchBookInSystem(int isbn) {
        BookAVLNode book = libraryBooks.search(isbn);
        if (book != null) {
            System.out.println("Book Found: '" + book.title + "' by " + book.author + " | Available Copies: " + book.availableCopies + " | Total Borrowed: " + book.borrowCount);
        } else {
            System.out.println("Book with ISBN " + isbn + " not found in the library.");
        }
    }

    public void updateBookCopiesInSystem(int isbn, int newCopies) {
        BookAVLNode book = libraryBooks.search(isbn);
        if (book != null) {
            book.availableCopies = newCopies;
            System.out.println("Success: Copies count for '" + book.title + "' has been updated to " + newCopies + ".");
        } else {
            System.out.println("Book with ISBN " + isbn + " not found in the library.");
        }
    }

    public void deleteBookFromSystem(int isbn) {
        BookAVLNode book = libraryBooks.search(isbn);
        if (book != null) {
            libraryBooks.delete(isbn);
            System.out.println("Success: Book with ISBN " + isbn + " has been deleted from the library.");
        } else {
            System.out.println("Book with ISBN " + isbn + " not found in the library.");
        }
    }

    public void displayLibraryStatus() {
        System.out.println("\n--- Current Waitlist ---");
        waitlistQueue.displayWaitlist();
        System.out.println("\n--- Active Borrow Records ---");
        borrowManager.displayRecords();
    }
    public void generateAnalyticsReport() {
        System.out.println("\n--- Library Analytics Report ---");

        int totalCopies = libraryBooks.countTotalCopies();
        System.out.println("Total Available Copies in Library: " + totalCopies);

        BookAVLNode mostBorrowed = libraryBooks.findMostBorrowed();
        if (mostBorrowed != null && mostBorrowed.borrowCount > 0) {
            System.out.println("Most Borrowed Book: '" + mostBorrowed.title + "' (Borrowed " + mostBorrowed.borrowCount + " times)");
        } else {
            System.out.println("Most Borrowed Book: None");
        }

        String topAuthor = libraryBooks.findMostReadAuthor();
        System.out.println("Most Read Author: " + (topAuthor.isEmpty() ? "None" : topAuthor));
    }

}