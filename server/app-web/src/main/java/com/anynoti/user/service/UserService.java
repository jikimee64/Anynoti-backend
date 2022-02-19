package com.anynoti.user.service;

import com.anynoti.domain.user.User;
import com.anynoti.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public User findUserByProviderId(String providerId) {
        return userRepository.findUserByProviderId(providerId)
            .orElseThrow(IllegalArgumentException::new);
    }

}