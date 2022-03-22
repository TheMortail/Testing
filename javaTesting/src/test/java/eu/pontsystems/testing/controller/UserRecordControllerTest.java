package eu.pontsystems.testing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.pontsystems.testing.persistence.entity.UserRecord;
import eu.pontsystems.testing.persistence.repository.UserRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(UserRecordController.class)
class UserRecordControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    UserRecordRepository userRecordRepository;

    UserRecord RECORD_1 = new UserRecord(1L, "Teszt Géza", 20, "Zalabér-batyk");
    UserRecord RECORD_2 = new UserRecord(2L, "Teszt Sándor", 35, "Pusztakotkodács");

    @Test
    @DisplayName("Get all records")
    void getAllRecords_success() throws Exception {
        List<UserRecord> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2));

        Mockito.when(userRecordRepository.findAll()).thenReturn(records);

        System.out.println("---///Records\\\\\\---");
        records.forEach(System.out::println);
        System.out.println("---///Records\\\\\\---");

        mockMvc.perform(MockMvcRequestBuilders
                .get("/user")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Teszt Géza")));
    }

    @Test
    @DisplayName("Get user by id")
    void getUserById_success() throws Exception {
        Mockito.when(userRecordRepository.findById(RECORD_1.getUserId())).thenReturn(java.util.Optional.of(RECORD_1));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/user/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Teszt Géza")));
    }

    @Test
    @DisplayName("Create user")
    void createRecord_success() throws Exception {
        UserRecord record = UserRecord.builder()
                .name("Teszt Péter")
                .age(20)
                .address("Budapest")
                .build();

        Mockito.when(userRecordRepository.save(record)).thenReturn(record);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(record));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Teszt Péter")));
    }

    @Test
    @DisplayName("Update user")
    void updateUserRecord_success() throws Exception {
        UserRecord updatedRecord = UserRecord.builder()
                .userId(1L)
                .name("Teszt Gábor")
                .age(23)
                .address("Szolnok")
                .build();

        Mockito.when(userRecordRepository.findById(RECORD_1.getUserId())).thenReturn(Optional.of(RECORD_1));
        Mockito.when(userRecordRepository.save(updatedRecord)).thenReturn(updatedRecord);

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

        Mockito.when(userRecordRepository.findById(updatedRecord.getUserId())).thenReturn(Optional.empty());

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
        Mockito.when(userRecordRepository.findById(RECORD_2.getUserId())).thenReturn(Optional.of(RECORD_2));


        mockMvc.perform(MockMvcRequestBuilders
                .delete("/user/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Delete a nonexistent user")
    void deleteUserById_notFound() throws Exception {
        Mockito.when(userRecordRepository.findById(5L)).thenReturn(Optional.empty());

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