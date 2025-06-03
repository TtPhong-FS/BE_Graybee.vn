package vn.graybee.modules.comment.service;

import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.modules.comment.model.ReviewComment;

import java.util.List;

public interface AdminReviewCommentService {

    BasicMessageResponse<List<ReviewComment>> fetchAll();

    BasicMessageResponse<Integer> delete(int id);

    BasicMessageResponse<String> getCommentById(int id);


}
