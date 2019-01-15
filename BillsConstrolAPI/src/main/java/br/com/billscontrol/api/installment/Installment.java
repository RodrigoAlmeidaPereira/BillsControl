package br.com.billscontrol.api.installment;

import br.com.billscontrol.api.transaction.Transaction;
import br.com.billscontrol.api.transaction.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@Entity(name = "installment")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Data
public class Installment {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private BigDecimal value;
    private Date expireDate;
    private Date effectiveDate;

    @Column(name = "transaction_status")
    private TransactionStatus transactionStatus;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

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
