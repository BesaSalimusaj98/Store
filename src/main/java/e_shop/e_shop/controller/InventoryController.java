package e_shop.e_shop.controller;

import e_shop.e_shop.dto.InventoryDto;
import e_shop.e_shop.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{productId}")
    public ResponseEntity<List<InventoryDto>> getInventoryByProductId(@PathVariable("productId") Long productId) {
        List<InventoryDto> inventoryDtos = inventoryService.findByProductId(productId);
        return ResponseEntity.ok(inventoryDtos);
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/{productId}")
    public ResponseEntity<InventoryDto> createInventory(
            @PathVariable("productId") Long productId,
            @RequestBody InventoryDto inventoryDto) {



        System.out.print("Produkt id:" + productId);
        // Call service method to save inventory item
        InventoryDto savedInventory = inventoryService.saveInventoryItem(productId, inventoryDto);

        // Return response with CREATED status and the saved inventory item
        return new ResponseEntity<>(savedInventory, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/{productId}")
    public ResponseEntity<InventoryDto> updateInventory(
            @PathVariable("productId") Long productId,
            @RequestBody InventoryDto inventoryDto) {
        // Call service method to save inventory item
        InventoryDto updateInventory = inventoryService.updateInventoryItem(productId, inventoryDto);

        // Return response with CREATED status and the saved inventory item
        return new ResponseEntity<>(updateInventory, HttpStatus.CREATED);
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/check-availability")
    public ResponseEntity<Map<String, Object>> checkAvailability(
            @RequestParam("productId") Long productId,
            @RequestParam("sizeId") String sizeId,
            @RequestParam("quantity") int quantity) {

        Map<String, Object> response = inventoryService.checkAvailability(productId, sizeId, quantity);
        return ResponseEntity.ok(response);
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/update")
    public ResponseEntity<Void> updateInventoryAndSaveTransaction(
            @RequestParam("productId") Long productId,
            @RequestParam("sizeId") int sizeId,
            @RequestParam("quantity") int quantity,
            @RequestParam("totalPrice") double totalPrice,
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam("userId") Long userId) {

        inventoryService.updateInventoryAndCreateTransaction(productId, sizeId, quantity, totalPrice, date, userId);
        return ResponseEntity.ok().build();
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/delete-by-barcode")
    public ResponseEntity<String> deleteInventoryByBarcode(@RequestParam("barcode") Long barcode) {
        inventoryService.deleteInventoryByBarcode(barcode);
        return ResponseEntity.ok("Inventory deleted successfully");
    }

}
