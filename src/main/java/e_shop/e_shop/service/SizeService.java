package e_shop.e_shop.service;

import e_shop.e_shop.dto.SizeDto;

import java.util.List;

public interface SizeService {

    List<SizeDto> getAllSizes();
    SizeDto findByName(String name);

}
