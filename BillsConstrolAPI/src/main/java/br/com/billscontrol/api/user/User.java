package br.com.billscontrol.api.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class User {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private String email;

    @Column(name = "user_type")
    private UserType userType;

    @ElementCollection
    @CollectionTable(name = "phone")
    private Set<String> phones;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "create_instant")
    private Instant createInstant;

    @Column(name = "last_update_user")
    private String lastUpdateUser;

    @Column(name = "last_update_instant")
    private Instant lastUpdateInstant;


}
