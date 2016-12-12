package ru.javawebinar.topjava.dao;

import java.util.List;

public interface GenericDao<T> {
    void add(T object);

    void delete(long id);

    void update(T object);

    List<T> getAll();

    T getById(long id);
}
