package vn.graybee.product.service;

import java.util.List;

public interface ProductClassificationService {

    void createProductClassification(Long productId, List<Integer> tagIds, List<Integer> subcategoryIds);

    void updateProductClassification(Long productId, List<Integer> tagIds, List<Integer> subcategoryIds);

    void unsetTag(Long productId, Integer tagId);

    void unsetSubcategory(Long productId, Integer subcategoryId);

}
