package vn.graybee.services.classifications;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.enums.DirectoryStatus;
import vn.graybee.models.classifications.CategoryClassification;
import vn.graybee.repositories.classifications.CategoryClassificationRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryClassificationImp implements ICategoryClassificationService {

    private final CategoryClassificationRepository categoryClassificationRepository;

    public CategoryClassificationImp(CategoryClassificationRepository categoryClassificationRepository) {
        this.categoryClassificationRepository = categoryClassificationRepository;
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void createCategoryClassification(Integer categoryId, List<Integer> subcategoryIds, List<Integer> manufacturerIds) {

        List<CategoryClassification> classifications = new ArrayList<>();

        int maxLength = Math.max(subcategoryIds.size(), manufacturerIds.size());

        for (int i = 0; i < maxLength; i++) {
            CategoryClassification cf = new CategoryClassification();

            cf.setSubcategoryId(i < subcategoryIds.size() ? subcategoryIds.get(i) : null);
            cf.setManufacturerId(i < manufacturerIds.size() ? manufacturerIds.get(i) : null);

            cf.setCategoryId(categoryId);
            cf.setStatus(DirectoryStatus.ACTIVE);

            classifications.add(cf);
        }

        categoryClassificationRepository.saveAll(classifications);

    }

    private CategoryClassification buildClassification(Integer categoryId, Integer subId, Integer manuId) {
        CategoryClassification cf = new CategoryClassification();
        cf.setCategoryId(categoryId);
        cf.setSubcategoryId(subId);
        cf.setManufacturerId(manuId);
        cf.setStatus(DirectoryStatus.ACTIVE);
        return cf;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateCategoryClassification(Integer categoryId, List<Integer> subcategoryIds, List<Integer> manufacturerIds) {

        List<CategoryClassification> currentRelation = categoryClassificationRepository.findByCategoryId(categoryId);

        Set<String> existingKeys = currentRelation.stream()
                .map(e -> generateKey(e.getSubcategoryId(), e.getManufacturerId()))
                .collect(Collectors.toSet());

        Set<String> incomingKeys = new HashSet<>();
        List<CategoryClassification> toAdd = new ArrayList<>();

        int maxLength = Math.max(subcategoryIds.size(), manufacturerIds.size());

        for (int i = 0; i < maxLength; i++) {
            Integer subId = i < subcategoryIds.size() ? subcategoryIds.get(i) : null;
            Integer manId = i < manufacturerIds.size() ? manufacturerIds.get(i) : null;

            String key = generateKey(subId, manId);
            incomingKeys.add(key);

            if (!existingKeys.contains(key)) {
                CategoryClassification cf = new CategoryClassification();

                cf.setCategoryId(categoryId);
                cf.setSubcategoryId(subId);
                cf.setManufacturerId(manId);
                cf.setStatus(DirectoryStatus.ACTIVE);

                toAdd.add(cf);
            }
        }

        List<CategoryClassification> toDelete = currentRelation.stream()
                .filter(e -> !incomingKeys.contains(generateKey(e.getSubcategoryId(), e.getManufacturerId())))
                .toList();

        List<Integer> ids = toDelete.stream().map(CategoryClassification::getId).toList();

        if (!toDelete.isEmpty()) {
            categoryClassificationRepository.deleteByCategoryIdAndIds(categoryId, ids);
        }

        if (!toAdd.isEmpty()) {
            categoryClassificationRepository.saveAll(toAdd);
        }
    }

    private String generateKey(Integer subId, Integer manId) {
        return (subId != null ? subId.toString() : "null") + "-" +
                (manId != null ? manId.toString() : "null");
    }

}
