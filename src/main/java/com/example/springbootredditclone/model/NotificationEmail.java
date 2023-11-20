package com.example.springbootredditclone.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NotificationEmail {
    private String Subject;
    private String reciptent;
    private String body;
}
