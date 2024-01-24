package ru.safonoviv;


import ru.safonoviv.crpt.ConsumerQueue;
import ru.safonoviv.crpt.ProducerQueue;
import ru.safonoviv.crpt.RequestSchedule;
import ru.safonoviv.crpt.SendRequest;

import java.util.concurrent.*;

public class CrptApi {
    private final ConsumerQueue consumerQueue;
    private final ProducerQueue producerQueue;
    private final Thread consumerThread;
    private final Thread producerThread;

    public CrptApi(TimeUnit timeUnit, int requestLimit) {
        LinkedBlockingQueue<SendRequest> queue = new LinkedBlockingQueue<>(Integer.MAX_VALUE);

        consumerQueue = new ConsumerQueue(queue);
        producerQueue = new ProducerQueue(queue, new RequestSchedule(timeUnit, requestLimit));

        consumerThread = new Thread(consumerQueue);
        producerThread = new Thread(producerQueue);

        consumerThread.start();
        producerThread.start();


    }

    public void sendRequest(String jsonStr) {
        producerQueue.addToQueueResponse(jsonStr);

    }

//    public void shootDown(){
//        consumerQueue.setFinished(true);
//        producerQueue.setFinished(true);
//    }


}
