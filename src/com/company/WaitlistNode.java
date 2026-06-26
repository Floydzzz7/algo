package com.company;

public class WaitlistNode {

    public String studentName;
    public int bookIsbn;
    public boolean isGraduate;
    public WaitlistNode next;

    public WaitlistNode(String studentName, int bookIsbn, boolean isGraduate) {
        this.studentName = studentName;
        this.bookIsbn = bookIsbn;
        this.isGraduate = isGraduate;
        this.next = null;
    }
}