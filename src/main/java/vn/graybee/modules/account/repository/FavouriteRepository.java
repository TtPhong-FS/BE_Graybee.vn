package vn.graybee.modules.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.modules.account.dto.response.FavoriteProductResponse;
import vn.graybee.modules.account.model.Favorite;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favorite, Integer> {


    @Query("Select f.productId from Favorite f where f.accountId = :accountId")
    List<Long> findProductIdsByAccountId(@Param("accountId") Long accountId);

    @Transactional
    @Modifying
    @Query("Delete from Favorite f where f.accountId = :accountId and f.productId = :productId")
    void deleteByAccountIdAndProductId(@Param("accountId") Long accountId, @Param("productId") Long productId);

    @Query("Select new vn.graybee.modules.account.dto.response.FavoriteProductResponse(p.id, p.slug, p.price, p.finalPrice, p.name, p.thumbnail) from Favorite f join Product p on f.productId = p.id where f.accountId = :accountId")
    List<FavoriteProductResponse> findAllFavoriteProductByAccountId(Long accountId);

}
