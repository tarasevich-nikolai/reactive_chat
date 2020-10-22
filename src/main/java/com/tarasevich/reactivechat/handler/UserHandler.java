package com.tarasevich.reactivechat.handler;

import com.tarasevich.reactivechat.model.User;
import com.tarasevich.reactivechat.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author Nikolai Tarasevich
 * @since 19.10.2020
 */
@Log4j2
@Service
@AllArgsConstructor
public class UserHandler {

    private final UserRepository userRepository;

    public Mono<ServerResponse> getUsers() {
        var users = userRepository.findAll()
            .doOnSubscribe(s -> log.debug("Called 'getUsers'."))
            .doOnNext(u -> log.debug("Got a user={}.", u));

        return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(users, User.class);
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        Long id = Long.parseLong(serverRequest.pathVariable("id"));
        return userRepository.findById(id)
            .flatMap(user -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
            )
            .switchIfEmpty(ServerResponse.notFound().build())
            .doOnSubscribe(s -> log.debug("Called 'getUser' by id={}.", id))
            .doOnSuccess(m -> log.debug("User {} has been founded.", m));
    }

}
