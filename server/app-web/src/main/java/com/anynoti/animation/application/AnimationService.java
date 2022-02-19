package com.anynoti.animation.application;

import com.anynoti.LoginUser;
import com.anynoti.animation.dto.AnimationGenerator;
import com.anynoti.animation.dto.request.PatchAnimationRequest;
import com.anynoti.animation.dto.response.AnimationResponse;
import com.anynoti.domain.animation.Animation;
import com.anynoti.domain.animation.AnimationBox;
import com.anynoti.domain.animation.AnimationRepository;
import com.anynoti.domain.animation.query.AnimationBoxQueryRepository;
import com.anynoti.domain.animation.query.AnimationClause;
import com.anynoti.domain.animation.query.AnimationDetailQuery;
import com.anynoti.domain.user.User;
import com.anynoti.enums.appweb.SearchKind;
import com.anynoti.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnimationService {

    private final AnimationGenerator animationGenerator;
    private final AnimationBoxQueryRepository animationBoxQueryRepository;
    private final AnimationRepository animationRepository;
    private final UserService userService;

    public List<AnimationResponse> findTodoAnimations(LoginUser loginUser, SearchKind searchKind) {
        //TODO: Animations의 List 객체도 일급 컬렉션 사용
        List<Animation> animations = animationBoxQueryRepository.findTodoAnimationsClause(
            AnimationClause.ofTodos(loginUser.getProviderId(), searchKind)
        );

        return animationGenerator.toTodosAnimationResponse(animations);
    }

    public List<AnimationResponse> findAnimationsBySearch(String title) {
        List<Animation> animations = animationBoxQueryRepository.findTodoAnimationsByTitle(title);
        return animationGenerator.toSearchAnimationResponse(animations);
    }

    public AnimationResponse findDetailAnimations(LoginUser loginUser, Long id) {
        AnimationDetailQuery animationDetailQuery = animationBoxQueryRepository.findDetailsAnimationsClause(
            AnimationClause.ofDetails(loginUser.getProviderId(), id)
        );
        return animationGenerator.toDetailsAnimationResponse(animationDetailQuery);
    }

    @Transactional
    public void addTodoAnimations(LoginUser loginUser, Long id) {
        Animation savedAnimation = findByAnimationById(id);
        User savedUser = userService.findUserByProviderId(loginUser.getProviderId());

        AnimationBox animationBox = AnimationBox.builder()
            .animation(savedAnimation)
            .user(savedUser)
            .build();

        animationBoxQueryRepository.save(animationBox);
    }

    @Transactional
    public AnimationResponse patchAnimation(
        Long id,
        LoginUser loginUser,
        PatchAnimationRequest patchAnimationRequest) {
        Animation savedAnimation = findByAnimationById(id);
        User savedUser = userService.findUserByProviderId(loginUser.getProviderId());
        AnimationBox animationBox = animationBoxQueryRepository.findAnimationBox(
            savedUser,
            savedAnimation
        );

        animationBox.changeDetail(
            patchAnimationRequest.getMemo(),
            patchAnimationRequest.getLiked(),
            patchAnimationRequest.getNotied()
        );

        return animationGenerator.toPatchAnimationResponse(savedAnimation, animationBox);
    }

    private Animation findByAnimationById(Long id) {
        return animationRepository.findById(id)
            .orElseThrow(IllegalArgumentException::new);
    }

}