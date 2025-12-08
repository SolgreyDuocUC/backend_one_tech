package backend_one_tech.validations.RUN;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RunValidator implements ConstraintValidator<ValidRun, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank())
            return false;

        // Formato xx.xxx.xxx-x
        if (!value.matches("^\\d{1,2}\\.\\d{3}\\.\\d{3}-[\\dkK]$"))
            return false;

        String clean = value.replace(".", "").replace("-", "");
        String cuerpo = clean.substring(0, clean.length() - 1);
        char dv = clean.charAt(clean.length() - 1);

        // Calcular DV
        int m = 0, s = 1;
        for (int i = cuerpo.length() - 1; i >= 0; i--) {
            s = (s + Character.getNumericValue(cuerpo.charAt(i)) * (9 - m++ % 6)) % 11;
        }

        char dvEsperado = (char) (s != 0 ? s + 47 : 75); // 75 = 'K'

        return dv == dvEsperado || dv == (char)(dvEsperado + 32); // acepta k minúscula
    }
}

