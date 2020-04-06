package com.cih.userregistration.repository;

import com.cih.userregistration.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u FROM User u WHERE (:firstName is null or u.firstName = :firstName) " +
            "and (:lastName is null or u.lastName = :lastName) " +
            "and (:address is null or u.address = :address) " +
            "and (:email is null or u.email = :email) " +
            "and (:phoneNumber is null or u.phoneNumber = :phoneNumber)" )
    Page<User> findUserByFirstNameAndLastNameAndAddressAndEmailAndPhoneNumber(@Param("firstName") String firstName,
                                                                              @Param("lastName") String lastName,
                                                                              @Param("address") String address,
                                                                              @Param("email") String email,
                                                                              @Param("phoneNumber") String phoneNumber,
                                                                              Pageable pageable);

    List<User> findByLastName(String lastName);
}
