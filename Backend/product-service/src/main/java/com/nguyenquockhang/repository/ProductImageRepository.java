
package com.nguyenquockhang.repository;

import com.nguyenquockhang.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository
        extends JpaRepository<ProductImage, Long> {

    // Lấy tất cả hình ảnh của một sản phẩm
    List<ProductImage> findByProductId(Long productId);

    // Xóa tất cả hình ảnh theo sản phẩm
    void deleteByProductId(Long productId);
}
