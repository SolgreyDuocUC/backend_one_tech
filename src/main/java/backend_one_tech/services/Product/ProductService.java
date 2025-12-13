package backend_one_tech.services.Product;

import backend_one_tech.dto.product.ProductCreateDTO;
import backend_one_tech.dto.product.ProductResponseDTO;
import backend_one_tech.dto.product.ProductUpdateDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO create(ProductCreateDTO dto);
    List<ProductResponseDTO> findAll();
    ProductResponseDTO findById(Long id);
    ProductResponseDTO update(Long id, ProductUpdateDTO dto);
    void delete(Long id);
}
