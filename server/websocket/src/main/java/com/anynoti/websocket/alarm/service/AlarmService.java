package com.anynoti.websocket.alarm.service;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlarmService {

    private final SimpMessageSendingOperations messagingTemplate;

    public void pushAlarmOfAnimation(Set<String> providerIdSet){
        //최신화가 노티됬다면
        if(true){
//            messagingTemplate.convertAndSend("/topic/animations/alarm/" + userId,
//                MessageResponse.of(count, "읽지 않은 소원이 " + count + "건 있습니다."));
        }

    }

}