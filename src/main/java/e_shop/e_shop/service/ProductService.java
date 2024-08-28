package e_shop.e_shop.service;

import e_shop.e_shop.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProductService {


    ProductDto createProduct(ProductDto productDto);
    List<ProductDto> getAllProducts();
    ProductDto getProductById(Long productId);

    ProductDto updateProduct(Long productId , ProductDto updatedProduct);

    void deleteProductWithInventoryAndTransactions(Long productId);

}
