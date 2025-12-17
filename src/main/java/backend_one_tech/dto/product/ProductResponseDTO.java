package backend_one_tech.dto.product;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ProductResponseDTO(
        Long id,
        String name,
        String slug,
        String description,
        BigDecimal price,
        Integer stock,
        Integer stockCritico,
        String category,
        String imagen,
        Boolean featured,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {}
