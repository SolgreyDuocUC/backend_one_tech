package backend_one_tech.dto.product;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(name = "ProductCreateDTO", description = "Datos para crear un producto")
public record ProductCreateDTO(

        @Schema(description = "Nombre del producto", example = "Teclado Mecánico RGB")
        @NotBlank
        @Size(max = 120)
        String name,

        @Schema(description = "Slug único para URL", example = "teclado-mecanico-rgb")
        @NotBlank
        @Size(max = 140)
        @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$")
        String slug,

        @Schema(description = "Descripción del producto", example = "Teclado mecánico con iluminación RGB")
        @NotBlank
        @Size(max = 2000)
        String description,

        @Schema(description = "Precio del producto", example = "49990")
        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        BigDecimal price,

        @Schema(description = "Stock disponible", example = "15")
        @NotNull
        @Min(0)
        Integer stock,

        @Schema(description = "Stock crítico para alertas", example = "3")
        @NotNull
        @Min(0)
        Integer stockCritico,

        @Schema(description = "Categoría del producto", example = "Periféricos")
        @NotBlank
        String category,

        @Schema(description = "URL de la imagen del producto", example = "https://...")
        @NotBlank
        String imagen,

        @Schema(description = "Producto destacado", example = "true")
        Boolean featured
) {}
