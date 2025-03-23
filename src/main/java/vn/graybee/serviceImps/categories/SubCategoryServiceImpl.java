package vn.graybee.serviceImps.categories;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.ConstantCategory;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.categories.SubCategory;
import vn.graybee.models.categories.SubCategoryTag;
import vn.graybee.projections.admin.category.SubCategoryProjection;
import vn.graybee.projections.admin.category.SubCategorySummaryProject;
import vn.graybee.repositories.categories.SubCategoryRepository;
import vn.graybee.repositories.categories.SubCategoryTagRepository;
import vn.graybee.repositories.categories.TagRepository;
import vn.graybee.requests.directories.SubCategoryCreateRequest;
import vn.graybee.requests.directories.SubCategoryUpdateRequest;
import vn.graybee.response.admin.directories.subcate.SubCategoryResponse;
import vn.graybee.response.admin.directories.tag.TagResponse;
import vn.graybee.services.categories.SubCategoryServices;
import vn.graybee.utils.TextUtils;
import vn.graybee.validation.SubCategoryValidation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        List<TagResponse> tagResponses = new ArrayList<>();
        if (!request.getTags().isEmpty()) {
            tagResponses = handleTags(savedSubcategory.getId(), request.getTags());
        }

        SubCategoryResponse subCategoryResponse = new SubCategoryResponse(
                savedSubcategory,
                tagResponses
        );

        return new BasicMessageResponse<>(201, "Tạo danh mục con thành công", subCategoryResponse);
    }

    private List<TagResponse> handleTags(int subcategoryId, List<Integer> tagIds) {

        List<TagResponse> tags = tagRepository.findByIds(tagIds);

        if (tags.size() != tagIds.size()) {
            throw new BusinessCustomException(ConstantCategory.TAGS, ConstantCategory.TAG_DOES_NOT_EXIST);
        }

        List<SubCategoryTag> relations = tags.stream()
                .map(tag -> new SubCategoryTag(subcategoryId, tag.getId()))
                .toList();

        subCategoryTagRepository.saveAll(relations);

        return tags;
    }


    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<SubCategoryResponse> update(int id, SubCategoryUpdateRequest request) {

        if (request.getStatus() == null) {
            throw new BusinessCustomException(ConstantCategory.GENERAL_ERROR, ConstantCategory.STATUS_CANNOT_BE_EMPTY);
        }

        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantCategory.GENERAL_ERROR, ConstantCategory.SUBCATEGORY_DOES_NOT_EXIST));

        subCategory.setSubcategoryName(request.getSubcategoryName());
        subCategory.setStatus(request.getStatus().name());

        List<TagResponse> tagResponses = new ArrayList<>();
        if (!request.getTags().isEmpty()) {
            List<Integer> tagIds = request.getTags();
            List<Integer> validateTagIds = tagRepository.findAllByIds(tagIds);

            if (validateTagIds.size() != tagIds.size()) {
                throw new BusinessCustomException(ConstantCategory.TAGS, ConstantCategory.TAG_DOES_NOT_EXIST);
            }

            List<Integer> currentTagIds = subCategoryTagRepository.findTagIdsBySubcategoryId(subCategory.getId());

            Set<Integer> newTags = new HashSet<>(tagIds);

            subCategoryTagRepository.deleteBySubCategoryIdAndTagIdNotIn(subCategory.getId(), new ArrayList<>(newTags));

            List<SubCategoryTag> newRelations = newTags
                    .stream()
                    .filter(tagId -> !currentTagIds.contains(tagId))
                    .map(tagId -> new SubCategoryTag(subCategory.getId(), tagId)).toList();

            subCategoryTagRepository.saveAll(newRelations);

            tagResponses = tagRepository.findByIds(tagIds);
        }

        SubCategory saved = subCategoryRepository.save(subCategory);

        SubCategoryResponse response = new SubCategoryResponse(saved, tagResponses);

        return new BasicMessageResponse<>(200, "Cập nhật danh mục con thành công!", response);
    }

    @Override
    public BasicMessageResponse<SubCategoryResponse> getById(int id) {
        SubCategoryResponse response = subCategoryRepository.getById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantCategory.GENERAL_ERROR, ConstantCategory.SUBCATEGORY_DOES_NOT_EXIST));

        List<TagResponse> tags = subCategoryTagRepository.findBySubCategoryId(response.getId());

        response.setTags(tags);

        return new BasicMessageResponse<>(200, "Tìm danh mục con thành công!", response);
    }

    @Override
    public BasicMessageResponse<Integer> delete(int id) {
        int subId = subCategoryValidation.checkExistsById(id);
        subCategoryRepository.delete(subId);
        return new BasicMessageResponse<>(200, "Xoá danh mục con thành công!", subId);
    }

    @Override
    public BasicMessageResponse<List<SubCategorySummaryProject>> findByCategoryId(int categoryId) {
        List<SubCategorySummaryProject> subcategories = subCategoryRepository.findByCategoryId(categoryId);

        return new BasicMessageResponse<>(200, "List subcategory owned by category id = " + categoryId + ": ", subcategories);
    }

    @Override
    public BasicMessageResponse<List<SubCategoryProjection>> fetchAll() {
        List<SubCategoryProjection> subcategories = subCategoryRepository.fetchAll();
        return new BasicMessageResponse<>(200, "Lấy danh sách danh mục con thành công!", subcategories);
    }


}
