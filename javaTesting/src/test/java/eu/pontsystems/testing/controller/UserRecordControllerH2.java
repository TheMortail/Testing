package eu.pontsystems.testing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.pontsystems.testing.TestingApplication;
import eu.pontsystems.testing.persistence.entity.UserRecord;
import eu.pontsystems.testing.persistence.repository.UserRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = {
        TestingApplication.class
})
class UserRecordControllerH2 {
    @Autowired
    UserRecordRepository userRecordRepository;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Get all records")
    void getAllRecords_success() throws Exception {
        List<UserRecord> records = userRecordRepository.findAll();
        System.out.println("---///Records\\\\\\---");
        records.forEach(System.out::println);
        System.out.println("---///Records\\\\\\---");

        mockMvc.perform(MockMvcRequestBuilders
                .get("/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("Teszt Géza")));
    }

    @Test
    @DisplayName("Get user by id")
    void getUserById_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Teszt Aladár")));
    }

    @Test
    @DisplayName("Create user")
    void createRecord_success() throws Exception {
        UserRecord record = UserRecord.builder()
                .userId(10L)
                .name("Teszt Peter")
                .age(20)
                .address("Budapest")
                .build();

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(record));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Teszt Peter")));
    }

    @Test
    @DisplayName("Update user")
    void updateUserRecord_success() throws Exception {
        UserRecord updatedRecord = UserRecord.builder()
                .userId(1L)
                .name("Teszt Gábor")
                .age(35)
                .address("Pusztakotkodács")
                .build();

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedRecord));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Teszt Gábor")));
    }

    @Test
    @DisplayName("Update user with null id")
    void updateUserRecord_nullId() throws Exception {
        UserRecord updatedRecord = UserRecord.builder()
                .name("Teszt József")
                .age(40)
                .address("Veszprém")
                .build();

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedRecord));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof UserRecordController.InvalidRequestException))
                .andExpect(result ->
                        assertEquals("UserRecord or ID must not be null!", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    @DisplayName("Update a nonexistent user")
    void updateUserRecord_recordNotFound() throws Exception {
        UserRecord updatedRecord = UserRecord.builder()
                .userId(5L)
                .name("Teszt József")
                .age(40)
                .address("Veszprém")
                .build();

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedRecord));

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof UserRecordController.InvalidRequestException))
                .andExpect(result ->
                        assertEquals("User with ID 5 does not exist.", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    @DisplayName("Delete user")
    void deleteUserById_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/user/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Delete a nonexistent user")
    void deleteUserById_notFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/user/5")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof UserRecordController.InvalidRequestException))
                .andExpect(result ->
                        assertEquals("User with ID 5 does not exist.", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }
}