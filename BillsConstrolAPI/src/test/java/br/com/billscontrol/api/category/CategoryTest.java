package br.com.billscontrol.api.category;

import br.com.billscontrol.api.financialcontrol.FinancialControlRepository;
import br.com.billscontrol.api.financialcontrol.FinancialControlService;
import br.com.billscontrol.api.financialcontrol.FinancialControlServiceImpl;
import br.com.billscontrol.core.AbstractTestSupport;
import br.com.billscontrol.template.FixtureTemplateEnum;
import br.com.six2six.fixturefactory.Fixture;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;

public class CategoryTest extends AbstractTestSupport {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private FinancialControlRepository financialControlRepository;

    private CategoryService categoryService;
    private FinancialControlService financialControlService;

    @Override
    public void init() {
        financialControlService = new FinancialControlServiceImpl(financialControlRepository);
        categoryService = new CategoryServiceImpl(categoryRepository, financialControlService);
    }

    @Test
    public void should_create_new() {

        Category entity = Fixture.from(Category.class).gimme(FixtureTemplateEnum.VALID.name());

        Mockito.when(categoryRepository.save(entity)).thenReturn(entity);

        Assert.assertEquals(entity, categoryService.save(entity));

        InOrder inOrder = Mockito.inOrder(categoryRepository);
        inOrder.verify(categoryRepository, Mockito.times(1)).save(entity);
        inOrder.verifyNoMoreInteractions();
    }


}
