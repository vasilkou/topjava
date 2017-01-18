package ru.javawebinar.topjava.service;

import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles(Profiles.JPA)
public class UserServiceJpaTest extends UserServiceTest{

    public UserServiceJpaTest() {
        log = LoggerFactory.getLogger(UserServiceJpaTest.class);
    }
}
