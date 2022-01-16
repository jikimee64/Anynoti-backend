package com.anynoti.user;

import com.mysema.commons.lang.Assert;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String providerId;
    @Column(nullable = false, length = 10)
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    @Builder
    public User(String providerId, ProviderType providerType) {
        Assert.notNull(providerId, "providerId must not be null");
        Assert.notNull(providerType, "providerType must not be null");

        this.providerId = providerId;
        this.providerType = providerType;
    }

}