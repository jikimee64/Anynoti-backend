package com.anynoti.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.anynoti.animation.application.AnimationService;
import com.anynoti.animation.dto.AnimationWrapper;
import com.anynoti.animation.dto.request.PatchAnimationRequest;
import com.anynoti.animation.dto.response.AnimationResponse;
import com.anynoti.documentation.AnimationDocumentation;
import com.anynoti.dto.MockMvcDto;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class AnimationControllerTest extends BaseControllerTest {

    private static final String PREFIX_URL = "/animations";

    @MockBean
    private AnimationService animationService;

    @ParameterizedTest
    @MethodSource("com.anynoti.fixture.AnimationFixture#findTodoAnimationsResponse")
    void findTodoAnimations(List<AnimationResponse> animationResponses) throws Exception {

        given(animationService.findTodoAnimations(any(), any())).willReturn(animationResponses);

        AnimationWrapper<List<AnimationResponse>> animationWrapper = AnimationWrapper.<List<AnimationResponse>>builder()
            .content(animationResponses)
            .build();

        String responseBody = objectMapper.writeValueAsString(animationWrapper);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("kind", "todo");

        this.doGet(PREFIX_URL + "/todos", params, responseBody)
            .andDo(AnimationDocumentation.findTodoAnimations());
    }

    @ParameterizedTest
    @MethodSource("com.anynoti.fixture.AnimationFixture#findAnimationsResponse")
    void findAnimations(List<AnimationResponse> animationResponses) throws Exception {

        given(animationService.findAnimationsBySearch(any())).willReturn(animationResponses);

        AnimationWrapper<List<AnimationResponse>> animationWrapper = AnimationWrapper.<List<AnimationResponse>>builder()
            .content(animationResponses)
            .build();

        String responseBody = objectMapper.writeValueAsString(animationWrapper);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "any");

        this.doGet(PREFIX_URL + "/search", params, responseBody)
            .andDo(AnimationDocumentation.findAnimations());
    }

    @Test
    void findDetailAnimations() throws Exception {
        AnimationResponse animationResponse = AnimationResponse.builder()
            .id(1L)
            .title("??????")
            .thumbnail("https://img.seoul.co.kr/img/upload/2017/07/14/SSI_20170714170426_O2.jpg")
            .recent_episode(1)
            .recent_datetime(LocalDateTime.of(2022, 2, 3, 13, 0, 0))
            .memo("?????????")
            .liked(true)
            .notied(false)
            .build();

        given(animationService.findDetailAnimations(any(), any())).willReturn(animationResponse);

        String responseBody = objectMapper.writeValueAsString(animationResponse);

        this.doGet(PREFIX_URL + "/1", new LinkedMultiValueMap<>(), responseBody)
            .andDo(AnimationDocumentation.findDetailAnimations());
    }

    @Test
    void addTodoAnimations() throws Exception {
        this.doPost(
                MockMvcDto.of(PREFIX_URL + "/todos/1", "", "",
                    status().isNoContent())
            )
            .andDo(AnimationDocumentation.addTodoAnimations());
    }

    @Test
    void patchAnimation() throws Exception {
        PatchAnimationRequest patchAnimationRequest = new PatchAnimationRequest();
        patchAnimationRequest.setMemo("?????????@@");
        patchAnimationRequest.setLiked(true);
        patchAnimationRequest.setNotied(false);

        AnimationResponse animationResponse = AnimationResponse.builder()
            .id(1L)
            .title("??????")
            .thumbnail("https://img.seoul.co.kr/img/upload/2017/07/14/SSI_20170714170426_O2.jpg")
            .recent_episode(1)
            .memo("?????????@@")
            .liked(true)
            .notied(false)
            .build();

        given(animationService.patchAnimation(any(), any(), any())).willReturn(animationResponse);

        String requestBody = objectMapper.writeValueAsString(patchAnimationRequest);
        String responseBody = objectMapper.writeValueAsString(animationResponse);

        this.doPatch(
                MockMvcDto.of(PREFIX_URL + "/todos/1", requestBody, responseBody,
                    status().isOk())
            )
            .andDo(AnimationDocumentation.patchAnimation());
    }

}