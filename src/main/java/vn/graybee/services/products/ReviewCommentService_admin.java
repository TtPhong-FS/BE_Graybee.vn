package vn.graybee.services.products;

import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.admin.products.ReviewCommentProjection;

import java.util.List;

public interface ReviewCommentService_admin {

    BasicMessageResponse<List<ReviewCommentProjection>> fetchAll();

    BasicMessageResponse<Integer> delete(int id);

    BasicMessageResponse<String> getCommentById(int id);


}
