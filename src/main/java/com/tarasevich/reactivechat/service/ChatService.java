package com.tarasevich.reactivechat.service;

import com.tarasevich.reactivechat.dto.MessageDto;
import com.tarasevich.reactivechat.model.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Nikolai Tarasevich
 * @since 19.10.2020
 */
public interface ChatService {

    Flux<MessageDto> getMessages();

    Mono<Message> createMessage(Message message);

}
