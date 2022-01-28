package com.anynoti.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.anynoti.animation.application.AnimationService;
import com.anynoti.animation.dto.AnimationWrapper;
import com.anynoti.animation.dto.response.AnimationResponse;
import com.anynoti.documentation.AnimationDocumentation;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class AnimationController extends BaseControllerTest{

    private static final String PREFIX_URL = "/animations";

    @MockBean
    private AnimationService animationService;

    @ParameterizedTest
    @MethodSource("com.anynoti.fixture.AnimationFixture#findTodoAnimationsResponse")
    void findTodoAnimations(List<AnimationResponse> animationResponses) throws Exception {

        given(animationService.findTodoAnimations(any())).willReturn(animationResponses);

        AnimationWrapper<List<AnimationResponse>> animationWrapper = AnimationWrapper.<List<AnimationResponse>>builder()
            .content(animationResponses)
            .count(animationResponses.size())
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

        given(animationService.findAnimations(any())).willReturn(animationResponses);

        AnimationWrapper<List<AnimationResponse>> animationWrapper = AnimationWrapper.<List<AnimationResponse>>builder()
            .content(animationResponses)
            .count(animationResponses.size())
            .build();

        String responseBody = objectMapper.writeValueAsString(animationWrapper);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "any");

        this.doGet(PREFIX_URL + "/search", params, responseBody)
            .andDo(AnimationDocumentation.findAnimations());
    }

}