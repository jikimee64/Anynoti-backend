package com.anynoti.acceptance.animations;

import static org.assertj.core.api.Assertions.assertThat;

import com.anynoti.acceptance.AcceptanceTest;
import com.anynoti.animation.dto.response.AnimationResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AnimationsAcceptanceTest extends AcceptanceTest {

    private static final String PREFIX_URL = "/animations";

    @BeforeEach
    void beforeEach(){
    }

    @Test
    void TODO조회_성공() throws Exception {

        //given
        Map<String, String> params = new HashMap<>();
        params.put("kind","todo");

        //when
        List<AnimationResponse> animationResponse = callApiGet(PREFIX_URL + "/todos", params)
            .jsonPath().getList("animations", AnimationResponse.class);

        //then
        todosAssertquals(animationResponse);

    }

    @Test
    void TODO조회_부정확한_kindr값을_보해도_디폴트값으로_TODO_성공() throws Exception {

        //given
        Map<String, String> params = new HashMap<>();
        params.put("kind","kwc");

        //when
        List<AnimationResponse> animationResponse = callApiGet(PREFIX_URL + "/todos", params)
            .jsonPath().getList("animations", AnimationResponse.class);

        //then
        todosAssertquals(animationResponse);
    }

    private void todosAssertquals(List<AnimationResponse> animationResponse){
        assertThat(animationResponse.get(0).getId()).isNotNull();
        assertThat(animationResponse.get(0).getThumbnail()).isNotBlank();
        assertThat(animationResponse.get(0).getTitle()).isNotBlank();
        assertThat(animationResponse.get(0).getRecent_episode()).isNotNull();
        assertThat(animationResponse.get(0).getEnd()).isNotNull();
    }

}