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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String googleId;

    @Column(nullable = false, length = 50)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    public User(String googleId, String email, ProviderType providerType) {
        Assert.notNull(googleId, "googleId must not be null");
        Assert.notNull(email, "email must not be null");
        Assert.notNull(providerType, "providerType must not be null");

        this.googleId = googleId;
        this.email = email;
        this.providerType = providerType;
    }

}