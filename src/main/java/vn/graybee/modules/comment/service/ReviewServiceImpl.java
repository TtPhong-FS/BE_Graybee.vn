package vn.graybee.modules.comment.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.common.Constants;
import vn.graybee.common.exception.CustomNotFoundException;
import vn.graybee.modules.account.dto.response.ProfileResponse;
import vn.graybee.modules.account.service.AccountService;
import vn.graybee.modules.account.service.ProfileService;
import vn.graybee.modules.comment.dto.request.ReviewRequest;
import vn.graybee.modules.comment.dto.response.CommentRating;
import vn.graybee.modules.comment.dto.response.ReviewCommentDto;
import vn.graybee.modules.comment.dto.response.ReviewIdProductId;
import vn.graybee.modules.comment.model.ReviewComment;
import vn.graybee.modules.comment.repository.ReviewCommentRepository;
import vn.graybee.modules.product.service.ProductService;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewCommentRepository reviewCommentRepository;

    private final ProductService productService;

    private final ProfileService profileService;

    private final AccountService accountService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ReviewCommentDto reviewProduct(long accountId, String productSlug, ReviewRequest request) {

        long productId = productService.getIdBySlug(productSlug);
        ProfileResponse profile = profileService.findByAccountId(accountId);
        String uid = accountService.getUidById(accountId);
        ReviewComment reviewComment = new ReviewComment();
        reviewComment.setProductId(productId);
        reviewComment.setAccountId(accountId);
        reviewComment.setComment(request.getComment());
        reviewComment.setRating(request.getRating());
        reviewComment.setPublishedAt(LocalDateTime.now());

        reviewComment = reviewCommentRepository.save(reviewComment);


        return new ReviewCommentDto(reviewComment.getId(), productId, uid, profile.getFullName(), reviewComment.getRating(), reviewComment.getComment(), reviewComment.getPublishedAt());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ReviewCommentDto editReviewProduct(int id, ReviewRequest request) {

        ReviewComment reviewComment = reviewCommentRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, "Bạn chưa đánh giá sản phẩm này"));

        ProfileResponse profile = profileService.findByAccountId(reviewComment.getAccountId());
        String uid = accountService.getUidById(reviewComment.getAccountId());

        reviewComment.setComment(request.getComment());
        reviewComment.setRating(request.getRating());
        reviewComment.setPublishedAt(LocalDateTime.now());

        reviewComment = reviewCommentRepository.save(reviewComment);

        return new ReviewCommentDto(reviewComment.getId(), reviewComment.getProductId(), uid, profile.getFullName(), reviewComment.getRating(), reviewComment.getComment(), reviewComment.getPublishedAt());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ReviewIdProductId deleteReviewProduct(int id) {
        ReviewComment reviewComment = reviewCommentRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, "Bạn chưa đánh giá sản phẩm này"));

        reviewCommentRepository.delete(reviewComment);

        return new ReviewIdProductId(reviewComment.getId(), reviewComment.getProductId());
    }

    @Override
    public CommentRating getReviewCommentForUpdate(int id, long accountId) {
        return reviewCommentRepository.findCommentRatingById(id, accountId).orElseThrow(() -> new CustomNotFoundException(Constants.Common.global, "Bạn chưa đánh giá sản phẩm này"));
    }

}
