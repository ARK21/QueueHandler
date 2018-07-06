package ru.ark.domain;

import ru.ark.domain.Item;

import java.util.concurrent.BlockingQueue;

/**
 * Thread class produce new Items and put it into queue
 */
public class Producer implements Runnable {

    // static field uses for initialize ItemId fr Item
    private static int ItemId;
    // interface BlockingQueue thar refer to queue
    private final BlockingQueue queue;

    // constructor
    public Producer(BlockingQueue queue) {
        this.queue = queue;
    }

    // override method from Runnable interface. It's work when thread start
    public void run() {
        try {
            // synchronize by queue
            synchronized (queue) {
                // create new Item and put it in the queue;
                Item item = new Item(++ItemId);
                queue.put(item);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
