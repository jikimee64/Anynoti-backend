package com.anynoti.controller;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.anynoti.dto.MockMvcDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@ComponentScan({"com.anynoti"}) //없을시 LoginController 404 에러
@Import({HttpEncodingAutoConfiguration.class}) //한글깨짐 방지
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ActiveProfiles({"test"})
public abstract class BaseControllerTest {

    //TODO: 2월 15일 22시 45분 발급, 한달짜리 테스트 토큰
    private static final String JWT_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJwcm92aWRlcklkXCI6XCIxMDA4ODU1MDgwNTMxNzE2ODI1MTdcIixcInByb3ZpZGVyVHlwZVwiOlwiR09PR0xFXCJ9IiwiaWF0IjoxNjQ0OTMyNjkxLCJleHAiOjE4MDA0NTI2OTF9.SME1C1JdkIyJiWBeROrghW0EyoaCiSLTgh5euaWhBevaOG0wXZNhagl4Kwt1WBk2jJxxhVxJTX1C_amtFvFeqA";

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;

    @BeforeEach
    public void before(WebApplicationContext ctx,
        RestDocumentationContextProvider restDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
            .apply(documentationConfiguration(restDocumentationContextProvider))
            .addFilters(new CharacterEncodingFilter("UTF-8", true))
            .alwaysDo(print())
            .build();
    }

    protected ResultActions doGet(String path, MultiValueMap<String, String> params,
        String responseBody) throws Exception {
        return this.mockMvc.perform(get(path)
                .params(params)
                .header(AUTHORIZATION, "Bearer " + JWT_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(responseBody));
    }

    protected ResultActions doPost(MockMvcDto mockMvcDto) throws Exception {
        return this.mockMvc.perform(post(mockMvcDto.getPath())
                .content(mockMvcDto.getRequestBody())
                .header(AUTHORIZATION, "Bearer " + JWT_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(mockMvcDto.getResultMatcher())
            .andExpect(content().string(mockMvcDto.getResponseBody()));
    }

    protected ResultActions doPatch(MockMvcDto mockMvcDto) throws Exception {
        return this.mockMvc.perform(patch(mockMvcDto.getPath())
                .content(mockMvcDto.getRequestBody())
                .header(AUTHORIZATION, "Bearer " + JWT_TOKEN)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
            )
            .andDo(print())
            .andExpect(mockMvcDto.getResultMatcher())
            .andExpect(content().string(mockMvcDto.getResponseBody()));
    }

}