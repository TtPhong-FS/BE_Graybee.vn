package vn.graybee.taxonomy.subcategory.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.account.security.UserDetail;
import vn.graybee.common.constants.ConstantGeneral;
import vn.graybee.common.constants.ConstantSubcategory;
import vn.graybee.common.constants.ConstantTag;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.common.utils.MessageSourceUtil;
import vn.graybee.common.utils.TextUtils;
import vn.graybee.models.directories.SubCategoryTag;
import vn.graybee.repositories.categories.SubCategoryTagRepository;
import vn.graybee.taxonomy.dto.response.UpdateStatusDto;
import vn.graybee.taxonomy.enums.TaxonomyStatus;
import vn.graybee.taxonomy.subcategory.dto.request.SubcategoryCreateRequest;
import vn.graybee.taxonomy.subcategory.dto.request.SubcategoryUpdateRequest;
import vn.graybee.taxonomy.subcategory.dto.response.SubcategoryDto;
import vn.graybee.taxonomy.subcategory.dto.response.SubcategoryIdTagIdDto;
import vn.graybee.taxonomy.subcategory.dto.response.SubcategoryTagDto;
import vn.graybee.taxonomy.subcategory.dto.response.SubcategoryTagsDto;
import vn.graybee.taxonomy.subcategory.model.Subcategory;
import vn.graybee.taxonomy.subcategory.repository.SubcategoryRepository;
import vn.graybee.taxonomy.tag.dto.response.TagDto;
import vn.graybee.taxonomy.tag.repository.TagRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepository subCategoryRepository;

    private final MessageSourceUtil messageSourceUtil;

    private final TagRepository tagRepository;

    private final SubCategoryTagRepository subCategoryTagRepository;

    public SubcategoryServiceImpl(SubcategoryRepository subCategoryRepository, MessageSourceUtil messageSourceUtil, TagRepository tagRepository, SubCategoryTagRepository subCategoryTagRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.messageSourceUtil = messageSourceUtil;
        this.tagRepository = tagRepository;
        this.subCategoryTagRepository = subCategoryTagRepository;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<SubcategoryDto> create(SubcategoryCreateRequest request) {

        if (subCategoryRepository.validateName(request.getName()).isPresent()) {
            throw new BusinessCustomException(ConstantSubcategory.name, ConstantSubcategory.name_exists);
        }

        List<TagDto> tags = tagRepository.findByIds(request.getTags());
        Set<Integer> foundTagIds = tags.stream().map(TagDto::getId).collect(Collectors.toSet());

        if (!foundTagIds.containsAll(request.getTags())) {
            throw new BusinessCustomException(ConstantTag.tags, ConstantTag.does_not_exists);
        }

        Subcategory subCategory = new Subcategory();
        subCategory.setName(TextUtils.capitalize(request.getName()));
        subCategory.setStatus(TaxonomyStatus.PENDING);

        Subcategory savedSubcategory = subCategoryRepository.save(subCategory);

        if (request.getTags() != null && !request.getTags().isEmpty()) {
            handleTags(savedSubcategory.getId(), foundTagIds);
        }

        SubcategoryDto subCategoryDto = new SubcategoryDto(
                savedSubcategory

        );
        subCategoryDto.setTags(tags);

        return new BasicMessageResponse<>(201, ConstantSubcategory.success_create, subCategoryDto);
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
    public BasicMessageResponse<SubcategoryDto> update(int id, SubcategoryUpdateRequest request) {

        TaxonomyStatus status = TaxonomyStatus.getStatus(request.getStatus(), messageSourceUtil);

        List<TagDto> tags = tagRepository.findByIds(request.getTags());
        Set<Integer> foundTagIds = tags.stream().map(TagDto::getId).collect(Collectors.toSet());

        if (!foundTagIds.containsAll(request.getTags())) {
            throw new BusinessCustomException(ConstantTag.tags, ConstantTag.does_not_exists);
        }

        Subcategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantSubcategory.general, ConstantSubcategory.does_not_exists));

        if (!subCategory.getName().equals(request.getName()) && subCategoryRepository.existsByNameAndNotId(request
                .getName(), subCategory.getId())) {
            throw new BusinessCustomException(ConstantSubcategory.name, ConstantSubcategory.name_exists);
        }

        subCategory.setName(request.getName());
        subCategory.setStatus(status);
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

        SubcategoryDto response = new SubcategoryDto(subCategory);
        response.setTags(tags);

        return new BasicMessageResponse<>(200, ConstantSubcategory.success_update_by_id, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<UpdateStatusDto> updateStatusById(int id, String status) {

        TaxonomyStatus newStatus = TaxonomyStatus.getStatus(status, messageSourceUtil);


        int subcategory = subCategoryRepository.checkExistsById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantSubcategory.does_not_exists));

        subCategoryRepository.updateStatusById(subcategory, newStatus);

        UpdateStatusDto response = new UpdateStatusDto(subcategory, newStatus, LocalDateTime.now());

        return new BasicMessageResponse<>(200, ConstantGeneral.success_update_status, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<SubcategoryDto> restoreById(int id, UserDetail userDetail) {

        Subcategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantSubcategory.does_not_exists));

        TaxonomyStatus status = TaxonomyStatus.getStatus(subCategory.getStatus().name(), messageSourceUtil);

        if (userDetail != null && !userDetail.getUser().isSuperAdmin() && status == TaxonomyStatus.DELETED) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantGeneral.not_super_admin);
        }

        if (status != TaxonomyStatus.DELETED && status != TaxonomyStatus.REMOVED) {
            throw new BusinessCustomException(ConstantGeneral.general, ConstantSubcategory.not_removed);
        }

        subCategoryRepository.updateStatusById(id, TaxonomyStatus.INACTIVE);

        List<TagDto> tags = subCategoryTagRepository.findTagsBySubCategoryId(subCategory.getId());

        SubcategoryDto response = new SubcategoryDto(subCategory);
        response.setTags(tags);

        return new BasicMessageResponse<>(200, ConstantSubcategory.success_restore, response);
    }

    @Override
    public BasicMessageResponse<SubcategoryDto> getById(int id) {
        SubcategoryDto response = subCategoryRepository.getById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantSubcategory.general, ConstantSubcategory.does_not_exists));

        List<TagDto> tags = subCategoryTagRepository.findTagsBySubCategoryId(response.getId());

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
    public BasicMessageResponse<List<SubcategoryDto>> fetchAll() {

        List<SubcategoryDto> subcategories = subCategoryRepository.fetchAll();

        if (subcategories.isEmpty()) {
            return new BasicMessageResponse<>(200, ConstantGeneral.empty_list, subcategories);
        }

        List<Integer> subIds = subcategories.stream().map(SubcategoryDto::getId).toList();
        List<SubcategoryTagDto> subcategoryTagRespons = subCategoryTagRepository.findTagsBySubcategoryIds(subIds);
        Map<Integer, List<TagDto>> subcategoryTagMap = subcategoryTagRespons.stream()
                .collect(Collectors.groupingBy(SubcategoryTagDto::getId, Collectors.mapping(tag -> new TagDto(tag.getTagId(), tag.getTagName()), Collectors.toList())));

        subcategories.forEach(subcategory -> {
            subcategory.setTags(subcategoryTagMap.getOrDefault(subcategory.getId(), Collections.emptyList()));
        });

        return new BasicMessageResponse<>(200, ConstantSubcategory.success_fetch_subcategories, subcategories);
    }

    @Override
    public List<SubcategoryTagsDto> getForSidebar() {

        List<SubcategoryTagsDto> subcategories = subCategoryRepository.getAllForSidebar();

        if (subcategories.isEmpty()) {
            return subcategories;
        }


        List<Integer> subIds = subcategories.stream().map(SubcategoryTagsDto::getId).toList();

        List<SubcategoryTagDto> subcategoryTagRespons = subCategoryTagRepository.findTagsBySubcategoryIds(subIds);
        Map<Integer, List<TagDto>> subcategoryTagMap = subcategoryTagRespons.stream()
                .collect(Collectors.groupingBy(SubcategoryTagDto::getId, Collectors.mapping(tag -> new TagDto(tag.getTagId(), tag.getTagName()), Collectors.toList())));

        subcategories.forEach(subcategory -> {
            subcategory.setTags(subcategoryTagMap.getOrDefault(subcategory.getId(), Collections.emptyList()));
        });

        return subcategories;
    }

    @Override
    public BasicMessageResponse<SubcategoryIdTagIdDto> deleteRelationsBySubCategoryIdAndTagId(int subcategoryId, int tagId) {
        SubcategoryIdTagIdDto response = subCategoryTagRepository.findRelationsBySubcategoryIdAndTagId(subcategoryId, tagId)
                .orElseThrow(() -> new BusinessCustomException(ConstantSubcategory.general, ConstantSubcategory.tag_relation_does_not_exists));

        subCategoryTagRepository.deleteRelationBySubcategoryIdAndTagId(response.getSubcategoryId(), response.getTagId());

        return new BasicMessageResponse<>(200, ConstantSubcategory.success_delete_tag_relation, response);
    }


}
