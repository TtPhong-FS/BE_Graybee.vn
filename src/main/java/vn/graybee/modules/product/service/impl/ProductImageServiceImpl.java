package vn.graybee.modules.product.service.impl;

import org.springframework.stereotype.Service;
import vn.graybee.modules.product.model.ProductImage;
import vn.graybee.modules.product.repository.ProductImageRepository;
import vn.graybee.modules.product.service.ProductImageService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;

    public ProductImageServiceImpl(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    @Override
    public void saveProductImages(Long productId, List<String> imageUrls) {

        if (imageUrls.isEmpty()) {
            return;
        }

        List<ProductImage> productImages = imageUrls
                .stream()
                .map(url -> {
                    ProductImage productImage = new ProductImage();
                    productImage.setProductId(productId);
                    productImage.setImageUrl(url);
                    return productImage;
                })
                .toList();

        productImageRepository.saveAll(productImages);

    }

    @Override
    public List<String> getProductImages(Long productId) {
        return productImageRepository.findAllImageUrlsByProductId(productId);
    }

    @Override
    public void updateProductImages(Long productId, List<String> images) {

        if (images.isEmpty()) {
            return;
        }

        List<String> currentImageUrls = productImageRepository.findImageUrlsByProductId(productId);

        Set<String> uniqueImageUrl = new HashSet<>(images);

        productImageRepository.deleteByProductIdAndImageUrlNotIn(productId, new ArrayList<>(uniqueImageUrl));

        List<ProductImage> newImages = uniqueImageUrl.stream().skip(1)
                .filter(url -> !currentImageUrls.contains(url))
                .map(url -> new ProductImage(productId, url))
                .toList();

        if (!newImages.isEmpty()) {
            productImageRepository.saveAll(newImages);
        }

    }

}
