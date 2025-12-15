package backend_one_tech.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(name = "ProductCreateDTO", description = "Datos para crear un producto")
public record ProductCreateDTO(

        @Schema(description = "Nombre del producto", example = "Teclado Mecánico RGB")
        @NotBlank(message = "Nombre es obligatorio")
        @Size(max = 120, message = "Nombre máximo 120 caracteres")
        String name,

        @Schema(description = "Slug único para URL", example = "teclado-mecanico-rgb")
        @NotBlank(message = "Slug es obligatorio")
        @Size(max = 140, message = "Slug máximo 140 caracteres")
        @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$", message = "Slug inválido (minúsculas, números y guiones)")
        String slug,

        @Schema(description = "Descripción del producto", example = "Teclado mecánico con iluminación RGB")
        @NotBlank(message = "Descripción es obligatoria")
        @Size(max = 2000, message = "Descripción máximo 2000 caracteres")
        String description,

        @Schema(description = "Precio del producto", example = "49990")
        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
        BigDecimal price,

        @Schema(description = "Stock disponible", example = "15")
        @NotNull(message = "El stock es obligatorio")
        @Min(value = 0, message = "Stock no puede ser negativo")
        Integer stock,

        @Schema(description = "Stock crítico para alertas", example = "3")
        @NotNull(message = "Stock crítico es obligatorio")
        @Min(value = 0, message = "Stock crítico no puede ser negativo")
        Integer stockCritico,

        @Schema(description = "Producto destacado", example = "true")
        Boolean featured
) {}
