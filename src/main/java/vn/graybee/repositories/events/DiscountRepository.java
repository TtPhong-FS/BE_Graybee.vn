package vn.graybee.repositories.events;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.graybee.models.events.Discount;

import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {

    @Query("Select d.id from Discount d where d.code = :code")
    Optional<Integer> findByCode(@Param("code") String code);

}
