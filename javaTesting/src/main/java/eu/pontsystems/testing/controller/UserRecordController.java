package eu.pontsystems.testing.controller;

import eu.pontsystems.testing.persistence.entity.UserRecord;
import eu.pontsystems.testing.persistence.repository.UserRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/user")
@RequiredArgsConstructor
public class UserRecordController {
    private final UserRecordRepository userRecordRepository;

    @GetMapping()
    public List<UserRecord> getAllRecords() {
        return userRecordRepository.findAll();
    }

    @GetMapping(value = "{userId}")
    public UserRecord getUserById(@PathVariable(value="userId") Long userId) {
        Optional<UserRecord> userRecord = userRecordRepository.findById(userId);
        return userRecord.orElse(null);
    }

    @PostMapping
    public UserRecord createRecord(@RequestBody UserRecord userRecord){
        return userRecordRepository.save(userRecord);
    }

    @PutMapping
    public UserRecord updateUserRecord(@RequestBody UserRecord userRecord) throws InvalidRequestException {
        if (userRecord == null || userRecord.getUserId() == null) {
            throw new InvalidRequestException("UserRecord or ID must not be null!");
        }
        Optional<UserRecord> optionalRecord = userRecordRepository.findById(userRecord.getUserId());
        if (optionalRecord.isEmpty()){
            throw new InvalidRequestException("User with ID " + userRecord.getUserId() + " does not exist.");
        }
        UserRecord existingUserRecord = optionalRecord.get();

        existingUserRecord.setName(userRecord.getName());
        existingUserRecord.setAge(userRecord.getAge());
        existingUserRecord.setAddress(userRecord.getAddress());

        return userRecordRepository.save(existingUserRecord);
    }

    @DeleteMapping(value = "{userId}")
    public void deleteUserById(@PathVariable(value = "userId") Long userId){
        if (userRecordRepository.findById(userId).isEmpty()) {
            throw new InvalidRequestException("User with ID " + userId + " does not exist.");
        }
        userRecordRepository.deleteById(userId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    static
    class InvalidRequestException extends RuntimeException {
        public InvalidRequestException(String s) {
            super(s);
        }
    }
}