
package com.nguyenquockhang.controller;

import com.nguyenquockhang.entity.Product;
import com.nguyenquockhang.repository.ProductRepository;
import com.nguyenquockhang.repository.CategoryRepository;
import com.nguyenquockhang.repository.BrandRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    public ProductController(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            BrandRepository brandRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
    }

    @GetMapping
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {

        product.setId(null);

        if (product.getCategory() != null) {
            Long categoryId = product.getCategory().getId();
            product.setCategory(categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Category không tồn tại")));
        }

        if (product.getBrand() != null) {
            Long brandId = product.getBrand().getId();
            product.setBrand(brandRepository.findById(brandId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Brand không tồn tại")));
        }

        return productRepository.save(product);
    }

    @PutMapping("/{id}")
    public Product update(
            @PathVariable Long id,
            @RequestBody Product product) {

        Product oldProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm"));

        oldProduct.setName(product.getName());
        oldProduct.setDescription(product.getDescription());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setQuantity(product.getQuantity());

        if (product.getCategory() != null) {
            Long categoryId = product.getCategory().getId();
            oldProduct.setCategory(categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Category không tồn tại")));
        }

        if (product.getBrand() != null) {
            Long brandId = product.getBrand().getId();
            oldProduct.setBrand(brandRepository.findById(brandId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST, "Brand không tồn tại")));
        }

        return productRepository.save(oldProduct);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Không tìm thấy sản phẩm");
        }

        productRepository.deleteById(id);
    }
}
