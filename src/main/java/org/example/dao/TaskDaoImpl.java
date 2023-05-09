package org.example.dao;

import org.example.domain.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskDaoImpl implements TaskDAO {
    private final SessionFactory sessionFactory;

    public TaskDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Task> findOne(int id) {
        return Optional.ofNullable(getSession().find(Task.class, id));
    }

    @Override
    public List<Task> findAll(int page, int size) {
        return getSession().createQuery("SELECT t from Task t order by t.id", Task.class)
                .setMaxResults(size)
                .setFirstResult(page * size - size)
                .getResultList();
    }

    @Override
    public Long getTaskCount() {
        return getSession().createQuery("SELECT COUNT(t) FROM Task t", Long.class).getSingleResult();
    }

    @Override
    public void create(Task task) {
        getSession().persist(task);
    }

    @Override
    public Optional<Task> update(Task task) {
        return Optional.ofNullable(getSession().merge(task));
    }

    @Override
    public void delete(Task task) {
        getSession().remove(task);
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
