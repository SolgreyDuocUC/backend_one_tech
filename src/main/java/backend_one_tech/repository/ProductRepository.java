package backend_one_tech.repository;

import backend_one_tech.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsBySlug(String slug);
}
