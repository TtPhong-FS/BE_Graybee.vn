package vn.graybee.repositories.classifications;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.classifications.CategoryClassification;

import java.util.List;

public interface CategoryClassificationRepository extends JpaRepository<CategoryClassification, Integer> {


    List<CategoryClassification> findByCategoryId(@Param("categoryId") Integer categoryId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CategoryClassification c WHERE c.categoryId = :categoryId AND c.id IN :ids")
    void deleteByCategoryIdAndIds(@Param("categoryId") Integer categoryId, @Param("ids") List<Integer> ids);

}
