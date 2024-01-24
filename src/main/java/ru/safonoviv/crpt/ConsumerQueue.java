package ru.safonoviv.crpt;

import lombok.Setter;

import java.util.concurrent.BlockingQueue;

public class ConsumerQueue implements Runnable{
    private final BlockingQueue<SendRequest> queue;
    @Setter
    private boolean isFinished = false;

    public ConsumerQueue(BlockingQueue<SendRequest> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (!isFinished){
            if (queue.isEmpty()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }else{
                while (!queue.isEmpty()){
                    new Thread(queue.poll()).start();
                }
            }


        }

    }
}
