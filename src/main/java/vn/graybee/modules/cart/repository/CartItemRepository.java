package vn.graybee.modules.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.modules.cart.model.CartItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findAllByCartId(Integer cartId);

    @Query("Select c from CartItem c where c.id in :ids")
    List<CartItem> findByIds(List<Integer> ids);

    @Query("Select c from CartItem c where c.id in :ids and c.cartId = :cartId")
    List<CartItem> findByIdsAndCartId(@Param("ids") List<Integer> ids, @Param("cartId") Integer cartId);

    @Query("Select ci from CartItem ci where ci.cartId = :cartId and ci.productId = :productId ")
    Optional<CartItem> findByCartIdAndProductId(@Param("cartId") int cartId, @Param("productId") long productId);

    @Query("SELECT COALESCE(SUM(c.total), 0) FROM CartItem c WHERE c.cartId = :cartId")
    BigDecimal sumTotalByCartId(@Param("cartId") int cartId);

    @Query("SELECT c.id FROM CartItem c WHERE c.id = :id")
    Optional<Integer> checkExistsById(@Param("id") int id);

    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.cartId = :cartId")
    void deleteAllByCartId(@Param("cartId") Integer cartId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.id = :id")
    void deleteById(@Param("id") int id);

    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.id in :ids")
    void deleteByIds(@Param("ids") List<Integer> ids);

    @Query("SELECT c FROM CartItem c WHERE c.productId = :productId")
    Optional<CartItem> findByProductId(@Param("productId") Long productId);

    Optional<CartItem> findByProductIdAndCartId(@Param("productId") Long productId, @Param("cartId") Integer cartId);

    @Query("SELECT c.id FROM CartItem c WHERE c.cartId = :cartId AND c.id = :id")
    Optional<Integer> findByIdAndCartId(@Param("cartId") Integer cartId, @Param("id") Integer id);

    @Query("SELECT c.total FROM CartItem c WHERE c.cartId = :cartId")
    List<BigDecimal> findAllTotalByCartId(Integer cartId);

}
