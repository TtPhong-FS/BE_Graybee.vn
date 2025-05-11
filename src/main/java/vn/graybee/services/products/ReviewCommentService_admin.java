package vn.graybee.services.products;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.models.products.ReviewComment;

import java.util.List;

public interface ReviewCommentService_admin {

    BasicMessageResponse<List<ReviewComment>> fetchAll();

    BasicMessageResponse<Integer> delete(int id);

    BasicMessageResponse<String> getCommentById(int id);


}
