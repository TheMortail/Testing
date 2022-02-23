package eu.pontsystems.testing.persistence.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    @NonNull
    private String name;

    @NonNull
    private Integer age;

    @NonNull
    private String address;
}
