package org.example.repository;


import org.example.model.Faculty;
import org.example.model.Student;

import java.util.Collection;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty,Long> {
    Collection<Faculty> findAllByColor(String color);
    Collection<Faculty> findAllByColorIgnoreCaseOrNameIgnoreCase(String color, String name);

    Collection<Student> findAllByFaculty_Id(long faculyId);

}
