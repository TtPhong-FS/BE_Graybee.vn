package vn.graybee.services.categories;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.category.TagProjection;

import java.util.List;

public interface TagServices {

    BasicMessageResponse<List<TagProjection>> fetchAll();

}
