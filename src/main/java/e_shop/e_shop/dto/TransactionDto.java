package e_shop.e_shop.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TransactionDto {

    private Long transactionId;
    private Long barcode;
    private Double soldPrice;
    private UserDto user;
    private LocalDate transactionDate;
}

