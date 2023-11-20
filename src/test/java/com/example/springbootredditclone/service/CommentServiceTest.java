package com.example.springbootredditclone.service;

import com.example.springbootredditclone.exceptions.SpringRedditException;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

public  class CommentServiceTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test()
    @DisplayName("Test should pass when comment do not contain swear words")
    public    void shouldNotContainSwearWord() {
    CommentService commentService = new CommentService(null,null,null,null,null,null,null);
        Assertions.assertFalse(commentService.containSwearWords("This is clean comment"));
    }



    @Test()
    @DisplayName("Test should pass when comment do not contain swear words")
    public   void shouldFailwhenContainSwearWord() {
        CommentService commentService = new CommentService(null,null,null,null,null,null,null);
        SpringRedditException exception =assertThrows(SpringRedditException.class,()->
        {
         commentService.containSwearWords("This is a shitty comment");
        });
//        assertTrue(exception.getMessage().contains("comment contain unacceptable language"));
        assertThatThrownBy(()-> {
            commentService.containSwearWords("This is a shitty comment");
        }).isInstanceOf(SpringRedditException.class)
                .hasMessage("comment contain unacceptable language");
    }
}