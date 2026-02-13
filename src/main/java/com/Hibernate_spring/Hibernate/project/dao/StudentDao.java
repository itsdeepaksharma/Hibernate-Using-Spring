package com.Hibernate_spring.Hibernate.project.dao;


import com.Hibernate_spring.Hibernate.project.entity.Student;

import java.util.List;

public interface StudentDao {

    void save(Student student);

    Student findById(Long Id);

    List<Student> findAll();

    void delete(Long id);

    void update(Student student);
}
