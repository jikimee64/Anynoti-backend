package com.anynoti.animation.dto;

import static java.util.stream.Collectors.toList;

import com.anynoti.animation.dto.response.AnimationResponse;
import com.anynoti.domain.animation.Animation;
import com.anynoti.domain.animation.AnimationBox;
import com.anynoti.domain.animation.query.AnimationDetailQuery;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AnimationGenerator {

    public List<AnimationResponse> toTodosAnimationResponse(List<Animation> animations) {
        return animations.stream()
            .map(AnimationResponse::ofTodos)
            .collect(toList());
    }

    public List<AnimationResponse> toSearchAnimationResponse(List<Animation> animations) {
        return animations.stream()
            .map(AnimationResponse::ofSearch)
            .collect(toList());
    }

    public AnimationResponse toDetailsAnimationResponse(AnimationDetailQuery AnimationDetailQuery) {
        return AnimationResponse.ofDetails(AnimationDetailQuery);
    }

    public AnimationResponse toPatchAnimationResponse(Animation animation,
        AnimationBox animationBox) {
        return AnimationResponse.ofPatch(animation, animationBox);
    }

}