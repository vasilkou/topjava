package ru.javawebinar.topjava.service;

import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles(Profiles.JDBC)
public class MealServiceJdbcTest extends MealServiceTest {
    public MealServiceJdbcTest() {
        log = LoggerFactory.getLogger(MealServiceJdbcTest.class);
    }
}
