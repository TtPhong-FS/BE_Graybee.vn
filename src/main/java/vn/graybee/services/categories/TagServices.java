package vn.graybee.services.categories;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.category.TagProjection;
import vn.graybee.requests.categories.TagCreateRequest;
import vn.graybee.response.categories.TagResponse;

import java.util.List;

public interface TagServices {

    BasicMessageResponse<List<TagProjection>> fetchAll();

    BasicMessageResponse<TagResponse> create(TagCreateRequest request);

}
