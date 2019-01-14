package br.com.billscontrol.config;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import br.com.billscontrol.api.user.User;
import br.com.billscontrol.api.user.UserService;
import br.com.billscontrol.api.user.UserType;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.com.billscontrol.api.account.Account;
import br.com.billscontrol.api.account.AccountService;
import br.com.billscontrol.api.category.Category;
import br.com.billscontrol.api.category.CategoryService;
import br.com.billscontrol.api.paymenttype.PaymentType;
import br.com.billscontrol.api.paymenttype.PaymentTypeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@Transactional
public class ConfigDML implements ApplicationRunner {

	private final CategoryService categoryService;
	private final AccountService accountService;
	private final PaymentTypeService paymentTypeService;
	private final UserService userService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		if (categoryService.isEmpty()) {
			insertCategories();
		}
		
		if (paymentTypeService.isEmpty()) {
			insertPaymentTypes();
		}
		
		if (accountService.isEmpty()) {
			insertAccounts();
		}

		if (userService.isEmpty()) {
			inserUsers();
		}

		userService.findAll(PageRequest.of(0, 20)).forEach(System.out::println);

	}

	private void insertAccounts() {
		
		Page<PaymentType> paymentsType = paymentTypeService.findAll(PageRequest.of(0, 1000));
		
		accountService.save(createAccount("Conta Itaú", "Conta Corrente Itaú", paymentsType.getContent()));
		accountService.save(createAccount("Conta Santander", "Conta Corrente Santander", paymentsType.getContent()));
	}

	private void insertPaymentTypes() {
		paymentTypeService.save(createPaymentType("Cartão de Crédito", "Transação com cartão de crédito."));
		paymentTypeService.save(createPaymentType("Cartão de Débito", "Transação com cartão de débito."));
		paymentTypeService.save(createPaymentType("Dinheiro", "Transação com dinheiro."));
	}

	private void insertCategories() {
		categoryService.save(createCategory("Compras", "Gastos com supermercado"));
		categoryService.save(createCategory("Carro", "Gastos com carro"));
		categoryService.save(createCategory("Vestuáro", "Gastos com roupas"));
	}

	private void inserUsers() {
		userService.save(createUser("Rodrigo", "rodrigoalmeida.as@gmail.com", UserType.ADMIN, Set.of("995543599")));
		userService.save(createUser("Lizandra", "lizandra.azuos@gmail.com", UserType.ADMIN, Set.of("980949999", "1234567")));
	}
	
	private Category createCategory(String name, String description) {
		
		return Category.builder()
				.name(name)
				.description(description)
				.createUser("SYSTEM")
				.createInstant(java.time.Instant.now())
				.build();
	}
	
	private PaymentType createPaymentType(String name, String description) {
		return PaymentType.builder()
				.name(name)
				.description(description)
				.createUser("SYSTEM")
				.createInstant(java.time.Instant.now())
				.build();
	}
	
	private Account createAccount(String name, String description, Collection<PaymentType> paymentTypes) {
		return Account.builder()
				.name(name)
				.description(description)
				.paymentTypes(paymentTypes)
				.createUser("SYSTEM")
				.createInstant(java.time.Instant.now())
				.build();
	}


	private User createUser(String name, String email, UserType userType, Set<String> phones) {
		return User.builder()
				.name(name)
				.email(email)
				.userType(userType)
				.phones(phones)
				.createUser("System")
				.createInstant(java.time.Instant.now())
				.build();
	}
}
