package vn.graybee.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.account.model.Address;
import vn.graybee.response.users.AddressExistingDto;
import vn.graybee.response.users.PersonalAddressDto;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Transactional
    @Modifying
    @Query("Delete from Address a where a.id = :id and a.userUid = :userUid")
    void deleteByIdAndUserUid(@Param("id") int id, @Param("userUid") String userUid);

    @Transactional
    @Modifying
    @Query("UPDATE Address a SET a.isDefault = false WHERE a.userUid = :userUid")
    void updateAllDefaultToFalseByUserUid(@Param("userUid") String userUid);

    @Transactional
    @Modifying
    @Query(value = """
                UPDATE address
                SET is_default = CASE
                    WHEN id = :id THEN true
                    ELSE false
                END
                WHERE user_uid = :userUid
            """, nativeQuery = true)
    void setOnlyOneDefaultAddress(@Param("id") int id, @Param("userUid") String userUid);

    @Query("Select new vn.graybee.response.users.PersonalAddressDto(a.id, a.phoneNumber, a.fullName, a.city, a.district, a.commune, a.streetAddress, a.isDefault) from Address a where a.userUid = :userUid")
    List<PersonalAddressDto> getAddressesByUserUid(@Param("userUid") String userUid);

    @Query("Select new vn.graybee.response.users.AddressExistingDto(a.id, a.phoneNumber, a.fullName, a.city, a.district, a.commune, a.streetAddress) from Address a where (:userUid IS NULL OR a.userUid = :userUid) and (:sessionId IS NULL OR a.sessionId = :sessionId)")
    List<AddressExistingDto> getAddressExistingByUserUidOrSessionId(@Param("userUid") String userUid, @Param("sessionId") String sessionId);

    @Query("Select exists (Select 1 from Address a where a.id = :id)")
    Boolean checkExistsById(@Param("id") int id);

    @Query("Select a from Address a where a.id = :id and a.userUid = :userUid")
    Optional<Address> findByIdAndUserUid(@Param("id") int id, @Param("userUid") String userUid);

    @Query("Select a from Address a where a.id = :id and a.sessionId = :sessionId")
    Optional<Address> findByIdAndSessionId(@Param("id") int id, @Param("sessionId") String sessionId);

}
