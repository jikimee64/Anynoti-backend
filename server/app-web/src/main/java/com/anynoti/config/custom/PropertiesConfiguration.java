package com.anynoti.config.custom;

import com.anynoti.common.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * JwtProperties 불변성 유지를 위한 수동 Spring Bean 등록
 */
@Configuration
@EnableConfigurationProperties(value = {JwtProperties.class})
public class PropertiesConfiguration {
}