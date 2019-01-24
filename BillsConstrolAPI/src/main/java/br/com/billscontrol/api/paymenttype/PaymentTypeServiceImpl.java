package br.com.billscontrol.api.paymenttype;

import br.com.billscontrol.api.financialcontrol.FinancialControlService;
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
	private FinancialControlService financialService;
	
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

		if (id == null) {
			return Optional.empty();
		}

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

	@Override
	public void delete(Long id) {

		PaymentType entity = findById(id)
				.orElseThrow(() -> ResourceNotFoundException.builder()
					.resourceId(id)
					.clazz(PaymentType.class).build());

		repository.delete(entity);
	}

	@Override
	public PaymentType toEntity(PaymentTypeVO vo) {

		if (vo == null) {
			return null;
		}

		return findById(vo.getId())
				.orElse(PaymentType.builder()
						.name(vo.getName())
						.description(vo.getDescription())
						.build());
	}

	@Override
	public PaymentTypeVO toVO(PaymentType entity) {

		if (entity == null) {
			return null;
		}

		return PaymentTypeVO.builder()
				.id(entity.getId())
				.description(entity.getDescription())
				.name(entity.getName())
				.financialControlVO(financialService.toVO(entity.getFinancialControl()))
				.build();
	}
}
