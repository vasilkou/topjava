package ru.javawebinar.topjava.service;

import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.DATAJPA})
public class MealServiceDataJpaTest extends MealServiceTest {
    public MealServiceDataJpaTest() {
        log = LoggerFactory.getLogger(MealServiceDataJpaTest.class);
    }
}
