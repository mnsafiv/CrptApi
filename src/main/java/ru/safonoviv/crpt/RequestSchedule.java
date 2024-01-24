package ru.safonoviv.crpt;

import lombok.Getter;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RequestSchedule {
    @Getter
    private volatile int currentCount = 1;
    private final long COUNTER_RESET;
    private final int LIMIT_REQUEST;
    private volatile Long lastDate;//время отсчета
    private volatile Long expiredDate;//время сброса

    public RequestSchedule(TimeUnit timeUnit, int LIMIT_REQUEST) {
        this.COUNTER_RESET = timeUnit.toMillis(1);
        this.LIMIT_REQUEST = LIMIT_REQUEST;
        this.lastDate = -1L;
        this.expiredDate = new Date().getTime();
    }

    public synchronized RequestScheduleTime getSchedule() {
        long time = new Date().getTime();
        if (lastDate < expiredDate) {
            expiredDate = new Date().getTime();
            lastDate = expiredDate;
            currentCount = 1;
        } else if (LIMIT_REQUEST > currentCount) {
            currentCount++;
        } else {
            currentCount = 1;
            expiredDate = expiredDate + COUNTER_RESET;
            lastDate = expiredDate;

        }
        return new RequestScheduleTime(lastDate, time);

    }
}
