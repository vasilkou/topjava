package ru.javawebinar.topjava.service;

import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles(Profiles.JDBC)
public class UserServiceJdbcTest extends UserServiceTest {

    public UserServiceJdbcTest() {
        log = LoggerFactory.getLogger(UserServiceJdbcTest.class);
    }
}
