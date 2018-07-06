package ru.ark.domain;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * class divide main queue to queues by GroupId
 */
public class QueueDivider {
    // map save queues. Map<groupId, queue >
    private Map<Integer, LinkedBlockingQueue<Item>> groups;
    // instance of main queue
    private BlockingQueue<Item> mainQueue;

    // constructor
    public QueueDivider(BlockingQueue mainQueue) {
        //initialize groups and mainQueue
        groups = new ConcurrentHashMap<Integer, LinkedBlockingQueue<Item>>();
        this.mainQueue = mainQueue;
    }

    // method divides mainQueue
    public Map<Integer, LinkedBlockingQueue<Item>> divideIntoGroups() {
        // get queue's iterator
        for (Iterator<Item> it = mainQueue.iterator(); it.hasNext(); ) {
            // if queue has next take item
            Item item = it.next();
            // tate groupId of item
            int group = item.getGroupId();
            // check if map already contains queue with groupId like it, put new item in queue
            if (groups.containsKey(group)) {
                try {
                    groups.get(group).put(item);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // if does not contains, create new queue with new groupId, add this queue in map and put Item in this queue
            else {
                try {
                    BlockingQueue queue = new LinkedBlockingQueue<Item>();
                    queue.put(item);
                    groups.put(group, (LinkedBlockingQueue<Item>) queue);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        // return map of queues divide by groupId
        return groups;
    }

    // return total size of all queues in map
    public int getTotalSize() {
        int totalSize = 0;
        //get size of each queue and sum it
        for (Map.Entry<Integer, LinkedBlockingQueue<Item>> entry : groups.entrySet()) {
            totalSize += entry.getValue().size();
        }
        return totalSize;
    }
}
