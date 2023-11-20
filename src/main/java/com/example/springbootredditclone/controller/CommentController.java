package com.example.springbootredditclone.controller;


import com.example.springbootredditclone.dto.CommentsDto;
import com.example.springbootredditclone.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public void createPost(@RequestBody CommentsDto commentsDto){
         commentService.save(commentsDto);
        ResponseEntity.status(HttpStatus.CREATED).body("commeted for Post "+commentsDto.getPostId());
    }

    @GetMapping("/by-post/{id}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@PathVariable(value="id") Long postId){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getAllComments(postId));
    }

    @GetMapping("/by-user/{userName}")
    public ResponseEntity<List<CommentsDto>> getAllCommentForUser(@PathVariable(value="userName") String userName){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getALLCommentsForUser(userName));
    }
}
