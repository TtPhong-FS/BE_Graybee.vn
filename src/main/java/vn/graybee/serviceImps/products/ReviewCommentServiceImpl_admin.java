package vn.graybee.serviceImps.products;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.constants.ConstantGeneral;
import vn.graybee.constants.ConstantProduct;
import vn.graybee.exceptions.BusinessCustomException;
import vn.graybee.messages.BasicMessageResponse;
import vn.graybee.projections.admin.products.ReviewCommentProjection;
import vn.graybee.repositories.products.ReviewCommentRepository;
import vn.graybee.services.products.ReviewCommentService_admin;

import java.util.List;

@Service
public class ReviewCommentServiceImpl_admin implements ReviewCommentService_admin {

    private final ReviewCommentRepository reviewCommentRepository;

    public ReviewCommentServiceImpl_admin(ReviewCommentRepository reviewCommentRepository) {
        this.reviewCommentRepository = reviewCommentRepository;
    }

    @Override
    public BasicMessageResponse<List<ReviewCommentProjection>> fetchAll() {
        List<ReviewCommentProjection> reviewComments = reviewCommentRepository.fetchAll();
        if (reviewComments.isEmpty()) {
            return new BasicMessageResponse<>(200, ConstantGeneral.empty_list, reviewComments);
        }

        return new BasicMessageResponse<>(200, ConstantGeneral.success_fetch_all, reviewComments);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public BasicMessageResponse<Integer> delete(int id) {
        int foundId = reviewCommentRepository.findById(id)
                .orElseThrow(() -> new BusinessCustomException(ConstantGeneral.general, ConstantGeneral.record_not_exists));

        reviewCommentRepository.deleteById(foundId);

        return new BasicMessageResponse<>(200, ConstantGeneral.success_delete_by_id, foundId);
    }

    @Override
    public BasicMessageResponse<String> getCommentById(int id) {
        String comment = reviewCommentRepository.getCommentById(id);
        if (comment.isEmpty()) {
            return new BasicMessageResponse<>(200, ConstantProduct.no_comment, comment);
        }
        return new BasicMessageResponse<>(200, ConstantProduct.success_get_comment, comment);
    }

}
