package br.com.billscontrol.api.paymenttype;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentTypeService {

	PaymentType save(PaymentType paymentType);
	Optional<PaymentType> findById(Long id);
	PaymentType update(PaymentType paymentType);
    Page<PaymentType> findAll(Pageable pageable);
	boolean isEmpty();
			
}
