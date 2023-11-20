package com.example.springbootredditclone.service;

import com.example.springbootredditclone.dto.RegisterRequest;
import com.example.springbootredditclone.model.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@AllArgsConstructor
public class DtoToEntityConverter {
 private final ModelMapper modelMapper;
// private final SubredditMapper subredditMapper;
    public static User convertUserDto2User(RegisterRequest registerRequest){
        User user = new User();
        user.setUserName(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setCreated(Instant.now());
        user.setEnabled(false);
        return user;
    }
//    public Subreddit convertSubreddit2SubredditDto(SubredditDto subredditDto){
//    return Subreddit.builder().name(subredditDto.getName())
//            .description(subredditDto.getDescription())
//            .build();
//    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
//    return modelMapper.map(subredditDto,Subreddit.class);
//    }

}
