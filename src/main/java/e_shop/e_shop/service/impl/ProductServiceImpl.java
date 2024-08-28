package e_shop.e_shop.service.impl;

import e_shop.e_shop.dto.InventoryDto;
import e_shop.e_shop.dto.ProductDto;
import e_shop.e_shop.entity.Product;
import e_shop.e_shop.exception.ResourceNotFoundException;
import e_shop.e_shop.mapper.ProductMapper;
import e_shop.e_shop.repository.ProductRepository;
import e_shop.e_shop.service.InventoryService;
import e_shop.e_shop.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private InventoryService inventoryService;

    public ProductServiceImpl(ProductRepository productRepository, InventoryService inventoryService) {
        this.productRepository = productRepository;
        this.inventoryService = inventoryService;
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = ProductMapper.mapToProduct(productDto);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.mapToProductDto(savedProduct);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper::mapToProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product doesn't exist with following id:" + productId));
        return ProductMapper.mapToProductDto(product);
    }

    @Override
    public ProductDto updateProduct(Long productId, ProductDto updatedProduct) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Product doesn't exist with following id:" + productId));
        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setCost(updatedProduct.getCost());
        product.setPrice(updatedProduct.getPrice());
        product.setCommission(updatedProduct.getCommission());

        Product updatedProductObj = productRepository.save(product);
        return ProductMapper.mapToProductDto(updatedProductObj);
    }


    @Transactional
    @Override
    public void deleteProductWithInventoryAndTransactions(Long productId) {
        // 1. Find all inventory items with the given product ID
        List<InventoryDto> inventoryItems = inventoryService.findByProductId(productId);
        // 2. Delete all inventory items
        for (InventoryDto inventoryItem : inventoryItems) {
            inventoryService.deleteInventoryByBarcode(inventoryItem.getBarcode());
        }

        // 4. Delete the product
        productRepository.deleteById(productId);
    }
}
