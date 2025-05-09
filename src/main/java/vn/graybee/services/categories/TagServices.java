package vn.graybee.services.categories;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.directories.Tag;
import vn.graybee.requests.directories.TagRequest;

import java.util.List;

public interface TagServices {

    BasicMessageResponse<List<Tag>> findAll();

    BasicMessageResponse<Tag> create(TagRequest request);

    BasicMessageResponse<Tag> update(int id, TagRequest request);

    BasicMessageResponse<Tag> findById(int id);

    BasicMessageResponse<Integer> delete(int id);

    BasicMessageResponse<List<Integer>> deleteByIds(List<Integer> ids);

}
