package backend_one_tech.services.Product;

import backend_one_tech.dto.product.ProductCreateDTO;
import backend_one_tech.dto.product.ProductResponseDTO;
import backend_one_tech.dto.product.ProductUpdateDTO;
import backend_one_tech.model.Product.Product;
import backend_one_tech.repository.ProductRepository;
import backend_one_tech.services.Product.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductResponseDTO create(ProductCreateDTO dto) {

        if (productRepository.existsBySlug(dto.slug())) {
            throw new ResponseStatusException(CONFLICT, "Ya existe un producto con ese slug");
        }

        if (dto.stockCritico() > dto.stock()) {
            throw new ResponseStatusException(BAD_REQUEST, "Stock crítico no puede ser mayor al stock");
        }

        Product p = new Product();
        p.setName(dto.name());
        p.setSlug(dto.slug());
        p.setDescription(dto.description());
        p.setPrice(dto.price());
        p.setStock(dto.stock());
        p.setStockCritico(dto.stockCritico());
        p.setFeatured(dto.featured() != null ? dto.featured() : false);

        return toResponse(productRepository.save(p));
    }

    @Override
    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    public ProductResponseDTO findById(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Producto no encontrado"));
        return toResponse(p);
    }

    @Override
    public ProductResponseDTO update(Long id, ProductUpdateDTO dto) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Producto no encontrado"));

        // slug único
        if (dto.slug() != null && !dto.slug().equals(p.getSlug())) {
            if (productRepository.existsBySlug(dto.slug())) {
                throw new ResponseStatusException(CONFLICT, "Ya existe un producto con ese slug");
            }
            p.setSlug(dto.slug());
        }

        if (dto.name() != null) p.setName(dto.name());
        if (dto.description() != null) p.setDescription(dto.description());
        if (dto.price() != null) p.setPrice(dto.price());
        if (dto.stock() != null) p.setStock(dto.stock());
        if (dto.stockCritico() != null) p.setStockCritico(dto.stockCritico());
        if (dto.featured() != null) p.setFeatured(dto.featured());

        // regla stockCritico <= stock (considerando valores finales)
        if (p.getStockCritico() != null && p.getStock() != null && p.getStockCritico() > p.getStock()) {
            throw new ResponseStatusException(BAD_REQUEST, "Stock crítico no puede ser mayor al stock");
        }

        return toResponse(productRepository.save(p));
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(NOT_FOUND, "Producto no encontrado");
        }
        productRepository.deleteById(id);
    }

    private ProductResponseDTO toResponse(Product p) {
        return new ProductResponseDTO(
                p.getId(),
                p.getName(),
                p.getSlug(),
                p.getDescription(),
                p.getPrice(),
                p.getStock(),
                p.getStockCritico(),
                p.getFeatured(),
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }
}
