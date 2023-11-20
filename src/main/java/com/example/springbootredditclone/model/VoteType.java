package com.example.springbootredditclone.model;

import com.example.springbootredditclone.exceptions.SpringRedditException;

import java.util.Arrays;

public enum VoteType {
    UPVOTE(1),DOWNVOTE(-1);

    private int direction;
    VoteType(int direction) {
    }

    public Integer getDirection() {
        return direction;
    }
    public static VoteType lookup(Integer direction){
        return Arrays.stream(values())
                .filter(v->v.getDirection().equals(direction)).findAny()
                .orElseThrow(()-> new SpringRedditException("vote not found"));
    }
}
