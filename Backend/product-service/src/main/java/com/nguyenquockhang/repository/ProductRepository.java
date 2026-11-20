package com.nguyenquockhang.repository;

import com.nguyenquockhang.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
