package com.tarasevich.reactivechat.service;

import com.tarasevich.reactivechat.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Nikolai Tarasevich
 * @since 19.10.2020
 */
public interface UserService {

    Flux<User> getUsers();

    Mono<User> findById(Long id);

}
