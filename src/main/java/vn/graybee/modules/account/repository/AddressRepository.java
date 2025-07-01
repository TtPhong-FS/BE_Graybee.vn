package vn.graybee.modules.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.modules.account.dto.response.AddressResponse;
import vn.graybee.modules.account.model.Address;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Transactional
    @Modifying
    @Query("Delete from Address a where a.id = :id and a.customerId = :customerId")
    void deleteByIdAndCustomerId(@Param("id") long id, @Param("customerId") Long customerId);

    @Transactional
    @Modifying
    @Query("UPDATE Address a SET a.isDefault = false WHERE a.customerId = :customerId")
    void unsetDefaultAddressByUserUid(@Param("customerId") Long customerId);

    @Transactional
    @Modifying
    @Query(value = """
                UPDATE address
                SET is_default = CASE
                    WHEN id = :id THEN true
                    ELSE false
                END
                WHERE customer_id = :customerId
            """, nativeQuery = true)
    void setOnlyOneDefaultAddress(@Param("customerId") Long customerId, @Param("id") long id);

    @Query("Select new vn.graybee.modules.account.dto.response.AddressResponse(a) from Address a where a.customerId = :customerId")
    List<AddressResponse> findAllByCustomerId(@Param("customerId") Long customerId);

    @Query("Select exists (Select 1 from Address a where a.id = :id)")
    boolean checkExistsById(@Param("id") long id);

    @Query("Select a from Address a where a.id = :id and a.customerId = :customerId")
    Optional<Address> findByIdAndCustomerId(@Param("customerId") Long customerId, @Param("id") long id);

    @Query("Select new vn.graybee.modules.account.dto.response.AddressResponse(a) from Address a where a.id = :id and a.customerId = :customerId and a.isDefault = true")
    Optional<AddressResponse> findAddressDefaultByIdAndCustomerId(@Param("customerId") Long customerId, @Param("id") long id);

    @Query("Select a from Address a where a.customerId = :customerId and a.id = :addressId")
    Optional<Address> findAddressByIdAndCustomerId(Long customerId, Long addressId);

    @Query("""
            Select new vn.graybee.modules.account.dto.response.AddressResponse(a)
            from Address a where a.id = :addressId
            """)
    Optional<AddressResponse> findAddressResponseById(long addressId);

}
