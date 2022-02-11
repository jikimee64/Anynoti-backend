package com.anynoti.animation.dto;

import static java.util.stream.Collectors.toList;

import com.anynoti.animation.dto.response.AnimationResponse;
import com.anynoti.domain.animation.Animation;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AnimationGenerator {

    public List<AnimationResponse> toTodosAnimationResponse(List<Animation> animations){
        return animations.stream()
            .map(AnimationResponse::ofTodos)
            .collect(toList());
    }

}