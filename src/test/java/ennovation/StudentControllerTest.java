package ennovation;

import com.fasterxml.jackson.databind.ObjectMapper;
import ennovation.student.Student;
import ennovation.student.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql")
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired private ObjectMapper mapper;

    @Autowired
    StudentRepository studentRepository;

    @Test
    void testGetStudentById() throws Exception {
        mockMvc
                .perform(get("/ennovation"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize((int) studentRepository.count())));
    }

    @Test
    void createStudent() throws Exception {
        Student newStudent = new Student("fatima", "bdeir");
        Long newStudentId =
                mapper
                        .readValue(
                                mockMvc
                                        .perform(
                                                post("/ennovation")
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .content(mapper.writeValueAsString(newStudent)))
                                        .andExpect(status().isCreated())
                                        .andReturn()
                                        .getResponse()
                                        .getContentAsString(),
                                Student.class)
                        .getId();
        newStudent.setId(newStudentId); // Populate the ID of the hotel after successful creation

        assertThat(
                studentRepository
                        .findById(newStudentId)
                        .orElseThrow(
                                () -> new IllegalStateException("New Hotel has not been saved in the repository")),
                equalTo(newStudent));
    }

}