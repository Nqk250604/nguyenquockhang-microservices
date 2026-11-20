
package com.nguyenquockhang.controller;

import com.nguyenquockhang.entity.Product;
import com.nguyenquockhang.entity.ProductImage;
import com.nguyenquockhang.repository.ProductImageRepository;
import com.nguyenquockhang.repository.ProductRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product-images")
@CrossOrigin(origins = "*")
public class ProductImageController {

    private final ProductImageRepository imageRepository;
    private final ProductRepository productRepository;

    public ProductImageController(
            ProductImageRepository imageRepository,
            ProductRepository productRepository) {
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
    }

    // 1. Lấy tất cả hình ảnh
    @GetMapping
    public List<ProductImage> getAll() {
        return imageRepository.findAll();
    }

    // 2. Lấy hình ảnh theo sản phẩm
    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getByProduct(
            @PathVariable Long productId) {

        if (!productRepository.existsById(productId)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                imageRepository.findByProductId(productId)
        );
    }

    // 3. Thêm hình ảnh
    @PostMapping
    public ResponseEntity<?> create(
            @RequestBody Map<String, Object> data) {

        Long productId = Long.valueOf(
                data.get("productId").toString()
        );

        Product product = productRepository.findById(productId)
                .orElse(null);

        if (product == null) {
            return ResponseEntity.badRequest()
                    .body("Không tìm thấy sản phẩm");
        }

        String imageUrl = (String) data.get("imageUrl");

        if (imageUrl == null || imageUrl.isBlank()) {
            return ResponseEntity.badRequest()
                    .body("Đường dẫn hình ảnh không được để trống");
        }

        ProductImage image = new ProductImage();

        image.setImageUrl(imageUrl);
        image.setProduct(product);

        image.setIsThumbnail(
                data.get("isThumbnail") != null
                        ? Boolean.valueOf(data.get("isThumbnail").toString())
                        : false
        );

        image.setSortOrder(
                data.get("sortOrder") != null
                        ? Integer.valueOf(data.get("sortOrder").toString())
                        : 0
        );

        return ResponseEntity.ok(imageRepository.save(image));
    }

    // 4. Cập nhật hình ảnh
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody Map<String, Object> data) {

        ProductImage image = imageRepository.findById(id)
                .orElse(null);

        if (image == null) {
            return ResponseEntity.notFound().build();
        }

        if (data.containsKey("imageUrl")) {
            image.setImageUrl(data.get("imageUrl").toString());
        }

        if (data.containsKey("isThumbnail")) {
            image.setIsThumbnail(
                    Boolean.valueOf(data.get("isThumbnail").toString())
            );
        }

        if (data.containsKey("sortOrder")) {
            image.setSortOrder(
                    Integer.valueOf(data.get("sortOrder").toString())
            );
        }

        return ResponseEntity.ok(imageRepository.save(image));
    }

    // 5. Xóa hình ảnh
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        if (!imageRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        imageRepository.deleteById(id);

        return ResponseEntity.ok("Xóa hình ảnh thành công");
    }
}
