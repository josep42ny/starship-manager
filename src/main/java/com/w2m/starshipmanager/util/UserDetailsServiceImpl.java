package com.w2m.starshipmanager.util;

import com.w2m.starshipmanager.data.model.User;
import com.w2m.starshipmanager.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @NonNull
    public UserDetails loadUserByUsername(@NonNull final String username) {
        final User user = this.userRepository.findUserByUsername(username).orElseThrow();

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}
