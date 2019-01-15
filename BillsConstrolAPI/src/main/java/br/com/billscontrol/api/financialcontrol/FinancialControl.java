package br.com.billscontrol.api.financialcontrol;

import br.com.billscontrol.api.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Collection;

@Entity(name = "financial_control")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class FinancialControl {

    @Id @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany
    @JoinTable(name = "financial_control_user",
        joinColumns = @JoinColumn(name = "financial_control_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Collection<User> allowedUsers;

    @NotNull
    @Column(name = "create_user")
    private String createUser;

    @NotNull
    @Column(name = "create_instant")
    private Instant createInstant;

    @Column(name = "last_update_user")
    private String lastUpdateUser;

    @Column(name = "last_update_instant")
    private Instant lastUpdateInstant;
}
