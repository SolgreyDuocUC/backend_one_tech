package backend_one_tech.dto.product;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private BigDecimal price;
    private Integer stock;
    private Integer stockCritico;
    private String category;
    private String imagen;
    private Boolean featured;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}