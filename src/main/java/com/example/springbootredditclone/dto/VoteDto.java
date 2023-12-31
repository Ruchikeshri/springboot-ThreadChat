package com.example.springbootredditclone.dto;

import com.example.springbootredditclone.model.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteDto {
    private VoteType voteType;
    private Long postId;
}
