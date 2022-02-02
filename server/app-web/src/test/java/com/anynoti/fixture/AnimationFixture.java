package com.anynoti.fixture;

import com.anynoti.animation.dto.response.AnimationResponse;
import java.util.Arrays;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

/**
 * Junit5의 MethodSource 사용
 */
public class AnimationFixture {

    static Stream<Arguments> findTodoAnimationsResponse() {
        return Stream.of(
            Arguments.of(Arrays.asList(
                AnimationResponse.builder()
                    .id(2)
                    .thumbnail("https://img.seoul.co.kr/img/upload/2017/07/14/SSI_20170714170426_O2.jpg")
                    .title("애니제목1")
                    .recent_episode(12)
                    .end(true)
                    .build(),
                AnimationResponse.builder()
                    .id(2)
                    .thumbnail("https://img.seoul.co.kr/img/upload/2017/07/14/SSI_20170714170426_O2.jpg")
                    .title("애니제목1")
                    .recent_episode(4)
                    .end(false)
                    .build()
            ))
        );
    }

    static Stream<Arguments> findAnimationsResponse() {
        return Stream.of(
            Arguments.of(Arrays.asList(
                AnimationResponse.builder()
                    .id(1)
                    .thumbnail("https://img.seoul.co.kr/img/upload/2017/07/14/SSI_20170714170426_O2.jpg")
                    .title("애니제목1")
                    .build(),
                AnimationResponse.builder()
                    .id(2)
                    .thumbnail("https://img.seoul.co.kr/img/upload/2017/07/14/SSI_20170714170426_O2.jpg")
                    .title("애니제목2")
                    .build()
            ))
        );
    }

}