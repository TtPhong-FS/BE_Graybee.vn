package vn.graybee.modules.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.modules.cart.model.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findAllByCartId(Long cartId);

    @Query("Select c from CartItem c where c.id in :ids")
    List<CartItem> findByIds(List<Long> ids);

    @Query("Select c from CartItem c where c.id in :ids and c.cartId = :cartId")
    List<CartItem> findByIdsAndCartId(@Param("ids") List<Long> ids, @Param("cartId") Long cartId);

    @Query("Select ci from CartItem ci where ci.cartId = :cartId and ci.productId = :productId ")
    Optional<CartItem> findByCartIdAndProductId(@Param("cartId") long cartId, @Param("productId") long productId);

    @Query("SELECT COALESCE(SUM(c.totalAmount), 0) FROM CartItem c WHERE c.cartId = :cartId")
    Double sumTotalByCartId(@Param("cartId") long cartId);

    @Query("SELECT c.id FROM CartItem c WHERE c.id = :id")
    Optional<Long> checkExistsById(@Param("id") long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.cartId = :cartId")
    void deleteAllByCartId(@Param("cartId") Long cartId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.id = :id")
    void deleteById(@Param("id") long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.id in :ids")
    void deleteByIds(@Param("ids") List<Long> ids);

    @Query("SELECT c FROM CartItem c WHERE c.productId = :productId")
    Optional<CartItem> findByProductId(@Param("productId") Long productId);

    Optional<CartItem> findByProductIdAndCartId(@Param("productId") Long productId, @Param("cartId") Long cartId);

    @Query("SELECT c.id FROM CartItem c WHERE c.cartId = :cartId AND c.id = :id")
    Optional<Long> findByIdAndCartId(@Param("cartId") Long cartId, @Param("id") Long id);

    @Query("SELECT c.totalAmount FROM CartItem c WHERE c.cartId = :cartId")
    List<Double> findAllTotalByCartId(Long cartId);

    @Query("Select exists (Select 1 from CartItem ci where ci.id = :id)")
    boolean existsById(@Param("id") Long id);

}
