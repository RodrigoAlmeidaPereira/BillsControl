package br.com.billscontrol.core;

import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public abstract class AbstractTestSupport {

    public abstract void init();


    @BeforeClass
    public static void setUp() {
        FixtureFactoryLoader.loadTemplates("br.com.billscontrol.template");
    }

    @Before
    public void setUpTest() {
        MockitoAnnotations.initMocks(this);
        this.init();
    }

}
