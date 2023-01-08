package org.example.service;



import org.example.model.Student;

import java.util.Collection;

public interface StudentService {
    Student addStudent(Student student);

    Student findStudent(long id);

    Student editStudent(long id,Student student);

    void deleteStudent(long id);

    Student read(long id);

    Student update(lond id , Student student);

    Student delete(long id);

    Student create(Student studentRecord);

    Collection<Student> findByAge(int age);
}
