package com.tarasevich.reactivechat.repository;

import com.tarasevich.reactivechat.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

/**
 * @author Nikolai Tarasevich
 * @since 19.10.2020
 */
public interface UserRepository extends ReactiveMongoRepository<User, Long> {

}
