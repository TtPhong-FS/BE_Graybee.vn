package vn.graybee.repositories.carousels;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.carousels.CarouselGroup;

import java.util.List;
import java.util.Optional;

public interface CarouselGroupRepository extends JpaRepository<CarouselGroup, Integer> {

    @Query("Select exists (Select 1 from CarouselGroup cg where cg.name = :name)")
    boolean checkExistsByName(@Param("name") String name);

    @Query("Select exists (Select 1 from CarouselGroup cg where cg.name = :name and cg.id <> :id)")
    boolean checkExistsByNameAndNotId(@Param("name") String name, @Param("id") int id);

    @Query("Select exists (Select 1 from CarouselGroup cg where cg.id = :id)")
    boolean checkExistsById(@Param("id") Integer id);

    @Query("Select exists (Select 1 from CarouselGroup cg where cg.type = :type)")
    boolean checkExistsByType(@Param("type") String type);

    @Query("Select exists (Select 1 from CarouselGroup cg where cg.categoryName = :categoryName)")
    boolean checkExistsByCategoryName(@Param("categoryName") String categoryName);

    @Query("Select cg.type from CarouselGroup cg where cg.id = :id")
    Optional<String> getTypeById(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("Update CarouselGroup cg set cg.active = :active where cg.id = :id")
    void updateActiveById(@Param("id") int id, @Param("active") boolean active);

    @Query("Select cg.categoryName from CarouselGroup cg")
    List<String> fetchCategoriesExists();

}
