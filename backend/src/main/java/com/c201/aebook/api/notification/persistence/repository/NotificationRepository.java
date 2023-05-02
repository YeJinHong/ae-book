package com.c201.aebook.api.notification.persistence.repository;

import com.c201.aebook.api.notification.persistence.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    Optional<NotificationEntity> findByUserIdAndBookId(Long userId, Long bookId);
}
