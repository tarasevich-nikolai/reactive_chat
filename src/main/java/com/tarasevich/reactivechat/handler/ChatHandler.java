package com.tarasevich.reactivechat.handler;

import java.time.Duration;

import com.tarasevich.reactivechat.dto.MessageDto;
import com.tarasevich.reactivechat.model.Message;
import com.tarasevich.reactivechat.model.User;
import com.tarasevich.reactivechat.repository.MessageRepository;
import com.tarasevich.reactivechat.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Nikolai Tarasevich
 * @since 19.10.2020
 */
@Log4j2
@Service
@AllArgsConstructor
public class ChatHandler {

    private static final Mono<User> GUEST_USER = Mono.just(User.builder().nickname("guest").build());

    private final MessageRepository messageRepository;

    private final UserRepository userRepository;

    public Mono<ServerResponse> getMessages() {
        var messages = messageRepository.findWithTailableCursorBy()
            .delayElements(Duration.ofSeconds(2))
            .doOnNext(message -> log.debug("New message from db={}.", message))
            .flatMap(message -> userRepository.findById(message.getUserId())
                .switchIfEmpty(GUEST_USER)
                .map(user -> MessageDto.of(user.getNickname(), message.getText()))
            )
            .doOnSubscribe(s -> log.debug("Called 'chat'."))
            .doOnNext(m -> log.debug("Got a message={}.", m));

        return ServerResponse.ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(messages, MessageDto.class);
    }

    public Mono<ServerResponse> createMessage(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Message.class)
            .map(messageRepository::save)
            .flatMap(message -> ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(message, Message.class)
            )
            .doOnSubscribe(s -> log.debug("Called 'chat'."))
            .doOnNext(m -> log.debug("Got a message={}.", m));
    }

}
