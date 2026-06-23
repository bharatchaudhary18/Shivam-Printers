package com.shivamprinters.service;

import com.shivamprinters.entity.Notification;
import com.shivamprinters.entity.User;
import com.shivamprinters.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Transactional
    public void notify(User user, String title, String message, String link) {
        Notification notification = Notification.builder()
                .user(user)
                .title(title)
                .message(message)
                .link(link)
                .build();
        notificationRepository.save(notification);
    }

    public Page<Notification> getUserNotifications(User user, Pageable pageable) {
        return notificationRepository.findByUserOrderByCreatedTimestampDesc(user, pageable);
    }

    public long getUnreadCount(User user) {
        return notificationRepository.countByUserAndReadFalse(user);
    }

    @Transactional
    public void markAsRead(Long notificationId, User user) {
        notificationRepository.findById(notificationId).ifPresent(notification -> {
            if (notification.getUser().getId().equals(user.getId())) {
                notification.setRead(true);
            }
        });
    }
}
