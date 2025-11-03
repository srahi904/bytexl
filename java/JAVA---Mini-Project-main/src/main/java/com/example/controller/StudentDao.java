package com.example.controller;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class StudentDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void save(Student student) {
        sessionFactory.getCurrentSession().saveOrUpdate(student);
    }

    public Student get(int id) {
        return sessionFactory.getCurrentSession().get(Student.class, id);
    }

    public List<Student> list() {
        return sessionFactory.getCurrentSession().createQuery("from Student", Student.class).list();
    }

    public void delete(int id) {
        Student s = get(id);
        if (s != null) sessionFactory.getCurrentSession().delete(s);
    }
}
