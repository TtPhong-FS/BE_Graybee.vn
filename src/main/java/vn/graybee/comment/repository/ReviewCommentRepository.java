package vn.graybee.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.comment.model.ReviewComment;
import vn.graybee.response.publics.products.ReviewCommentDto;

import java.util.List;
import java.util.Optional;

public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Integer> {

    @Transactional
    @Modifying
    @Query("delete from ReviewComment rc where rc.id = :id ")
    void deleteById(@Param("id") int id);

    @Query("Select rc.comment from ReviewComment rc where rc.id = :id ")
    String getCommentById(@Param("id") int id);

    @Query("Select rc.id from ReviewComment rc where rc.id = :id ")
    Optional<Integer> findById(@Param("id") int id);

    @Query("Select new vn.graybee.response.publics.products.ReviewCommentDto(u.fullName, rc.rating, rc.comment, rc.publishedAt) from ReviewComment rc join User u on rc.userId = u.id")
    List<ReviewCommentDto> getReviewsByProductId(@Param("productId") long productId);

}
