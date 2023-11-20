package com.example.springbootredditclone.service;

import com.example.springbootredditclone.dto.PostRequestDto;
import com.example.springbootredditclone.dto.PostResponseDto;
import com.example.springbootredditclone.mapper.PostMapper;
import com.example.springbootredditclone.model.Post;
import com.example.springbootredditclone.model.Subreddit;
import com.example.springbootredditclone.model.User;
import com.example.springbootredditclone.repository.PostRepository;
import com.example.springbootredditclone.repository.SubredditRepository;
import com.example.springbootredditclone.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PostServiceTest {

    @InjectMocks
    private PostService postService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private PostMapper postMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthService authService;
    @Mock
    private SubredditRepository subredditRepository;
    @Captor
    private ArgumentCaptor<Post> postArgumentCaptor;
//            = Mockito.mock(SubredditRepository.class);
   @BeforeEach
   public void setUp(){
       postService = new PostService(postRepository,postMapper,authService,subredditRepository,userRepository);

   }

    @Test
    void shouldSavePost() {
     User user = new User(123L,"Test User","secret password","user@gmail.com",Instant.now(),true);
     Subreddit subreddit = new Subreddit(123L,"First Subreddit","subreddit desc",emptyList(),Instant.now(),user);
     Post post = new Post(123L,"First Post","http://localhost:8080/url.site",
             "Test",0,null,Instant.now(),null);
     PostRequestDto postRequestDto = new PostRequestDto(null,"First Subreddit","First Post","http://localhost:8080/url.site","Test");
     Mockito.when(subredditRepository.findByName("First Subreddit"))
            .thenReturn(Optional.of(subreddit));
    Mockito.when(authService.getCurrentUser()).thenReturn(user);
    Mockito.when(postMapper.map(postRequestDto,subreddit,user)).thenReturn(post);
    postService.savePost(postRequestDto);
//    verify(postRepository,Mockito.times(1)).save(ArgumentMatchers.any(Post.class));
//     assertThat(postArgumentCaptor.getValue().getPostId()).isEqualTo(123L);
//     assertThat(postArgumentCaptor.getValue().getPostName()).isEqualTo("First Post");
     Mockito.verify(postRepository, Mockito.times(1)).save(postArgumentCaptor.capture());

     assertThat(postArgumentCaptor.getValue().getPostId()).isEqualTo(123L);
     assertThat(postArgumentCaptor.getValue().getPostName()).isEqualTo("First Post");
    }

    @Test
    @DisplayName("Should Find Post By Id")
    void shouldFindPostById() {
        Post post = new Post(123L,"First Post","http://localhost:8080/url.site","Test"
                ,0,null, Instant.now(),null);
       PostResponseDto expectedPostResponseDto = new PostResponseDto(123L,"First Post","http://localhost:8080/url.site","Test"
             ,"Test User","Test Subreddit",0,0,"1 Hour Ago",false,false);
        Mockito.when(postRepository.findById(123L)).thenReturn(Optional.of(post));
        when(postMapper.mapToDto(Mockito.any(Post.class))).thenReturn(expectedPostResponseDto);
        PostResponseDto actualPostResponseDto = postService.getPost(123L);
        assertThat(actualPostResponseDto.getId()).isEqualTo(expectedPostResponseDto.getId());
    assertThat(actualPostResponseDto.getPostName()).isEqualTo(expectedPostResponseDto.getPostName());
    }

    @Test
    void getAllPosts() {
    }

    @Test
    void getPostBySubredditId() {
    }

    @Test
    void getPostByUserName() {
    }
}