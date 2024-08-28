package e_shop.e_shop.mapper;

import e_shop.e_shop.dto.ProductDto;
import e_shop.e_shop.entity.Product;

public class ProductMapper {
    public static ProductDto mapToProductDto(Product product){
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getCost(),
                product.getCommission()
        );
    }
    public static Product mapToProduct(ProductDto produtcDto){
        return new Product(
                produtcDto.getId(),
                produtcDto.getName(),
                produtcDto.getDescription(),
                produtcDto.getPrice(),
                produtcDto.getCost(),
                produtcDto.getCommission()
        );
    }
}
