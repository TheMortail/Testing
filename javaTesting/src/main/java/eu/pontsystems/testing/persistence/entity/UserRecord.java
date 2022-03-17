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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;

    @NonNull
    @Column(name="name")
    private String name;

    @Column(name="age")
    private Integer age;

    @Column(name="address")
    private String address;
}
