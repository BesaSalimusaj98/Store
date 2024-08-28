package e_shop.e_shop.controller;

import e_shop.e_shop.dto.InventoryDto;
import e_shop.e_shop.dto.ProductDto;
import e_shop.e_shop.entity.Size;
import e_shop.e_shop.mapper.SizeMapper;
import e_shop.e_shop.response.ProductResponse;
import e_shop.e_shop.service.InventoryService;
import e_shop.e_shop.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.LinkedHashSet;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;
    private InventoryService inventoryService;

    public ProductController(ProductService productService, InventoryService inventoryService) {
        this.productService = productService;
        this.inventoryService = inventoryService;
    }


    //Build Add Product REST API
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        ProductDto savedProduct = productService.createProduct(productDto);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    //Build Get All Product REST API
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        // Fetch all products
        List<ProductDto> products = productService.getAllProducts();

        List<ProductResponse> responses = new ArrayList<>();

        // Iterate through each product
        for (ProductDto productDto : products) {
            // Find inventory items by product ID
            List<InventoryDto> inventoryItems = inventoryService.findByProductId(productDto.getId());

            // Convert InventoryDto sizes to Size objects and collect unique sizes with state not equal to 2
            Set<Size> uniqueSizes = inventoryItems.stream()
                    .map(inventoryDto -> SizeMapper.toEntity(inventoryDto.getSize())) // Convert SizeDto to Size
                    .collect(Collectors.toCollection(LinkedHashSet::new)); // Use LinkedHashSet to maintain insertion order

            // Create ProductResponse
            ProductResponse response = new ProductResponse();
            response.setProduct(productDto);
            response.setSizes(uniqueSizes);

            // Add to response list
            responses.add(response);
        }

        // Return the list of ProductResponse
        return ResponseEntity.ok(responses);
    }


    //Build Get Product by id
    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId){
        ProductDto productDto = productService.getProductById(productId);
        return ResponseEntity.ok(productDto);
    }

    //Build Update Product REST API
    @PutMapping("{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Long productId,
                                                      @RequestBody ProductDto updatedProduct){
        ProductDto productDto = productService.updateProduct(productId, updatedProduct);
        return ResponseEntity.ok(productDto);
    }

    //Build Delete Product By id
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable("id") Long productId){
        productService.deleteProductWithInventoryAndTransactions(productId);
        return ResponseEntity.ok("Product and related inventory and transactions deleted successfully");
    }



}
