package e_shop.e_shop.service.impl;

import e_shop.e_shop.dto.SizeDto;
import e_shop.e_shop.entity.Size;
import e_shop.e_shop.mapper.SizeMapper;
import e_shop.e_shop.repository.SizeRepository;
import e_shop.e_shop.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service

public class SizeServiceImpl implements SizeService {
    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public List<SizeDto> getAllSizes() {
        List<Size> sizes = sizeRepository.findAll();
        return sizes.stream()
                .map(SizeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SizeDto findByName(String name) {
        Optional<Size> size = sizeRepository.findByName(name); // Find size by name
        return size.map(SizeMapper::toDto).orElse(null); // Convert to DTO or return null if not found
    }
}
