package com.anynoti.animation.presentation;

import com.anynoti.animation.application.AnimationService;
import com.anynoti.animation.dto.AnimationWrapper;
import com.anynoti.animation.dto.response.AnimationResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/animations")
public class AnimationController {

    private final AnimationService animationService;

    public AnimationController(AnimationService animationService) {
        this.animationService = animationService;
    }

    //TODO: @LoginUser
    @GetMapping("/todos")
    public ResponseEntity<AnimationWrapper> findTodoAnimations(
        @RequestParam String kind //TODO: enum(todo, noti, like)
    ){
        List<AnimationResponse> animationResponses = animationService.findTodoAnimations("");
        return ResponseEntity.ok(AnimationWrapper.<List<AnimationResponse>>builder()
            .content(animationResponses)
            .count(animationResponses.size())
            .build()
        );
    }

    @GetMapping("/search")
    public ResponseEntity<AnimationWrapper> findAnimations(
        @RequestParam String title
    ){
        List<AnimationResponse> animationResponses = animationService.findAnimations(title);
        return ResponseEntity.ok(
            AnimationWrapper.<List<AnimationResponse>>builder()
            .content(animationResponses)
            .count(animationResponses.size())
            .build()
        );
    }

}