package backend_one_tech.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(name = "ProductUpdateDTO", description = "Datos para actualizar un producto (campos opcionales)")
public record ProductUpdateDTO(

        @Schema(description = "Nombre del producto", example = "Teclado Mecánico RGB Pro")
        @Size(max = 120)
        String name,

        @Schema(description = "Slug único para URL", example = "teclado-mecanico-rgb-pro")
        @Size(max = 140)
        @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$")
        String slug,

        @Schema(description = "Descripción del producto", example = "Versión pro con switches mejorados")
        @Size(max = 2000)
        String description,

        @Schema(description = "Precio del producto", example = "59990")
        @DecimalMin(value = "0.0", inclusive = false)
        BigDecimal price,

        @Schema(description = "Stock disponible", example = "20")
        @Min(0)
        Integer stock,

        @Schema(description = "Stock crítico para alertas", example = "4")
        @Min(0)
        Integer stockCritico,

        @Schema(description = "Categoría del producto", example = "Periféricos")
        String category,

        @Schema(description = "URL de la imagen del producto", example = "https://...")
        String imagen,

        @Schema(description = "Producto destacado", example = "false")
        Boolean featured
) {}
