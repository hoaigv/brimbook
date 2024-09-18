package com.example.bookshop.books.controllers.dto.notifications;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PushNotificationRequest {
     String title;
     String message;
     String topic;
     String token;
}
