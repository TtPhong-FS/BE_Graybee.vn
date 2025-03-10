package vn.graybee.serviceImps.categories;

import org.springframework.stereotype.Service;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.category.TagProjection;
import vn.graybee.repositories.categories.TagRepository;
import vn.graybee.services.categories.TagServices;

import java.util.List;

@Service
public class TagServiceImpl implements TagServices {

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public BasicMessageResponse<List<TagProjection>> fetchAll() {
        List<TagProjection> tags = tagRepository.fetchAll();
        
        return new BasicMessageResponse<>(200, "Lấy danh sách thẻ phân loại thành công!", tags);
    }

}
