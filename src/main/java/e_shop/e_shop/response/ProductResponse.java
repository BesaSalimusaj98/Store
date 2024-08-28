package e_shop.e_shop.response;

import e_shop.e_shop.dto.ProductDto;
import e_shop.e_shop.entity.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ProductResponse {
    private ProductDto product;
    private Set<Size> sizes;

}
