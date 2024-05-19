package com.github.kotooriiii.myworld;

import org.testcontainers.containers.PostgreSQLContainer;

public class AbstractDatabaseContainerBaseTest
{
    protected static final PostgreSQLContainer<?> postgres;

    static
    {
        postgres = new PostgreSQLContainer<>(
                "postgres:16.2"
        );

        postgres
                .withDatabaseName("myworld_test").
                withUsername("myworld_test_admin").
                withPassword("myworld_test_admin_pw_123")
                .withEnv("PGUSER", "myworld_test_admin")
                .withEnv("PGPASSWORD", "myworld_test_admin_pw_123");

        postgres.start();
    }

}
