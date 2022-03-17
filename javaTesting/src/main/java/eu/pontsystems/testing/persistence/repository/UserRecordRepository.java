package eu.pontsystems.testing.persistence.repository;

import eu.pontsystems.testing.persistence.entity.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRecordRepository extends JpaRepository<UserRecord, Long> {
}
