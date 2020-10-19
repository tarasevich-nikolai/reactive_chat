package com.tarasevich.reactivechat.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Nikolai Tarasevich
 * @since 19.10.2020
 */
@Data
@Builder
@Document("message")
public class Message {

    @Id
    private String id;

    @NonNull
    private Long userId;

    @NonNull
    private String text;

}
