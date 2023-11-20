package com.example.springbootredditclone.security;

import org.springframework.security.core.userdetails.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;


//@AllArgsConstructor
@RequiredArgsConstructor
@Service
public class JwtProvider {

    @Autowired
    private final JwtEncoder jwtEncoder;
    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

   public String generateToken(Authentication authentication){
     User principal = (User) authentication.getPrincipal();
     return generateTokenWithUserName(principal.getUsername());
    }

    public String generateTokenWithUserName(String userName) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusMillis(jwtExpirationInMillis))
                .subject(userName)
                .claim("scope","ROLE_USER")
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
   }



    public Long getJwtExpirationInMillis(){
        return jwtExpirationInMillis;
    }
}
