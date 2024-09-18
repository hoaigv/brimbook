package com.example.bookshop.books.services.impl;

import com.example.bookshop.books.controllers.dto.notifications.PushNotificationRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PushNotificationService {
    FCMService fcmService;
    public void sendPushNotificationToToken(PushNotificationRequest request) {
        try {
        fcmService.sendMessageToToken(request);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
    public void sendPushNotificationToTopic(PushNotificationRequest request) {
        try {
            fcmService.sendMessageToTopic(request);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
