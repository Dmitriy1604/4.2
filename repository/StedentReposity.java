package org.example.repository;



import java.util.Collection;
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    public Collection<Student> findByAge(int age);

    public Collection<Student> findByAgeBetween(int min, int max);

    public Collection<Student> findByFacultyId(long facultyId);

    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    public int getAllStudentsNumber();

    @Query(value = "SELECT AVG(age) FROM student", nativeQuery = true)
    public double getAverageAge();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT :limit", nativeQuery = true)
    public List<ListOfStudents> getLastStudentsById(@Param("limit") int limit);

}