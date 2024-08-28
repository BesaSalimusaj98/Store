package e_shop.e_shop.mapper;

import e_shop.e_shop.dto.SizeDto;
import e_shop.e_shop.entity.Size;

public class SizeMapper {

    public static SizeDto toDto(Size size) {
        if (size == null) {
            return null;
        }
        SizeDto sizeDto = new SizeDto();
        sizeDto.setId(size.getId());
        sizeDto.setName(size.getName());
        sizeDto.setSurcharge(size.getSurcharge());
        return sizeDto;
    }

    public static Size toEntity(SizeDto sizeDto) {
        if (sizeDto == null) {
            return null;
        }
        Size size = new Size();
        size.setId(sizeDto.getId());
        size.setName(sizeDto.getName());
        size.setSurcharge(sizeDto.getSurcharge());
        return size;
    }
}
