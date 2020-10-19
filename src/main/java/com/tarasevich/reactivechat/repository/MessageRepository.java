package com.tarasevich.reactivechat.repository;

import com.tarasevich.reactivechat.model.Message;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import reactor.core.publisher.Flux;

/**
 * @author Nikolai Tarasevich
 * @since 19.10.2020
 */
public interface MessageRepository extends ReactiveMongoRepository<Message, String> {

    @Tailable
    Flux<Message> findWithTailableCursorBy();

}
