package com.example.springbootredditclone.repository;

import com.example.springbootredditclone.dto.CommentsDto;
import com.example.springbootredditclone.model.Comment;
import com.example.springbootredditclone.model.Post;
import com.example.springbootredditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("select c from Comment c where c.post = ?1")
    List<Comment> findByPost(Post post);

    @Query("select c from Comment c where c.user = ?1")
    List<Comment> findAllByUser(Optional<User> user);
}
