package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
    }

    @Transactional
    @Override
    public User save(User user) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", user.getId())
                .addValue("name", user.getName())
                .addValue("email", user.getEmail())
                .addValue("password", user.getPassword())
                .addValue("registered", user.getRegistered())
                .addValue("enabled", user.isEnabled())
                .addValue("caloriesPerDay", user.getCaloriesPerDay());

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(map);
            insertBatch(newKey.intValue(), new ArrayList<>(user.getRoles()));
            user.setId(newKey.intValue());
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", map);
            jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.getId());
            insertBatch(user.getId(), new ArrayList<>(user.getRoles()));
        }
        return user;
    }

    private void insertBatch(int userId, List<Role> roles) {
        String sql = "INSERT INTO user_roles VALUES (?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Role role = roles.get(i);
                ps.setInt(1, userId);
                ps.setString(2, role.toString());
            }

            @Override
            public int getBatchSize() {
                return roles.size();
            }
        });
    }

    @Transactional
    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        User user = DataAccessUtils.singleResult(
                jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id)
        );
        return setRoles(user);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        User user = DataAccessUtils.singleResult(
                jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email)
        );
        return setRoles(user);
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        SqlRowSet allRoles = jdbcTemplate.queryForRowSet("SELECT * FROM user_roles");
        Map<Integer, Set<Role>> map = new HashMap<>();
        while (allRoles.next()) {
            map.merge(
                    allRoles.getInt("user_id"),
                    createNewRoleSet(Role.valueOf(allRoles.getString("role"))),
                    (roles, roles2) -> {
                        roles.addAll(roles2);
                        return roles;
                    });
        }
        users.forEach(user -> user.setRoles(map.get(user.getId())));
        return users;
    }

    private Set<Role> createNewRoleSet(Role role) {
        Set<Role> set = new HashSet<>();
        set.add(role);
        return set;
    }

    private User setRoles(User user) {
        if (user == null) {
            return null;
        }
        Set<Role> roles = jdbcTemplate
                .queryForList("SELECT role FROM user_roles WHERE user_id=?", String.class, user.getId())
                .stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());

        user.setRoles(roles);
        return user;
    }
}
