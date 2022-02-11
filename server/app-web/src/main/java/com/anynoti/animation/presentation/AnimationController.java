package com.anynoti.animation.presentation;

import com.anynoti.AuthenticationPrincipal;
import com.anynoti.LoginUser;
import com.anynoti.animation.application.AnimationService;
import com.anynoti.animation.dto.AnimationWrapper;
import com.anynoti.animation.dto.request.AddAnimationRequest;
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
    ){
        System.out.println("Lopgin" + loginUser.getProviderId());
        List<AnimationResponse> animationResponses = animationService.findTodoAnimations(loginUser, searchKind);
        return ResponseEntity.ok(AnimationWrapper.<List<AnimationResponse>>builder()
            .content(animationResponses)
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
            .build()
        );
    }

    //TODO: @AuthenticationPrincipal
    @GetMapping("/{id}")
    public ResponseEntity<AnimationResponse> findDetailAnimations(
        @PathVariable("id") Integer id
    ){
        AnimationResponse animationResponses = animationService.findDetailAnimations();
        return ResponseEntity.ok(animationResponses);
    }

    //TODO: @AuthenticationPrincipal
    @PostMapping("/todos")
    public ResponseEntity<Void> AddAnimations(
        @RequestBody @Valid AddAnimationRequest addAnimationRequest
    ){
        animationService.AddAnimations(addAnimationRequest);
        return ResponseEntity.noContent().build();
    }

    //TODO: @AuthenticationPrincipal
    @PatchMapping("/todos/{id}")
    public ResponseEntity<AnimationResponse> patchAnimation(
        @PathVariable Integer id,
        @RequestBody @Valid PatchAnimationRequest patchAnimationRequest
    ){
        AnimationResponse animationResponse = animationService.patchAnimation(patchAnimationRequest);
        return ResponseEntity.ok(animationResponse);
    }

}