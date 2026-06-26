package com.company;

public class BookBST {
    private BookNode root;

    public BookBST(){}

    public BookBST(BookNode root) {
        this.root = null;
    }

    public void insert(int isbn, String title, String author, int availableCopies){
        root = insertRec(root, isbn, title, author, availableCopies);
    }

    private BookNode insertRec(BookNode root, int isbn, String title, String author, int availableCopies){
        if (root == null){
            return new BookNode(isbn, title, author, availableCopies);
        }
        if (isbn < root.isbn){
            root.left = insertRec(root.left, isbn, title, author, availableCopies);
        }
        else if(isbn > root.isbn){
            root.right = insertRec(root.right, isbn, title, author, availableCopies);
        }
        return root;
    }

    public BookNode search(int isbn){
        return searchRec(root, isbn);
    }

    private BookNode searchRec(BookNode root, int isbn){
        if (root == null || root.isbn == isbn){
            return root;
        }
        if (isbn > root.isbn){
            return searchRec(root.right, isbn);
        }
        return searchRec(root.left, isbn);
    }

    public boolean updateCopies(int isbn, int newCopies){
        BookNode book = search(isbn);
        if (book != null) {
            book.availableCopies = newCopies;
            return true;
        }
        return false;
    }

    public void delete(int isbn){
        root = deleteRec(root, isbn);
    }

    private BookNode deleteRec(BookNode root, int isbn){
        if (root == null){
            return null;
        }
        else if (isbn < root.isbn){
            root.left = deleteRec(root.left, isbn);
        }
        else if (isbn > root.isbn){
            root.right = deleteRec(root.right, isbn);
        }
        else{
            if (root.left == null){
                return root.right;
            }
            else if (root.right == null){
                return root.left;
            }
            root.isbn = minValue(root.right);
            BookNode temp = search(root.isbn);
            root.title = temp.title;
            root.author = temp.author;
            root.availableCopies = temp.availableCopies;
            root.borrowCount = temp.borrowCount;
            root.right = deleteRec(root.right, root.isbn);
        }
        return root;
    }

    private int minValue(BookNode root){
        int minv = root.isbn;
        while (root.left != null){
            minv = root.left.isbn;
            root = root.left;
        }
        return minv;
    }
}