package com.d24hostels.bo.custom.dao.custom.impl;

import com.d24hostels.dao.custom.StudentDao;
import com.d24hostels.entity.*;
import com.d24hostels.util.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {
    @Override
    public List<Student> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery(
                "SELECT s.sid, s.name, s.nic, s.contact, s.email, s.gender, s.university.uniName FROM Student s"
        );

        List<Object[]> list = query.list();
        System.out.println("ob :"+list.size());
        List<Student> students = new ArrayList<>();

        for (Object[] o : list) {
            Student student = new Student();

            student.setSid(String.valueOf(o[0]));
            student.setName(String.valueOf(o[1]));
            student.setNic(String.valueOf(o[2]));
            student.setContact(String.valueOf(o[3]));
            student.setEmail(String.valueOf(o[4]));
            student.setGender(String.valueOf(o[5]));

            University university = new University();
            university.setUniName(String.valueOf(o[6]));
            student.setUniversity(university);

            students.add(student);
        }

        transaction.commit();
        session.close();

        System.out.println("dao: "+students.size());
        return students;
    }

    @Override
    public void save(Student entity) throws Exception {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();

        session.persist(entity);

        transaction.commit();
        session.close();
    }

    @Override
    public void update(Student entity) throws Exception {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();

        session.update(entity);

        transaction.commit();
        session.close();
    }

    @Override
    public void delete(String s) throws Exception {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();

        Student student = session.get(Student.class, s);
        session.remove(student);

        transaction.commit();
        session.close();
    }

    @Override
    public Student search(String s) throws Exception {
        Session session= FactoryConfiguration.getInstance().getSession();
        Transaction transaction=session.beginTransaction();

        Student student = session.get(Student.class, s);

        transaction.commit();
        session.close();
        return student;
    }
}
