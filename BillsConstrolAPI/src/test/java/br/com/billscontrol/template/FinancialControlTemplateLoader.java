package br.com.billscontrol.template;

import br.com.billscontrol.api.financialcontrol.FinancialControl;
import br.com.billscontrol.api.user.User;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import org.springframework.core.annotation.Order;

import java.time.Instant;

@Order(2)
public class FinancialControlTemplateLoader implements TemplateLoader {

    @Override
    public void load() {

        Fixture.of(FinancialControl.class).addTemplate(FixtureTemplateEnum.VALID.name(), new Rule() {{
            add("id", random(Long.class, range(1, 99)));
            add("name", random("Car", "Cloths"));
            add("description", random("Car description", "Cloths description"));
            add("createUser", random("system", "user"));
            add("createInstant", random(Instant.now(), Instant.now()));
            add("owner", one(User.class, FixtureTemplateEnum.VALID.name()));
        }});
    }
}
