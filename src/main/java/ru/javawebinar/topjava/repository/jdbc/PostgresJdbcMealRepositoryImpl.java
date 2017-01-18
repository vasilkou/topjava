package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;

import javax.sql.DataSource;
import java.time.LocalDateTime;

@Repository
@Profile(Profiles.POSTGRES)
public class PostgresJdbcMealRepositoryImpl extends AbstractJdbcMealRepository {

    @Autowired
    public PostgresJdbcMealRepositoryImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public <T> T convertDate(LocalDateTime dateTime) {
        return (T) dateTime;
    }
}
