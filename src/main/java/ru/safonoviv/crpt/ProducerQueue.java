package ru.safonoviv.crpt;


import java.util.Queue;


public class ProducerQueue {
    private final Queue<Request> queue;

    public ProducerQueue(Queue<Request> queue) {
        this.queue = queue;
    }

    public void add(Request message) {
        queue.add(message);
    }
}
