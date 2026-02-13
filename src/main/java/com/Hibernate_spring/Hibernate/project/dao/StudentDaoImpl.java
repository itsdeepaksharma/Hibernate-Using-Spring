package com.Hibernate_spring.Hibernate.project.dao;

import com.Hibernate_spring.Hibernate.project.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class StudentDaoImpl implements StudentDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(Student student) {
        Session session = sessionFactory.getCurrentSession();
        session.save(student);

    }

    @Override
    public Student findById(Long Id) {

        Session session = sessionFactory.getCurrentSession();
        return session.get(Student.class, Id);

    }

    @Override
    public List<Student> findAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Student", Student.class).getResultList();
    }

    @Override
    public void delete(Long Id) {
        Session session = sessionFactory.getCurrentSession();
        Student student = session.get(Student.class, Id);
        if(student!= null){
            session.delete(student);
        }
    }

    @Override
    public void update(Student student) {

        Session session = sessionFactory.getCurrentSession();
        session.update(student);

    }
}
