package com.shivamprinters.repository;

import com.shivamprinters.entity.Notification;
import com.shivamprinters.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findByUserOrderByCreatedTimestampDesc(User user, Pageable pageable);

    long countByUserAndReadFalse(User user);
}
