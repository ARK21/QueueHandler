package ru.ark.domain;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread class handle queues
 */
public class Consumer implements Runnable {

    // reference to map of divided queues
    private final Map<Integer, LinkedBlockingQueue<Item>> groups;

    // constructor that get reference to map
    public Consumer(Map groups) {
        this.groups = groups;
    }

    // override method from Runnable interface
    public void run() {
        // while true Consumer try to handle queue
        while (true) {
            // initialize Lock to synchronize access
            Lock lock = new ReentrantLock();
            // random get groupId number
            int groupNumber = new Random().nextInt(4) + 1;
            // get queue from map of queues by groupNumber var
            BlockingQueue<Item> queue = groups.get(groupNumber);
            // if got access try to handle queue
            if (lock.tryLock()) {
                // if not empty
                if (!queue.isEmpty()) {
                    try {
                        // get head if queue
                        Item item = queue.element();
                        //handle it
                        System.out.println(item + " handled by: " + Thread.currentThread().getName());
                        // remove Item from queue
                        queue.remove(item);
                        // out of loop
                        break;
                    } finally {
                        try {
                            // before out of loop
                            // a little sleep
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // unlock
                        lock.unlock();
                    }
                }
                // if queue already empty, out of loop
                else break;

            }
            // if didn't get access
            else break;
        }
    }
}




