package com.tarasevich.reactivechat.controller;

import com.tarasevich.reactivechat.model.User;
import com.tarasevich.reactivechat.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Nikolai Tarasevich
 * @since 19.10.2020
 */
@Log4j2
//@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public Flux<User> getUsers() {
        return userService.getUsers()
            .doOnNext(x -> {
                throw new RuntimeException("Exception");
            })
            .doOnSubscribe(s -> log.debug("Called 'getUsers'."))
            .doOnNext(u -> log.debug("Got a user={}.", u));
    }

    @GetMapping("/users/{id}")
    public Mono<User> getUser(@PathVariable Long id) {
        return userService.findById(id)
            .doOnSubscribe(s -> log.debug("Called 'getUser' by id={}.", id))
            .doOnSuccess(m -> log.debug("User {} has been founded.", m));
    }

}
