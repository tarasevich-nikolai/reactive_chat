package com.tarasevich.reactivechat.config;

import com.tarasevich.reactivechat.dto.MessageDto;
import com.tarasevich.reactivechat.handler.ChatHandler;
import com.tarasevich.reactivechat.handler.UserHandler;
import com.tarasevich.reactivechat.service.ChatService;
import com.tarasevich.reactivechat.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author Nikolai Tarasevich
 * @since 22.10.2020
 */
@Configuration
public class RouterConfiguration {

    @Bean
    public RouterFunction<ServerResponse> chatRoutes(ChatHandler chatHandler) {
        return RouterFunctions.route()
            .GET("/chat", req -> chatHandler.getMessages())
            .POST("/message", chatHandler::createMessage)
            .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userRoutes(UserHandler userHandler) {
        return RouterFunctions.route()
            .GET("/users", req -> userHandler.getUsers())
            .GET("/users/{id}", userHandler::findById)
            .build();
    }

}
