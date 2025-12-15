package backend_one_tech.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(name = "ProductUpdateDTO", description = "Datos para actualizar un producto (campos opcionales)")
public record ProductUpdateDTO(

        @Schema(description = "Nombre del producto", example = "Teclado Mecánico RGB Pro")
        @Size(max = 120, message = "Nombre máximo 120 caracteres")
        String name,

        @Schema(description = "Slug único para URL", example = "teclado-mecanico-rgb-pro")
        @Size(max = 140, message = "Slug máximo 140 caracteres")
        @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$", message = "Slug inválido (minúsculas, números y guiones)")
        String slug,

        @Schema(description = "Descripción del producto", example = "Versión pro con switches mejorados")
        @Size(max = 2000, message = "Descripción máximo 2000 caracteres")
        String description,

        @Schema(description = "Precio del producto", example = "59990")
        @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
        BigDecimal price,

        @Schema(description = "Stock disponible", example = "20")
        @Min(value = 0, message = "Stock no puede ser negativo")
        Integer stock,

        @Schema(description = "Stock crítico para alertas", example = "4")
        @Min(value = 0, message = "Stock crítico no puede ser negativo")
        Integer stockCritico,

        @Schema(description = "Producto destacado", example = "false")
        Boolean featured
) {}
