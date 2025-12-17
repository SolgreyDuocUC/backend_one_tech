package backend_one_tech.repository;

import backend_one_tech.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsBySlug(String slug);

    // Buscar por slug
    Optional<Product> findBySlug(String slug);

    // Buscar productos destacados
    List<Product> findByFeaturedTrue();

    // Buscar por categoría
    List<Product> findByCategory(String category);
}
