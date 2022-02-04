package com.anynoti.websocket.alarm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AlarmController {

    private final SimpMessageSendingOperations messagingTemplate;

    //Client에서 한번 호출?
//    @MessageMapping("/{userId}")
//    public void message(@DestinationVariable("userId") Long userId) {
//        tokenStore.set(userId);
//        map.put()
        //messagingTemplate.convertAndSend("/topic/animations/alarm/" + userId, "alarm socket connection completed.");
    //}

}