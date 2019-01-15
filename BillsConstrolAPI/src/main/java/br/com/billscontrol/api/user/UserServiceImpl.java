package br.com.billscontrol.api.user;

import br.com.billscontrol.api.account.Account;
import br.com.billscontrol.api.account.AccountService;
import br.com.billscontrol.api.bank.Bank;
import br.com.billscontrol.api.bank.BankService;
import br.com.billscontrol.api.category.Category;
import br.com.billscontrol.api.category.CategoryService;
import br.com.billscontrol.api.financialcontrol.FinancialControl;
import br.com.billscontrol.api.financialcontrol.FinancialControlService;
import br.com.billscontrol.api.paymentsource.PaymentOperation;
import br.com.billscontrol.api.paymentsource.PaymentSource;
import br.com.billscontrol.api.paymentsource.PaymentSourceService;
import br.com.billscontrol.api.paymenttype.PaymentType;
import br.com.billscontrol.api.paymenttype.PaymentTypeService;
import br.com.billscontrol.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private FinancialControlService financialControlService;
    private BankService bankService;
    private AccountService accountService;
    private PaymentTypeService paymentTypeService;
    private PaymentSourceService paymentSourceService;
    private CategoryService categoryService;


    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public User update(User user) {
        if (!repository.existsById(user.getId())) {
            throw new ResourceNotFoundException();
        }

        return repository.save(user);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }

    @Override
    public boolean isEmpty() {
        return this.repository.count() <= 0;
    }

    @Override
    public void init(User user) {

        String createUser = "SYSTEM";

        User userEntity = user.toBuilder()
                .userType(UserType.ADMIN)
                .createInstant(Instant.now())
                .createUser(createUser)
                .build();

        userEntity = repository.save(userEntity);

        FinancialControl financialControl = FinancialControl.builder()
                .owner(userEntity)
                .name("Finanças Pessoais")
                .description("Controle de finanças pessoais")
                .createInstant(Instant.now())
                .createUser(createUser)
                .build();

        financialControl = financialControlService.save(financialControl);

        Bank itau = Bank.builder()
                .financialControl(financialControl)
                .name("Itau")
                .description("Banco Itau")
                .createInstant(Instant.now())
                .createUser(createUser)
                .build();

        Bank nubank = Bank.builder()
                .financialControl(financialControl)
                .name("Nubank")
                .description("Banco Digital Nubank")
                .createInstant(Instant.now())
                .createUser(createUser)
                .build();

        itau = bankService.save(itau);
        nubank = bankService.save(nubank);

        Account itauAccount = Account.builder()
                .bank(itau)
                .financialControl(financialControl)
                .name("Conta Corrente")
                .description("Conta Corrente Banco Itau")
                .createInstant(Instant.now())
                .createUser(createUser)
                .build();


        Account nubankAccount = Account.builder()
                .bank(nubank)
                .financialControl(financialControl)
                .name("Conta Digital")
                .description("Conta Digital Nubank")
                .createInstant(Instant.now())
                .createUser(createUser)
                .build();

        itauAccount = accountService.save(itauAccount);
        nubankAccount = accountService.save(nubankAccount);

        PaymentType typeCredit = PaymentType.builder()
                .financialControl(financialControl)
                .name("Cartao de Credito")
                .description("Despesas ou receitas de cartao de credito")
                .createInstant(Instant.now())
                .createUser(createUser)
                .build();

        PaymentType typeDebit = PaymentType.builder()
                .financialControl(financialControl)
                .name("Cartao de Debito")
                .description("Despesas ou receitas de cartao de debito")
                .createInstant(Instant.now())
                .createUser(createUser)
                .build();

        PaymentType typeMoney = PaymentType.builder()
                .financialControl(financialControl)
                .name("Dinheiro")
                .description("Despesas ou receitas em dinheiro")
                .createInstant(Instant.now())
                .createUser(createUser)
                .build();

        typeCredit = paymentTypeService.save(typeCredit);
        typeDebit = paymentTypeService.save(typeDebit);
        typeMoney = paymentTypeService.save(typeMoney);

        PaymentSource itauCredCard = PaymentSource.builder()
                .account(itauAccount)
                .paymentType(typeCredit)
                .operation(PaymentOperation.OUT)
                .financialControl(financialControl)
                .name("Cartao de Credito Itau")
                .description("Cartao de Credito Itau")
                .createInstant(Instant.now())
                .createUser(createUser)
                .build();


        PaymentSource itauDebitCard = PaymentSource.builder()
                .account(itauAccount)
                .paymentType(typeDebit)
                .operation(PaymentOperation.OUT)
                .financialControl(financialControl)
                .name("Cartao de Debito Itau")
                .description("Cartao de Debito Itau")
                .createInstant(Instant.now())
                .createUser(createUser)
                .build();


        PaymentSource itauMoney = PaymentSource.builder()
                .account(itauAccount)
                .paymentType(typeMoney)
                .operation(PaymentOperation.IN_OUT)
                .financialControl(financialControl)
                .name("Dinheiro Itau")
                .description("Dinheiro Itau")
                .createInstant(Instant.now())
                .createUser(createUser)
                .build();


        PaymentSource nubankCredCard = PaymentSource.builder()
                .account(nubankAccount)
                .paymentType(typeCredit)
                .operation(PaymentOperation.OUT)
                .financialControl(financialControl)
                .name("Cartao de Credito Nubank")
                .description("Cartao de Credito Nubank")
                .createInstant(Instant.now())
                .createUser(createUser)
                .build();

        paymentSourceService.save(itauCredCard);
        paymentSourceService.save(itauDebitCard);
        paymentSourceService.save(itauMoney);
        paymentSourceService.save(nubankCredCard);

        Category grossery = Category.builder()
                .financialControl(financialControl)
                .name("Compras")
                .description("Gastos com Compras")
                .createInstant(Instant.now())
                .createUser(createUser)
                .build();


        Category cloths = Category.builder()
                .financialControl(financialControl)
                .name("Vestuario")
                .description("Gastos com Vestuario")
                .createInstant(Instant.now())
                .createUser(createUser)
                .build();

        categoryService.save(grossery);
        categoryService.save(cloths);

    }
}
