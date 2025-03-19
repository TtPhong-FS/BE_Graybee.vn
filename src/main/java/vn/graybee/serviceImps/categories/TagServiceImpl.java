package vn.graybee.serviceImps.categories;

import org.springframework.stereotype.Service;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.categories.Tag;
import vn.graybee.projections.category.TagProjection;
import vn.graybee.repositories.categories.TagRepository;
import vn.graybee.requests.categories.TagCreateRequest;
import vn.graybee.response.categories.TagResponse;
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

        TagResponse response = new TagResponse(savedTag.getId(), savedTag.getTagName());

        return new BasicMessageResponse<>(201, "Tạo thẻ phân loại thành công!", response);
    }

}
