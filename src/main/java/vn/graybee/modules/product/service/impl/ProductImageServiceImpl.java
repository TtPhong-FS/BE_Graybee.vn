package vn.graybee.modules.product.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.modules.product.model.ProductImage;
import vn.graybee.modules.product.repository.ProductImageRepository;
import vn.graybee.modules.product.service.ProductImageService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;

    public ProductImageServiceImpl(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void saveProductImages(Long productId, List<String> imageUrls) {

        if (imageUrls == null || imageUrls.isEmpty()) {
            return;
        }

        List<ProductImage> productImages = imageUrls
                .stream()
                .skip(1)
                .map(url -> {
                    ProductImage productImage = new ProductImage();
                    productImage.setProductId(productId);
                    productImage.setImageUrl(url);
                    return productImage;
                })
                .toList();

        if (!productImages.isEmpty()) {

            productImageRepository.saveAll(productImages);
        }

    }

    @Override
    public List<String> getProductImages(Long productId) {
        return productImageRepository.findAllImageUrlsByProductId(productId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateProductImages(Long productId, List<String> images) {

        if (images == null || images.isEmpty()) {
            productImageRepository.deleteByProductId(productId);
            return;
        }

        List<ProductImage> currentImages = productImageRepository.findAllProductImageByProductId(productId);
        Set<String> currentImageUrls = currentImages.stream()
                .map(ProductImage::getImageUrl)
                .collect(Collectors.toSet());

        Set<String> newImageUrls = new HashSet<>(images);

        List<String> toDelete = currentImageUrls.stream()
                .filter(url -> !newImageUrls.contains(url))
                .collect(Collectors.toList());

        if (!toDelete.isEmpty()) {
            productImageRepository.deleteByProductIdAndImageUrlIn(productId, toDelete);
        }

        List<ProductImage> toAdd = newImageUrls.stream()
                .filter(url -> !currentImageUrls.contains(url))
                .map(url -> new ProductImage(productId, url))
                .collect(Collectors.toList());

        if (!toAdd.isEmpty()) {
            productImageRepository.saveAll(toAdd);
        }

    }

}
