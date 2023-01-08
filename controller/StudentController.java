package org.example.controller;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.create (student);
    }

    @GetMapping("/{id}")
    public Student read(@PathVariable long id) {
        return studentService.read (id);
    }

    @PutMapping("/{id}")
    public Student update(@PathVariable lond id ,
                          @RequestBody Student student) {
        return studentService.update(id , student);
    }

    @DeleteMapping("/{id}")
    public Student delete(@PathVariable long id) {
        return studentService.delete(id);
    }

    @GetMapping
    public Collection<Student> findByAge(@RequestParam int age) {
        return studentService.findByAge(age);
    }

    @GetMapping(params = {"minAge","maxAge"})
    public Collection<Student> findByAgeBetween(@RequestParam int minAge,
                                                @RequestParam int maxAge){
        return studentService.findByAgeBetween(minAge,maxAge);
    }
    @GetMapping("{id}/faculty")
    public Faculty getFacultyByStudent(@PathVariable long id){
        return studentService.getFacultyByStudent(id);
    }
}

