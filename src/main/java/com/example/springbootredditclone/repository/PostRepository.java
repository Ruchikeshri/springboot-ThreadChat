package com.example.springbootredditclone.repository;

import com.example.springbootredditclone.dto.PostResponseDto;
import com.example.springbootredditclone.model.Post;
import com.example.springbootredditclone.model.Subreddit;
import com.example.springbootredditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.annotations.Cacheable;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    @Query("select p from Post p where p.subreddit = ?1")
    List<Post> findAllBySubreddit(Subreddit subredditId);

    @Query("select p from Post p where p.user = ?1")
    @Cacheable("users")
    @Transactional(readOnly = true)
    List<Post> findByUser(User user);
}
