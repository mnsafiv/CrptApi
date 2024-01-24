package ru.safonoviv.crpt;

import lombok.Setter;
import ru.safonoviv.util.SSLUtil;

import javax.net.ssl.SSLContext;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class ProducerQueue implements Runnable {

    private final BlockingQueue<SendRequest> queue;
    private final Queue<SendRequest> await = new LinkedBlockingQueue<>();
    private final RequestSchedule requestSchedule;
    private final SSLContext sslContext;
    private volatile long lastSchedule;
    @Setter
    private boolean isFinished = false;

    public ProducerQueue(BlockingQueue<SendRequest> queue, RequestSchedule requestSchedule) {
        this.queue = queue;
        this.requestSchedule = requestSchedule;
        this.sslContext = SSLUtil.getSSLContext();
    }

    public void addToQueueResponse(String jsonStr) {
        await.add(new SendRequest(jsonStr, sslContext));
    }

    @Override
    public void run() {
        while (!isFinished) {
            if (await.isEmpty()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                RequestScheduleTime schedule = requestSchedule.getSchedule();
                lastSchedule = schedule.getTime();
                long currentTime = schedule.getTimeRequest();

                if (lastSchedule > currentTime) {
                    try {
                        Thread.sleep(lastSchedule - currentTime);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                System.out.println(requestSchedule.getCurrentCount());

                SendRequest request = await.poll();
                if (request != null) {
                    queue.add(request);
                }


            }


        }

    }
}
