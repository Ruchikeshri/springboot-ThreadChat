package com.example.springbootredditclone.repository;
import com.example.springbootredditclone.model.Post;
import com.example.springbootredditclone.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostRepositoryTest {
    @Container
    MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest")
            .withDatabaseName("spring-reddit-test-db")
            .withUsername("153412")
            .withPassword("Ruchi7717@");

    @Autowired
    private PostRepository postRepository;

    @Test
    public void shouldSavePost(){
        Post expectedPost = new Post(null, "First Post","http://url.site","test",0,null, Instant.now(),null);
        Post actualExcepted = postRepository.save(expectedPost);
        assertThat(actualExcepted).usingRecursiveComparison()
                .ignoringFields("postId").isEqualTo(expectedPost);
    }

}
