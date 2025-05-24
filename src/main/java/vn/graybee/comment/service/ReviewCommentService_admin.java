package vn.graybee.comment.service;

import vn.graybee.comment.model.ReviewComment;
import vn.graybee.common.dto.BasicMessageResponse;

import java.util.List;

public interface ReviewCommentService_admin {

    BasicMessageResponse<List<ReviewComment>> fetchAll();

    BasicMessageResponse<Integer> delete(int id);

    BasicMessageResponse<String> getCommentById(int id);


}
