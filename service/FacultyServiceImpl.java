package org.example.service;



import org.example.exception.FacultyNotFoundException;
import org.example.model.Faculty;
import org.example.model.Student;
import org.example.repository.FacultyRepository;
import org.example.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;


@Service
public  class FacultyService {

        private final FacultyRepository facultyRepository;
        private final StudentRepository studentRepository;


        public FacultyService (FacultyRepository facultyRepository,
                               StudentRepository studentRepository){
            this.facultyRepository = facultyRepository;
            this.studentRepository = studentRepository;

        }
        public  Faculty create(Faculty faculty){
            faculty.setId(null);
            return facultyRepository.save(faculty);
        }
        public Faculty read(long id){
            return facultyRepository.findById(id).orElseThrow(() -> new FacultyNotFoundException (id));
}
        public Faculty update(long id,
                      Faculty faculty) {
        Faculty oldFaculty = read (id);
        oldFaculty.setName(faculty.getName());
        oldFaculty.setName(faculty.getColor());
        return facultyRepository.save(oldFaculty);
}
        public Faculty delete(long id){
            Faculty faculty = read(id);
            facultyRepository.delete(faculty);
            return faculty;
}
        public Collection<Faculty> findByColor(String color) {
            return facultyRepository.findAllByColor ( color );


            public Collection<Faculty> findByColorOrName(String colorOrName){
                return facultyRepository.findAllByColorIgnoreCaseOrNameIgnoreCase(colorOrName, colorOrName);

            }
            public Collection<Student> getStudentsByFaculty(long id) {
                Faculty faculty = read(id);
                return studentRepository.findAllByFaculty_Id(faculty.getId())


            }

        }
}
