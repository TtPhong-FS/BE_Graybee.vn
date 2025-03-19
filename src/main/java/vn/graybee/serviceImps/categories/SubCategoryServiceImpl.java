package vn.graybee.serviceImps.categories;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.categories.ConstantCategory;
import vn.graybee.enums.CategoryStatus;
import vn.graybee.exceptions.CustomNotFoundException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.categories.SubCategory;
import vn.graybee.models.categories.SubCategoryTag;
import vn.graybee.models.categories.Tag;
import vn.graybee.projections.category.SubCategoryProjection;
import vn.graybee.projections.category.SubCategorySummaryProject;
import vn.graybee.repositories.categories.SubCategoryRepository;
import vn.graybee.repositories.categories.SubCategoryTagRepository;
import vn.graybee.repositories.categories.TagRepository;
import vn.graybee.requests.categories.SubCategoryCreateRequest;
import vn.graybee.requests.categories.SubCategoryUpdateRequest;
import vn.graybee.response.categories.SubCategoryResponse;
import vn.graybee.services.categories.SubCategoryServices;
import vn.graybee.utils.TextUtils;
import vn.graybee.validation.SubCategoryValidation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SubCategoryServiceImpl implements SubCategoryServices {

    private final SubCategoryRepository subCategoryRepository;

    private final SubCategoryValidation subCategoryValidation;

    private final TagRepository tagRepository;

    private final SubCategoryTagRepository subCategoryTagRepository;

    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository, SubCategoryValidation subCategoryValidation, TagRepository tagRepository, SubCategoryTagRepository subCategoryTagRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.subCategoryValidation = subCategoryValidation;
        this.tagRepository = tagRepository;
        this.subCategoryTagRepository = subCategoryTagRepository;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<SubCategoryResponse> create(SubCategoryCreateRequest request) {
        subCategoryRepository.checkExistsByName(request.getSubcategoryName());

        SubCategory subCategory = new SubCategory(TextUtils.capitalize(request.getSubcategoryName()));

        subCategory.setStatus("ACTIVE");

        SubCategory savedSubcategory = subCategoryRepository.save(subCategory);

        if (!request.getTagNames().isEmpty()) {
            handleTags(savedSubcategory.getId(), request.getTagNames());
        }

        SubCategoryResponse subCategoryResponse = new SubCategoryResponse(
                subCategory.getCreatedAt(),
                subCategory.getUpdatedAt(),
                subCategory.getId(),
                subCategory.getSubcategoryName(),
                subCategory.getStatus()
        );

        return new BasicMessageResponse<>(201, "Tạo danh mục con thành công", subCategoryResponse);
    }

    private void handleTags(int subcategory, List<String> tagNames) {
        // Lấy danh sách ID của các tag đã tồn tại bằng SELECT IN
        Map<String, Integer> tagMap = tagRepository.getIdMapByName(tagNames);
        System.out.println(tagMap);
        List<Tag> newTags = new ArrayList<>();
        List<SubCategoryTag> subCategoryTags = new ArrayList<>();

        // Duyệt qua từng tag name để xử lý
        for (String tagName : tagNames) {
            Integer tagId = tagMap.get(tagName);

            if (tagId == null) {
                // Nếu không tìm thấy tagId, tạo mới tag
                Tag newTag = new Tag(tagName);
                newTags.add(newTag);
            }
        }

        // Lưu các tag mới (nếu có)
        if (!newTags.isEmpty()) {
            List<Tag> savedTags = tagRepository.saveAll(newTags);

            // Cập nhật lại map với các tag mới
            savedTags.forEach(tag -> tagMap.put(tag.getTagName(), tag.getId()));
        }

        // Tạo danh sách SubCategoryTag từ tagIdMap
        tagMap.forEach((name, id) -> {
            subCategoryTags.add(new SubCategoryTag(subcategory, id, CategoryStatus.ACTIVE));
        });

        // Lưu toàn bộ SubCategoryTag
        subCategoryTagRepository.saveAll(subCategoryTags);
    }


    @Override
    @Transactional
    public BasicMessageResponse<SubCategoryResponse> update(int subCategoryId, SubCategoryUpdateRequest request) {
        // 1. Tìm SubCategory hiện tại
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId)
                .orElseThrow(() -> new RuntimeException("SubCategory not found"));

        subCategoryRepository.save(subCategory);


        return null;
    }

    @Override
    public BasicMessageResponse<List<SubCategorySummaryProject>> findByCategoryId(int categoryId) {
        List<SubCategorySummaryProject> subcategories = subCategoryRepository.findByCategoryId(categoryId);

        return new BasicMessageResponse<>(200, "List subcategory owned by category id = " + categoryId + ": ", subcategories);
    }

    @Override
    public BasicMessageResponse<SubCategoryProjection> findById(int id) {
        SubCategoryProjection subCategoryProjection = subCategoryRepository.getById(id)
                .orElseThrow(() -> new CustomNotFoundException(ConstantCategory.GENERAL_ERROR, ConstantCategory.SUBCATEGORY_DOES_NOT_EXIST));

        return new BasicMessageResponse<>(200, "Tìm danh mục con thành công", subCategoryProjection);
    }

    @Override
    public BasicMessageResponse<List<SubCategoryProjection>> fetchAll() {
        List<SubCategoryProjection> subcategories = subCategoryRepository.fetchAll();
        return new BasicMessageResponse<>(200, "Lấy danh sách danh mục con thành công!", subcategories);
    }


}
