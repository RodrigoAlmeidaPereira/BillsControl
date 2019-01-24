package br.com.billscontrol.api.paymenttype;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PaymentTypeService {

	PaymentType save(PaymentType paymentType);
	Optional<PaymentType> findById(Long id);
	PaymentType update(PaymentType paymentType);
	Page<PaymentType> findAll(Pageable pageable);
	Page<PaymentType> findAll(Pageable pageable, Long financialControlId);
	void delete(Long id);
	boolean isEmpty();

	PaymentType toEntity(PaymentTypeVO vo);
	PaymentTypeVO toVO(PaymentType entity);
}
