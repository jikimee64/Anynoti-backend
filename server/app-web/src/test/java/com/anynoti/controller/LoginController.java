package com.anynoti.controller;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.anynoti.documentation.LoginDocumentation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


public class LoginController extends BaseControllerTest{

    private Map<String, String> map = new HashMap();

    @BeforeEach
    void init(){
        map = new HashMap();
        map.put("accessToken",
            "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJwcm92aWRlcklkXCI6XCIxMTgyOTEzNjQ4MzcwMjcxMDU4MDBcIixcInByb3ZpZGVyVHlwZVwiOlwiR09PR0xFXCJ9IiwiaWF0IjoxNjQzMzU1MTUyLCJleHAiOjE2NDM0NDE1NTJ9.CfEEIG5Sd-z6ABc1Rp7o3K5oKgwvRZhflA0O3CxcMap532yoD0CYmY39krk1j0FZcOTreHi72afhZKIeBGsjSA");
    }

    @Test
    void login() throws Exception {
        String responseBody = objectMapper.writeValueAsString(map);

        this.mockMvc.perform(get("/oauth2/authorization/google")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(responseBody))
            .andDo(LoginDocumentation.login());
    }

    @RestController
    public static class TestLoginController {

        @GetMapping("/oauth2/authorization/google")
        public Map<String, String> login() {
            Map<String, String> map = new HashMap();
            map.put("accessToken",
                "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJwcm92aWRlcklkXCI6XCIxMTgyOTEzNjQ4MzcwMjcxMDU4MDBcIixcInByb3ZpZGVyVHlwZVwiOlwiR09PR0xFXCJ9IiwiaWF0IjoxNjQzMzU1MTUyLCJleHAiOjE2NDM0NDE1NTJ9.CfEEIG5Sd-z6ABc1Rp7o3K5oKgwvRZhflA0O3CxcMap532yoD0CYmY39krk1j0FZcOTreHi72afhZKIeBGsjSA");

            return map;
        }

    }

}