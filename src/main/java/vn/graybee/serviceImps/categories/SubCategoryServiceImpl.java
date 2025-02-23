package vn.graybee.serviceImps.categories;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.categories.SubCategory;
import vn.graybee.repositories.categories.SubCategoryRepository;
import vn.graybee.requests.categories.SubCategoryCreateRequest;
import vn.graybee.requests.categories.SubCategoryUpdateRequest;
import vn.graybee.services.categories.SubCategoryServices;

@Service
public class SubCategoryServiceImpl implements SubCategoryServices {

    private final SubCategoryRepository repository;

    public SubCategoryServiceImpl(SubCategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public BasicMessageResponse<SubCategory> create(SubCategoryCreateRequest request) {

        SubCategory subCategory = repository.save(new SubCategory(request.getName()));


        repository.save(subCategory);

        return new BasicMessageResponse<>(201, "Create SubCategory success", subCategory);
    }

    @Override
    @Transactional
    public BasicMessageResponse<SubCategory> update(int subCategoryId, SubCategoryUpdateRequest request) {
        // 1. Tìm SubCategory hiện tại
        SubCategory subCategory = repository.findById(subCategoryId)
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));

        repository.save(subCategory);

        return new BasicMessageResponse<>(200, "Update SubCategory success", subCategory);
    }


}
