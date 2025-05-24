package vn.graybee.product.service.impl;

import org.springframework.stereotype.Service;
import vn.graybee.common.constants.ConstantGeneral;
import vn.graybee.common.constants.ConstantProduct;
import vn.graybee.common.constants.ConstantSubcategory;
import vn.graybee.common.constants.ConstantTag;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.product.model.ProductClassification;
import vn.graybee.product.repository.ProductClassificationRepository;
import vn.graybee.product.service.ProductClassificationService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductClassificationServiceImpl implements ProductClassificationService {

    private final ProductClassificationRepository productClassificationRepository;

    private final MessageSourceUtil messageSourceUtil;

    public ProductClassificationServiceImpl(ProductClassificationRepository productClassificationRepository, MessageSourceUtil messageSourceUtil) {
        this.productClassificationRepository = productClassificationRepository;
        this.messageSourceUtil = messageSourceUtil;
    }

    @Override
    public void createProductClassification(Long productId, List<Integer> tagIds, List<Integer> subcategoryIds) {

        List<ProductClassification> productClassificationList = new ArrayList<>();

        int maxLength = Math.max(tagIds.size(), subcategoryIds.size());

        for (int i = 0; i < maxLength; i++) {
            Integer subcategoryId = i < subcategoryIds.size() ? subcategoryIds.get(i) : null;
            Integer tagId = i < tagIds.size() ? tagIds.get(i) : null;

            ProductClassification productClassification = new ProductClassification();
            productClassification.setProductId(productId);
            productClassification.setTagId(tagId);
            productClassification.setSubcategoryId(subcategoryId);

            productClassificationList.add(productClassification);
        }

        productClassificationRepository.saveAll(productClassificationList);
    }

    @Override
    public void updateProductClassification(Long productId, List<Integer> tagIds, List<Integer> subcategoryIds) {

        List<ProductClassification> currentRelation = productClassificationRepository.findByProductId(productId);

        List<String> existingRelationKey = currentRelation
                .stream()
                .map(e -> generateKey(e.getTagId(), e.getSubcategoryId()))
                .toList();

        List<String> incomingKeys = new ArrayList<>();
        List<ProductClassification> newProductClassifications = new ArrayList<>();

        int maxLength = Math.max(tagIds.size(), subcategoryIds.size());

        for (int i = 0; i < maxLength; i++) {
            Integer subcategoryId = i < subcategoryIds.size() ? subcategoryIds.get(i) : null;
            Integer tagId = i < tagIds.size() ? tagIds.get(i) : null;

            String newKey = generateKey(tagId, subcategoryId);
            incomingKeys.add(newKey);

            if (!existingRelationKey.contains(newKey)) {

                ProductClassification productClassification = new ProductClassification();
                productClassification.setProductId(productId);
                productClassification.setTagId(tagId);
                productClassification.setSubcategoryId(subcategoryId);

                newProductClassifications.add(productClassification);
            }
        }

        List<ProductClassification> checkExistingByKey = currentRelation.stream()
                .filter(e -> !incomingKeys.contains(generateKey(e.getTagId(), e.getSubcategoryId())))
                .toList();

        List<Long> ids = checkExistingByKey.stream().map(ProductClassification::getId).toList();

        if (!checkExistingByKey.isEmpty()) {
            productClassificationRepository.deleteByProductIdAndIds(productId, ids);
        }

        if (!newProductClassifications.isEmpty()) {
            productClassificationRepository.saveAll(newProductClassifications);
        }

    }

    @Override
    public void unsetTag(Long productId, Integer tagId) {

        if (!productClassificationRepository.hasTag(productId, tagId)) {
            throw new CustomNotFoundException(
                    ConstantGeneral.general,
                    messageSourceUtil.get("common.entity.not-related", new Object[]{ConstantProduct.product, ConstantTag.tag
                    }));
        }

        productClassificationRepository.unsetTagByProductIdAndTagId(productId, tagId);

    }

    @Override
    public void unsetSubcategory(Long productId, Integer subcategoryId) {
        if (!productClassificationRepository.hasTag(productId, subcategoryId)) {
            throw new CustomNotFoundException(
                    ConstantGeneral.general,
                    messageSourceUtil.get("common.entity.not-related", new Object[]{ConstantProduct.product, ConstantSubcategory.subcategory
                    }));
        }

        productClassificationRepository.unsetTagByProductIdAndTagId(productId, subcategoryId);
    }

    private String generateKey(Integer subId, Integer manId) {
        return (subId != null ? subId.toString() : "null") + "-" +
                (manId != null ? manId.toString() : "null");
    }

}
