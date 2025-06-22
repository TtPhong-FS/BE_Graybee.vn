package vn.graybee.modules.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.modules.comment.dto.response.CommentRating;
import vn.graybee.modules.comment.dto.response.ReviewCommentDto;
import vn.graybee.modules.comment.model.ReviewComment;

import java.util.List;
import java.util.Optional;

public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Integer> {

    @Transactional
    @Modifying
    @Query("delete from ReviewComment rc where rc.id = :id ")
    void deleteById(@Param("id") int id);

    @Query("Select rc.comment from ReviewComment rc where rc.id = :id ")
    String getCommentById(@Param("id") int id);


    @Query("Select new vn.graybee.modules.comment.dto.response.ReviewCommentDto(rc.id, rc.productId, a.uid, p.fullName, rc.rating, rc.comment, rc.publishedAt) from ReviewComment rc JOIN Profile p on rc.accountId = p.accountId JOIN Account a on a.id = rc.accountId")
    List<ReviewCommentDto> getReviewsByProductId(@Param("productId") long productId);

    @Query("""
            Select new vn.graybee.modules.comment.dto.response.ReviewCommentDto(rc.id, rc.productId, a.uid, p.fullName, rc.rating, rc.comment, rc.publishedAt) 
            from ReviewComment rc
            JOIN Profile p on rc.accountId = p.accountId
            JOIN Account a on a.id = rc.accountId
            where rc.productId = :productId
            """)
    List<ReviewCommentDto> findReviewCommentDtosByProductId(long productId);

    @Query("""
            Select new vn.graybee.modules.comment.dto.response.CommentRating(rc.comment, rc.rating) 
            from ReviewComment rc
            where rc.id = :id and rc.accountId = :accountId
            """)
    Optional<CommentRating> findCommentRatingById(int id, long accountId);

}
