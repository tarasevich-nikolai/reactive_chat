package com.tarasevich.reactivechat.model;

import lombok.AllArgsConstructor;
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
@Document("users")
public class User {

    @Id
    private Long id;

    @NonNull
    private String nickname;

    private String firstName;

    private String lastName;

}
