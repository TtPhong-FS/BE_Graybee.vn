package vn.graybee.taxonomy.category.service;

import java.util.List;

public interface CategoryClassificationService {

    void createCategoryClassification(Integer categoryId, List<Integer> subcategoryIds, List<Integer> manufacturerIds);

    void updateCategoryClassification(Integer categoryId, List<Integer> subcategoryIds, List<Integer> manufacturerIds);

}
