package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentDao studentDao;

    @Transactional
    public void addOrUpdate(Student s) { studentDao.save(s); }

    @Transactional(readOnly = true)
    public List<Student> getAll() { return studentDao.list(); }

    @Transactional
    public void delete(int id) { studentDao.delete(id); }
}
