package org.example.controller;


import org.example.model.Faculty;
import org.example.service.FacultyService;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;

@RestController
@RequestMapping("faculties")
public class FacultyController {
    private final FacultyService facultyService;
    public FacultyController(FacultyService facultyService){
        this.facultyService = facultyService;
    }


    @PostMapping
    public Faculty create(@RequestBody Faculty faculty){return facultyService.create(faculty); }

    @GetMapping("{id}")
    public Faculty read(@PathVariable long id){
        return facultyService.read(id);
    }


    @PutMapping("/{id}")
    public Faculty update(@PathVariable long id,
                                @RequestBody Faculty faculty){
       return facultyService.update(id, faculty);
    }


    @DeleteMapping("/{id}")
    public Faculty delete(@PathVariable long id){
        return facultyService.delete(id);
    }


    @GetMapping
    public Collection<Faculty> findByColor(@RequestParam String color){
        return facultyService.findByColor(color);

        @GetMapping(params = "colorOrName")
        public Collection<Faculty> findByColorOrName(@RequestParam String colorOrName) {
            return facultyService.findByColorOrName(colorOrName);
        }
        @GetMapping("/{id}/students")
        public Collection<Stedent> getStudentsByFaculty(@PathVariable long id) {
            return facultyService.getStudentsByFaculty(id);
        }


    }

}
