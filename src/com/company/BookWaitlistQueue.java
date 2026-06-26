package com.company;

public class BookWaitlistQueue {
    private WaitlistNode head;

    public void enqueue(String studentName, int bookIsbn, boolean isGraduate) {
        WaitlistNode newNode = new WaitlistNode(studentName, bookIsbn, isGraduate);

        if (head == null) {
            head = newNode;
            return;
        }

        if (newNode.isGraduate && !head.isGraduate) {
            newNode.next = head;
            head = newNode;
            return;
        }

        WaitlistNode current = head;
        while (current.next != null) {
            if (newNode.isGraduate && !current.next.isGraduate) {
                break;
            }
            current = current.next;
        }

        newNode.next = current.next;
        current.next = newNode;
    }

    public WaitlistNode dequeue() {
        if (head == null) {
            return null;
        }
        WaitlistNode temp = head;
        head = head.next;
        return temp;
    }

    public void displayWaitlist() {
        WaitlistNode current = head;
        while (current != null) {
            System.out.println("Student: " + current.studentName + " | Graduate: " + current.isGraduate + " | Book ISBN: " + current.bookIsbn);
            current = current.next;
        }
    }
}