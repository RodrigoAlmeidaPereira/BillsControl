package br.com.billscontrol.api.paymentsource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PaymentSourceService {

	PaymentSource save(PaymentSource category);
	Optional<PaymentSource> findById(Long id);
	PaymentSource update(PaymentSource category);
    Page<PaymentSource> findAll(Pageable pageable);
	boolean isEmpty();
			
}
