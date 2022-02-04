package com.anynoti.websocket.alarm;

import com.anynoti.websocket.alarm.service.AlarmService;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final AlarmService alarmService;

    //public static ThreadLocal<String> tokenStore = new ThreadLocal<>();
    private Set<String> providerIdSet = new HashSet<>();

    @Async("schedulerExecutor")
    @Scheduled(cron = "0/1 * * * * *")
    public void checkAnimation() {
        //String userId = tokenStore.get();
        alarmService.pushAlarmOfAnimation(providerIdSet);
        //tokenStore.remove();
    }

    public Set<String> getProviderIdSet() {
        return providerIdSet;
    }
}