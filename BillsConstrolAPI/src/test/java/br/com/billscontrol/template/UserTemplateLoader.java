package br.com.billscontrol.template;

import br.com.billscontrol.api.user.User;
import br.com.billscontrol.api.user.UserType;
import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import org.springframework.core.annotation.Order;

import java.time.Instant;

@Order(1)
public class UserTemplateLoader implements TemplateLoader {

    @Override
    public void load() {

        Fixture.of(User.class).addTemplate(FixtureTemplateEnum.VALID.name(), new Rule() {{
            add("id", random(Long.class, 1L, 99L));
            add("name", random("Jack", "John"));
            add("email", random("jack@billscontrol.com", "john@billscontrol.com"));
            add("userType", random(UserType.ADMIN, UserType.TESTER));
            add("createUser", random("jack@billscontrol.com", "john@billscontrol.com"));
            add("createInstant", random(Instant.now(), Instant.now()));
        }});
    }
}
