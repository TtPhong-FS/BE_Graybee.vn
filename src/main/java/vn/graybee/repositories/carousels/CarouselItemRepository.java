package vn.graybee.repositories.carousels;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.carousels.CarouselItem;
import vn.graybee.response.admin.carousels.CarouselItemResponse;
import vn.graybee.response.publics.products.ProductBasicResponse;

public interface CarouselItemRepository extends JpaRepository<CarouselItem, Integer> {

    @Query("""
                SELECT new vn.graybee.response.publics.products.ProductBasicResponse(
                    p.id, p.name, p.price, p.finalPrice, p.thumbnail
                )
                FROM CarouselItem ci
                JOIN CarouselGroup cg on cg.id = ci.carouselGroupId
                JOIN Product p ON ci.productId = p.id
                WHERE ci.active = true AND cg.active = true
                  AND cg.type = :type AND cg.categoryName = :category
            """)
    Page<ProductBasicResponse> getCarouselProducts(@Param("type") String type, @Param("category") String category, Pageable pageable);

    @Query("""
                SELECT new vn.graybee.response.admin.carousels.CarouselItemResponse(
                    ci.id, cg.type, p.id, p.name, p.price, p.finalPrice, p.thumbnail, ci.position, ci.active
                )
                FROM CarouselItem ci
                JOIN CarouselGroup cg ON ci.carouselGroupId = cg.id
                JOIN Product p ON ci.productId = p.id
                WHERE cg.type = :type
            """)
    Page<CarouselItemResponse> getCarouselForDashboard(@Param("type") String type, Pageable pageable);

    @Query("Select exists (Select 1 from CarouselItem ci where ci.id = :id)")
    boolean checkExistsById(@Param("id") int id);

    @Transactional
    @Modifying
    @Query("Update CarouselItem ci set ci.active = :active where ci.id = :id")
    void updateActiveById(@Param("id") int id, @Param("active") boolean active);

    @Transactional
    @Modifying
    @Query("Update CarouselItem ci set ci.position = :position where ci.id = :id")
    void updatePositionById(@Param("id") int id, @Param("position") int position);

}
