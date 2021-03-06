package br.com.billscontrol.api.bank;

import br.com.billscontrol.api.financialcontrol.FinancialControl;
import br.com.billscontrol.api.financialcontrol.FinancialControlRepository;
import br.com.billscontrol.api.financialcontrol.FinancialControlService;
import br.com.billscontrol.api.financialcontrol.FinancialControlServiceImpl;
import br.com.billscontrol.core.AbstractTestSupport;
import br.com.billscontrol.exception.ResourceNotFoundException;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static br.com.billscontrol.template.FixtureTemplateEnum.VALID;
import static br.com.six2six.fixturefactory.Fixture.from;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class BankServiceImplTest extends AbstractTestSupport {

    @Mock
    private BankRepository repository;

    @Mock
    private FinancialControlRepository financialControlRepository;

    private BankService service;
    private FinancialControlService financialControlService;

    @Override
    public void init() {
        financialControlService = new FinancialControlServiceImpl(financialControlRepository);
        service = new BankServiceImpl(repository, financialControlService);
    }

    @Test
    public void should_create_new() {

        Bank entity = from(Bank.class).gimme(VALID.name());

        when(repository.save(entity)).thenReturn(entity);

        assertEquals(entity, service.save(entity));

        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository, times(1)).save(entity);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_update() {

        Bank entity = from(Bank.class).gimme(VALID.name());

        when(repository.existsById(entity.getId())).thenReturn(Boolean.TRUE);

        when(repository.save(entity)).thenReturn(entity);

        assertEquals(entity, service.update(entity));

        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository, times(1)).existsById(entity.getId());
        inOrder.verify(repository, times(1)).save(entity);
        inOrder.verifyNoMoreInteractions();
    }


    @Test(expected = ResourceNotFoundException.class)
    public void should_thrown_a_resource_not_found_exception_when_try_to_update_and_id_not_exists() {

        Bank entity = from(Bank.class).gimme(VALID.name());

        when(repository.existsById(entity.getId())).thenReturn(Boolean.FALSE);

        service.update(entity);

        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository, times(1)).existsById(entity.getId());
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_delete() {

        Bank entity = from(Bank.class).gimme(VALID.name());

        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));

        service.delete(entity.getId());

        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository, times(1)).findById(entity.getId());
        inOrder.verify(repository, times(1)).delete(entity);
        inOrder.verifyNoMoreInteractions();

    }

    @Test
    public void should_find_by_id() {

        Bank entity = from(Bank.class).gimme(VALID.name());

        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));

        assertEquals(entity, service.findById(entity.getId()).orElse(null));

        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository, times(1)).findById(entity.getId());
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_return_null_when_tries_to_find_one_and_id_does_not_exists() {

        Bank entity = from(Bank.class).gimme(VALID.name());

        when(repository.findById(entity.getId())).thenReturn(Optional.empty());

        assertEquals(null, service.findById(entity.getId()).orElse(null));

        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository, times(1)).findById(entity.getId());
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_find_all_pageable() {

        PageImpl<Bank> entities = new PageImpl<>(from(Bank.class).gimme(20, VALID.name()));

        PageRequest request = PageRequest.of(0, 25);

        when(repository.findAll(request)).thenReturn(entities);

        assertEquals(entities, service.findAll(request));

        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository, times(1)).findAll(request);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_find_all_pageable_by_financial_control() {

        FinancialControl financialControl = from(FinancialControl.class).gimme(VALID.name());

        PageImpl<Bank> entities = new PageImpl<>(from(Bank.class).gimme(20, VALID.name()));

        PageRequest request = PageRequest.of(0, 25);

        when(repository.findAllByFinancialControlId(request, financialControl.getId())).thenReturn(entities);

        assertEquals(entities, service.findAll(request, financialControl.getId()));

        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository, times(1)).findAllByFinancialControlId(request, financialControl.getId());
        inOrder.verifyNoMoreInteractions();
    }

}
