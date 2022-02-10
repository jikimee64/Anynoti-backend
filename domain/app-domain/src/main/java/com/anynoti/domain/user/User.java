package com.anynoti.domain.user;

import com.anynoti.domain.animation.AnimationBox;
import com.anynoti.enums.ProviderType;
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

import com.anynoti.domain.common.BaseTimeEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, updatable = false, unique = true)
    private String providerId;

    //@Column(columnDefinition = "ENUM('GOOGLE')")
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    @OneToMany(mappedBy = "user")
    private List<AnimationBox> animationBoxes = new ArrayList<>();

    @Builder
    public User(String providerId, ProviderType providerType) {
        this.providerId = providerId;
        this.providerType = providerType;
    }

    public void addAnimationBox(AnimationBox animationBox){
        this.animationBoxes.add(animationBox);
        if(animationBox.getUser() != this){
            animationBox.setUser(this);
        }
    }

}