package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.DATAJPA})
public class UserServiceDataJpaTest extends UserServiceTest {
}
