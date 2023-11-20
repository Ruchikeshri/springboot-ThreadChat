package com.example.springbootredditclone.repository;

import com.example.springbootredditclone.model.Post;
import com.example.springbootredditclone.model.User;
import com.example.springbootredditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {


    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);}
