package br.com.billscontrol.api.paymenttype;

import br.com.billscontrol.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentTypeServiceImpl implements PaymentTypeService {

	private PaymentTypeRepository repository;
	
	@Override
	public PaymentType save(PaymentType paymentType) {
		return this.repository.save(paymentType);
	}
	
	@Override
	public PaymentType update(PaymentType paymentType) {
		
		if (!repository.existsById(paymentType.getId())) {
			throw new ResourceNotFoundException();
		} 

		return this.save(paymentType);
	}
	
	@Override
	public Optional<PaymentType> findById(Long id) {		
		return repository.findById(id);
	}
	
	@Override
	public Page<PaymentType> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	@Override
	public boolean isEmpty() {
		return repository.count() <= 0;
	}

	@Override
	public Page<PaymentType> findAll(Pageable pageable, Long financialControlId) {
		return this.repository.findAllByFinancialControlId(pageable, financialControlId);
	}
}
