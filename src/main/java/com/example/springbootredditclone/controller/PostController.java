package com.example.springbootredditclone.controller;

import com.example.springbootredditclone.dto.PostRequestDto;
import com.example.springbootredditclone.dto.PostResponseDto;
import com.example.springbootredditclone.exceptions.SubredditNotFoundException;
import com.example.springbootredditclone.model.Post;
import com.example.springbootredditclone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity createPost(@RequestBody PostRequestDto postRequestDto){
      Post post = postService.savePost(postRequestDto);
      return new  ResponseEntity(postRequestDto.getPostId(),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPost(id));
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> getAllPosts(){
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/by-subreddit/{id}")
    public ResponseEntity<?> getPostBySubredditId(@PathVariable Long id ){
       try {
           return status(HttpStatus.OK).body(postService.getPostBySubredditId(id));
       }catch(SubredditNotFoundException e){
           return new ResponseEntity<>("Subreddit with this Id does not exist",HttpStatus.NOT_FOUND);
       }
    }

    @GetMapping("/by-user/{name}")
    public ResponseEntity<List<PostResponseDto>> getPostByUserName(@PathVariable String name){
        return status(HttpStatus.OK).body(postService.getPostByUserName(name));
    }
}
