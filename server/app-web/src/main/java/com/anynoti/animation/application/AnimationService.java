package com.anynoti.animation.application;

import com.anynoti.LoginUser;
import com.anynoti.animation.dto.AnimationGenerator;
import com.anynoti.animation.dto.request.AddAnimationRequest;
import com.anynoti.animation.dto.request.PatchAnimationRequest;
import com.anynoti.animation.dto.response.AnimationResponse;
import com.anynoti.domain.animation.Animation;
import com.anynoti.domain.animation.query.AnimationBoxQueryRepository;
import com.anynoti.enums.appweb.SearchKind;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AnimationService {

    private AnimationGenerator animationGenerator;
    private AnimationBoxQueryRepository animationBoxQueryRepository;

    public AnimationService(AnimationGenerator animationGenerator,
        AnimationBoxQueryRepository animationBoxQueryRepository) {
        this.animationGenerator = animationGenerator;
        this.animationBoxQueryRepository = animationBoxQueryRepository;
    }

    public List<AnimationResponse> findTodoAnimations(LoginUser loginUser, SearchKind searchKind) {
        //TODO: Animations의 List 객체도 일급 컬렉션 사용
        List<Animation> animations = animationBoxQueryRepository.findTodoAnimationsClause(
            loginUser.getProviderId(), searchKind
        );

        return animationGenerator.toTodosAnimationResponse(animations);
    }

    public List<AnimationResponse> findAnimations(String title) {
        return Collections.EMPTY_LIST;
    }

    public void AddAnimations(AddAnimationRequest addAnimationRequest) {

    }

    public AnimationResponse findDetailAnimations() {
        return AnimationResponse.builder().build();
    }

    public AnimationResponse patchAnimation(PatchAnimationRequest patchAnimationRequest) {
        return AnimationResponse.builder().build();
    }
}