package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.domain.Task;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TaskDaoImpl implements TaskDAO {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Optional<Task> getById(int id) {
        return Optional.ofNullable(entityManager.find(Task.class, id));
    }

    @Override
    public List<Task> getAll(int page, int size) {
        return entityManager.createQuery("from Task", Task.class)
                .setMaxResults(size)
                .setFirstResult(page * size - size)
                .getResultList();
    }

    @Override
    public Long getTaskCount() {
        return entityManager.createQuery("SELECT COUNT(t) FROM Task t", Long.class).getSingleResult();
    }

    @Override
    public void create(Task task) {
        entityManager.persist(task);
    }

    @Override
    public Optional<Task> update(Task task) {
        return Optional.ofNullable(entityManager.merge(task));
    }

    @Override
    public void delete(Task task) {
        entityManager.remove(task);
    }

}
