package com.dms.notificationservice.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    @PostMapping
    public String sendNotification(@RequestParam  String message) {
        System.out.println("NOTIFICATION RECEIVED: " + message);
        return "Notification Processed";
    }
}
