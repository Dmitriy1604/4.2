package org.example.service;

import com.example.demo.model.Student;
import org.example.exception.StudentNotFoundException;
import org.example.model.Faculty;
import org.example.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Service
public class StudentService {
    private final StudentRepository studentRepository;
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }
    public Student create(Student student){
        student.setId(null);
        return studentRepository.save(student);
    }
    public Student read(long id){
        return studentRepository.findBy(id).orElseThrow(() -> new StudentNotFoundException (id));
    }
    public Student update(long id,
                          Student student){
        Student oldStudent = read(id);
        oldStudent.setName(student.getName());
        oldStudent.setAge(student.getAge());
        return studentRepository.save(oldStudent);
    }
    public Student delete(long id){
        Student student = read(id);
        studentRepository.delete(student);
        return student;
    }
    public Collection<Student> findByAge(int age){
        return studentRepository.findAllByAge(age);
    }

    public Collection<Student> findByAgeBetween(int minAge, int maxAge){
        return studentRepository.findAllByAgeBetween(minAge,maxAge);
    }
    public Faculty getFacultyByStudent(long id){
        return read(id).gerFaculty();
    }
}




