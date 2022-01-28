package com.anynoti.domain.animation;

import com.anynoti.domain.common.BooleanToYNConverter;
import com.anynoti.domain.user.ProviderType;
import com.anynoti.domain.common.BaseTimeEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Animation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    private String thumbnail;

    @Column(nullable = false)
    private Integer recentEpisode;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(nullable = false, columnDefinition = "N")
    private boolean isEnd;

    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    @OneToMany(mappedBy = "animation", cascade = CascadeType.ALL)
    private List<AnimationBox> animationBoxes = new ArrayList<>();

    @Builder
    public Animation(String title, String thumbnail, Integer recentEpisode, ProviderType providerType) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.recentEpisode = recentEpisode;
        this.providerType = providerType;
    }

    public void addAnimationBox(AnimationBox animationBox){
        this.animationBoxes.add(animationBox);
        if(animationBox.getAnimation() != this){
            animationBox.setAnimation(this);
        }
    }

}
