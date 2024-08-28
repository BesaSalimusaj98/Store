package e_shop.e_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDto {

    private Long barcode; // Updated to Long to match the primary key type
    private Integer state;
    private ProductDto product;
    private SizeDto size; // Assuming you want to include size details
    private LocalDate sold_date;
    private Double sold_price;
}
