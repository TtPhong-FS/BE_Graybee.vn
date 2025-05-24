package vn.graybee.taxonomy.tag.service;

import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.taxonomy.tag.dto.request.TagRequest;
import vn.graybee.taxonomy.tag.model.Tag;

import java.util.List;

public interface TagService {

    BasicMessageResponse<List<Tag>> findAll();

    BasicMessageResponse<Tag> create(TagRequest request);

    BasicMessageResponse<Tag> update(int id, TagRequest request);

    BasicMessageResponse<Tag> findById(int id);

    BasicMessageResponse<Integer> delete(int id);

    BasicMessageResponse<List<Integer>> deleteByIds(List<Integer> ids);

}
