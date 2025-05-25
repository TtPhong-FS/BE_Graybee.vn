package vn.graybee.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.cart.model.Cart;

import java.math.BigDecimal;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Query("Select c from Cart c where c.accountId = :accountId")
    Optional<Cart> findByAccountId(@Param("accountId") Long accountId);

    @Query("Select c from Cart c where c.sessionId = :sessionId ")
    Optional<Cart> findBySessionId(@Param("sessionId") String sessionId);

    @Query("Select c.id from Cart c where c.userUid = :userUid")
    Optional<Integer> findIdByUserUid(@Param("userUid") String userUid);

    @Query("Select c.id from Cart c where c.sessionId = :sessionId ")
    Optional<Integer> findIdBySessionId(@Param("sessionId") String sessionId);

    @Query("Select c.id from Cart c where c.id = :id ")
    Optional<Integer> checkExistsById(@Param("id") int id);

    @Transactional
    @Modifying
    @Query("UPDATE Cart c SET c.totalAmount = :totalAmount WHERE c.id = :id")
    void updateCartTotal(@Param("id") int id, @Param("totalAmount") BigDecimal totalAmount);

    @Query("SELECT c.id FROM Cart c WHERE c.accountId = :accountId OR c.sessionId = :sessionId")
    Optional<Integer> findIdByAccountIdOrSessionId(@Param("accountId") Long accountId, @Param("sessionId") String sessionId);

}
