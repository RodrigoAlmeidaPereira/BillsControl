package br.com.billscontrol.template;

import br.com.billscontrol.api.bank.Bank;
import br.com.billscontrol.api.financialcontrol.FinancialControl;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;

import java.time.Instant;

public class BankTemplateLoader implements TemplateLoader {

    @Override
    public void load() {

        Fixture.of(Bank.class).addTemplate(FixtureTemplateEnum.VALID.name(), new Rule() {{
            add("id", random(Long.class, range(1, 99)));
            add("name", random("Car", "Cloths"));
            add("description", random("Car description", "Cloths description"));
            add("createUser", random("system", "user"));
            add("createInstant", random(Instant.now(), Instant.now()));
            add("financialControl", one(FinancialControl.class, FixtureTemplateEnum.VALID.name()));
        }});
    }
}
