package br.com.billscontrol.config;

import java.util.Collection;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.billscontrol.api.account.Account;
import br.com.billscontrol.api.account.AccountService;
import br.com.billscontrol.api.category.Category;
import br.com.billscontrol.api.category.CategoryService;
import br.com.billscontrol.api.paymenttype.PaymentType;
import br.com.billscontrol.api.paymenttype.PaymentTypeService;
import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ConfigDML implements ApplicationRunner {

	private final CategoryService categoryService;
	private final AccountService accountService;
	private final PaymentTypeService paymentTypeService;
	
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

}
