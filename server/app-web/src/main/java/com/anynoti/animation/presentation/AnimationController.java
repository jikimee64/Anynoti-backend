package com.anynoti.animation.presentation;

import com.anynoti.AuthenticationPrincipal;
import com.anynoti.LoginUser;
import com.anynoti.animation.application.AnimationService;
import com.anynoti.animation.dto.AnimationWrapper;
import com.anynoti.animation.dto.request.PatchAnimationRequest;
import com.anynoti.animation.dto.response.AnimationResponse;
import com.anynoti.enums.appweb.SearchKind;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/todos")
    public ResponseEntity<AnimationWrapper> findTodoAnimations(
        @AuthenticationPrincipal LoginUser loginUser,
        @RequestParam(name = "kind", defaultValue = "TODO", required = false) SearchKind searchKind
    ) {
        List<AnimationResponse> animationResponses = animationService.findTodoAnimations(loginUser,
            searchKind);
        return ResponseEntity.ok(
            AnimationWrapper.<List<AnimationResponse>>builder()
                .content(animationResponses)
                .build()
        );
    }

    @GetMapping("/search")
    public ResponseEntity<AnimationWrapper> findAnimations(
        @RequestParam String title
    ) {
        List<AnimationResponse> animationResponses = animationService.findAnimationsBySearch(title);
        return ResponseEntity.ok(
            AnimationWrapper.<List<AnimationResponse>>builder()
                .content(animationResponses)
                .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimationResponse> findDetailAnimations(
        @AuthenticationPrincipal LoginUser loginUser,
        @PathVariable("id") Long id
    ) {
        AnimationResponse animationResponses = animationService.findDetailAnimations(loginUser, id);
        return ResponseEntity.ok(animationResponses);
    }

    @PostMapping("/todos/{id}")
    public ResponseEntity<Void> addTodoAnimations(
        @AuthenticationPrincipal LoginUser loginUser,
        @PathVariable Long id
    ) {
        animationService.addTodoAnimations(loginUser, id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/todos/{id}")
    public ResponseEntity<AnimationResponse> patchAnimation(
        @PathVariable Long id,
        @AuthenticationPrincipal LoginUser loginUser,
        @RequestBody @Valid PatchAnimationRequest patchAnimationRequest
    ) {
        AnimationResponse animationResponse = animationService.patchAnimation(
            id, loginUser, patchAnimationRequest);
        return ResponseEntity.ok(animationResponse);
    }

}