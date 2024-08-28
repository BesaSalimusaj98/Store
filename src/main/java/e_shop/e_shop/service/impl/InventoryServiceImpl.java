package e_shop.e_shop.service.impl;

import e_shop.e_shop.dto.InventoryDto;
import e_shop.e_shop.dto.SizeDto;
import e_shop.e_shop.dto.UserDto;
import e_shop.e_shop.entity.Inventory;
import e_shop.e_shop.entity.Product;
import e_shop.e_shop.entity.Size;
import e_shop.e_shop.entity.Transaction;
import e_shop.e_shop.exception.ResourceNotFoundException;
import e_shop.e_shop.mapper.InventoryMapper;
import e_shop.e_shop.mapper.UserMapper;
import e_shop.e_shop.repository.InventoryRepository;
import e_shop.e_shop.repository.ProductRepository;
import e_shop.e_shop.repository.SizeRepository;
import e_shop.e_shop.repository.TransactionRepository;
import e_shop.e_shop.service.InventoryService;
import e_shop.e_shop.service.SizeService;
import e_shop.e_shop.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class InventoryServiceImpl implements InventoryService {


    @Autowired
    private InventoryRepository inventoryRepository; // Ensure you have this repository

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private InventoryMapper inventoryMapper;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionRepository;


    @Override
    public List<InventoryDto> findByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId).stream()
                .map(InventoryMapper::toDto)
                .toList();
    }

    @Override
    public InventoryDto findByBarcode(Long barcode) {
        Inventory inventory = inventoryRepository.findById(barcode)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Inventory doesn't exist with following id:" + barcode));
        return InventoryMapper.toDto(inventory);
    }

    @Override
    public List<InventoryDto> findAllByProductIdAndSizeIdAndState(Long productId, int sizeId, int state) {
        return inventoryRepository.findAllByProductIdAndSizeIdAndState(productId, sizeId, state).stream()
                .map(InventoryMapper::toDto)
                .toList();
    }

    @Override
    public InventoryDto saveInventoryItem(Long productId, InventoryDto inventoryDto) {
        // Validate if Product and Size exist
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        Size size = sizeRepository.findById(inventoryDto.getSize().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Size not found with id: " + inventoryDto.getSize().getId()));

        Inventory inventory = new Inventory();
        // Create new Inventory entity from DTO
        inventory.setProduct(product);
        inventory.setSize(size);
        inventory.setState(1);
        inventory.setSold_date(null);
        inventory.setSold_price(null);
        inventory.setBarcode(generateRandomBarcode());
        // Generate a unique barcode

        // Save and return the saved Inventory DTO
        Inventory savedInventory = inventoryRepository.save(inventory);
        return inventoryMapper.toDto(savedInventory);
    }

    @Override
    public InventoryDto updateInventoryItem(Long productId, InventoryDto inventoryDto) {
        // Validate if Product and Size exist
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        Size size = sizeRepository.findById(inventoryDto.getSize().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Size not found with id: " + inventoryDto.getSize().getId()));
        UserDto userDto = userService.getUserById((long)2);


        Inventory inventory = inventoryRepository.findByBarcode(inventoryDto.getBarcode());

        inventory.setSize(size);
        inventory.setProduct(product);
        inventory.setSold_date(inventoryDto.getSold_date());
        inventory.setSold_price(inventoryDto.getSold_price());
        inventory.setState(inventoryDto.getState());

        // Check the state and handle transactions accordingly
        if (inventoryDto.getState() == 1) { // State 1: Available
            // Check for an existing transaction by barcode
            Optional<Transaction> existingTransaction = transactionRepository.findByBarcode(inventory.getBarcode());
            if (existingTransaction.isPresent()) {
                // Remove the transaction if it exists
                transactionRepository.delete(existingTransaction.get());
            }
        } else if (inventoryDto.getState() == 2) { // State 2: Sold
            // Check for an existing transaction by barcode
            Optional<Transaction> existingTransaction = transactionRepository.findByBarcode(inventory.getBarcode());
            if (existingTransaction.isPresent()) {
                // Update the existing transaction with the new sold price and date
                Transaction transaction = existingTransaction.get();
                transaction.setSoldPrice(inventoryDto.getSold_price());
                transaction.setTransactionDate(inventoryDto.getSold_date());
                transactionRepository.save(transaction);
            } else {
                // Create a new transaction if none exists
                Transaction newTransaction = new Transaction();
                newTransaction.setBarcode(inventory.getBarcode());
                newTransaction.setSoldPrice(inventoryDto.getSold_price());
                newTransaction.setTransactionDate(inventoryDto.getSold_date());
                newTransaction.setUser(UserMapper.toEntity(userDto));
                transactionRepository.save(newTransaction);
            }
        }
        // Save and return the saved Inventory DTO
        Inventory savedInventory = inventoryRepository.save(inventory);
        return inventoryMapper.toDto(savedInventory);
    }

    @Override
    public void updateInventoryAndCreateTransaction(Long productId, int sizeId, int quantity, double totalPrice, LocalDate date, Long userId) {
        List<Inventory> inventoryItems = inventoryRepository.findAllByProductIdAndSizeIdAndState(productId, sizeId, 1);
        UserDto userDto = userService.getUserById(userId);

        int count = 0;
        for (Inventory inventory : inventoryItems) {
            if (count >= quantity) break;
            inventory.setState(2); // Mark as sold
            inventory.setSold_price(totalPrice);
            inventory.setSold_date(date);
            inventoryRepository.save(inventory);

            Transaction transaction = new Transaction();
            transaction.setBarcode(inventory.getBarcode());
            transaction.setSoldPrice(totalPrice);
            transaction.setUser(UserMapper.toEntity(userDto));
            transaction.setTransactionDate(date);
            transactionRepository.save(transaction);

            count++;
        }
    }


    public Map<String, Object> checkAvailability(Long productId, String sizeId, int requestedQuantity) {
        SizeDto sizeDto = sizeService.findByName(sizeId);
        if (sizeDto == null) {
            throw new ResourceNotFoundException("Size not found with name: " + sizeId);
        }

        Long sizeID = sizeDto.getId();
        Double sizeSurcharge = sizeDto.getSurcharge();


        int availableQuantity = inventoryRepository.countAvailableByProductIdAndSizeId(productId, sizeID);

        boolean isAvailable = availableQuantity >= requestedQuantity;

        Map<String, Object> response = new HashMap<>();
        response.put("isAvailable", isAvailable);
        response.put("availableQuantity", availableQuantity);
        response.put("sizeSurcharge", sizeSurcharge);

        return response;
    }

    @Transactional
    @Override
    public void deleteInventoryByBarcode(Long barcode) {
        // Delete transactions related to the inventory item
        transactionRepository.deleteByBarcode(barcode);

        // Delete the inventory item(s) with the given barcode
        inventoryRepository.deleteByBarcode(barcode);
    }

    private Long generateRandomBarcode() {
        // Generate a random 10-digit number as a Long
        return ThreadLocalRandom.current().nextLong(1000000000L, 10000000000L);
    }

}