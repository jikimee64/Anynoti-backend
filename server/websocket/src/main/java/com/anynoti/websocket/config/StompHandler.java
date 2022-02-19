package com.anynoti.websocket.config;

import com.anynoti.jwt.JwtPayloadDto;
import com.anynoti.jwt.JwtTokenProvider;
import com.anynoti.websocket.alarm.Scheduler;
import com.anynoti.websocket.common.BeanUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (accessor.getCommand() == StompCommand.CONNECT) {
            String jwtToken = accessor.getFirstNativeHeader("Authorization");
            //TODO: 해당 부분 AuthorizationExtractor에서 메소드 추가후 변경 하기
            if (StringUtils.hasText(jwtToken) && jwtToken.startsWith("Bearer")) {
                jwtToken = jwtToken.substring(6);
            }
            jwtTokenProvider.isValidToken(jwtToken);
            JwtPayloadDto jwtPayloadDto = new JwtPayloadDto();
            try {
                jwtPayloadDto = jwtTokenProvider.extractJwtPayload(jwtToken);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            String providerId = jwtPayloadDto.getProviderId();
            Scheduler scheduler = (Scheduler) BeanUtils.getBean(Scheduler.class);
            scheduler.getProviderIdSet().add(providerId);
        }
        return message;
    }

//    @Override
//    public void postSend(Message message, MessageChannel channel, boolean sent) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//        String sessionId = accessor.getSessionId();
//        switch (Objects.requireNonNull(accessor.getCommand())) {
//            case CONNECT:
//                // 유저가 Websocket으로 connect()를 한 뒤 호출됨
//                break;
//            case DISCONNECT:
//                setProviderId.remove()
//                log.info("DISCONNECT");
//                log.info("sessionId: {}",sessionId);
//                log.info("channel:{}",channel);
//                // 유저가 Websocket으로 disconnect() 를 한 뒤 호출됨 or 세션이 끊어졌을 때 발생함(페이지 이동~ 브라우저 닫기 등)
//                break;
//            default:
//        }
//    }

}