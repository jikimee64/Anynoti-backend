package com.anynoti.animation.application;

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
}
