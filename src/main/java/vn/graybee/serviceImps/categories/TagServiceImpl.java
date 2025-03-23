package vn.graybee.serviceImps.categories;

import org.springframework.stereotype.Service;
import vn.graybee.constants.ConstantCategory;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.categories.Tag;
import vn.graybee.projections.admin.category.TagProjection;
import vn.graybee.repositories.categories.TagRepository;
import vn.graybee.requests.directories.TagCreateRequest;
import vn.graybee.requests.directories.TagUpdateRequest;
import vn.graybee.response.admin.directories.tag.TagResponse;
import vn.graybee.services.categories.TagServices;
import vn.graybee.utils.TextUtils;
import vn.graybee.validation.TagValidation;

import java.util.List;

@Service
public class TagServiceImpl implements TagServices {

    private final TagRepository tagRepository;

    private final TagValidation tagValidation;

    public TagServiceImpl(TagRepository tagRepository, TagValidation tagValidation) {
        this.tagRepository = tagRepository;
        this.tagValidation = tagValidation;
    }

    @Override
    public BasicMessageResponse<List<TagProjection>> fetchAll() {
        List<TagProjection> tags = tagRepository.fetchAll();

        return new BasicMessageResponse<>(200, "Lấy danh sách thẻ phân loại thành công!", tags);
    }

    @Override
    public BasicMessageResponse<TagResponse> create(TagCreateRequest request) {
        tagValidation.validateNameExists(request.getTagName());

        Tag tag = new Tag(TextUtils.capitalize(request.getTagName()));

        Tag savedTag = tagRepository.save(tag);

        TagResponse response = new TagResponse(savedTag);

        return new BasicMessageResponse<>(201, "Tạo thẻ phân loại thành công!", response);
    }

    @Override
    public BasicMessageResponse<TagResponse> update(int id, TagUpdateRequest request) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantCategory.GENERAL_ERROR, ConstantCategory.TAG_DOES_NOT_EXIST));
        tag.setTagName(request.getTagName());

        Tag saved = tagRepository.save(tag);

        TagResponse response = new TagResponse(saved);

        return new BasicMessageResponse<>(200, "Cập nhật thẻ phân loại thành công!", response);
    }

    @Override
    public BasicMessageResponse<TagResponse> getById(int id) {
        TagResponse response = tagRepository.getById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantCategory.GENERAL_ERROR, ConstantCategory.TAG_DOES_NOT_EXIST));

        return new BasicMessageResponse<>(200, "Tìm thẻ phân loại thành công!", response);
    }

    @Override
    public BasicMessageResponse<Integer> delete(int id) {
        int tagId = tagValidation.checkExistsById(id);

        tagRepository.delete(tagId);
        return new BasicMessageResponse<>(200, "Xoá thẻ phân loại thành công!", tagId);
    }

}
