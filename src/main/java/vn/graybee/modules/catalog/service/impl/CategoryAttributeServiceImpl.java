package vn.graybee.modules.catalog.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.graybee.modules.catalog.model.CategoryAttribute;
import vn.graybee.modules.catalog.repository.CategoryAttributeRepository;
import vn.graybee.modules.catalog.service.CategoryAttributeService;

@AllArgsConstructor
@Service
public class CategoryAttributeServiceImpl implements CategoryAttributeService {

    private final CategoryAttributeRepository categoryAttributeRepository;

    @Override
    public void saveByCategoryIdAndAttributeId(Long categoryId, Long attributeId) {
        CategoryAttribute categoryAttribute = new CategoryAttribute();
        categoryAttribute.setCategoryId(categoryId);
        categoryAttribute.setAttributeId(attributeId);

        categoryAttributeRepository.save(categoryAttribute);
    }

}
