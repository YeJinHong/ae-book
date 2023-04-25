package com.c201.aebook.api.user.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.c201.aebook.api.user.persistence.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findById(Long id);

    UserEntity findByKakaoId(Long id);

    Long countByNicknameStartingWith(String nickname);

    boolean existsByNickname(String nickname);

    String findProfileUrlById(long userId);
}
