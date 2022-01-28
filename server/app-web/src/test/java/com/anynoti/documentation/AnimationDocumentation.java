package com.anynoti.documentation;

import static com.anynoti.documentation.utils.DocumentationUtils.getDocumentRequest;
import static com.anynoti.documentation.utils.DocumentationUtils.getDocumentResponse;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.BOOLEAN;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

public class AnimationDocumentation {

    private final static String AUTHORIZATION = "Authorization";

    public static RestDocumentationResultHandler findTodoAnimations() {
        return document("/animation/todos",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(headerWithName(AUTHORIZATION).description("Jwt 토큰")),
            requestParameters(
                parameterWithName("kind").description("todo 필터(todo, noti, like)")
            ),
            responseFields(
                fieldWithPath("animations.[].id").type(NUMBER).description(""),
                fieldWithPath("animations.[].thumbnail").type(STRING).description(""),
                fieldWithPath("animations.[].title").type(STRING).description(""),
                fieldWithPath("animations.[].recentEpisode").type(NUMBER).description(""),
                fieldWithPath("animations.[].end").type(BOOLEAN).description(""),
                fieldWithPath("count").type(NUMBER).description("")
            )
        );
    }

    public static RestDocumentationResultHandler findAnimations() {
        return document("/animation/search",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(headerWithName(AUTHORIZATION).description("Jwt 토큰")),
            requestParameters(
                parameterWithName("title").description("검색어(제목)")
            ),
            responseFields(
                fieldWithPath("animations.[].id").type(NUMBER).description(""),
                fieldWithPath("animations.[].thumbnail").type(STRING).description(""),
                fieldWithPath("animations.[].title").type(STRING).description(""),
                fieldWithPath("count").type(NUMBER).description("")
            )
        );
    }

    public static RestDocumentationResultHandler findDetailAnimations() {
        return document("/animation/detail",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(headerWithName(AUTHORIZATION).description("Jwt 토큰")),
            responseFields(
                fieldWithPath("id").type(NUMBER).description(""),
                fieldWithPath("title").type(STRING).description(""),
                fieldWithPath("thumbnail").type(STRING).description(""),
                fieldWithPath("recentEpisode").type(NUMBER).description(""),
                fieldWithPath("bookMarked").type(BOOLEAN).description(""),
                fieldWithPath("notied").type(BOOLEAN).description(""),
                fieldWithPath("memo").type(STRING).description("")
            )
        );
    }

    public static RestDocumentationResultHandler addTodoAnimations() {
        return document("/animation/addTodo",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(headerWithName(AUTHORIZATION).description("Jwt 토큰")),
            requestFields(
                fieldWithPath("id").description("animation index")
            )
        );
    }

    public static RestDocumentationResultHandler patchAnimation() {
        return document("/animation/patchTodo",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(headerWithName(AUTHORIZATION).description("Jwt 토큰")),
            requestFields(
                fieldWithPath("memo").description("")
            ),
            responseFields(
                fieldWithPath("id").type(NUMBER).description(""),
                fieldWithPath("title").type(STRING).description(""),
                fieldWithPath("thumbnail").type(STRING).description(""),
                fieldWithPath("recentEpisode").type(NUMBER).description(""),
                fieldWithPath("bookMarked").type(BOOLEAN).description(""),
                fieldWithPath("notied").type(BOOLEAN).description(""),
                fieldWithPath("memo").type(STRING).description("")
            )
        );
    }

    public static RestDocumentationResultHandler patchAnimationOfBookMark() {
        return document("/animation/patchBookMarkAnimation",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(headerWithName(AUTHORIZATION).description("Jwt 토큰")),
            requestFields(
                fieldWithPath("bookmarked").description("노티 표시여부")
            )
        );
    }

    public static RestDocumentationResultHandler patchAnimationOfNotification() {
        return document("/animation/patchNotificationAnimation",
            getDocumentRequest(),
            getDocumentResponse(),
            requestHeaders(headerWithName(AUTHORIZATION).description("Jwt 토큰")),
            requestFields(
                fieldWithPath("notied").description("북마크 표시여부")
            )
        );
    }

}