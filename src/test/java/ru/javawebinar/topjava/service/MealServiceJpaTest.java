package ru.javawebinar.topjava.service;

import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles(Profiles.JPA)
public class MealServiceJpaTest extends MealServiceTest {
    public MealServiceJpaTest() {
        log = LoggerFactory.getLogger(MealServiceJpaTest.class);
    }
}
