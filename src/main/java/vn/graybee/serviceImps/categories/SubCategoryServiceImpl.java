package vn.graybee.serviceImps.categories;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.constants.ConstantSubcategory;
import vn.graybee.constants.ConstantTag;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.directories.SubCategory;
import vn.graybee.models.directories.SubCategoryTag;
import vn.graybee.repositories.categories.SubCategoryRepository;
import vn.graybee.repositories.categories.SubCategoryTagRepository;
import vn.graybee.repositories.categories.TagRepository;
import vn.graybee.requests.directories.SubCategoryCreateRequest;
import vn.graybee.requests.directories.SubCategoryUpdateRequest;
import vn.graybee.response.admin.directories.subcate.SubCategoryResponse;
import vn.graybee.response.admin.directories.subcate.SubcategoryTagIdResponse;
import vn.graybee.response.admin.directories.subcate.SubcategoryTagResponse;
import vn.graybee.response.admin.directories.tag.TagResponse;
import vn.graybee.services.categories.SubCategoryServices;
import vn.graybee.utils.TextUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubCategoryServiceImpl implements SubCategoryServices {

    private final SubCategoryRepository subCategoryRepository;

    private final TagRepository tagRepository;

    private final SubCategoryTagRepository subCategoryTagRepository;

    public SubCategoryServiceImpl(SubCategoryRepository subCategoryRepository, TagRepository tagRepository, SubCategoryTagRepository subCategoryTagRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.tagRepository = tagRepository;
        this.subCategoryTagRepository = subCategoryTagRepository;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<SubCategoryResponse> create(SubCategoryCreateRequest request) {

        if (subCategoryRepository.validateName(request.getName()).isPresent()) {
            throw new BusinessCustomException(ConstantSubcategory.name, ConstantSubcategory.name_exists);
        }

        List<TagResponse> tags = tagRepository.findByIds(request.getTags());
        Set<Integer> foundTagIds = tags.stream().map(TagResponse::getId).collect(Collectors.toSet());

        if (!foundTagIds.containsAll(request.getTags())) {
            throw new BusinessCustomException(ConstantTag.tags, ConstantTag.does_not_exists);
        }

        SubCategory subCategory = new SubCategory();
        subCategory.setName(TextUtils.capitalize(request.getName()));
        subCategory.setStatus("ACTIVE");

        SubCategory savedSubcategory = subCategoryRepository.save(subCategory);

        if (request.getTags() != null && !request.getTags().isEmpty()) {
            handleTags(savedSubcategory.getId(), foundTagIds);
        }

        SubCategoryResponse subCategoryResponse = new SubCategoryResponse(
                savedSubcategory,
                tags
        );

        return new BasicMessageResponse<>(201, ConstantSubcategory.success_create, subCategoryResponse);
    }

    private void handleTags(int subcategoryId, Set<Integer> tagIds) {

        List<SubCategoryTag> relations = tagIds.stream()
                .map(tag -> new SubCategoryTag(subcategoryId, tag))
                .toList();
        if (!relations.isEmpty()) {
            subCategoryTagRepository.saveAll(relations);
        }
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<SubCategoryResponse> update(int id, SubCategoryUpdateRequest request) {

        List<TagResponse> tags = tagRepository.findByIds(request.getTags());
        Set<Integer> foundTagIds = tags.stream().map(TagResponse::getId).collect(Collectors.toSet());

        if (!foundTagIds.containsAll(request.getTags())) {
            throw new BusinessCustomException(ConstantTag.tags, ConstantTag.does_not_exists);
        }

        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantSubcategory.general, ConstantSubcategory.does_not_exists));

        if (!subCategory.getName().equals(request.getName()) && subCategoryRepository.existsByNameAndNotId(request
                .getName(), subCategory.getId())) {
            throw new BusinessCustomException(ConstantSubcategory.name, ConstantSubcategory.name_exists);
        }

        subCategory.setName(request.getName());
        subCategory.setStatus(request.getStatus().name());
        subCategory.setUpdatedAt(LocalDateTime.now());

        subCategory = subCategoryRepository.save(subCategory);
        int subcategoryId = subCategory.getId();

        if (request.getTags() != null && !request.getTags().isEmpty()) {

            List<Integer> currentTagIds = subCategoryTagRepository.findTagIdsBySubcategoryId(subcategoryId);
            Set<Integer> newTags = new HashSet<>(request.getTags());
            subCategoryTagRepository.deleteBySubCategoryIdAndTagIdNotIn(subcategoryId, new ArrayList<>(newTags));

            List<SubCategoryTag> newRelations = newTags
                    .stream()
                    .filter(tagId -> !currentTagIds.contains(tagId))
                    .map(tagId -> new SubCategoryTag(subcategoryId, tagId)).toList();
            if (!newRelations.isEmpty()) {
                subCategoryTagRepository.saveAll(newRelations);
            }
        }

        SubCategoryResponse response = new SubCategoryResponse(subCategory, tags);

        return new BasicMessageResponse<>(200, ConstantSubcategory.success_update_by_id, response);
    }

    @Override
    public BasicMessageResponse<SubCategoryResponse> getById(int id) {
        SubCategoryResponse response = subCategoryRepository.getById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantSubcategory.general, ConstantSubcategory.does_not_exists));

        List<TagResponse> tags = subCategoryTagRepository.findTagsBySubCategoryId(response.getId());

        response.setTags(tags);

        return new BasicMessageResponse<>(200, ConstantSubcategory.success_find_by_id, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Integer> delete(int id) {
        int subId = subCategoryRepository.checkExistsById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantSubcategory.general, ConstantSubcategory.does_not_exists));

        subCategoryRepository.delete(subId);

        return new BasicMessageResponse<>(200, ConstantSubcategory.success_delete_by_id, subId);
    }

    @Override
    public BasicMessageResponse<List<SubCategoryResponse>> fetchAll() {

        List<SubCategoryResponse> subcategories = subCategoryRepository.fetchAll();

        if (subcategories.isEmpty()) {
            return new BasicMessageResponse<>(200, ConstantGeneral.empty_list, subcategories);
        }

        List<Integer> subIds = subcategories.stream().map(SubCategoryResponse::getId).toList();
        List<SubcategoryTagResponse> subcategoryTagResponses = subCategoryTagRepository.findTagsBySubcategoryIds(subIds);
        Map<Integer, List<TagResponse>> subcategoryTagMap = subcategoryTagResponses.stream()
                .collect(Collectors.groupingBy(SubcategoryTagResponse::getId, Collectors.mapping(tag -> new TagResponse(tag.getTagId(), tag.getTagName()), Collectors.toList())));

        subcategories.forEach(subcategory -> {
            subcategory.setTags(subcategoryTagMap.getOrDefault(subcategory.getId(), Collections.emptyList()));
        });

        return new BasicMessageResponse<>(200, ConstantSubcategory.success_fetch_subcategories, subcategories);
    }

    @Override
    public BasicMessageResponse<SubcategoryTagIdResponse> deleteRelationsBySubCategoryIdAndTagId(int subcategoryId, int tagId) {
        SubcategoryTagIdResponse response = subCategoryTagRepository.findRelationsBySubcategoryIdAndTagId(subcategoryId, tagId)
                .orElseThrow(() -> new BusinessCustomException(ConstantSubcategory.general, ConstantSubcategory.tag_relation_does_not_exists));

        subCategoryTagRepository.deleteRelationBySubcategoryIdAndTagId(response.getSubcategoryId(), response.getTagId());

        return new BasicMessageResponse<>(200, ConstantSubcategory.success_delete_tag_relation, response);
    }


}
