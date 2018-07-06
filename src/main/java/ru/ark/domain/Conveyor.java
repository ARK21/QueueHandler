package ru.ark.domain;

import java.util.concurrent.*;

/**
 * Class contains all necessary classes to start app
 */
public class Conveyor {

    // main queue
    private BlockingQueue<Item> queue = new LinkedBlockingQueue<Item>();
    // executor service create threads of producers to fill main queue
    private ExecutorService fillService = Executors.newCachedThreadPool();
    // executor service create 10 threads of consumer to handle main queue
    private ExecutorService service = Executors.newFixedThreadPool(10);
    // Thread class to fill queue. Get reference to main queue in constructor
    private Producer producer = new Producer(queue);
    // Divide main queue by groupId. Get reference to main queue in constructor
    private QueueDivider divider = new QueueDivider(queue);
    // Thread class to handle queues
    private Consumer consumer;

    // Constructor
    public Conveyor() {

        // fill main queue
        toFillQueue();
        // initialize consumer. Get reference to map of divided queues in constructor
        consumer = new Consumer(divider.divideIntoGroups());
        // consumers handle map of queues
        toClearQueue();
    }

    // method create 100 tasks to fill main queue
    private void toFillQueue() {
        // create tasks
        for (int i = 1; i <= 100; i++) {
            fillService.submit(producer);
        }
        // stops all thread after complete
        fillService.shutdown();
        try {
            fillService.awaitTermination(1000, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // method create tasks to handle queues
    private void toClearQueue() {
        // while divider not empty create tasks
        while (divider.getTotalSize() != 0) {
            service.submit(consumer);
        }
        // stops all thread after complete
        service.shutdown();
        try {
            service.awaitTermination(1000, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
