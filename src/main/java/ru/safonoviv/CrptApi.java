package ru.safonoviv;

import lombok.Getter;
import ru.safonoviv.crpt.ConsumerQueue;
import ru.safonoviv.crpt.ProducerQueue;
import ru.safonoviv.crpt.Request;

import java.util.concurrent.*;

public class CrptApi {
    @Getter
    private final BlockingQueue<Request> queue = new LinkedBlockingQueue<>();
    private final ProducerQueue producerQueue;
    private final ConsumerQueue consumerQueue;
    private final Thread consumerThread;

    private final Object IS_EMPTY_HOLDER = new Object();

    public CrptApi(TimeUnit timeUnit, int requestLimit) {
        producerQueue = new ProducerQueue(queue);
        consumerQueue = new ConsumerQueue(this, timeUnit, requestLimit);
        consumerThread=new Thread(consumerQueue);
        consumerThread.start();
    }


    public void add(Request request) {
        producerQueue.add(request);
        revokeConsume();
    }


    public boolean isEmpty() {
        return queue.isEmpty();
    }


    public void revokeConsume() {
        synchronized (IS_EMPTY_HOLDER) {
            IS_EMPTY_HOLDER.notify();
        }
    }

    public void waitConsume() throws InterruptedException {
        synchronized (IS_EMPTY_HOLDER) {
            IS_EMPTY_HOLDER.wait();
        }
    }

    public Request poll() {
        return queue.poll();
    }

    public void stop() throws InterruptedException {
        consumerQueue.stop();
        revokeConsume();
        consumerThread.join();
    }


}
