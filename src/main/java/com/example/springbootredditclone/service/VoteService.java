package com.example.springbootredditclone.service;

import com.example.springbootredditclone.dto.VoteDto;
import com.example.springbootredditclone.exceptions.PostNotFoundException;
import com.example.springbootredditclone.exceptions.SpringRedditException;
import com.example.springbootredditclone.model.Post;
import com.example.springbootredditclone.model.User;
import com.example.springbootredditclone.model.Vote;
import com.example.springbootredditclone.model.VoteType;
import com.example.springbootredditclone.repository.PostRepository;
import com.example.springbootredditclone.repository.UserRepository;
import com.example.springbootredditclone.repository.VoteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.springbootredditclone.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {

    private final PostRepository postRepository;
    private final VoteRepository voteRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId()).orElseThrow(() -> new PostNotFoundException("Post Not found with ID " + voteDto.getPostId().toString()));
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
            throw new SpringRedditException("You have already "
                    + voteDto.getVoteType() + "'d for this post");
//            throw new SpringRedditException("You have already " + voteDto.getVoteType() + "'d for this post");
        }
        if (UPVOTE.equals(voteDto.getVoteType())) {
            post.setVoteCount(post.getVoteCount() + 1);
        } else {
            post.setVoteCount(post.getVoteCount() - 1);
        }
        voteRepository.save(mapToVote(voteDto, post));
        postRepository.save(post);
    }

    public Vote mapToVote(VoteDto voteDto, Post post) {
        return Vote.builder().voteType(voteDto.getVoteType())
                .post(post)
                .user(authService.getCurrentUser())
                .build();
    }


}