package com.c201.book.repository;

import com.c201.book.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(long id);

    User findByKakaoId(Long id);

    Long countByNicknameStartingWith(String nickname);
}
