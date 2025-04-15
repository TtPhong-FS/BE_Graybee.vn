package vn.graybee.serviceImps.categories;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.constants.ConstantTag;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.directories.Tag;
import vn.graybee.repositories.categories.TagRepository;
import vn.graybee.requests.directories.TagRequest;
import vn.graybee.services.categories.TagServices;
import vn.graybee.utils.TextUtils;

import java.util.List;

@Service
public class TagServiceImpl implements TagServices {

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public BasicMessageResponse<List<Tag>> findAll() {
        List<Tag> tags = tagRepository.findAll();

        String message = tags.isEmpty() ? ConstantGeneral.empty_list : ConstantTag.success_fetch_tags;

        return new BasicMessageResponse<>(200, message, tags);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Tag> create(TagRequest request) {

        if (tagRepository.validateName(request.getName()).isPresent()) {
            throw new BusinessCustomException(ConstantTag.name, ConstantTag.name_exists);
        }

        Tag tag = new Tag();
        tag.setName(TextUtils.capitalize(request.getName()));
        tag = tagRepository.save(tag);

        return new BasicMessageResponse<>(201, ConstantTag.success_create, tag);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Tag> update(int id, TagRequest request) {

        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantTag.does_not_exists));

        if (!tag.getName().equals(request.getName()) && tagRepository.existsByNameAndNotId(request.getName(), tag.getId())) {
            throw new BusinessCustomException(ConstantTag.name, ConstantTag.name_exists);
        }

        tag.setName(request.getName());

        tag = tagRepository.save(tag);

        return new BasicMessageResponse<>(200, ConstantTag.success_update, tag);
    }

    @Override
    public BasicMessageResponse<Tag> findById(int id) {
        Tag response = tagRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantTag.does_not_exists));

        return new BasicMessageResponse<>(200, ConstantTag.success_find_by_id, response);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Integer> delete(int id) {
        int tagId = tagRepository.checkExistById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantTag.does_not_exists));

        tagRepository.delete(tagId);
        return new BasicMessageResponse<>(200, ConstantTag.success_delete, tagId);
    }

}
