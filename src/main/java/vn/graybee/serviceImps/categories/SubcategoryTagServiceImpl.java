package vn.graybee.serviceImps.categories;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.ConstantCategory;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.repositories.categories.SubCategoryTagRepository;
import vn.graybee.response.admin.directories.subcate.SubcategoryTagIdResponse;
import vn.graybee.services.categories.SubcategoryTagServices;

@Service
public class SubcategoryTagServiceImpl implements SubcategoryTagServices {

    private final SubCategoryTagRepository subCategoryTagRepository;

    public SubcategoryTagServiceImpl(SubCategoryTagRepository subCategoryTagRepository) {
        this.subCategoryTagRepository = subCategoryTagRepository;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<SubcategoryTagIdResponse> deleteRelationsBySubCategoryIdAndTagId(int subcategoryId, int tagId) {
        SubcategoryTagIdResponse response = subCategoryTagRepository.findRelationsBySubcategoryIdAndTagId(subcategoryId, tagId)
                .orElseThrow(() -> new BusinessCustomException(ConstantCategory.GENERAL_ERROR, ConstantCategory.RELATIONSHIP_DOES_NOT_EXIST));

        subCategoryTagRepository.deleteRelationBySubcategoryIdAndTagId(response.getSubcategoryId(), response.getTagId());

        return new BasicMessageResponse<>(200, "Xoá quan hệ thành công!", response);
    }

}
