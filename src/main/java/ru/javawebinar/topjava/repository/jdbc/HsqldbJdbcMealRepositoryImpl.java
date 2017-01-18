package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Repository
@Profile(Profiles.HSQLDB)
public class HsqldbJdbcMealRepositoryImpl extends AbstractJdbcMealRepository {

    @Autowired
    public HsqldbJdbcMealRepositoryImpl(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public <T> T dateTimeConverter(LocalDateTime dateTime) {
        return (T) Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
