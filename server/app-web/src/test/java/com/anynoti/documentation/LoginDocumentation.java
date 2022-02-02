package com.anynoti.documentation;

import static com.anynoti.documentation.utils.DocumentationUtils.getDocumentRequest;
import static com.anynoti.documentation.utils.DocumentationUtils.getDocumentResponse;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

public class LoginDocumentation {

    public static RestDocumentationResultHandler login() {
        return document("/oauth2/authorization/google",
            getDocumentRequest(),
            getDocumentResponse(),
            responseFields(
                fieldWithPath("access_token").type(STRING).description("Jwt access token")
            )
        );
    }

}