package com.anynoti.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.test.web.servlet.ResultMatcher;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MockMvcDto {
    private String path;
    private String requestBody;
    private String responseBody;
    private ResultMatcher resultMatcher;

    public static MockMvcDto of(String path, String requestBody,
        String responseBody, ResultMatcher resultMatcher){
        return MockMvcDto.builder()
            .path(path)
            .requestBody(requestBody)
            .responseBody(responseBody)
            .resultMatcher(resultMatcher)
            .build();
    }
}