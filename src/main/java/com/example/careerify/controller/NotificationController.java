package com.example.careerify.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.management.Notification;

@Controller
@RequestMapping("api/v1")
public class NotificationController {

    @MessageMapping("/notify") // Clients will send messages here
    @SendTo("/topic/notifications") // Send to subscribers of the topic
    public Notification sendNotification(Notification notification) {
        return notification; // Broadcast the notification to all subscribers
    }
}
