package com.anynoti.documentation.utils;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;

public interface DocumentationUtils {

    static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(
            prettyPrint()
        );
    }

    static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(
            prettyPrint()
        );
    }

    static ResponseFieldsSnippet getErrorResponseFieldsWithFieldErrors() {
        return responseFields(
            fieldWithPath("status").description("HTTP 상태코드"),
//            fieldWithPath("").description(),
            fieldWithPath("message").description("에러 메시지"),
            subsectionWithPath("errors").description("필드 에러").optional()
        );
    }

}

