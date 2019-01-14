package br.com.billscontrol.api.user;

import br.com.billscontrol.api.financialcontrol.FinancialControl;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.Collection;
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

    @OneToMany(mappedBy = "owner")
    private Collection<FinancialControl> myFinancialControls;

    @ManyToMany(mappedBy = "allowedUsers")
    @JsonBackReference
    private Collection<FinancialControl> guestFinancialControls;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "create_instant")
    private Instant createInstant;

    @Column(name = "last_update_user")
    private String lastUpdateUser;

    @Column(name = "last_update_instant")
    private Instant lastUpdateInstant;


}
