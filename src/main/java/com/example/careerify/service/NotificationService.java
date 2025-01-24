package com.example.careerify.service;

import com.example.careerify.model.Notification;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final SimpMessagingTemplate template;

    public NotificationService(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void sendJobPostedNotification(String jobTitle) {
        Notification notification = new Notification("New Job Posted: " + jobTitle, Long.toString(System.currentTimeMillis()));
        this.template.convertAndSend("/topic/notifications", notification);
    }

    public void sendApplicantAppliedNotification(String companyId, String applicantName, String jobTitle) {
        String message = "Applicant " + applicantName + " applied for your job posting: " + jobTitle;
        Notification notification = new Notification(message, Long.toString(System.currentTimeMillis()));
        this.template.convertAndSend("/topic/notifications/company", notification);
    }
}
