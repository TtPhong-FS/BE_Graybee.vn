package vn.graybee.services.classifications;

import java.util.List;

public interface ICategoryClassificationService {

    void createCategoryClassification(Integer categoryId, List<Integer> subcategoryIds, List<Integer> manufacturerIds);

    void updateCategoryClassification(Integer categoryId, List<Integer> subcategoryIds, List<Integer> manufacturerIds);
    
}
