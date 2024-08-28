package e_shop.e_shop.controller;

import e_shop.e_shop.dto.SizeDto;
import e_shop.e_shop.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sizes")
public class SizeController {

    @Autowired
    private SizeService sizeService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public ResponseEntity<List<SizeDto>> getAllSizes() {
        List<SizeDto> sizes = sizeService.getAllSizes();
        return ResponseEntity.ok(sizes);
    }
}
