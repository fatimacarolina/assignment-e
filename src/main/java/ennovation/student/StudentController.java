package ennovation.student;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController("/ennovation")
public class StudentController {

    StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){ this.studentService = studentService;}

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<Student> getStudents() throws NotFoundException {
        return studentService.getStudents();
    }

    @GetMapping("/{studentId}")
    @ResponseStatus(HttpStatus.FOUND)
    public Student getStudentById( @PathVariable Long studentId) throws NotFoundException {
        return studentService.getStudentById(studentId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createStudent(@Valid @RequestBody Student student){
        studentService.createStudent(student);
    }

}
