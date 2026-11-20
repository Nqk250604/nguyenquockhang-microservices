
package com.nguyenquockhang.controller;

import com.nguyenquockhang.entity.Brand;
import com.nguyenquockhang.repository.BrandRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
@CrossOrigin(origins = "*")
public class BrandController {

    private final BrandRepository repository;

    public BrandController(BrandRepository repository) {
        this.repository = repository;
    }

    // Lấy danh sách thương hiệu
    @GetMapping
    public List<Brand> getAll() {
        return repository.findAll();
    }

    // Lấy thương hiệu theo ID
    @GetMapping("/{id}")
    public Brand getById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Không tìm thấy thương hiệu"));
    }

    // Thêm thương hiệu
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Brand create(@RequestBody Brand brand) {
        brand.setId(null);
        return repository.save(brand);
    }

    // Cập nhật thương hiệu
    @PutMapping("/{id}")
    public Brand update(@PathVariable Long id,
                        @RequestBody Brand brand) {

        Brand oldBrand = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Không tìm thấy thương hiệu"));

        oldBrand.setName(brand.getName());
        oldBrand.setDescription(brand.getDescription());
        oldBrand.setLogo(brand.getLogo());
        oldBrand.setStatus(brand.getStatus());

        return repository.save(oldBrand);
    }

    // Xóa thương hiệu
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Không tìm thấy thương hiệu");
        }

        repository.deleteById(id);
    }
}
