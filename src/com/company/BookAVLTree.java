package com.company;

public class BookAVLTree {
    private  BookAVLNode root;

    public BookAVLTree(){
        this.root = null;
    }
    private int height (BookAVLNode node){
        if (node == null){
            return 0;
        }
        return node.height;
    }
    private int getBalance(BookAVLNode node){

        if (node == null){
            return 0;
        }
        return height(node.left) - height(node.right);
    }
    private BookAVLNode rightRotate(BookAVLNode y){

        BookAVLNode x = y.left;
        BookAVLNode T2 = x.right;
        return x;
    }
    private BookAVLNode leftRotate(BookAVLNode x){
        BookAVLNode y = x.right;
        BookAVLNode T2 = y.left;
        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) +1;
        y.height = Math.max(height(y.left), height(y.right)) +1;

        return y;
    }
    public void insert(int isbn, String title, String author, int availableCopies) {
        root = insertRec(root, isbn, title, author, availableCopies);
    }

    private BookAVLNode insertRec(BookAVLNode node, int isbn, String title, String author, int availableCopies) {
        if (node == null) {
            return new BookAVLNode(isbn, title, author, availableCopies);
        }

        if (isbn < node.isbn) {
            node.left = insertRec(node.left, isbn, title, author, availableCopies);
        } else if (isbn > node.isbn) {
            node.right = insertRec(node.right, isbn, title, author, availableCopies);
        } else {
            return node;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        if (balance > 1 && isbn < node.left.isbn) {
            return rightRotate(node);
        }

        if (balance < -1 && isbn > node.right.isbn) {
            return leftRotate(node);
        }

        if (balance > 1 && isbn > node.left.isbn) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && isbn < node.right.isbn) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }
    public BookAVLNode search(int isbn) {
        return searchRec(root, isbn);
    }

    private BookAVLNode searchRec(BookAVLNode root, int isbn) {
        if (root == null || root.isbn == isbn) {
            return root;
        }
        if (isbn < root.isbn) {
            return searchRec(root.left, isbn);
        }
        return searchRec(root.right, isbn);
    }
    public boolean updateCopies(int isbn, int newCopies) {
        BookAVLNode book = search(isbn);
        if (book != null) {
            book.availableCopies = newCopies;
            return true;
        }
        return false;
    }
    public void delete(int isbn) {
        root = deleteRec(root, isbn);
    }

    private BookAVLNode deleteRec(BookAVLNode root, int isbn) {
        if (root == null) {
            return root;
        }

        if (isbn < root.isbn) {
            root.left = deleteRec(root.left, isbn);
        } else if (isbn > root.isbn) {
            root.right = deleteRec(root.right, isbn);
        } else {
            if ((root.left == null) || (root.right == null)) {
                BookAVLNode temp = null;
                if (temp == root.left) {
                    temp = root.right;
                } else {
                    temp = root.left;
                }

                if (temp == null) {
                    temp = root;
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                BookAVLNode temp = minValueNode(root.right);
                root.isbn = temp.isbn;
                root.title = temp.title;
                root.author = temp.author;
                root.availableCopies = temp.availableCopies;
                root.right = deleteRec(root.right, temp.isbn);
            }
        }

        if (root == null) {
            return root;
        }

        root.height = Math.max(height(root.left), height(root.right)) + 1;
        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.left) >= 0) {
            return rightRotate(root);
        }

        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        if (balance < -1 && getBalance(root.right) <= 0) {
            return leftRotate(root);
        }

        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    private BookAVLNode minValueNode(BookAVLNode node) {
        BookAVLNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
    public int countTotalCopies() {
        return countTotalCopiesRec(root);
    }

    private int countTotalCopiesRec(BookAVLNode node) {
        if (node == null) return 0;
        return node.availableCopies + countTotalCopiesRec(node.left) + countTotalCopiesRec(node.right);
    }

    public BookAVLNode findMostBorrowed() {
        return findMostBorrowedRec(root);
    }

    private BookAVLNode findMostBorrowedRec(BookAVLNode node) {
        if (node == null) return null;
        BookAVLNode res = node;
        BookAVLNode leftRes = findMostBorrowedRec(node.left);
        BookAVLNode rightRes = findMostBorrowedRec(node.right);
        if (leftRes != null && leftRes.borrowCount > res.borrowCount) res = leftRes;
        if (rightRes != null && rightRes.borrowCount > res.borrowCount) res = rightRes;
        return res;
    }

    public String findMostReadAuthor() {
        java.util.HashMap<String, Integer> authorCounts = new java.util.HashMap<String, Integer>();
        collectAuthorBorrowCounts(root, authorCounts);
        String maxAuthor = "";
        int maxCount = -1;
        for (java.util.Map.Entry<String, Integer> entry : authorCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxAuthor = entry.getKey();
            }
        }
        return maxAuthor;
    }

    private void collectAuthorBorrowCounts(BookAVLNode node, java.util.HashMap<String, Integer> map) {
        if (node == null) return;

        int currentCount = 0;
        if (map.containsKey(node.author)) {
            currentCount = map.get(node.author);
        }

        map.put(node.author, currentCount + node.borrowCount);

        collectAuthorBorrowCounts(node.left, map);
        collectAuthorBorrowCounts(node.right, map);
    }

}
