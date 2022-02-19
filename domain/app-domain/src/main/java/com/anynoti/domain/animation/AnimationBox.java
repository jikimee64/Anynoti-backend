package com.anynoti.domain.animation;

import com.anynoti.domain.common.BaseTimeEntity;
import com.anynoti.domain.common.BooleanToYNConverter;
import com.anynoti.domain.user.User;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
public class AnimationBox extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String memo;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(nullable = false, columnDefinition = "N")
    private Boolean liked;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(nullable = false, columnDefinition = "N")
    private Boolean notied;

    @JoinColumn(name = "animation_id", nullable = false, foreignKey = @ForeignKey(name = "FK_animationbox_to_animation"))
    @ManyToOne
    private Animation animation;

    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_animationbox_to_user"))
    @ManyToOne
    private User user;

    @Builder
    public AnimationBox(Animation animation, User user) {
        this.animation = animation;
        this.user = user;
    }

    public void setAnimation(Animation animation) {
        if (this.animation != null) {
            this.animation.getAnimationBoxes().remove(this);
        }
        this.animation = animation;
        if (!animation.getAnimationBoxes().contains(this)) {
            animation.getAnimationBoxes().add(this);
        }
    }

    public void setUser(User user) {
        if (this.user != null) {
            this.user.getAnimationBoxes().remove(this);
        }
        this.user = user;
        if (!user.getAnimationBoxes().contains(this)) {
            user.getAnimationBoxes().add(this);
        }
    }

    public void changeDetail(String memo, boolean notied, boolean liked) {
        this.memo = memo;
        this.liked = liked;
        this.notied = notied;
    }

}