package Queues;

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

// implementation using resizing array
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] arr;
    private int N = 0;

    private class RandomizedQueueIterator implements Iterator<Item> {
        Item[] arrShuffled;
        int cur = 0;

        RandomizedQueueIterator() {
            arrShuffled = (Item[]) new Object[N];
            for (int i = 0; i < N; i++) {
                arrShuffled[i] = arr[i];
            }
            StdRandom.shuffle(arrShuffled);
        }

        @Override
        public boolean hasNext() {
            return this.cur < this.arrShuffled.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return this.arrShuffled[cur++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        this.arr = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return this.N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return this.N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        // resize if arr full
        if (this.N == this.arr.length) {
            resize(this.arr.length * 2);
        }

        this.arr[N++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        // remove and return from a random pos, and then fill in the gap with the last item
        int randomPos = StdRandom.uniform(N);
        Item item = this.arr[randomPos];
        this.arr[randomPos] = this.arr[--N];
        this.arr[N] = null;

        // resize if 1/4 full
        if (this.N < this.arr.length / 4) {
            resize(this.arr.length / 2);
        }

        return item;
    }

    // resize the underlying array
    private void resize(int cap) {
        Item[] arrNew = (Item[]) new Object[cap];
        for (int i = 0; i < N; i++)
            arrNew[i] = arr[i];
        this.arr = arrNew;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (this.N == 0) {
            throw new NoSuchElementException();
        }

        return this.arr[StdRandom.uniform(N)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rQueue = new RandomizedQueue<>();
        rQueue.enqueue(1);
        rQueue.enqueue(2);
        System.out.println(rQueue.dequeue());
        System.out.println(rQueue.sample());
        System.out.println(rQueue.isEmpty());
        System.out.println(rQueue.size());

        System.out.println(rQueue.dequeue());
        System.out.println(rQueue.isEmpty());
        System.out.println(rQueue.size());

        rQueue.enqueue(4);
        rQueue.enqueue(5);
        rQueue.enqueue(6);
        rQueue.enqueue(7);
        System.out.println(rQueue.isEmpty());
        System.out.println(rQueue.size());
        for (Integer x : rQueue) {
            System.out.print(x);
        }
        System.out.println();
        for (Integer x : rQueue) {
            System.out.print(x);
        }
    }
}
