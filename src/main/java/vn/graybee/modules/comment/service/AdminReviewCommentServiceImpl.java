package vn.graybee.modules.comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.constants.ConstantGeneral;
import vn.graybee.common.constants.ConstantProduct;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.exception.BusinessCustomException;
import vn.graybee.modules.comment.model.ReviewComment;
import vn.graybee.modules.comment.repository.ReviewCommentRepository;

import java.util.List;

@Service
public class AdminReviewCommentServiceImpl implements AdminReviewCommentService {

    private final ReviewCommentRepository reviewCommentRepository;

    public AdminReviewCommentServiceImpl(ReviewCommentRepository reviewCommentRepository) {
        this.reviewCommentRepository = reviewCommentRepository;
    }

    @Override
    public BasicMessageResponse<List<ReviewComment>> fetchAll() {
        List<ReviewComment> reviewComments = reviewCommentRepository.findAll();
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
