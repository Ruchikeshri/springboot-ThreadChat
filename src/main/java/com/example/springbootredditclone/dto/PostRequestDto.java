package com.example.springbootredditclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {
    private Long PostId;
    private String subredditName;
    private String postName;
    private String url;
    private String description;
}
