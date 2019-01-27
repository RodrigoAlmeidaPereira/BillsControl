package br.com.billscontrol.api.financialcontrol;

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

public class FinancialControlServiceImplTest extends AbstractTestSupport {

    @Mock
    private FinancialControlRepository repository;

    private FinancialControlService service;

    @Override
    public void init() {
        service = new FinancialControlServiceImpl(repository);
    }

    @Test
    public void should_create_new() {

        FinancialControl entity = from(FinancialControl.class).gimme(VALID.name());

        when(repository.save(entity)).thenReturn(entity);

        assertEquals(entity, service.save(entity));

        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository, times(1)).save(entity);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_update() {

        FinancialControl entity = from(FinancialControl.class).gimme(VALID.name());

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

        FinancialControl entity = from(FinancialControl.class).gimme(VALID.name());

        when(repository.existsById(entity.getId())).thenReturn(Boolean.FALSE);

        service.update(entity);

        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository, times(1)).existsById(entity.getId());
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_delete() {

        FinancialControl entity = from(FinancialControl.class).gimme(VALID.name());

        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));

        service.delete(entity.getId());

        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository, times(1)).findById(entity.getId());
        inOrder.verify(repository, times(1)).delete(entity);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_find_by_id() {

        FinancialControl entity = from(FinancialControl.class).gimme(VALID.name());

        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));

        assertEquals(entity, service.findById(entity.getId()).orElse(null));

        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository, times(1)).findById(entity.getId());
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_return_null_when_tries_to_find_one_and_id_does_not_exists() {

        FinancialControl entity = from(FinancialControl.class).gimme(VALID.name());

        when(repository.findById(entity.getId())).thenReturn(Optional.empty());

        assertEquals(null, service.findById(entity.getId()).orElse(null));

        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository, times(1)).findById(entity.getId());
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_find_all_pageable() {

        PageImpl<FinancialControl> entities = new PageImpl<>(from(FinancialControl.class).gimme(20, VALID.name()));

        PageRequest request = PageRequest.of(0, 25);

        when(repository.findAll(request)).thenReturn(entities);

        assertEquals(entities, service.findAll(request));

        InOrder inOrder = inOrder(repository);
        inOrder.verify(repository, times(1)).findAll(request);
        inOrder.verifyNoMoreInteractions();
    }

}
