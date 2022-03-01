package ennovation;

import ennovation.student.Student;
import ennovation.student.StudentRepository;
import ennovation.student.StudentService;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class StudentServiceTest {

    @MockBean
    StudentRepository studentRepository;
    @Autowired
    StudentService studentService;

    //Test that the Student inserted in the data.sql is retrieved correctly.
    @Test
    public void testGet() throws NotFoundException {
        Student expected = studentRepository.save(new Student("wendy", "waffles"));
        Student actual = studentService.getStudentById(expected.getId());
        assertThat(actual, equalTo(expected));
    }

    @Test
    public void testCreate() {
        Student expected = new Student("wendy", "waffles");
        studentService.createStudent(expected);
        List<Student> actual = studentRepository.findAll().stream().filter(student -> student.getFirstName().equals("wendy") && student.getLastName().equals("waffles")).collect(Collectors.toList());
        assertThat(actual, hasSize(1));
        assertThat(actual.get(0), equalTo(expected));
    }

}