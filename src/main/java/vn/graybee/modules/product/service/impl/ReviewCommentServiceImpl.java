package vn.graybee.modules.product.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vn.graybee.modules.comment.dto.response.ReviewCommentDto;
import vn.graybee.modules.comment.repository.ReviewCommentRepository;
import vn.graybee.modules.product.service.ReviewCommentSerivce;

import java.util.List;

@AllArgsConstructor
@Service
public class ReviewCommentServiceImpl implements ReviewCommentSerivce {

    private final ReviewCommentRepository reviewCommentRepository;


    @Override
    public List<ReviewCommentDto> getReviewCommentDtosByProductId(long productId) {
        return reviewCommentRepository.findReviewCommentDtosByProductId(productId);
    }

}
