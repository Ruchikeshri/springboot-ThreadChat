package com.example.springbootredditclone.controller;

import com.example.springbootredditclone.dto.SubredditDto;
import com.example.springbootredditclone.service.SubredditService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/subreddit")
public class SubredditController {

 private  final SubredditService subredditService;
 @PostMapping()
 public ResponseEntity<SubredditDto> CreateSubreddit(@RequestBody SubredditDto subredditDto){
  SubredditDto subredditDtos = subredditService.save(subredditDto);
  return ResponseEntity.status(HttpStatus.ACCEPTED).body(subredditDtos);
 }

 @GetMapping()
 public ResponseEntity<List<SubredditDto>> getAll(){
  return ResponseEntity.status(HttpStatus.OK).
          body(subredditService.getAll());
 }

 @GetMapping("/{id}")
 public ResponseEntity<SubredditDto> getSubredditById(@PathVariable Long id){
  return ResponseEntity.status(HttpStatus.OK)
          .body(subredditService.getSubreddit(id));
 }

 @PutMapping()
 public ResponseEntity<SubredditDto> updateSubreddit(@RequestBody SubredditDto subredditDto,@RequestParam Long id){
//  subredditService.updateSubreddit(subredditDto,id);
  return ResponseEntity.status(HttpStatus.ACCEPTED).body(subredditService.updateSubreddit(subredditDto,id));
 }
}
