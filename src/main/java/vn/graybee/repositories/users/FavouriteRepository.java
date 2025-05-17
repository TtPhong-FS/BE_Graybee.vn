package vn.graybee.repositories.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.users.Favourite;
import vn.graybee.response.favourites.ProductFavourite;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Integer> {

    @Query("Select new vn.graybee.response.favourites.ProductFavourite(p.id, p.price, p.finalPrice, p.name, p.thumbnail) from Favourite f join Product p on f.productId = p.id where f.userUid = :userUid")
    List<ProductFavourite> getProductFavouritesByUserUId(@Param("userUid") Integer userUid);

    @Transactional
    @Modifying
    @Query("Delete from Favourite f where f.productId = :productId and f.userUid = :userUid")
    void deleteByProductIdExistsAndUserUid(@Param("productId") long productId, @Param("userUid") int userUid);

}
