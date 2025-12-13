package backend_one_tech.dto.product;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductCreateDTO(
        @NotBlank(message = "Nombre es obligatorio")
        @Size(max = 120, message = "Nombre máximo 120 caracteres")
        String name,

        @NotBlank(message = "Slug es obligatorio")
        @Size(max = 140, message = "Slug máximo 140 caracteres")
        @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$", message = "Slug inválido (minúsculas, números y guiones)")
        String slug,

        @NotBlank(message = "Descripción es obligatoria")
        @Size(max = 2000, message = "Descripción máximo 2000 caracteres")
        String description,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
        BigDecimal price,

        @NotNull(message = "El stock es obligatorio")
        @Min(value = 0, message = "Stock no puede ser negativo")
        Integer stock,

        @NotNull(message = "Stock crítico es obligatorio")
        @Min(value = 0, message = "Stock crítico no puede ser negativo")
        Integer stockCritico,

        Boolean featured
) {}
