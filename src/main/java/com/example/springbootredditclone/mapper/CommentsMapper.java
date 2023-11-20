package com.example.springbootredditclone.mapper;

import com.example.springbootredditclone.dto.CommentsDto;
import com.example.springbootredditclone.model.Comment;
import com.example.springbootredditclone.model.Post;
import com.example.springbootredditclone.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface CommentsMapper {
    //    @Mapping(target = "id",ignore = true)
//    @Mapping(target="text",source = "commentsDto.text")
//    @Mapping(target = "createdDate",expression = "java(java.time.instant.now())")
//    @Mapping(target = "post",source = "post")
        @Mapping(target = "id", ignore = true)
        @Mapping(target = "text", source = "commentsDto.text")
        @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
        @Mapping(target = "post", source = "post")
        @Mapping(target = "user", source = "user")
        Comment map(CommentsDto commentsDto, Post post, User user);


        @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
        @Mapping(target = "userName", expression = "java(comment.getUser().getUserName())")
        CommentsDto mapToDto(Comment comment);


}
