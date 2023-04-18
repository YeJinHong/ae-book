package com.c201.book.auth;

import com.c201.book.model.User;
import com.c201.book.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername username : {}", username);

        long id = Long.parseLong(username);
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("해당 유저 정보를 찾을 수 없습니다"));

        return new CustomUserDetails(user);
    }
}
