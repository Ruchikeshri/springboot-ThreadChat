package com.example.springbootredditclone.controller;

import com.example.springbootredditclone.dto.PostResponseDto;
import com.example.springbootredditclone.security.JwtProvider;
import com.example.springbootredditclone.service.PostService;
import com.example.springbootredditclone.service.UserDetailsServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.okhttp3.MediaType;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.testcontainers.shaded.okhttp3.MediaType.*;

@WebMvcTest(controllers = PostController.class)
class PostControllerTest {

    @MockBean
    private PostService postService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserDetailsServiceImpl userDetailServiceImpl;

    @MockBean
    private JwtProvider jwtProvider;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("Should List All Post when Gdt request to endpoint-/api/posts")
    void getAllPosts() throws Exception {
        PostResponseDto postResponseDto1 = new PostResponseDto(1L,"Post Name","http://url.site","description","user 1",
                "subreddit Name",0,0,"1 day ago",false,false);
        PostResponseDto postResponseDto2 = new PostResponseDto(123L,"First Post","http://localhost:8080/url.site","Test"
                ,"Test User","Test Subreddit",0,0,"1 Hour Ago",false,false);
    when(postService.getAllPosts()).thenReturn(Arrays.asList(postResponseDto1,postResponseDto2));
    mockMvc.perform(MockMvcRequestBuilders.get("/api/posts"))
            .andExpect(MockMvcResultMatchers.status().is(200))
//            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.A))
            .andExpect(content().contentType(APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.size()", Matchers.is(2)))
            .andExpect(jsonPath("$[0].id",Matchers.is(1)))
            .andExpect(jsonPath("$[0].postName",Matchers.is("Post Name")))
            .andExpect(jsonPath("$[1].postName", Matchers.is("First Post")));

    }
}