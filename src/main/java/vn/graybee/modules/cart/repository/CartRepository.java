package vn.graybee.modules.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.modules.cart.model.Cart;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query("Select c from Cart c where c.accountId = :accountId or c.sessionId = :sessionId")
    Optional<Cart> findByAccountIdAndSessionId(@Param("accountId") Long accountId, @Param("sessionId") String sessionId);

    @Query("Select c.id from Cart c where c.id = :id ")
    Optional<Long> checkExistsById(@Param("id") long id);

    @Transactional
    @Modifying
    @Query("UPDATE Cart c SET c.totalAmount = :totalAmount WHERE c.id = :id")
    void updateCartTotal(@Param("id") long id, @Param("totalAmount") double totalAmount);

    @Query("SELECT c.id FROM Cart c WHERE c.accountId = :accountId OR c.sessionId = :sessionId")
    Optional<Long> findIdByAccountIdOrSessionId(@Param("accountId") Long accountId, @Param("sessionId") String sessionId);

    @Query("Select c from Cart c where c.sessionId = :sessionId")
    Optional<Cart> findBySessionId(String sessionId);

}
