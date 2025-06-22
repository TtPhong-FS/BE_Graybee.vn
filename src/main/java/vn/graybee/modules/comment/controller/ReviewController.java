package vn.graybee.modules.comment.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.graybee.common.dto.BasicMessageResponse;
import vn.graybee.common.utils.MessageBuilder;
import vn.graybee.modules.account.security.UserDetail;
import vn.graybee.modules.comment.dto.request.ReviewRequest;
import vn.graybee.modules.comment.dto.response.CommentRating;
import vn.graybee.modules.comment.dto.response.ReviewCommentDto;
import vn.graybee.modules.comment.dto.response.ReviewIdProductId;
import vn.graybee.modules.comment.service.ReviewService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/account/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{productSlug}")
    public ResponseEntity<BasicMessageResponse<ReviewCommentDto>> reviewProduct(
            @RequestBody ReviewRequest request,
            @PathVariable String productSlug,
            @AuthenticationPrincipal UserDetail userDetail
    ) {
        Long accountId = userDetail.user().getId();
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        reviewService.reviewProduct(accountId, productSlug, request),
                        "Đánh giá sản phẩm thành công"
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<ReviewCommentDto>> editReviewProduct(
            @RequestBody ReviewRequest request,
            @PathVariable("id") int id,
            @AuthenticationPrincipal UserDetail userDetail
    ) {

        return ResponseEntity.ok(
                MessageBuilder.ok(
                        reviewService.editReviewProduct(id, request),
                        "Cập nhật đánh giá sản phẩm thành công"
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BasicMessageResponse<ReviewIdProductId>> deleteReviewProduct(
            @PathVariable int id,
            @AuthenticationPrincipal UserDetail userDetail
    ) {

        return ResponseEntity.ok(
                MessageBuilder.ok(
                        reviewService.deleteReviewProduct(id),
                        "Xoá đánh giá thành công"
                )
        );
    }

    @GetMapping("/for-update/{id}")
    public ResponseEntity<BasicMessageResponse<CommentRating>> getReviewCommentForUpdate(
            @PathVariable int id,
            @AuthenticationPrincipal UserDetail userDetail
    ) {
        Long accountId = userDetail.user().getId();
        return ResponseEntity.ok(
                MessageBuilder.ok(
                        reviewService.getReviewCommentForUpdate(id, accountId),
                        null
                )
        );
    }

}
