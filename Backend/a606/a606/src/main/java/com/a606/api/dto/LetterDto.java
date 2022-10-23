package com.a606.api.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LetterDto {

    long id;
    long userId;
    String msg;
    LocalDateTime date;
    boolean isRead;
}
