package com.example.springbootredditclone.repository;

import com.example.springbootredditclone.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserRepositoryTestEmbedded {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void saveUser(){
        User user = new User(123L,"Test User","secret password","user@gmail.com", Instant.now(),true);
        User savedUser = userRepository.save(user);
        assertThat(savedUser).usingRecursiveComparison().ignoringFields("userId").isEqualTo(user);

    }

    @Test
    @Sql("classpath:test-data.sql")
    public void shouldSavedUsersThroughSqlFile() {
        Optional<User> test = userRepository.findByUserName("testuser_sql");
        assertThat(test).isEmpty();
    }
}
