package com.example.springbootredditclone.service;

import com.example.springbootredditclone.dto.AuthenticationResponse;
import com.example.springbootredditclone.dto.LoginRequest;
import com.example.springbootredditclone.dto.RefreshTokenRequest;
import com.example.springbootredditclone.dto.RegisterRequest;
import com.example.springbootredditclone.exceptions.SpringRedditException;
import com.example.springbootredditclone.model.NotificationEmail;
import com.example.springbootredditclone.model.User;
import com.example.springbootredditclone.model.VerificationToken;
import com.example.springbootredditclone.repository.UserRepository;
import com.example.springbootredditclone.repository.VerificationTokenRepository;
import com.example.springbootredditclone.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public void signUp(RegisterRequest registerRequest) {
        User user = new User();
        User savedUser = DtoToEntityConverter.convertUserDto2User(registerRequest);
        user.setUserName(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        userRepository.save(user);
        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("please Activate your Account", user.getEmail(), "Thank you for signing up to Spring Reddit, " + "please click on the below url to activate your account : " + "http://localhost:8080/api/auth/accountVerification/" + token));
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationToken.setExpiryDate(Instant.now());
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SpringRedditException("Invalid Token"));
        fetchUserAndEnable(verificationToken.get());
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String userName = verificationToken.getUser().getUserName();
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new SpringRedditException("User Not Found"));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public AuthenticationResponse login(@NotNull LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
//        return new AuthenticationResponse(token, loginRequest.getUsername());
        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .username(loginRequest.getUsername())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .build();

    }

    public User getCurrentUser() {
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUserName(principal.getSubject()).orElseThrow(() -> new UsernameNotFoundException("username not found" + principal.getSubject()));
        return user;
    }


    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
     refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
     String token =  jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
     return AuthenticationResponse.builder()
            .token(token)
            .refreshToken(refreshTokenRequest.getRefreshToken())
            .expiresAt(Instant.now().plusMillis((jwtProvider.getJwtExpirationInMillis())))
            .username(refreshTokenRequest.getUsername())
            .build();
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
