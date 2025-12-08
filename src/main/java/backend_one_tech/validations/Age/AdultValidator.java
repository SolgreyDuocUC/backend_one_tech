package backend_one_tech.validations.Age;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class AdultValidator implements ConstraintValidator<Adult, LocalDate> {

    @Override
    public boolean isValid(LocalDate fechaNacimiento, ConstraintValidatorContext context) {

        if (fechaNacimiento == null) {
            return false;
        }

        // Calcula edad exacta
        int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();

        return edad >= 18;
    }
}
