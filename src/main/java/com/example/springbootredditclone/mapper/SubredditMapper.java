package com.example.springbootredditclone.mapper;

import com.example.springbootredditclone.dto.SubredditDto;
import com.example.springbootredditclone.model.Post;
import com.example.springbootredditclone.model.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface SubredditMapper {

@Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
SubredditDto mapSubredditToDto(Subreddit subreddit);

default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Subreddit mapDtoToSubreddit(SubredditDto subredditDto);
}

