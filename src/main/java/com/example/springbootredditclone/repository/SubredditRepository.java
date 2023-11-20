package com.example.springbootredditclone.repository;

import com.example.springbootredditclone.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.annotations.Cacheable;

import java.util.Optional;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit,Long> {
    @Cacheable("users")
//    @Transactional(readOnly = true)
    Optional<Subreddit> findByName(String subredditName);
}
