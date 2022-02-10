package com.anynoti.oauth2;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.anynoti.domain.user.User;
import com.anynoti.domain.user.UserRepository;
import com.anynoti.enums.ProviderType;
import com.anynoti.oauth2.provider.JwtTokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@SpringBootTest
class JwtTokenProviderTest {

    public static final String INVALID_TOKEN = "invalid.token.123";
    public static final String SECRET_KEY = "58a5dc8cb51f34986058f21ce38ee9fecb91bfe7e9e344131d3a0877317f4e533b738b93791db032918c599da59cccd6b2d148cd4023268c827bc3a534b74e";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void createToken() throws Exception {
        //given
        String providerId = "providerId";
        ProviderType providerType = ProviderType.GOOGLE;

        //when
        String token = jwtTokenProvider.createToken(providerId, providerType);

        //then
        assertThat(token).isNotBlank();
    }

    @Test
    void extractAuthentication() throws JsonProcessingException {
        //given
        String providerId = "providerId";
        ProviderType providerType = ProviderType.GOOGLE;

        User user = User.builder()
            .providerId(providerId)
            .providerType(providerType)
            .build();

        User savedUser = userRepository.save(user);
        String token = jwtTokenProvider.createToken(providerId, providerType);

        //when
        Authentication authentication = jwtTokenProvider.extractAuthentication(token);

        //TODO: 권한계층 설계 후 수정
        Iterator<? extends GrantedAuthority> iterator = (authentication.getAuthorities()).iterator();
        String role = iterator.next().getAuthority();

        //then
        assertAll(
            () -> assertThat(role).isEqualTo("ROLE_USER"),
            () -> assertThat(authentication.getName()).isEqualTo("providerId")
        );

    }

    @Test
    void isValidToken() throws Exception {
        //given
        String providerId = "providerId";
        ProviderType providerType = ProviderType.GOOGLE;

        //when
        String token = jwtTokenProvider.createToken(providerId, providerType);

        //then
        assertThat(jwtTokenProvider.isValidToken(token)).isTrue();
    }

    @Test
    void isNotValidToken() throws Exception {
        //given
        LocalDateTime now = LocalDateTime.now().minusDays(5L);
        LocalDateTime expiredAt = now.minusDays(4L);

        String expiredKey = Jwts.builder()
            .setClaims(Jwts.claims().setSubject("social identification"))
            .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
            .setExpiration(Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
            .compact();

        //then
        assertAll(
            () -> assertThat(jwtTokenProvider.isValidToken(INVALID_TOKEN)).isFalse(),
            () -> assertThat(jwtTokenProvider.isValidToken(expiredKey)).isFalse()
        );
    }

}