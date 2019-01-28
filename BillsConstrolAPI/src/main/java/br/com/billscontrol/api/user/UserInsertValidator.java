package br.com.billscontrol.api.user;

import br.com.billscontrol.exception.FieldMessage;
import lombok.AllArgsConstructor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@AllArgsConstructor
public class UserInsertValidator implements ConstraintValidator<UserInsert, UserVO> {


    private HttpServletRequest request;


    private UserService userService;

    @Override
    public void initialize(UserInsert constraintAnnotation) {

    }

    @Override
    public boolean isValid(UserVO vo, ConstraintValidatorContext context) {

        final Map<String, String> pathVariables = (Map<String, String>) request
                .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        final Long id = pathVariables.containsKey("id") ? Long.valueOf(pathVariables.get("id")) : 0L;

        Collection<FieldMessage> fields = new ArrayList<FieldMessage>();

        if (vo.getUserType() == null) {
            fields.add(FieldMessage.builder()
                    .fieldName("userType")
                    .message("User type must not be empty")
                    .build());
        }


        boolean inUse = userService.findByEmail(vo.getEmail())
                .map(user -> !user.getId().equals(id))
                .orElse(false);

        if (inUse) {
            fields.add(FieldMessage.builder()
                    .fieldName("email")
                    .message("Email already taken")
                    .build());
        }

        fields.stream().forEach(fieldMessage -> {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(fieldMessage.getMessage())
                    .addPropertyNode(fieldMessage.getFieldName())
                    .addConstraintViolation();
        });

        return fields.isEmpty();
    }

}
