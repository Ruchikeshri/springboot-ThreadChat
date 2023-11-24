package com.example.springbootredditclone.controller;

import com.example.springbootredditclone.dto.VoteDto;
import com.example.springbootredditclone.exceptions.SpringRedditException;
import com.example.springbootredditclone.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/votes")
@AllArgsConstructor
public class VoteController {

    private VoteService voteservice;

    @PostMapping
    public ResponseEntity<?> vote(@RequestBody VoteDto voteDto){
//        try{
        voteservice.vote(voteDto);
        return new ResponseEntity<>(HttpStatus.OK);
//    }
//        catch(SpringRedditException e){
//            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
//        }
    }

}
