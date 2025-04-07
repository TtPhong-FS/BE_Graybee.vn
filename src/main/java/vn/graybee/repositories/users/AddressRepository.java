package vn.graybee.repositories.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import vn.graybee.models.users.Address;
import vn.graybee.response.users.PersonalAddressDto;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Transactional
    @Modifying
    @Query("Delete from Address a where a.id = :id and a.userUid = :userUid")
    void deleteByIdAndUserUid(@Param("id") int id, @Param("userUid") int userUid);

    @Transactional
    @Modifying
    @Query("UPDATE Address a SET a.isDefault = false WHERE a.userUid = :userUid")
    void updateAllDefaultToFalseByUserUid(@Param("userUid") int userUid);

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
    void setOnlyOneDefaultAddress(@Param("id") int id, @Param("userUid") int userUid);

    @Query("Select new vn.graybee.response.users.PersonalAddressDto(a.id, a.phoneNumber, a.fullname, a.city, a.district, a.commune, a.streetAddress, a.isDefault) from Address a where a.userUid = :userUid")
    List<PersonalAddressDto> getAddressesByUserUid(@Param("userUid") int userUid);

    @Query("Select exists (Select 1 from Address a where a.id = :id)")
    Boolean checkExistsById(@Param("id") int id);

    @Query("Select a from Address a where a.id = :id and a.userUid = :userUid")
    Optional<Address> findByIdAndUserUid(@Param("id") int id, @Param("userUid") int userUid);

}
