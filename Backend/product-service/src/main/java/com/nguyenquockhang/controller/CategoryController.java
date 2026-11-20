
package com.nguyenquockhang.controller;

import com.nguyenquockhang.entity.Category;
import com.nguyenquockhang.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    private final CategoryRepository repository;

    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    // Lấy tất cả loại sản phẩm
    @GetMapping
    public List<Category> getAll() {
        return repository.findAll();
    }

    // Lấy loại sản phẩm theo ID
    @GetMapping("/{id}")
    public Category getById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Không tìm thấy loại sản phẩm"));
    }

    // Thêm loại sản phẩm
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category create(@RequestBody Category category) {
        category.setId(null);
        return repository.save(category);
    }

    // Cập nhật loại sản phẩm
    @PutMapping("/{id}")
    public Category update(
            @PathVariable Long id,
            @RequestBody Category category) {

        Category oldCategory = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Không tìm thấy loại sản phẩm"));

        oldCategory.setName(category.getName());
        oldCategory.setDescription(category.getDescription());
        oldCategory.setStatus(category.getStatus());

        return repository.save(oldCategory);
    }

    // Xóa loại sản phẩm
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Không tìm thấy loại sản phẩm");
        }

        repository.deleteById(id);
    }
}
