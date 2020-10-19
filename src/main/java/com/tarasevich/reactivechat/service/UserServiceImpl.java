package com.tarasevich.reactivechat.service;

import com.tarasevich.reactivechat.model.User;
import com.tarasevich.reactivechat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Nikolai Tarasevich
 * @since 19.10.2020
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Flux<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Mono<User> findById(Long id) {
        return userRepository.findById(id);
    }

}
