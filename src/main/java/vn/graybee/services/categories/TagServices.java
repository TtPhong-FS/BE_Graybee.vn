package vn.graybee.services.categories;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.admin.category.TagProjection;
import vn.graybee.requests.directories.TagRequest;
import vn.graybee.response.admin.directories.tag.TagResponse;

import java.util.List;

public interface TagServices {

    BasicMessageResponse<List<TagProjection>> fetchAll();

    BasicMessageResponse<TagResponse> create(TagRequest request);

    BasicMessageResponse<TagResponse> update(int id, TagRequest request);

    BasicMessageResponse<TagResponse> getById(int id);

    BasicMessageResponse<Integer> delete(int id);

}
