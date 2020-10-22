package com.tarasevich.reactivechat.controller;

import com.tarasevich.reactivechat.dto.MessageDto;
import com.tarasevich.reactivechat.model.Message;
import com.tarasevich.reactivechat.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Nikolai Tarasevich
 * @since 19.10.2020
 */
@Log4j2
//@Controller
@AllArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping(value = "/chat", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseBody
    public Flux<MessageDto> chat() {
        return chatService.getMessages()
            .doOnSubscribe(s -> log.debug("Called 'chat'."))
            .doOnNext(m -> log.debug("Got a message={}.", m));
    }

    @PostMapping(value = "/message", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Mono<Message> createMessage(@RequestBody Message message) {
        return chatService.createMessage(message)
            .doOnSubscribe(s -> log.debug("Called 'createMessage'."))
            .doOnSuccess(m -> log.debug("Message {} has been created.", m));
    }

}
