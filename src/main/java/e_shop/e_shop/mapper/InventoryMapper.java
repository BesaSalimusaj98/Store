package e_shop.e_shop.mapper;

import e_shop.e_shop.dto.InventoryDto;
import e_shop.e_shop.entity.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {

    public static InventoryDto toDto(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        InventoryDto inventoryDto = new InventoryDto();
        inventoryDto.setBarcode(inventory.getBarcode());
        inventoryDto.setState(inventory.getState());
        inventoryDto.setProduct(ProductMapper.mapToProductDto(inventory.getProduct()));//Map nested product
        inventoryDto.setSize(SizeMapper.toDto(inventory.getSize())); // Map nested Size
        inventoryDto.setSold_date(inventory.getSold_date());
        inventoryDto.setSold_price(inventory.getSold_price());
        return inventoryDto;
    }

    public static Inventory toEntity(InventoryDto inventoryDto) {
        if (inventoryDto == null) {
            return null;
        }
        Inventory inventory = new Inventory();
        inventory.setBarcode(inventoryDto.getBarcode());
        inventory.setState(inventoryDto.getState());
        inventory.setProduct(ProductMapper.mapToProduct(inventoryDto.getProduct()));//Map nested Product
        inventory.setSize(SizeMapper.toEntity(inventoryDto.getSize())); // Map nested Size
        inventory.setSold_date(inventoryDto.getSold_date());
        inventory.setSold_price(inventoryDto.getSold_price());
        return inventory;
    }
}
