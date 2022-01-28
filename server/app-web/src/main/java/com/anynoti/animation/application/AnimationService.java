package com.anynoti.animation.application;

import com.anynoti.animation.dto.request.AddAnimationRequest;
import com.anynoti.animation.dto.request.PatchAnimationRequest;
import com.anynoti.animation.dto.request.PatchBookMarkRequest;
import com.anynoti.animation.dto.request.PatchNotificationRequest;
import com.anynoti.animation.dto.response.AnimationResponse;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AnimationService {

    public List<AnimationResponse> findTodoAnimations(String providerId) {
        return Collections.EMPTY_LIST;
    }

    public List<AnimationResponse> findAnimations(String title) {
        return Collections.EMPTY_LIST;
    }

    public void AddAnimations(AddAnimationRequest addAnimationRequest) {

    }

    public AnimationResponse findDetailAnimations() {
        return AnimationResponse.builder().build();
    }

    public void patchAnimationOfBookMark(PatchBookMarkRequest patchBookMarkRequest) {

    }

    public void patchAnimationOfNotification(PatchNotificationRequest patchNotificationRequest){

    }

    public AnimationResponse patchAnimation(PatchAnimationRequest patchAnimationRequest) {
        return AnimationResponse.builder().build();
    }
}