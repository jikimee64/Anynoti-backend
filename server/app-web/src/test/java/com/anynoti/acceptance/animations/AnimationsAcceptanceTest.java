package com.anynoti.acceptance.animations;

import static org.assertj.core.api.Assertions.assertThat;

import com.anynoti.acceptance.AcceptanceTest;
import com.anynoti.animation.dto.request.PatchAnimationRequest;
import com.anynoti.animation.dto.response.AnimationResponse;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AnimationsAcceptanceTest extends AcceptanceTest {

    private static final String PREFIX_URL = "/animations";

    @BeforeEach
    void beforeEach() {
    }

    @Test
    void TODO조회_성공() throws Exception {
        //given
        Map<String, String> params = new HashMap<>();
        params.put("kind", "todo");

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
        params.put("kind", "kwc");

        //when
        List<AnimationResponse> animationResponse = callApiGet(PREFIX_URL + "/todos", params)
            .jsonPath().getList("animations", AnimationResponse.class);

        //then
        todosAssertquals(animationResponse);
    }

    private void todosAssertquals(List<AnimationResponse> animationResponse) {
        assertThat(animationResponse.get(0).getId()).isNotNull();
        assertThat(animationResponse.get(0).getThumbnail()).isNotBlank();
        assertThat(animationResponse.get(0).getTitle()).isNotBlank();
        assertThat(animationResponse.get(0).getRecent_episode()).isNotNull();
        assertThat(animationResponse.get(0).getEnd()).isNotNull();
    }

    @Test
    void 애니검색_성공() throws Exception {
        //given
        Map<String, String> params = new HashMap<>();
        params.put("title", "프린세스");

        //when
        List<AnimationResponse> animationResponse = callApiGet(PREFIX_URL + "/search", params)
            .jsonPath().getList("animations", AnimationResponse.class);

        //then
        searchAssertquals(animationResponse);
    }

    private void searchAssertquals(List<AnimationResponse> animationResponse) {
        assertThat(animationResponse.get(0).getId()).isNotNull();
        assertThat(animationResponse.get(0).getThumbnail()).isNotBlank();
        assertThat(animationResponse.get(0).getTitle()).isNotBlank();
    }

    @Test
    void 애니검색_조회된값이없음_성공() throws Exception {
        //given
        Map<String, String> params = new HashMap<>();
        params.put("title", "이런제목이 있을수가!!");

        //when
        List<AnimationResponse> animationResponse = callApiGet(PREFIX_URL + "/search", params)
            .jsonPath().getList("animations", AnimationResponse.class);

        //then
        assertThat(animationResponse.size()).isEqualTo(0L);
    }

    @Test
    void 애니상세조회_성공() throws Exception {
        //when
        AnimationResponse animationResponse = callApiGet(PREFIX_URL + "/1", new HashMap<>())
            .jsonPath().getObject("", AnimationResponse.class);

        //then
        detailsAssertEquals(animationResponse);
    }

    @Test
    void 애니TODO추가_성공() throws Exception {
        //when
        Response response = callApiPost(PREFIX_URL + "/todos/4", null)
            .response();

        int statusCode = response.getStatusCode();
        //then
        assertThat(statusCode).isEqualTo(204);
    }

    @Test
    void 애니TODO수정_성공() throws Exception {
        //given
        PatchAnimationRequest patchAnimationRequest = new PatchAnimationRequest();
        patchAnimationRequest.setMemo("메모수정");
        patchAnimationRequest.setLiked(true);
        patchAnimationRequest.setNotied(true);

        //when
        AnimationResponse animationResponse = callApiPatch(
            PREFIX_URL + "/todos/4",
            patchAnimationRequest
        ).jsonPath().getObject(".", AnimationResponse.class);

        //then
        detailsAssertEquals(animationResponse);
    }

    private void detailsAssertEquals(AnimationResponse animationResponse) {
        assertThat(animationResponse.getId()).isNotNull();
        assertThat(animationResponse.getThumbnail()).isNotBlank();
        assertThat(animationResponse.getTitle()).isNotBlank();
        assertThat(animationResponse.getRecent_episode()).isNotNull();
        assertThat(animationResponse.getRecent_datetime()).isNotNull();
        assertThat(animationResponse.getMemo()).isNotBlank();
        assertThat(animationResponse.getLiked()).isNotNull();
        assertThat(animationResponse.getNotied()).isNotNull();
    }

}