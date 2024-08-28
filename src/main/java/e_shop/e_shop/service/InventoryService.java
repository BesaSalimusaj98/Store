package e_shop.e_shop.service;

import e_shop.e_shop.dto.InventoryDto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface InventoryService {

    List<InventoryDto> findByProductId(Long productId);

    InventoryDto findByBarcode(Long barcode);
    List<InventoryDto> findAllByProductIdAndSizeIdAndState(Long productId, int sizeId, int state);

    InventoryDto saveInventoryItem(Long productId, InventoryDto inventoryDto);
    InventoryDto updateInventoryItem(Long productId, InventoryDto inventoryDto);

    void updateInventoryAndCreateTransaction(Long productId, int sizeId, int quantity, double totalPrice, LocalDate date, Long userId);

    Map<String, Object> checkAvailability(Long productId, String sizeId, int requestedQuantity);

    void deleteInventoryByBarcode(Long barcode);

}
