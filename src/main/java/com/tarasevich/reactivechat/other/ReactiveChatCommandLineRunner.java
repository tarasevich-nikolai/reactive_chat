package com.tarasevich.reactivechat.other;

import java.util.List;

import com.tarasevich.reactivechat.model.Message;
import com.tarasevich.reactivechat.model.User;
import com.tarasevich.reactivechat.repository.MessageRepository;
import com.tarasevich.reactivechat.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Nikolai Tarasevich
 * @since 19.10.2020
 */
@Component
@AllArgsConstructor
public class ReactiveChatCommandLineRunner implements CommandLineRunner {

    private static final Message WELCOME_MESSAGE = Message.builder().userId(1L).text("Welcome to this friendly chat!").build();

    private static final List<User> DEFAULT_USERS = List.of(
        User.builder().id(1L).nickname("system").build(),
        User.builder().id(2L).nickname("wizard").firstName("John").lastName("Irenicus").build(),
        User.builder().id(3L).nickname("jedi").firstName("Lunke").lastName("Skywalker").build()
    );

    private final ReactiveMongoTemplate reactiveMongoTemplate;

    private final MessageRepository messageRepository;

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        reactiveMongoTemplate.dropCollection("message")
            .then(reactiveMongoTemplate.createCollection("message", CollectionOptions.empty().capped().size(4096).maxDocuments(100)))
            .then(messageRepository.save(WELCOME_MESSAGE))
            .thenMany(userRepository.saveAll(DEFAULT_USERS))
            .subscribe();
    }

}
