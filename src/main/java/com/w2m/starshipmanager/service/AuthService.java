package com.w2m.starshipmanager.service;

import com.w2m.starshipmanager.data.model.User;
import com.w2m.starshipmanager.data.repository.UserRepository;
import com.w2m.starshipmanager.exception.ConflictingUserNameException;
import com.w2m.starshipmanager.model.auth.RegisterRequest;
import com.w2m.starshipmanager.util.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    UserDetailsServiceImpl userDetailsService;
    UserRepository userRepository;

    public String login(final String username, final String password) {
        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

        if (!this.passwordMatches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Wrong credentials for user: " + username);
        }

        return JwtUtils.generateAccessToken(userDetails);
    }

    @Transactional
    public String register(final RegisterRequest request) throws UsernameNotFoundException {
        final boolean userExists = this.userRepository.existsUserByUsername(request.getUsername());
        if (userExists) {
            throw new ConflictingUserNameException("Username is already taken: " + request.getUsername());
        }

        final User userToSave = User.builder()
                .username(request.getUsername())
                .password(this.hashPassword(request.getPassword()))
                .role(request.getRole())
                .build();

        final User savedUser = this.userRepository.saveAndFlush(userToSave);

        return this.login(savedUser.getUsername(), savedUser.getPassword());
    }

    private String hashPassword(final String plaintext) {
        return BCrypt.hashpw(plaintext, BCrypt.gensalt());
    }

    private boolean passwordMatches(final String plaintext, final String ciphertext) {
        return BCrypt.checkpw(plaintext, ciphertext);
    }

}
