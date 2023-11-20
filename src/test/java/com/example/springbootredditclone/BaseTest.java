package com.example.springbootredditclone;

import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

public class BaseTest {

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest")
            .withDatabaseName("spring-reddit-test-db")
            .withUsername("153412")
            .withPassword("******");
static {
mySQLContainer.start();
}
}
