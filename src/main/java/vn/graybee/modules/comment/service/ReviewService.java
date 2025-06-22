package vn.graybee.modules.comment.service;

import vn.graybee.modules.comment.dto.request.ReviewRequest;
import vn.graybee.modules.comment.dto.response.CommentRating;
import vn.graybee.modules.comment.dto.response.ReviewCommentDto;
import vn.graybee.modules.comment.dto.response.ReviewIdProductId;

public interface ReviewService {

    ReviewCommentDto reviewProduct(long accountId, String productSlug, ReviewRequest request);

    ReviewCommentDto editReviewProduct(int id, ReviewRequest request);

    ReviewIdProductId deleteReviewProduct(int id);

    CommentRating getReviewCommentForUpdate(int id, long accountId);

}
