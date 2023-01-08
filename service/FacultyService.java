package org.example.service;

import com.example.demo.model.Faculty;

import java.util.Collection;

public interface FacultyService {
    Faculty addFaculty(Faculty student);

    Faculty findFaculty(long id);

    Faculty editFaculty(long id, Faculty student);

    void deleteFaculty(long id);

    Faculty read(long id);

    Faculty update(long id , Faculty faculty);

    Faculty delete(long id);

    Collection<Faculty> findByColor(String color);
}
