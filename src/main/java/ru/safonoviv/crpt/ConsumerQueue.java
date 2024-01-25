package ru.safonoviv.crpt;

import ru.safonoviv.CrptApi;
import ru.safonoviv.util.ThreadUtil;

import java.util.concurrent.TimeUnit;

public class ConsumerQueue implements Runnable {
    private boolean running = false;
    private final CrptApi crptApi;
    private final RequestSchedule requestSchedule;
    private volatile long nextSchedule;

    public ConsumerQueue(CrptApi crptApi, TimeUnit timeUnit, int requestLimit) {
        this.crptApi = crptApi;
        requestSchedule = new RequestSchedule(timeUnit,requestLimit);
    }


    @Override
    public void run() {
        running = true;
        consume();
    }

    public void stop() {
        running = false;
    }

    public void consume() {
        while (running) {
            if (crptApi.isEmpty()) {
                try {
                    crptApi.waitConsume();
                } catch (InterruptedException e) {
                    break;
                }
            }

            RequestScheduleTime schedule = requestSchedule.getSchedule();
            nextSchedule = schedule.getTime();

            if (nextSchedule > schedule.getTimeRequest()) {
                ThreadUtil.sleep(nextSchedule - schedule.getTimeRequest()); //fix sleep warning in idea
            }

            Request request = crptApi.poll();
            new Thread(request).start();

        }
    }
}
