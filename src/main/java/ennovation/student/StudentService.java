package ennovation.student;

import ennovation.student.Student;
import ennovation.student.StudentRepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }
    public Student getStudentById(Long id) throws NotFoundException {
        return studentRepository.findById(id).orElseThrow(() -> new NotFoundException("Student not found"));
    }

    public void createStudent(Student student){
        if(student.getId() != null){
            throw new IllegalArgumentException("Student should not be provided an ID upon creation.");
        }
        studentRepository.save(student);
    }
}
