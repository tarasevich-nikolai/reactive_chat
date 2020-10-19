package com.tarasevich.reactivechat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

/**
 * @author Nikolai Tarasevich
 * @since 19.10.2020
 */
@Value
@AllArgsConstructor(staticName = "of")
public class MessageDto {

    String nickName;

    String text;

}
