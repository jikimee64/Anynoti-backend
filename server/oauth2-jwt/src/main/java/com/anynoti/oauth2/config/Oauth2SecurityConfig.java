package com.anynoti.oauth2.config;

import com.anynoti.jwt.JwtTokenProvider;
import com.anynoti.oauth2.handler.OAuth2FailureHandler;
import com.anynoti.oauth2.handler.OAuth2SuccessHandler;
import com.anynoti.oauth2.provider.Oauth2CustomProvider;
import com.anynoti.oauth2.service.CustomOAuth2UserService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class Oauth2SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OAuth2FailureHandler oAuth2FailureHandler;

    private static final String[] PUBLIC_URI = {
        "/oauth2/authorization/google",
        "/animation"
    };

    public Oauth2SecurityConfig(JwtTokenProvider jwtTokenProvider,
        CustomOAuth2UserService customOAuth2UserService,
        OAuth2SuccessHandler oAuth2SuccessHandler,
        OAuth2FailureHandler oAuth2FailureHandler) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.customOAuth2UserService = customOAuth2UserService;
        this.oAuth2SuccessHandler = oAuth2SuccessHandler;
        this.oAuth2FailureHandler = oAuth2FailureHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .cors()
            .and()
            .httpBasic().disable()
            .csrf().disable()
            .headers().frameOptions().disable()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(PUBLIC_URI).permitAll()
            .anyRequest().authenticated()
            .and()
            .oauth2Login().loginPage("/oauth2/authorization/google")
            .userInfoEndpoint().userService(customOAuth2UserService)
            .and()
            .successHandler(oAuth2SuccessHandler)
            .failureHandler(oAuth2FailureHandler)
            .and();
//            .addFilterBefore(new JwtTokenFilter(jwtTokenProvider),
//                OAuth2LoginAuthenticationFilter.class);
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(
        OAuth2ClientProperties oAuth2ClientProperties) {

        List<ClientRegistration> registrations = oAuth2ClientProperties.getRegistration().keySet()
            .stream()
            .map(client -> getRegistration(oAuth2ClientProperties, client))
            .filter(Objects::nonNull).collect(Collectors.toList());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    private ClientRegistration getRegistration(OAuth2ClientProperties oAuth2ClientProperties,
        String client) {

        OAuth2ClientProperties.Registration registration = oAuth2ClientProperties.getRegistration()
            .get(client);
        String clientId = registration.getClientId();
        String clientSecret = registration.getClientSecret();

        if (clientId == null) {
            return null;
        }

        switch (client) {
            case "google":
                return Oauth2CustomProvider.GOOGLE.getBuilder(client)
                    .clientId(clientId).clientSecret(clientSecret).build();
        }
        return null;
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        corsConfiguration.addAllowedMethod(HttpMethod.PUT);
        corsConfiguration.addAllowedMethod(HttpMethod.PUT);
        corsConfiguration.addAllowedMethod(HttpMethod.DELETE);
        corsConfiguration.addExposedHeader("Location");
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();
    }

}