package com.tarasevich.reactivechat.service;

import java.time.Duration;

import com.tarasevich.reactivechat.dto.MessageDto;
import com.tarasevich.reactivechat.model.Message;
import com.tarasevich.reactivechat.model.User;
import com.tarasevich.reactivechat.repository.MessageRepository;
import com.tarasevich.reactivechat.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Nikolai Tarasevich
 * @since 19.10.2020
 */
@Log4j2
@Service
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private static final Mono<User> GUEST_USER = Mono.just(User.builder().nickname("guest").build());

    private final MessageRepository messageRepository;

    private final UserRepository userRepository;

    @Override
    public Flux<MessageDto> getMessages() {
        return messageRepository.findWithTailableCursorBy()
            .delayElements(Duration.ofSeconds(2))
            .doOnNext(message -> log.debug("New message from db={}.", message))
            .flatMap(message -> userRepository.findById(message.getUserId())
                .switchIfEmpty(GUEST_USER)
                .map(user -> MessageDto.of(user.getNickname(), message.getText()))
            );
    }

    @Override
    public Mono<Message> createMessage(Message message) {
        return messageRepository.save(message);
    }

}
