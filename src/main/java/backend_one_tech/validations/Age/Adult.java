package backend_one_tech.validations.Age;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AdultValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Adult {

    String message() default "El usuario debe ser mayor de 18 años";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
