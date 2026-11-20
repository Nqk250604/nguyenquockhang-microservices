package com.nguyenquockhang.service;

import com.nguyenquockhang.entity.Product;
import com.nguyenquockhang.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAll() {
        return repository.findAll();
    }

    public Product getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
    }

    public Product create(Product product) {
        return repository.save(product);
    }

    public Product update(Long id, Product product) {
        Product oldProduct = getById(id);

        oldProduct.setName(product.getName());
        oldProduct.setDescription(product.getDescription());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setQuantity(product.getQuantity());

        return repository.save(oldProduct);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}