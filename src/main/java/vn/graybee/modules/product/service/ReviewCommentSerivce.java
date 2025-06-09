package vn.graybee.modules.product.service;

import vn.graybee.modules.comment.dto.response.ReviewCommentDto;

import java.util.List;

public interface ReviewCommentSerivce {

    List<ReviewCommentDto> getReviewCommentDtosByProductId(long productId);

}
