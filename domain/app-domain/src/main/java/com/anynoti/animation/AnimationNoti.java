package com.anynoti.animation;

import com.anynoti.common.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class AnimationNoti extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String message;

    @Column(nullable = false, length = 255)
    private String url;

    @JoinColumn(name = "animation_box", nullable = false, foreignKey = @ForeignKey(name = "FK_animationnoti_to_animation_box"))
    @ManyToOne
    private AnimationBox animationBox;

    @Builder
    public AnimationNoti(String message, String url, AnimationBox animationBox) {
        this.message = message;
        this.url = url;
        this.animationBox = animationBox;
    }

}