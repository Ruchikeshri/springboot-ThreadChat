package com.example.springbootredditclone.service;

import com.example.springbootredditclone.dto.PostRequestDto;
import com.example.springbootredditclone.dto.PostResponseDto;
import com.example.springbootredditclone.exceptions.PostNotFoundException;
import com.example.springbootredditclone.exceptions.SubredditNotFoundException;
import com.example.springbootredditclone.mapper.PostMapper;
import com.example.springbootredditclone.model.Post;
import com.example.springbootredditclone.model.Subreddit;
import com.example.springbootredditclone.model.User;
import com.example.springbootredditclone.repository.PostRepository;
import com.example.springbootredditclone.repository.SubredditRepository;
import com.example.springbootredditclone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final AuthService authService;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Transactional
    public Post savePost(PostRequestDto postRequestDto) {
        Subreddit subreddit = subredditRepository.findByName(postRequestDto.getSubredditName()).orElseThrow(() -> new SubredditNotFoundException("Subreddit Not found" + postRequestDto.getSubredditName()));
//        Post post = new Post();
//        post.setCreatedDate(Instant.now());
//        post.setPostId(postRequestDto.getPostId());
//        post.setDescription(postRequestDto.getDescription());
//        post.setUrl(postRequestDto.getUrl());
//        post.setSubreddit(subreddit);
//        post.setUser(authService.getCurrentUser());
       return postRepository.save(postMapper.map(postRequestDto, subreddit, authService.getCurrentUser()));
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("Post Not found"));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public List<PostResponseDto> getPostBySubredditId(Long subredditId) {
        try {
            Subreddit subreddit = subredditRepository.findById(subredditId).
                    orElseThrow(()-> new SubredditNotFoundException(subredditId.toString()));
            logger.info("subreddit not found with {} given Id", subredditId);

            List<Post> posts = postRepository.findAllBySubreddit(subreddit);
            return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
        } catch (SubredditNotFoundException e) {
            logger.info("subreddit not found with {} given Id", subredditId);
            throw new SubredditNotFoundException("subreddit not found with id " + subredditId.toString());
        }
    }
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostByUserName(String userName){
        try{
            Optional<User> user = userRepository.findByUserName(userName);
            return postRepository.findByUser(user.get()).stream()
                    .map(postMapper::mapToDto).collect(Collectors.toList());
        }
        catch (UsernameNotFoundException e){
            throw new UsernameNotFoundException("userName not found Exception");
        }
    }


}