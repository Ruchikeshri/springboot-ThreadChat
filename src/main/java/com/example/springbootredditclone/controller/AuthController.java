package com.example.springbootredditclone.controller;

import com.example.springbootredditclone.dto.AuthenticationResponse;
import com.example.springbootredditclone.dto.LoginRequest;
import com.example.springbootredditclone.dto.RefreshTokenRequest;
import com.example.springbootredditclone.dto.RegisterRequest;
import com.example.springbootredditclone.service.AuthService;
import com.example.springbootredditclone.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> singUp(@RequestBody RegisterRequest registerRequest){
    authService.signUp(registerRequest);
    return new ResponseEntity<>("User Registration Successful",
            HttpStatus.ACCEPTED);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){
         authService.verifyAccount(token);
        return new ResponseEntity<>("Account is verified", OK);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest){
       AuthenticationResponse response = authService.login(loginRequest);
        return new ResponseEntity<AuthenticationResponse>(response, OK);
    }

    @PostMapping("refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
    return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
         refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
         return ResponseEntity.status(OK).body("Refresh Token deleted Successfully");
    }
}
