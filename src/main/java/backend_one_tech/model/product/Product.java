package backend_one_tech.model.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "productos")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long id;  // coincide con number en TS

    @NotBlank(message = "Nombre es obligatorio")
    @Size(max = 120, message = "Nombre máximo 120 caracteres")
    @Column(name = "name_product", length = 120, nullable = false)
    private String name; // coincide con 'name' en TS

    @NotBlank(message = "Slug es obligatorio")
    @Size(max = 140, message = "Slug máximo 140 caracteres")
    @Column(name = "slug_product", length = 140, nullable = false, unique = true)
    private String slug; // coincide con 'slug' en TS

    @NotBlank(message = "Descripción es obligatoria")
    @Size(max = 2000, message = "Descripción máximo 2000 caracteres")
    @Column(name = "desc_product", length = 2000)
    private String description; // coincide con 'description' en TS

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @Digits(integer = 10, fraction = 2, message = "Precio inválido (máx 10 enteros y 2 decimales)")
    @Column(name = "price_product", precision = 12, scale = 2, nullable = false)
    private BigDecimal price; // coincide con 'price' en TS

    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "Stock no puede ser negativo")
    @Column(name = "stock_product", nullable = false)
    private Integer stock; // coincide con 'stock' en TS

    @NotNull(message = "Stock crítico es obligatorio")
    @Min(value = 0, message = "Stock crítico no puede ser negativo")
    @Column(name = "stock_critico_product", nullable = false)
    private Integer stockCritico; // coincide con 'stockCritico' en TS

    @NotBlank(message = "Categoría es obligatoria")
    @Size(max = 120, message = "Categoría máximo 120 caracteres")
    @Column(name = "category_product", length = 120, nullable = false)
    private String category; // coincide con 'category' en TS

    @NotBlank(message = "Imagen es obligatoria")
    @Column(name = "imagen_product", nullable = false)
    private String imagen; // coincide con 'imagen' en TS

    @Column(name = "featured_product")
    private Boolean featured; // coincide con 'featured?' en TS

    @Column(name = "created_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdAt; // coincide con 'createdAt?' en TS

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime updatedAt; // coincide con 'updatedAt?' en TS

    @PrePersist
    public void prePersist() {
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }
}
