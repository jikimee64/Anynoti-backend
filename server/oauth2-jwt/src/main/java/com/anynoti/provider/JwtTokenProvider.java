package com.anynoti.provider;

import com.anynoti.dto.JwtPayloadDto;
import com.anynoti.user.ProviderType;
import com.anynoti.user.User;
import com.anynoti.user.UserRepository;
import com.anynoti.web.AuthorizationExtractor;
import com.anynoti.web.AuthorizationType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.micrometer.core.instrument.util.StringUtils;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret-key}")
    private String secretKey;
    @Value("${jwt.token.expire-seconds}")
    private long expireSeconds;
    private UserRepository userRepository;
    private ObjectMapper objectMapper;

    public JwtTokenProvider(UserRepository userRepository,
        ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    public String createToken(String providerId, ProviderType providerType)
        throws JsonProcessingException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plus(expireSeconds, ChronoUnit.MILLIS);
        JwtPayloadDto jwtPayloadDto = JwtPayloadDto.builder()
            .providerId(providerId)
            .providerType(providerType)
            .build();

        Claims claims = Jwts.claims().setSubject(objectMapper.writeValueAsString(jwtPayloadDto));

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
            .setExpiration(Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact();
    }

    public String extractToken(HttpServletRequest request) {
        return AuthorizationExtractor.extract(request, AuthorizationType.BEARER);
    }

    public Authentication extractAuthentication(String jwtToken) throws JsonProcessingException {
        JwtPayloadDto jwtPayloadDto = extractJwtPayload(jwtToken);
        String providerId = jwtPayloadDto.getProviderId();
        String provider = jwtPayloadDto.getProviderType().getProvider();

        User user = userRepository.findUserByProviderId(providerId)
            .orElseThrow(
                () -> new AuthenticationCredentialsNotFoundException(
                    "credential error: 존재하지 않는 회원입니다."));

        Map<String, Object> attribute = createAttribute(user, provider);

        //TODO: 권한계층 설계 후 수정
        OAuth2User authenticatedUser = new DefaultOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
            attribute, provider);

        //TODO: 권한계층 설계 후 수정
        return new OAuth2AuthenticationToken(authenticatedUser,
            Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
            provider);
    }

    private JwtPayloadDto extractJwtPayload(String jwtToken) throws JsonProcessingException {
        isValidToken(jwtToken);
        String subject = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(jwtToken)
            .getBody()
            .getSubject();

        return objectMapper.readValue(subject, JwtPayloadDto.class);
    }

    private Map<String, Object> createAttribute(User user, String provider) {
        Map<String, Object> attribute = new HashMap<>();

        attribute.put(provider, user.getProviderId());
        return attribute;
    }

    public boolean isValidToken(String jwtToken) {
        if (StringUtils.isNotEmpty(jwtToken)) {
            try {
                Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
                return true;
            } catch (ExpiredJwtException e) {
                log.error("Expired JWT token", e.getMessage());
            } catch (UnsupportedJwtException e) {
                log.error("Unsupported JWT token", e.getMessage());
            } catch (IllegalArgumentException e) {
                log.error("JWT claims string is empty.", e.getMessage());
            } catch (JwtException e) {
                log.error("Invalid JWT token", e.getMessage());
            }
        }
        return false;
    }

}