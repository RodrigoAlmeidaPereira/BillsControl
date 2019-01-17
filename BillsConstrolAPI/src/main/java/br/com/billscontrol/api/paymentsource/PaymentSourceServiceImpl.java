package br.com.billscontrol.api.paymentsource;

import br.com.billscontrol.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentSourceServiceImpl implements PaymentSourceService {

	private PaymentSourceRepository repository;
	
	@Override
	public PaymentSource save(PaymentSource paymentSource) {
		return this.repository.save(paymentSource);
	}

	@Override
	public PaymentSource update(PaymentSource paymentSource) {

		if (!repository.existsById(paymentSource.getId())) {
			throw ResourceNotFoundException.builder().resourceId(paymentSource.getId()).build();
		}

		return this.save(paymentSource);
	}

	@Override
	public Optional<PaymentSource> findById(Long id) {
		return repository.findById(id);
	}
	
	@Override
	public Page<PaymentSource> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	@Override
	public boolean isEmpty() {
		return repository.count() <= 0;
	}

	@Override
	public Page<PaymentSource> findAll(Pageable pageable, Long financialControlId) {
		return repository.findAllByFinancialControlId(pageable, financialControlId);
	}
}
