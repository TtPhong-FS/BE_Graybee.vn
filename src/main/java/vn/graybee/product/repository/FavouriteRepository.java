package vn.graybee.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.product.dto.response.FavoriteProductResponse;
import vn.graybee.product.model.Favourite;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Integer> {

    @Query("Select new vn.graybee.product.dto.response.FavoriteProductResponse(p.id, p.price, p.finalPrice, p.name, p.thumbnail) from Favourite f join Product p on f.productId = p.id where f.accountId = :accountId")
    List<FavoriteProductResponse> findByUserUid(@Param("accountId") String accountId);

    @Transactional
    @Modifying
    @Query("Delete from Favourite f where f.productId = :productId and f.userUid = :userUid")
    void deleteByProductIdExistsAndUserUid(@Param("productId") long productId, @Param("userUid") String userUid);

}
