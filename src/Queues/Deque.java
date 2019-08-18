package Queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

// implementation of deque using linked-list
public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node next;
        Node prev;

        Node(Item item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }
    private Node first = null;
    private Node last = null;
    private int size = 0;

    private class DequeIterator implements Iterator<Item> {
        Node cur = first;

        @Override
        public boolean hasNext() {
            return cur != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = cur.item;
            cur = cur.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // construct an empty deque
    public Deque() {}

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        this.first = new Node(item, this.first, null);

        if (this.size == 0) {
            this.last = this.first;
        } else {
            this.first.next.prev = this.first;
        }

        this.size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (this.size == 0) {
            addFirst(item);
        } else {
            this.last.next = new Node(item, null, this.last);
            this.last = this.last.next;
            this.size++;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node oldFirst = this.first;
        this.first = oldFirst.next;
        this.size--;

        if (this.size != 0) {
            this.first.prev = null;
        } else {
            this.last = null;
        }

        return oldFirst.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        Node oldLast = this.last;
        this.last = this.last.prev;
        this.size--;

        if (this.size != 0) {
            this.last.next = null;
        } else {
            this.first = null;
        }

        return oldLast.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addLast(1);
        deque.addFirst(2);
        deque.removeFirst();
        System.out.println(deque.isEmpty());
        System.out.println(deque.size());

        deque.removeLast();
        deque.addFirst(3);
        deque.removeFirst();
        System.out.println(deque.isEmpty());
        System.out.println(deque.size());

        deque.addFirst(4);
        deque.addFirst(5);
        deque.addLast(6);
        deque.addLast(7);
        System.out.println(deque.isEmpty());
        System.out.println(deque.size());
        for (Integer x : deque) {
            System.out.print(x);
        }
    }
}